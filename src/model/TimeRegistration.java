package model;

public class TimeRegistration {

    private double hoursWorked;

    /** initialises the hoursWorked variable with the value 0
     */
    public TimeRegistration(){
        this.hoursWorked = 0;
    }

    /**
     * updates the object hoursWorked by adding to it the
     * @param hoursWorked
     * value
     * */
    public void addHoursWorked(double hoursWorked){
        this.hoursWorked += hoursWorked;
    }

    public void setHoursWorked(double hoursWorked){
        this.hoursWorked = hoursWorked;
    }

    public double getHoursWorked(){
        return hoursWorked;
    }
}
