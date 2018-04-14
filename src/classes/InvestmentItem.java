package classes;

public class InvestmentItem {
    private String name;
    private Double amountPerMonth;
    private Double totalAmount;
    private Double netSum;

    public Double getNetSum() {
        return netSum;
    }

    public void setNetSum(Double netSum) {

        this.netSum = netSum;
    }

    public InvestmentItem(String name, Double amountPerMonth) {
        this.name = name;
        this.amountPerMonth = amountPerMonth;
    }

    public InvestmentItem(String name, Double amountPerMonth, Double totalAmount) {
        this.name = name;
        this.amountPerMonth = amountPerMonth;
        this.totalAmount = totalAmount;
    }

    public InvestmentItem(String name, Double amountPerMonth, Double totalAmount, Double netSum) {
        this.name = name;
        this.amountPerMonth = amountPerMonth;
        this.totalAmount = totalAmount;
        this.netSum = netSum;
    }

    public String getName() {
        return name;
    }

    public Double getAmountPerMonth() {
        return amountPerMonth;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmountPerMonth(Double amountPerMonth) {
        this.amountPerMonth = amountPerMonth;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }



    public Double getTotalAmount() {

        return totalAmount;
    }

    @Override
    public String toString() {
        return getName() + ":           $" + getAmountPerMonth() +"/month";
    }
}
