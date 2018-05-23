package classes;

public class BankItem {
    private String accountName;
    private Double balance;

    public BankItem(String accountName, Double balance) {
        this.accountName = accountName;
        this.balance = balance;
    }
    public void widthdraw(Double amount){
        this.balance -= amount;
    }
    public void deposit(Double amount){
        this.balance += amount;
    }

    public String getAccountName() {
        return accountName;
    }

    public Double getBalance() {
        return balance;
    }

    void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
