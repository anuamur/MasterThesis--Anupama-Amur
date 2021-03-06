package test123;

public class CustomAbsoluteDate {
    
    private int year;
    private int month;
    private int day;
    
    private int hours;
    private int minutes;
    private int seconds;
    

    /* -------------------------------------------------------------- */
    CustomAbsoluteDate(int inputYear, int inputMonth, int inputDay, 
                       int inputHours, int inputMinutes, int inputSeconds){
        year = inputYear;
        month = inputMonth;
        day = inputDay;
        
        hours = inputHours;
        minutes = inputMinutes;
        seconds = inputSeconds;
    }


    public int getYear() {
        return year;
    }


    public void setYear(int year) {
        this.year = year;
    }


    public int getMonth() {
        return month;
    }


    public void setMonth(int month) {
        this.month = month;
    }


    public int getDay() {
        return day;
    }


    public void setDay(int day) {
        this.day = day;
    }


    public int getHours() {
        return hours;
    }


    public void setHours(int hours) {
        this.hours = hours;
    }


    public int getMinutes() {
        return minutes;
    }


    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }


    public int getSeconds() {
        return seconds;
    }


    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
        
}
