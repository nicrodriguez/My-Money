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


    // Predictions chart data
    public List<Double> calculateBankPrediction(){

        Double val = BankItems.getInstance().getAccountsSum();
        List<Double> monthlyVals = new ArrayList<>();
        monthlyVals.add(val);
        for(int i = 0; i < 11; i++){
            val += (getMonthlyPayCheckNet()) ;
            monthlyVals.add(val);
        }

        return monthlyVals;
    }
    public List<Double> calculateInvestmentPredictions(InvestmentItem investmentItem){
        Double investmentVal = investmentItem.getNetSum();
        List<Double> monthlyVals = new ArrayList<>();
        monthlyVals.add(investmentVal*1.05);
        for(int i = 0; i < 11; i++){
            if(investmentItem.getName().equals("401k")){
                investmentVal += getMonthlyPayCheck()*rate401*2;
                monthlyVals.add(investmentVal);
            }else {
                investmentVal += investmentItem.getAmountPerMonth()*1.05;
                monthlyVals.add(investmentVal);
            }
        }
        return monthlyVals;
    }
    public List<Double> calculateNetWorthPredictions(Double netWorth){
        Double netWorthVal = netWorth;
        Double investments = 0.0;
        List<InvestmentItem> investmentItems = InvestmentItems.getInstance().getInvestmentItems();
        List<Double> monthlyVals = new ArrayList<>();
        for(InvestmentItem investmentItem : investmentItems){
            investments += investmentItem.getAmountPerMonth();
        }
        monthlyVals.add(netWorthVal);
        for(int i = 0; i < 11; i++){
            netWorthVal += ((investments + investments*0.05) + getMonthlyPayCheckNet() + getMonthlyPayCheck()*rate401*2);
            monthlyVals.add(netWorthVal);
        }
        return monthlyVals;
    }
    public List<Double> calculateBillPredictions(BillItem billItem){
        Double billVal = billItem.getAmountRemaining();
        List<Double> monthlyVals = new ArrayList<>();
        monthlyVals.add(billVal);
        for(int i = 0; i < 11; i++) {
            billVal -= billItem.getAmountPerMonth();
            monthlyVals.add(billVal);
        }
        return monthlyVals;
    }
}
