package classes;

import javafx.collections.FXCollections;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class InvestmentItems {
    private static InvestmentItems instance = new InvestmentItems();
    private List<InvestmentItem> investmentItems;

    public static InvestmentItems getInstance() {return instance;}

    private InvestmentItems(){}

    public List<InvestmentItem> getInvestmentItems() {
        return investmentItems;
    }
    public void loadInvestmentItems() throws URISyntaxException {
        investmentItems = FXCollections.observableArrayList();

        List<String> Investments = Utils.readData("../data/InvestmentInfo.txt");
        assert Investments != null;
        for(String investment : Investments){
            String[] investmentInfo = investment.split(",");
            investmentItems.add(new InvestmentItem(investmentInfo[0],
                    Double.parseDouble(investmentInfo[1]),
                    Double.parseDouble(investmentInfo[2]),
                    Double.parseDouble(investmentInfo[3])));
        }

    }

    //Alters Loaded Investment Items
    public void changeItem(int itemIndex, String newName, Double newAllocation, Double newTotalAmount, Double newNetAmount){
        investmentItems.get(itemIndex).setName(newName);
        investmentItems.get(itemIndex).setAmountPerMonth(newAllocation);
        investmentItems.get(itemIndex).setTotalAmount(newTotalAmount);
        investmentItems.get(itemIndex).setNetSum(newNetAmount);

        List<String> data = new ArrayList<>();
        updateInvestmentFile(data);
    }
    public void addItem(InvestmentItem item){
        investmentItems.add(item);
        List<String> data = new ArrayList<>();
        updateInvestmentFile(data);
    }
    public void deleteItem(InvestmentItem item){
        investmentItems.remove(item);
        List<String> data = new ArrayList<>();
        updateInvestmentFile(data);

    }

    //Updates Investment Information
    private void updateInvestmentFile(List<String> data){
        for(InvestmentItem investmentItem : investmentItems){
            data.add(investmentItem.getName() + "," +
                    investmentItem.getAmountPerMonth() + "," +
                    investmentItem.getTotalAmount() + "," +
                    investmentItem.getNetSum());
        }
        Utils.writeData(System.getProperty("user.dir") + "/src/data/InvestmentInfo.txt", data);
    }

    //Investment Math
    public Double getTotalMonthlyAllocations(){
        Double sum = 0.0;
        for(InvestmentItem item : investmentItems){
            sum += item.getAmountPerMonth();
        }
        return sum;
    }
    public Double getTotalInvestments(){
        Double investments = 0.0;
        for(InvestmentItem investment : investmentItems){
            investments += investment.getTotalAmount();
        }
        return investments;
    }
    public Double getNetInvestments(){
        Double investments = 0.0;
        for(InvestmentItem investment : investmentItems){
            investments += investment.getNetSum();
        }
        return investments;
    }
}
