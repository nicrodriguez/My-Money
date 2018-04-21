package classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimeSpan {
    private Integer day;
    private Integer month;
    private Integer year;

    public TimeSpan(Integer day, Integer month, Integer year) {
        this.day = day;
        this.month = month+1;
        this.year = year;
    }

    public Integer getDay() {
        return day;
    }

    public Integer getMonth() {
        return month;
    }


    public String getMonthName(){
        return determineMonth(getMonth());
    }

    public String getMonthName(Integer month){
        return determineMonth(month);
    }

    public Integer getYear() {
        return year;
    }

    public List<String> getHoursOfDay(){

        String[] hours = { "9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "1:00 PM", "2:00 PM",  "3:00 PM",  "4:00 PM",
                "5:00 PM"};
        return Arrays.asList(hours);
    }

    public List<String> getDaysOfMonth(){
        List<String> days = new ArrayList<>();
        switch(getMonth()){
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                for(Integer i = 1; i <= 31; i++)
                    days.add(i.toString());
                break;
            case 4: case 6: case 9: case 11:
                for(Integer i = 1; i <= 30; i++)
                    days.add(i.toString());
                break;
            case 2:
                int n = 28;
                if(getYear()%4 == 0)
                    n +=1;

                for(Integer i = 1; i <= n; i++)
                    days.add(i.toString());
                break;

        }
        return days;
    }

    public List<String> getThreeMonths(){
        int month2 = getMonth() - 1;
        int month3 = getMonth() - 2;
        if(month2 <= 0)
            month2 = month2 + 12;
        if(month3 <= 0)
            month3 = month3 + 12;
        return Arrays.asList(determineMonth(month3), determineMonth(month2), determineMonth(getMonth()));
    }

    public List<String> getMonthsOfYear(){
        String[] mons = {"Jan", "Feb", "Mar", "April", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return Arrays.asList(mons);
    }

    public List<String> getNext12Months(){
        int m = this.month - 1;
        List<String> futureMonths = new ArrayList<>();
        for(int i = 0; i < 12; i++){
            if(m > 12)
                m = 1;

            futureMonths.add(determineMonth(m + 1));
            m++;

        }
        return futureMonths;
    }
    public List<String> getALlTime(){
        return new ArrayList<>();
    }

    private String determineMonth(int monthNum){
        String month ="";
        switch(monthNum){
            case 1:
                month  = "Jan";
                break;
            case 2:
                month = "Feb";
                break;
            case 3:
                month = "Mar";
                break;
            case 4:
                month =  "Apr";
                break;
            case 5:
                month = "May";
                break;
            case 6:
                month = "Jun";
                break;
            case 7:
                month = "Jul";
                break;
            case 8:
                month = "Aug";
                break;
            case 9:
                month = "Sep";
                break;
            case 10:
                month = "Oct";
                break;
            case 11:
                month = "Nov";
                break;
            case 12:
                month = "Dec";
                break;
        }
        return month;
    }
}
