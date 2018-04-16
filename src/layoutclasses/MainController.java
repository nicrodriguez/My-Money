package layoutclasses;

import classes.*;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainController {

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
    private PieChart  summaryPieChart;

    private DecimalFormat df;
    private Double netWorth;

    public void initialize() throws URISyntaxException {
        df = new DecimalFormat("#.##");


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

        // initializing buttons on each tab
        initializeButtons();

        // calculate paychecks
        onSetValuesPressed();





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

        setValuesButton.setMaxWidth(Double.MAX_VALUE);
        setValuesButton.pressedProperty().addListener((observable, wasPressed, pressed) -> {
            if(pressed){
                setValuesButton.setStyle(Styles.UpdateValueButtonPressed());
            }else{
                setValuesButton.setStyle(Styles.UpdateValueButtonStyle());
            }
        });

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
                if(item.getAmountRemaining() != null){
                    selectedRemaining.setText("Amount Remaining: $" + item.getAmountRemaining());
                }else{
                    selectedRemaining.setText("Amount Remaining: NaN");
                }
                if(item.getInterest() != null){
                    selectedInterest.setText("Interest Rate: %" + item.getInterest());
                }else{
                    selectedInterest.setText("Interest Rate: NaN");
                }
            }
        }));
        initializeBillSummary();
        setBillButtonStyles(addBillButton);
        setBillButtonStyles(changeBIllButton);
        setBillButtonStyles(deleteBillButton);

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

    private Double determinePayCheck(Double rate401, Double rateTax) {

        MoneyCalculations MC = new MoneyCalculations(Double.parseDouble(wageField.getText()),
                Double.parseDouble(hoursField.getText()),
                bankItems.get(0).getBalance(),
                bankItems.get(1).getBalance());
        MC.setRate401(rate401);
        MC.setTaxPer(rateTax);
        MC.calculateNetPayCheck();

        biWeeklyPC.setText("Bi-Weekly:         $" + Double.valueOf(df.format(MC.getPaycheck())));
        biWeeklyPCNet.setText("Net Bi-Weekly:     $" + Double.valueOf(df.format(MC.getPaycheckNet())));
        monthlyPC.setText("Monthly:           $" + Double.valueOf(df.format(MC.getMonthlyPayCheck())));
        monthlyNet.setText("Net Monthly:       $" + Double.valueOf(df.format(MC.getMonthlyPayCheckNet())));

        return MC.getNetWorth();
    }

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
        Double rate401 = slider401.getValue()/100.0;
        Double rateTax = sliderTax.getValue()/100.0;
        netWorth = determinePayCheck(rate401, rateTax);
        checkingAccountLabel.setText("Checking Account: $" + bankItems.get(0).getBalance());
        savingsAccountLabel.setText("Savings Account: $" + bankItems.get(1).getBalance());
        netWorthLabel.setText("Net Worth: $" + netWorth);
        List<String> paycheckData  = new ArrayList<>();
        updatePaycheckFile(paycheckData);

        // load wealth chart
        loadPaycheckChartData();


    }
    @FXML
    public void onChangeCheckingsPressed() {
        BankAccountPopup.display(BankItems.getInstance().getBankItems().get(0), BankItems.getInstance().getBankItems().get(1));
        for(int i = 0; i < BankItems.getInstance().getBankItems().size(); i++){
            bankItems.get(i).setBalance(BankItems.getInstance().getBankItems().get(i).getBalance());
        }
        checkingAccountLabel.setText("Checking Account: $" + bankItems.get(0).getBalance());
        savingsAccountLabel.setText("Savings Account: $" + bankItems.get(1).getBalance());

    }

    private void initializeBillSummary(){
        selectedBill.setText("BIll: " + billListView.getSelectionModel().getSelectedItem().getName());
        selectedCharge.setText("Monthly Payment: $" + billListView.getSelectionModel().getSelectedItem().getAmountPerMonth());

        if(billListView.getSelectionModel().getSelectedItem().getAmountRemaining() != null){
            selectedRemaining.setText("Amount Remaining: " + billListView.getSelectionModel().getSelectedItem().getAmountRemaining());

        }else{
            selectedRemaining.setText("Amount Remaing: NaN");

        }

        if(billListView.getSelectionModel().getSelectedItem().getInterest() != null){
            selectedInterest.setText("Intereset Rate: %" + billListView.getSelectionModel().getSelectedItem().getInterest());

        }else{
            selectedInterest.setText("Interest Rate: NaN");

        }
    }
    private void initializeInvestmentSummary(){
//        InvestmentItem item = investmentListView.getSelectionModel().getSelectedItem();
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

    private void loadWealthChartData(){
        Double totalInvestments = InvestmentItems.getInstance().getNetInvestments();
        Double totalBankSum = BankItems.getInstance().getAccountsSum();

        Double percentInvestments = Double.valueOf(df.format(totalInvestments/netWorth*100));
        Double percentBankItems = Double.valueOf(df.format(totalBankSum/netWorth*100));

        summaryPieChart.getData().clear();
        summaryPieChart.getData().add(new PieChart.Data("Investments: " + percentInvestments +"%", percentInvestments));
        summaryPieChart.getData().add(new PieChart.Data("Bank Accounts: " + percentBankItems + "%", percentBankItems));
    }

    private void loadPaycheckChartData(){
        Double rate401 = slider401.getValue()/100.0;
        Double rateTax = sliderTax.getValue()/100.0;

        List<InvestmentItem> investmentItems = InvestmentItems.getInstance().getInvestmentItems();
        List<BillItem> billItems = BillItems.getInstance().getBillItems();
        MoneyCalculations MC = new MoneyCalculations(Double.parseDouble(wageField.getText()), Double.parseDouble(hoursField.getText()), bankItems.get(0).getBalance(), bankItems.get(1).getBalance());
        MC.setRate401(rate401);
        MC.setTaxPer(rateTax);
        MC.calculateNetPayCheck();

        Double totalBillCost = 0.0;
        for(BillItem billItem : BillItems.getInstance().getBillItems()){
            totalBillCost += billItem.getAmountPerMonth();
        }

        Double investmentCost = 0.0;
        for(InvestmentItem investmentItem : InvestmentItems.getInstance().getInvestmentItems()){
            investmentCost += investmentItem.getAmountPerMonth();
        }

        Double percentBills = Double.valueOf(df.format(totalBillCost/MC.getMonthlyPayCheck()*100));
        Double percentInvestment = Double.valueOf(df.format(investmentCost/MC.getMonthlyPayCheck()*100));
        Double percent401K = Double.valueOf(df.format(rate401*100));
        Double percentTax = Double.valueOf(df.format(rateTax*100));
        Double remaining = Double.valueOf(df.format(100 - percentBills - percentInvestment - percent401K - percentTax));

        summaryPieChart.getData().clear();
        summaryPieChart.setTitle("Paycheck Summary");
        summaryPieChart.getData().add(new PieChart.Data("Bills: " + percentBills +"%", percentBills));
        summaryPieChart.getData().add(new PieChart.Data("Investments: " + percentInvestment +"%", percentInvestment));
        summaryPieChart.getData().add(new PieChart.Data("Tax: " +percentTax+"%", percentTax));
        summaryPieChart.getData().add(new PieChart.Data("401K Contrib: " + percent401K + "%", percent401K));
        summaryPieChart.getData().add(new PieChart.Data("Remaining Paycheck: "+ remaining + "%", remaining));
    }



}
