package layoutclasses;

import classes.*;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class MainController {

    @FXML
    private BorderPane mainPane;
    /*********** Right Pane ***********/
    //Paycheck Info Tab
    @FXML
    private TitledPane PCI;
    @FXML
    private TextField wageField;
    @FXML
    private TextField hoursField;
    @FXML
    private Label label401;
    @FXML
    private Slider slider401;
    @FXML
    private Label labelTax;
    @FXML
    private Slider sliderTax;

    //Billing Info Tab
    @FXML
    private ListView<BillItem> billListView;
    @FXML
    private Label selectedBill;
    @FXML
    private Label selectedCharge;
    @FXML
    private Label selectedRemaining;
    @FXML
    private Label selectedInterest;
    @FXML
    private Button addBillButton;
    @FXML
    private Button deleteBillButton;
    @FXML
    private Button changeBIllButton;

    //Investment Info Tab
    @FXML
    private ListView<InvestmentItem> investmentListView;
    @FXML
    private Label totalInvestmentLabel;
    @FXML
    private Label totalNetInvestment;
    @FXML
    private Label selectedInvestment;
    @FXML
    private Label selectedSum;
    @FXML
    private Label selectedNet;
    @FXML
    private Button addInvButton;
    @FXML
    private Button changeInvButton;
    @FXML
    private Button deleteInvButton;

    //Calculated Pay
    private List<BankItem> bankItems;
    @FXML
    private Label checkingAccountLabel;
    @FXML
    private Label savingsAccountLabel;

    @FXML
    private Label biWeeklyPC;
    @FXML
    private Label biWeeklyPCNet;
    @FXML
    private Label monthlyPC;
    @FXML
    private Label monthlyNet;
    @FXML
    private Label netWorthLabel;
    @FXML
    private Button setValuesButton;

    /*********** Center Pane ***********/
    //Wealth summary
    @FXML
    private VBox pieChartVBox;
    @FXML
    private PieChart summaryPieChart;
    @FXML
    private Button summaryButton;
    @FXML
    private Button detailsButton;

    //Wealth Projection
    private LineChart<String, Number> predictionChart;

    /*********** Left Pane ***********/
    @FXML
    private ToggleGroup chartToggle;
    @FXML
    private RadioButton wealthRadio;
    @FXML
    private RadioButton billRadio;
    @FXML
    private RadioButton projectionsRadio;

    private DecimalFormat df;
    private Double netWorth;
    private MoneyCalculations MC;
    private Double rateTax;
    private Double rate401;
    private TimeSpan timeSpan;



    public void initialize() throws URISyntaxException {
        df = new DecimalFormat("#.##");
        Integer month = Calendar.getInstance().get(Calendar.MONTH);
        Integer day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        Integer year = Calendar.getInstance().get(Calendar.YEAR);
        timeSpan = new TimeSpan(day, month, year);
        // Load In Data
        BankItems.getInstance().loadBankItems();
        bankItems = BankItems.getInstance().getBankItems();

        InvestmentItems.getInstance().loadInvestmentItems();
        BillItems.getInstance().loadbillItems();


        // initializing paycheck info
        initializePaycheckInfo();

        // initializing billing info
        initializeBillingInfo();

        // initializing investment info
        initializeInvestmentInfo();

        MC = new MoneyCalculations(Double.parseDouble(wageField.getText()),
                Double.parseDouble(hoursField.getText()),
                bankItems.get(0).getBalance(),
                bankItems.get(1).getBalance());
        rateTax = sliderTax.getValue()/100;
        rate401 = slider401.getValue()/100;

        // initializing buttons on each tab
        initializeButtons();

        // calculate paychecks
        onSetValuesPressed();



    }

    /******** Loading in initial Data **********/
    private void initializePaycheckInfo() throws URISyntaxException {
        List<String> paycheckInfo = Utils.readData("../data/PaycheckInfo.txt");
        assert paycheckInfo != null;
        String[] paycheck = paycheckInfo.get(0).split(",");
        wageField.setText(paycheck[0]);
        hoursField.setText(paycheck[1]);
        slider401.setValue(Double.parseDouble(paycheck[2]));
        sliderTax.setValue(Double.parseDouble(paycheck[3]));
        label401.setText("401K Deposits " + slider401.getValue() +"%");
        labelTax.setText("Tax Percentage " + sliderTax.getValue() +"%");

        slider401.valueProperty().addListener((observable, oldValue, newValue) -> label401.setText("401K Deposit " +newValue.intValue()+"%"));
        sliderTax.valueProperty().addListener((observable, oldValue, newValue) -> labelTax.setText("Tax Percentage "+Double.valueOf(df.format(newValue.doubleValue()))+"%"));
    }
    private void initializeBillingInfo() {
        List<BillItem> bills = BillItems.getInstance().getBillItems();
        billListView.getItems().setAll(bills);
        billListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        billListView.getSelectionModel().selectFirst();

        billListView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue != null){
                BillItem item = billListView.getSelectionModel().getSelectedItem();
                selectedBill.setText("Bill: " + item.getName());
                selectedCharge.setText("Monthly Payment: $" + item.getAmountPerMonth());
                selectedRemaining.setText("Amount Remaining: $" + item.getAmountRemaining());
                selectedInterest.setText("Interest Rate: %" + item.getInterest());

            }
        }));
        initializeBillSummary();
        setBillButtonStyles(addBillButton);
        setBillButtonStyles(changeBIllButton);
        setBillButtonStyles(deleteBillButton);

    }
    private void initializeInvestmentInfo() {
        PCI.setExpanded(true);
        List<InvestmentItem> investments = InvestmentItems.getInstance().getInvestmentItems();
        investmentListView.getItems().setAll(investments);
        investmentListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        investmentListView.getSelectionModel().selectFirst();

        investmentListView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue)->{
            if(newValue != null){
                InvestmentItem item = investmentListView.getSelectionModel().getSelectedItem();
                selectedInvestment.setText("Investment: "+item.getName());
                selectedSum.setText("Sum: $" + item.getTotalAmount());
                selectedNet.setText("Net Sum: $" + item.getNetSum());
            }
        }));
        initializeInvestmentSummary();
        setInvestmentButtonStyles(addInvButton);
        setInvestmentButtonStyles(changeInvButton);
        setInvestmentButtonStyles(deleteInvButton);

        totalInvestmentLabel.setText("Investments: $" + InvestmentItems.getInstance().getTotalInvestments());
        totalNetInvestment.setText("Net Investments: $" + InvestmentItems.getInstance().getNetInvestments());
    }
    private void initializeButtons() {
        addBillButton.setMaxWidth(Double.MAX_VALUE);
        addBillButton.setStyle(Styles.BillButtonStyle());
        changeBIllButton.setMaxWidth(Double.MAX_VALUE);
        changeBIllButton.setStyle(Styles.BillButtonStyle());
        deleteBillButton.setMaxWidth(Double.MAX_VALUE);
        deleteBillButton.setStyle(Styles.BillButtonStyle());

        addInvButton.setMaxWidth(Double.MAX_VALUE);
        addInvButton.setStyle(Styles.InvestmentButtonStyle());
        changeInvButton.setMaxWidth(Double.MAX_VALUE);
        changeInvButton.setStyle(Styles.InvestmentButtonStyle());
        deleteInvButton.setMaxWidth(Double.MAX_VALUE);
        deleteInvButton.setStyle(Styles.InvestmentButtonStyle());

        summaryButton.setMaxWidth(Double.MAX_VALUE);
        summaryButton.pressedProperty().addListener((observable, wasPressed, pressed) ->{
            if(pressed && chartToggle.getSelectedToggle() == wealthRadio ){
                loadWealthChartData();
            }else if(pressed && chartToggle.getSelectedToggle() == billRadio){
                loadPaycheckChartData();
            }
        });
        detailsButton.setMaxWidth(Double.MAX_VALUE);
        detailsButton.pressedProperty().addListener(((observable, wasPressed, pressed) -> {
            if(pressed && chartToggle.getSelectedToggle() == wealthRadio){
                loadDetailedWealthPieChartData();
            }else if(pressed && chartToggle.getSelectedToggle() == billRadio){
                loadDetailedPaycheckPiechart();
            }
        }));

        setValuesButton.setMaxWidth(Double.MAX_VALUE);
        setValuesButton.pressedProperty().addListener((observable, wasPressed, pressed) -> {
            if(pressed){
                setValuesButton.setStyle(Styles.UpdateValueButtonPressed());
            }else{
                setValuesButton.setStyle(Styles.UpdateValueButtonStyle());
            }
        });

        chartToggle.selectedToggleProperty().addListener((observable, oldValue, newValue) -> setChart());


    }

    private void setInvestmentButtonStyles(Button InvestmentButton){
        InvestmentButton.pressedProperty().addListener((observable, wasPressed, pressed) -> {

            if (pressed) {
                InvestmentButton.setStyle(Styles.InvestmentButtonPressed());
                InvestmentButton.setTextFill(Color.WHITE);
            } else {
                InvestmentButton.setStyle(Styles.InvestmentButtonStyle());
                InvestmentButton.setTextFill(Color.BLACK);
            }
        });
    }
    private void setBillButtonStyles(Button BillButton) {
        BillButton.pressedProperty().addListener((observable, wasPressed, pressed) -> {

            if (pressed) {
                BillButton.setStyle(Styles.BillButtonPressed());
                BillButton.setTextFill(Color.WHITE);
            } else {
                BillButton.setStyle(Styles.BillButtonStyle());
                BillButton.setTextFill(Color.BLACK);
            }
        });
    }

    /******** Working Functions ********/
    private void initializeBillSummary(){
        selectedBill.setText("BIll: " + billListView.getSelectionModel().getSelectedItem().getName());
        selectedCharge.setText("Monthly Payment: $" + billListView.getSelectionModel().getSelectedItem().getAmountPerMonth());
        selectedRemaining.setText("Amount Remaining: " + billListView.getSelectionModel().getSelectedItem().getAmountRemaining());
        selectedInterest.setText("Intereset Rate: %" + billListView.getSelectionModel().getSelectedItem().getInterest());

    }
    private void initializeInvestmentSummary(){
        selectedInvestment.setText("Investment: "+investmentListView.getSelectionModel().getSelectedItem().getName());
        selectedSum.setText("Sum: $" + investmentListView.getSelectionModel().getSelectedItem().getTotalAmount());
        selectedNet.setText("Net Sum: $" + investmentListView.getSelectionModel().getSelectedItem().getNetSum());
    }
    private void updatePaycheckFile(List<String> data) {
        DecimalFormat df = new DecimalFormat("#.##");
        data.add(wageField.getText() +","+
                hoursField.getText() +","+
                String.valueOf(Double.valueOf(df.format(slider401.getValue()))) +","+
                String.valueOf(Double.valueOf(df.format(sliderTax.getValue()))));
        Utils.writeData(System.getProperty("user.dir") + "/src/data/PaycheckInfo.txt", data);
    }
    private Double determinePayCheck(Double rate401, Double rateTax) {

        MC.setRate401(rate401);
        MC.setTaxPer(rateTax);
        MC.calculateNetPayCheck();

        biWeeklyPC.setText("Bi-Weekly:         $" + Double.valueOf(df.format(MC.getPaycheck())));
        biWeeklyPCNet.setText("Net Bi-Weekly:     $" + Double.valueOf(df.format(MC.getPaycheckNet())));
        monthlyPC.setText("Monthly:           $" + Double.valueOf(df.format(MC.getMonthlyPayCheck())));
        monthlyNet.setText("Net Monthly:       $" + Double.valueOf(df.format(MC.getMonthlyPayCheckNet())));

        return MC.getNetWorth();
    }

    /****** Chart Functions *******/
    private void loadWealthChartData(){
        Double totalInvestments = InvestmentItems.getInstance().getNetInvestments();
        Double totalBankSum = BankItems.getInstance().getAccountsSum();

        Double percentInvestments = Double.valueOf(df.format(totalInvestments/netWorth*100));
        Double percentBankItems = Double.valueOf(df.format(totalBankSum/netWorth*100));
        Double[] percentages = {percentInvestments, percentBankItems};
        Double[] absValues = {totalInvestments, totalBankSum};
        loadWealthPieChartData(percentages, absValues);
    }
    private void loadPaycheckChartData() {
//        Double rate401 = slider401.getValue() / 100.0;
//        Double rateTax = sliderTax.getValue() / 100.0;

        MC = new MoneyCalculations(Double.parseDouble(wageField.getText()),
                Double.parseDouble(hoursField.getText()),
                bankItems.get(0).getBalance(),
                bankItems.get(1).getBalance());
        MC.setRate401(rate401);
        MC.setTaxPer(rateTax);
        MC.calculateNetPayCheck();

        Double totalBillCost = BillItems.getInstance().getTotalMonthlyBill();
        Double investmentCost = InvestmentItems.getInstance().getTotalMonthlyAllocations();

        Double percentBills = Double.valueOf(df.format(totalBillCost / MC.getMonthlyPayCheck() * 100));
        Double percentInvestment = Double.valueOf(df.format(investmentCost / MC.getMonthlyPayCheck() * 100));
        Double percent401K = Double.valueOf(df.format(rate401 * 100));
        Double percentTax = Double.valueOf(df.format(rateTax * 100));
        Double remaining = Double.valueOf(df.format(MC.getMonthlyPayCheckNet()/MC.getMonthlyPayCheck()*100));

        Double[] percentages = {percentBills, percentInvestment,  percentTax, percent401K, remaining};

        Double totalTax = Double.valueOf(df.format((MC.getMonthlyPayCheck() - MC.getMonthlyPayCheck()*rate401)*rateTax));
        Double[] absValues = {Double.valueOf(df.format(totalBillCost)),
                investmentCost,totalTax,
                Double.valueOf(df.format(MC.getMonthlyPayCheck()*rate401)),
                Double.valueOf(df.format(MC.getMonthlyPayCheckNet()))};
        loadPaycheckPiechart(percentages, absValues);
    }

    // Paycheck chart data
    private void loadPaycheckPiechart(Double[] percentages, Double[] absValues){
        summaryPieChart.getData().clear();
        summaryPieChart.setTitle("Paycheck Summary");
        summaryPieChart.getData().add(new PieChart.Data("Bills: $" + absValues[0], percentages[0]));
        summaryPieChart.getData().add(new PieChart.Data("Investments: $" + absValues[1], percentages[1]));
        summaryPieChart.getData().add(new PieChart.Data("Tax: $" +absValues[2], percentages[2]));
        summaryPieChart.getData().add(new PieChart.Data("401K Contrib: $" + absValues[3], percentages[3]));
        summaryPieChart.getData().add(new PieChart.Data("Remaining: $"+ absValues[4], percentages[4]));
    }

    private void loadDetailedPaycheckPiechart(){
        summaryPieChart.getData().clear();
        summaryPieChart.setTitle("Paycheck Details");
        for(BillItem billItem : BillItems.getInstance().getBillItems()){
            summaryPieChart.getData().add(new PieChart.Data(billItem.getName() +": $" + Double.valueOf(df.format(billItem.getAmountPerMonth())),
                    billItem.getAmountPerMonth()/MC.getMonthlyPayCheck()*100));
        }
        for(InvestmentItem investmentItem : InvestmentItems.getInstance().getInvestmentItems()){
            summaryPieChart.getData().add(new PieChart.Data(investmentItem.getName() + ": $" + Double.valueOf(df.format(investmentItem.getAmountPerMonth())),
                    investmentItem.getAmountPerMonth()/MC.getMonthlyPayCheck()*100));
        }
        summaryPieChart.getData().add(new PieChart.Data("Tax: $" + Double.valueOf(df.format(rateTax*(MC.getMonthlyPayCheck() - MC.getMonthlyPayCheck()*rate401)))+"%",
                rateTax*100));
        summaryPieChart.getData().add(new PieChart.Data("401K Contrib: $" + Double.valueOf(df.format(rate401*MC.getMonthlyPayCheck())), rate401*100));
        summaryPieChart.getData().add(new PieChart.Data("Remaining: $" + Double.valueOf(df.format(MC.getMonthlyPayCheckNet())), MC.getMonthlyPayCheckNet()/MC.getMonthlyPayCheck()*100));


    }

    // Wealth chart data
    private void loadWealthPieChartData(Double[] percentages, Double[] absValues){
        summaryPieChart.getData().clear();
        summaryPieChart.setTitle("Wealth Summary");
        summaryPieChart.getData().add(new PieChart.Data("Investments: $" + absValues[0], percentages[0]));
        summaryPieChart.getData().add(new PieChart.Data("Bank Accounts: $" + absValues[1], percentages[1]));
    }

    private void loadDetailedWealthPieChartData(){
        summaryPieChart.getData().clear();
        summaryPieChart.setTitle("Wealth Details");
        for(InvestmentItem investmentItem: InvestmentItems.getInstance().getInvestmentItems()){
            summaryPieChart.getData().add(new PieChart.Data(investmentItem.getName()+": $" + Double.valueOf(df.format(investmentItem.getNetSum())),
                    Double.valueOf(df.format(investmentItem.getTotalAmount()/netWorth))));
        }
        for(BankItem bankItem: BankItems.getInstance().getBankItems()){
            summaryPieChart.getData().add(new PieChart.Data(bankItem.getAccountName()+": $" + Double.valueOf(df.format(bankItem.getBalance())),
                    Double.valueOf(df.format(bankItem.getBalance()/netWorth))));
        }


    }


    private void loadPredChart(){
        MC = new MoneyCalculations(Double.parseDouble(wageField.getText()),
                Double.parseDouble(hoursField.getText()),
                bankItems.get(0).getBalance(),
                bankItems.get(1).getBalance());
        MC.setRate401(rate401);
        MC.setTaxPer(rateTax);
        MC.calculateNetPayCheck();
        // Initiating line chart that shows history of the current month
        CategoryAxis xAxisPred = new CategoryAxis();
        NumberAxis yAxisPred = new NumberAxis();
        yAxisPred.setLabel("Bank Balance ($)");
        predictionChart = new LineChart<>(xAxisPred, yAxisPred);
        predictionChart.setTitle("Projections");
        List<String> months = timeSpan.getNext12Months();
        List<Double> bankAccountBalance = MC.calculateBankPrediction();
        List<Double> netWorthPred = MC.calculateNetWorthPredictions(netWorth);



        XYChart.Series<String, Number> bankSeries = new XYChart.Series<>();
        bankSeries.setName("Bank Account");
        XYChart.Series<String, Number> netWorthSeries = new XYChart.Series<>();
        netWorthSeries.setName("Net Worth");

        List<List<Double>> investments = new ArrayList<>();
        List<InvestmentItem> investmentItems = InvestmentItems.getInstance().getInvestmentItems();

        List<XYChart.Series<String, Number>> investmentSeries = new ArrayList<>();


        for(int i = 0; i < investmentItems.size(); i++){
            investments.add(MC.calculateInvestmentPredictions(investmentItems.get(i)));
            investmentSeries.add(new XYChart.Series<>());
            investmentSeries.get(i).setName(investmentItems.get(i).getName());
            for(int j = 0; j < 12; j++){
                investmentSeries.get(i).getData().add(new XYChart.Data<>(months.get(j), investments.get(i).get(j)));
            }
        }

        for(int i = 0; i < 12; i++){
            bankSeries.getData().add(new XYChart.Data<>(months.get(i), bankAccountBalance.get(i)));
            netWorthSeries.getData().add(new XYChart.Data<>(months.get(i), netWorthPred.get(i)));
        }
        predictionChart.getData().add(bankSeries);
        predictionChart.getData().add(netWorthSeries);
        for(XYChart.Series<String, Number> invSeries: investmentSeries){
            predictionChart.getData().add(invSeries);
        }
    }

    //
    private void setChart(){
        if(chartToggle.getSelectedToggle() == wealthRadio){
            mainPane.setCenter(pieChartVBox);
            loadWealthChartData();
        }else if(chartToggle.getSelectedToggle() == billRadio){
            mainPane.setCenter(pieChartVBox);
            loadPaycheckChartData();
        }else if(chartToggle.getSelectedToggle() == projectionsRadio){
            loadPredChart();
            mainPane.setCenter(predictionChart);


        }
    }

    /******** FXML Functions *********/
    @FXML
    public void onAddBillItemPressed(){
        NewBillPopup.display();
        billListView.getItems().clear();
        billListView.getItems().setAll(BillItems.getInstance().getBillItems());
    }
    @FXML
    public void onChangeBillItemPressed(){
        ChangeBillPopup.display(billListView.getSelectionModel().getSelectedIndex(), billListView.getSelectionModel().getSelectedItem());
        billListView.getItems().clear();
        billListView.getItems().setAll(BillItems.getInstance().getBillItems());
    }
    @FXML
    public void onDeleteBillItemPressed() throws URISyntaxException {
        BillItem selected = billListView.getSelectionModel().getSelectedItem();
        if(selected!=null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Bill");
            alert.setHeaderText("Delete Bill");
            alert.setContentText("Are you sure you want to delete "+selected.getName()+"?");

            Optional<ButtonType> result = alert.showAndWait();
            if (ButtonType.OK == result.get()){
                BillItems.getInstance().deleteBillItem(selected);
                billListView.getItems().clear();
                billListView.getItems().setAll(BillItems.getInstance().getBillItems());

            }
        }
    }
    @FXML
    public void onAddInvButtonPressed(){
        NewInvestmentPopup.display();
        investmentListView.getItems().clear();
        investmentListView.getItems().addAll(InvestmentItems.getInstance().getInvestmentItems());

    }
    @FXML
    public void onChangeInvItemPressed(){
        ChangeInvestmentPopup.display(investmentListView.getSelectionModel().getSelectedIndex(),
                investmentListView.getSelectionModel().getSelectedItem());
        investmentListView.getItems().clear();
        investmentListView.getItems().addAll(InvestmentItems.getInstance().getInvestmentItems());


    }
    @FXML
    public void onDeleteInvItemPressed() {
        InvestmentItem selected = investmentListView.getSelectionModel().getSelectedItem();
        if(selected!=null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Bill");
            alert.setHeaderText("Delete Bill");
            alert.setContentText("Are you sure you want to delete "+selected.getName()+"?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                InvestmentItems.getInstance().deleteItem(selected);
                investmentListView.getItems().clear();
                investmentListView.getItems().setAll(InvestmentItems.getInstance().getInvestmentItems());

            }
        }
    }
    @FXML
    public void onSetValuesPressed() {
        MC = new MoneyCalculations(Double.parseDouble(wageField.getText()),
            Double.parseDouble(hoursField.getText()),

        bankItems.get(0).getBalance(),
        bankItems.get(1).getBalance());
        totalInvestmentLabel.setText("Investments: $" + InvestmentItems.getInstance().getTotalInvestments());
        totalNetInvestment.setText("Net Investments: $" + InvestmentItems.getInstance().getNetInvestments());

        rate401 = slider401.getValue()/100.0;
        rateTax = sliderTax.getValue()/100.0;
        netWorth = determinePayCheck(rate401, rateTax);
        checkingAccountLabel.setText("Checking Account: $" + bankItems.get(0).getBalance());
        savingsAccountLabel.setText("Savings Account: $" + bankItems.get(1).getBalance());
        netWorthLabel.setText("Net Worth: $" + netWorth);
        List<String> paycheckData  = new ArrayList<>();
        updatePaycheckFile(paycheckData);

        setChart();


    }
    @FXML
    public void onChangeBankingPressed() {
        BankAccountPopup.display(BankItems.getInstance().getBankItems().get(0), BankItems.getInstance().getBankItems().get(1));
        for(int i = 0; i < BankItems.getInstance().getBankItems().size(); i++){
            bankItems.get(i).setBalance(BankItems.getInstance().getBankItems().get(i).getBalance());
        }
        checkingAccountLabel.setText("Checking Account: $" + bankItems.get(0).getBalance());
        savingsAccountLabel.setText("Savings Account: $" + bankItems.get(1).getBalance());

    }





}
