package model;

import java.util.GregorianCalendar;

public class Date
{
    private int day;
    private int month;
    private int year;

    //CONSTRUCTOR

    public Date(int day, int month, int year)
    {
        if(isLegalDate(day, month, year))
        {
            this.day = day;
            this.month = month;
            this.year = year;
        }
        else
            throw new IllegalArgumentException("No legal date");
    }

    //CONSTRUCTOR FOR DATE-TODAY
    /** <p>Uses Gregorian Calendar</p>
     * @return today's date*/
    public Date()
    {
        Date date = Date.today();
        this.day = date.day;
        this.month = date.month;
        this. year = date.year;
    }

    /**
     *
     * @return today's date
     */
    public static Date today()
    {
        GregorianCalendar currentDate = new GregorianCalendar();
        int currentDay = currentDate.get(GregorianCalendar.DATE);
        int currentMonth = currentDate.get(GregorianCalendar.MONTH) + 1;
        int currentYear = currentDate.get(GregorianCalendar.YEAR);
        return new Date(currentDay, currentMonth, currentYear);
    }

    //VALIDATION DATE

    /**
     * Validates the date's day, month, year
     *
     * @param day
     * @param month
     * @param year
     *
     */

    public boolean isLegalDate(int day, int month, int year)
    {
        if (month < 1 || month > 12)
        {
            throw new IllegalArgumentException(month + " is an invalid month!");
        }
        if (day < 0 || day > numberOfDaysInMonth(month))
        {
            throw new IllegalArgumentException(day + " is an invalid day!");
        }
        if (year < 0)
        {
            throw new IllegalArgumentException(year + " is an invalid year!");
        }
        return true;
    }

    /**
     * Checks if the deadline is before today's day
     * Checks if the deadline is before the starting date
     * @param startingDate
     * @param deadline
     */
    public static void checkDates(Date startingDate, Date deadline)
    {
        if(deadline.isBefore(Date.today()))
        {
            throw new IllegalArgumentException("Deadline cannot be before today");
        }
        else if(deadline.isBefore(startingDate))
        {
            throw new IllegalArgumentException("Deadline cannot be before the starting date");
        }
    }

    //SET DATE

    public void set(int day, int month, int year)
    {
        if(isLegalDate(day, month, year))
        {
            this.day = day;
            this.month = month;
            this.year = year;
        }
    }

    //SETTERS

    public void setDay(int day)
    {
        this.day = day;
    }

    public void setMonth(int month)
    {
        this.month = month;
    }

    public void setYear(int year)
    {
        this.year = year;
    }


    //NEEDED METHODS

    public int numberOfDaysInMonth(int month)
    {
        switch(month)
        {
            case 2:
            {
                if(isLeapYear())
                    return 28;
                else
                    return 29;
            }
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            default:
                return -1;
        }
    }

    /**
     * Leap year : February has 29 days, the year has 366 days :)
     * @return a boolean value if the date's year is a leap year
     */
    public boolean isLeapYear()
    {
        if(( (year/4) == 0) && ((year/100) != 0) || ((year/400) == 0))
        {
            return true;
        }
        else
            return false;
    }

    /**
     * @param other a second date
     * @return a date object which checks if the second date is STRICTLY before the first one
     * RO:daca prima data este inaintea celei de-a doua
     * DK: hvis den første dato ligger før den anden
     *
     * */
    public boolean isBefore(Date other)
    {
        if( year > other.year)
        {
            return false;
        }
        if(year == other.year)
        {
            if( month > other.month)
            {
                return false;
            }
            if(month == other.month)
            {
                if(day == other.day)
                {
                    return false;
                }
                else if( day > other.day)
                {
                    return false;
                }
                else
                    return true;
            }
            else
                return true;
        }
        else
            return true;
    }
    //GETTERS


    public int getDay()
    {
        return day;
    }

    public int getMonth()
    {
        return month;
    }

    public int getYear()
    {
        return year;
    }
    //EQUALS
    public boolean equals(Object obj)
    {
        if(! (obj instanceof Date))
        {
            return false;
        }
        Date other = (Date) obj;
        return day == other.getDay() && month == other.getMonth() && year == other.getYear();
    }
    //COPY
    public Date copy()
    {
        return new Date(day, month, year);
    }

    //TO-STRING

    public String toString()
    {
        return String.format("%02d/%02d/%d", day, month, year);
    }
}
