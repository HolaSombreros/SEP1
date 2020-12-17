package model;

public class TimeRegistration {

    private double hoursWorked;

    /** no-arg constructor
     * initialises the hoursWorked variable with the value 0
     */
    public TimeRegistration(){
        this.hoursWorked = 0;
    }

    /**
     * updates hours worked by adding to it
     * @param hoursWorked the value to add
     * */
    public void addHoursWorked(double hoursWorked){
        this.hoursWorked += hoursWorked;
    }
    
    /**
     * method to set the hours worked
     * @param hoursWorked the value it will be set to
     */
    public void setHoursWorked(double hoursWorked){
        this.hoursWorked = hoursWorked;
    }
    
    /**
     * getter for the instance variable
     * @return the amount of hours worked
     */
    public double getHoursWorked(){
        return hoursWorked;
    }
    
    /**
     * standard equals method
     * @param obj the object to check
     * @return whether the two objects are equal
     */
    public boolean equals(Object obj){
        if(!(obj instanceof TimeRegistration))
            return false;
        TimeRegistration other = (TimeRegistration)obj;
        return getHoursWorked() == other.getHoursWorked();
    }
}
