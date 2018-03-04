package test123;

import org.orekit.time.AbsoluteDate;

public class EventsConfig{
    
    double inputEventLatitude; 
    double inputEventLongitude;  
    
    AbsoluteDate eventStartDate;
    AbsoluteDate eventEndDate;
    
    boolean isEventCreated = false; 

    //Setters and Getters
    
    public boolean getIsEventCreated(){
        return isEventCreated;
    }
    
    
    public double getInputEventLatitude() {
        return inputEventLatitude;
    }
    
    
    public void setInputEventLatitude(double inputEventLatitude) {
        this.inputEventLatitude = inputEventLatitude;
    }
    
    
    public double getInputEventLongitude() {
        return inputEventLongitude;
    }
    
    
    public void setInputEventLongitude(double inputEventLongitude) {
        this.inputEventLongitude = inputEventLongitude;
    }
    
    
    public AbsoluteDate getEventStartDate() {
        return eventStartDate;
    }
    
    
    public void setIsEventCreated(){
        isEventCreated = true;
    }
    
    public void setEventStartDate(AbsoluteDate eventStartDate) {
        this.eventStartDate = eventStartDate;
    }
    
    
    public AbsoluteDate getEventEndDate() {
        return eventEndDate;
    }
    
    
    public void setEventEndDate(AbsoluteDate eventEndDate) {
        this.eventEndDate = eventEndDate;
    }
    
} /* End of class */
