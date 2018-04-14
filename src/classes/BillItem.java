package classes;

public class BillItem {
    private String name;
    private Double amountPerMonth;
    private Double amountRemaining;
    private Double Interest;

    public BillItem(String name, Double amountPerMonth){
        this.name = name;
        this.amountPerMonth = amountPerMonth;
    }

    public BillItem(String name, Double amountPerMonth, Double amountRemaining) {
        this.name = name;
        this.amountPerMonth = amountPerMonth;
        this.amountRemaining = amountRemaining;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BillItem(String name, Double amountPerMonth, Double amountRemaining, Double interest) {
        this.name = name;
        this.amountPerMonth = amountPerMonth;
        this.amountRemaining = amountRemaining;
        Interest = interest;
    }

    public String getName() {
        return name;
    }

    public Double getAmountPerMonth() {
        return amountPerMonth;
    }

    public void setAmountPerMonth(Double amountPerMonth) {
        this.amountPerMonth = amountPerMonth;
    }

    public void setAmountRemaining(Double amountRemaining) {
        this.amountRemaining = amountRemaining;
    }

    public void setInterest(Double interest) {
        Interest = interest;
    }

    public Double getAmountRemaining() {

        return amountRemaining;
    }

    public Double getInterest() {
        return Interest;
    }

    @Override
    public String toString() {return this.name +":          $" + this.amountPerMonth +"/month";}
}
