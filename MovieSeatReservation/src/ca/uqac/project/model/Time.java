package ca.uqac.project.model;
import java.io.Serializable;
public class Time implements Comparable<Time>,Serializable
{
    
    private int hours;
    private int mins;
    private int secs;

    /**
     * The default constructor that will be called if there's no parameters set.
     * The default time it is set in is at midnight in a 24-hour format (00:00:00).
     */
    public Time()
    {
        this.hours = 0;
        this.mins = 0;
        this.secs = 0;
    }

    /**
     * The constructor that will be called if only parameter of hours is set.
     * @param hours set by the caller (0-23).
     */
    public Time(int hours)
    {
        this.setHours(hours);
        this.mins = 0;
        this.secs = 0;
    }

    /**
     * The constructor that will be called if only parameters of hours and minutes is set.
     * @param hours hours set by the caller (0-23).
     * @param mins minutes set by the caller (0-59).
     */
    public Time(int hours, int mins)
    {
        this.setHours(hours);
        this.setMinutes(mins);
        this.secs = 0;
    }

    /**
     * The constructor that will be called if all parameters of hours, minutes, and seconds is set.
     * @param hours hours set by the caller (0-23).
     * @param mins minutes set by the caller (0-59).
     * @param secs seconds set by the caller (0-59).
     */
    public Time(int hours, int mins, int secs)
    {
        this.setHours(hours);
        this.setMinutes(mins);
        this.setSeconds(secs);
    }

    /**
     * Method to set the seconds of the time, as long as it's within 0 to 59.
     * @param secs seconds set by the caller (0-59).
     */
    public void setSeconds(int secs)
    {
        if (secs >= 0 && secs < 60)
        {
            this.secs = secs;
        }
        else
        {
            System.out.println("Invalid Seconds Set (0-59) !!");
            assert secs >= 0 && secs < 60;
        }
    }

    /**
     * Method to set the minutes of the time, as long as it's within 0 to 59.
     * @param mins minutes set by the caller (0-59).
     */
    public void setMinutes(int mins)
    {
        if (mins >= 0 && mins < 60)
        {
            this.mins = mins;
        }
        else
        {
            System.out.println("Invalid Minutes Set (0-59) !!");
            assert mins >= 0 && mins < 60;
        }
    }

    /**
     * Method to set the hours of the time, as long as it's within 0 to 23.
     * @param hours set by the caller (0-23).
     */
    public void setHours(int hours)
    {
        if (hours >= 0 && hours < 24)
        {
            this.hours = hours;
        }
        else
        {
            System.out.println("Invalid Hours Set (0-23) !!");
            assert hours >= 0 && hours < 24;
        }
    }

    /**
     * Method to get the seconds of the class time.
     * @return seconds of the class time.
     */
    public int getSeconds()
    {
        return this.secs;
    }

    /**
     * Method to get the minutes of the class time.
     * @return minutes of the class time.
     */
    public int getMinutes()
    {
        return this.mins;
    }

    /**
     * Method to get the hours of the class time.
     * @return hours of the class time.
     */
    public int getHours()
    {
        return this.hours;
    }

    /**
     * A method to check if whether the current time is equals to another time.
     * @param otherTime the other time the caller wants to check.
     * @return a boolean to tell whether they are equal or not.
     */
    @Override
    public boolean equals(Object otherTime)
    {
        Time timeObject = (Time) otherTime;
        return timeObject.toString().equals(this.toString());
    }

    /**
     * A method to determine whether the current time is greater or less than the other time.
     * <b>1)</b> A positive number means that the current time is greater.
     * <b>2)</b> A negative number means that the current time is lesser.
     * <b>3)</b> A zero number means that both times are equal.
     * @param otherTime the other time the caller wants to check.
     * @return an integer to tell whether current time is greater or less than the other time.
     */
    @Override
    public int compareTo(Time otherTime)
{
        // Are both times Equal?
        if (this.equals(otherTime))
        {
            return 0;
        }
        else
        {
            // Does both times have equal hours?
            if (this.hours == otherTime.getHours())
            {
                // Does both times have equal minutes?
                if (this.mins == otherTime.getMinutes())
                {
                    // If both minutes are equal, then compare seconds
                    return this.secs - otherTime.getSeconds();
                }
                else
                {
                    // If both minutes aren't equal, then compare minutes
                    return this.mins - otherTime.getMinutes();
                }
            }
            else
            {
                // If both hours aren't equal, then compare hours
                return this.hours - otherTime.getHours();
            }
        }
    }

    /**
     * Method to create a string of the date format (HOURS:MINS:SECONDS)
     * @return Formatted String of the Time Class.
     */
    @Override
    public String toString()
    {
        return String.format("%02d:%02d:%02d", this.hours, this.mins, this.secs);
    }
}