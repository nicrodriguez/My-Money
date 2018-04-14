package classes;

import javafx.collections.FXCollections;

import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BillItems {
    private static BillItems instance = new BillItems();
    private List<BillItem> billItems;

    public static BillItems getInstance() {return instance;}

    private BillItems(){}

    public List<BillItem> getBillItems(){return billItems;}
    public void loadbillItems() throws URISyntaxException {
        DecimalFormat df = new DecimalFormat("#.##");
        billItems = FXCollections.observableArrayList();
//        List<BillItem> bills = new ArrayList<>();
        List<String> bills = Utils.readData("../data/BillingInfo.txt");
        assert bills != null;
        for(String bill : bills){
            String[] billInfo = bill.split(",");
            billItems.add(new BillItem(billInfo[0],
                    Double.parseDouble(billInfo[1]),
                    Double.parseDouble(billInfo[2]),
                    Double.parseDouble(billInfo[3])));
        }

    }
    public void addBillItems(BillItem billItem) throws URISyntaxException {
            billItems.add(billItem);
        List<String> data = new ArrayList<>();
        updateBillFile(data);
    }

    public void changeBillItems(int index, String billName, Double rate, Double remaining, Double interest) throws URISyntaxException {
        billItems.get(index).setName(billName);
        billItems.get(index).setAmountPerMonth(rate);
        billItems.get(index).setAmountRemaining(remaining);
        billItems.get(index).setInterest(interest);
        List<String> data = new ArrayList<>();
        updateBillFile(data);
    }

    public void deleteBillItem(BillItem billItem) throws URISyntaxException {
        billItems.remove(billItem);
        List<String> data = new ArrayList<>();
        updateBillFile(data);
    }

    private void updateBillFile(List<String> data) throws URISyntaxException {
        for(BillItem billItem : billItems){
            data.add(billItem.getName() + "," +
                    billItem.getAmountPerMonth() + "," +
                    billItem.getAmountRemaining() + "," +
                    billItem.getInterest());
        }
        Utils.writeData(System.getProperty("user.dir") + "/src/data/BillingInfo.txt", data);
    }

    public Double getTotalMonthlyBill(){
        Double totalBill = 0.0;
        for(BillItem bill : billItems){
            totalBill += bill.getAmountPerMonth();
        }
        return totalBill;
    }
}
