package classes;

import java.util.ArrayList;
import java.util.List;

public class MoneyCalculations {
    private Double wage;
    private Double hours;
    private Double paycheck;
    private Double paycheckNet;
    private Double monthlyPayCheck;
    private Double monthlyPayCheckNet;
    private Double checkingsAccount;
    private Double savingsAccount;
    private Double rate401 = 0.0;
    private Double taxPer = 0.0;
    private List<BillItem> bills = new ArrayList<>();
    private List<InvestmentItem> investments = new ArrayList<>();

    public MoneyCalculations(Double wage, Double hours, Double checkingsAccount, Double savingsAccount) {
        this.wage = wage;
        this.hours = hours;
        this.checkingsAccount = checkingsAccount;
        this.savingsAccount = savingsAccount;
        this.paycheck = 2*this.wage*this.hours;
        this.monthlyPayCheck = this.paycheck*2;
    }




    public void calculateNetPayCheck(){
        this.paycheckNet = this.paycheck - this.paycheck*this.rate401 - this.paycheck*this.taxPer
                - BillItems.getInstance().getTotalMonthlyBill()/2 - InvestmentItems.getInstance().getTotalMonthlyAllocations()/2;
        this.monthlyPayCheckNet = 2*this.paycheckNet;
    }

    public Double getNetWorth(){
        return InvestmentItems.getInstance().getNetInvestments() + checkingsAccount + savingsAccount;
    }

    public Double getPaycheck() {
        return paycheck;
    }

    public Double getPaycheckNet() {
        return paycheckNet;
    }


    public Double getMonthlyPayCheck() {
        return monthlyPayCheck;
    }

    public Double getMonthlyPayCheckNet() {
        return monthlyPayCheckNet;
    }

    public void setRate401(Double rate401) {
        this.rate401 = rate401;
    }

    public void setTaxPer(Double taxPer) {
        this.taxPer = taxPer;
    }
}
