package classes;

import javafx.collections.FXCollections;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class BankItems {
    private static BankItems instance = new BankItems();
    private List<BankItem> bankItems;

    public static BankItems getInstance() {return instance;}
    private BankItems(){}

    public List<BankItem> getBankItems() {
        return bankItems;
    }
    public void loadBankItems() throws URISyntaxException {
        bankItems = FXCollections.observableArrayList();

        List<String> Investments = Utils.readData("../data/BankInfo.txt");
        assert Investments != null;
        for(String investment : Investments){
            String[] investmentInfo = investment.split(",");
            bankItems.add(new BankItem(investmentInfo[0], Double.parseDouble(investmentInfo[1])));
        }

    }

    //Alters Loaded Bank Items
    public void changeItem(int itemIndex, String newName, Double newAmount){
        bankItems.get(itemIndex).setAccountName(newName);
        bankItems.get(itemIndex).setBalance(newAmount);

        List<String> data = new ArrayList<>();
        updateBankFile(data);
    }


    //Updates Bank Information
    private void updateBankFile(List<String> data){
        for(BankItem bankItem : bankItems){
            data.add(bankItem.getAccountName() + "," +
                    bankItem.getBalance());
        }

        Utils.writeData(System.getProperty("user.dir") + "/src/data/BankInfo.txt", data);
    }

}
