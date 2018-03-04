package test123;
import java.util.ArrayList;
import java.util.Iterator;

import org.orekit.time.AbsoluteDate;
public class Event {
    
    private double timeSlice = 0;
    
    private int eventID;
    private boolean amIDeleted;
    
    private double eventLatitude;
    private double eventLongitude;
    private int eventRadius;
    
    private AbsoluteDate eventStartDateTime;
    private AbsoluteDate eventCurrDateTime;
    private AbsoluteDate eventEndDateTime;
    
    private double eventPercentObserved;

    private static int eventCounter = 0;
    private double currTimeSlot = 0;
    
    
    private int totalTimeSlots = 0;
    private int timeSlotsElapsed = 0;
    
    private int timeSlotsObserved = 0;
    private int timeSlotsNotObserved = 0;
    
      
    private ArrayList<EventObsSplitPerSat> eventObsSplitPerSatObj;
    private double percentObserved;
    
    //add eventData data str?
    
    /* --------------------- Constructor to create event --------------------------- */
    //Generate event location and start/end times randomly using a random function
    public Event(double inputEventLatitude, 
                      double inputEventLongitude, 
                      int inputEventRadius, 
                      AbsoluteDate inputEventStartDateTime,
                      AbsoluteDate inputEventEndDateTime,
                      double inputTimeSlice){
        
        eventID = eventCounter;
        eventCounter = eventCounter + 1;
        
        //Event active and not deleted
        amIDeleted=false;
        
        timeSlice = inputTimeSlice;
        
        //Stores the satellite wise split of event observed
        eventObsSplitPerSatObj = new ArrayList<EventObsSplitPerSat>();
        
        eventLatitude = inputEventLatitude;
        eventLongitude = inputEventLongitude;
        eventRadius = inputEventRadius;
        eventStartDateTime = inputEventStartDateTime;
        eventEndDateTime = inputEventEndDateTime;    
        
        //Setting the current Date time to event start date time during event creation
        eventCurrDateTime = eventStartDateTime;
    
        //Computes the total number of time slots that the event has
        calcEventTotalTimeSlots();
    
    }

    
    
    /* ------------------------------ get methods ----------------------------------- */    
    public int getEventCount(){
        return eventCounter;
    }
    
    public int getEventID(){
        return eventID;
    } 
    
    
    public double getEventLatitude(){
        return eventLatitude;
    }

    
    public double getEventLongitude(){
        return eventLongitude;
    }

    
    public AbsoluteDate getEventEndDateTime(){
        
        return eventEndDateTime;
    }
    
    public AbsoluteDate getEventCurrDateTime(){
        return eventCurrDateTime;
    }
    
    public ArrayList<EventObsSplitPerSat> getEventObsSplitPerSatObj(){
        return eventObsSplitPerSatObj;
    }
    
    public boolean getAmIDeleted(){
        return amIDeleted;
    }
    
    /* ------------------------------ set methods ----------------------------------- */    
    
    public void setAmIDeleted(boolean inputAmIDeleted){
        amIDeleted = inputAmIDeleted;
    }
    
      
/* ----------------------------------------------------------------------------- */
//When an event is complete, reducing the total number of events in the system    
    public int reduceEventCount(){
        eventCounter = eventCounter - 1;
        if(eventCounter<0){
            eventCounter=0;
        }
        return eventCounter;
    }
    
/* ----------------------------------------------------------------------------- */
//Called in the constructor
//Ideally we would not have an event end time already when an event occurs. 
//For the sake of simplicity, the end time is fixed
//Round2: make events with an unknown endtime where we check at each time slice if an event is still active

private void calcEventTotalTimeSlots(){
    for(AbsoluteDate progressDate = eventStartDateTime;
            progressDate.compareTo(eventEndDateTime) <= 0;
            progressDate = progressDate.shiftedBy(timeSlice)){
        
        totalTimeSlots = totalTimeSlots + 1;
    }
    
    System.out.println("Total Time Slots for Event " + getEventID() + ": " + totalTimeSlots);
}


/* ----------------------------------------------------------------------------- */
//get current time
public void setCurrDateTime(AbsoluteDate inputCurrDateTime){
    eventCurrDateTime = inputCurrDateTime;
}
 

/* ----------------------------------------------------------------------------- */
//Calculates time and time slots elapsed since the event occured
public void progressEventTime(){
    
    if((eventCurrDateTime.compareTo(eventEndDateTime) <= 0)){
        
        //Computing the current time 
        //Not essential for now
        //eventCurrDateTime.shiftedBy(timeSlice);
         
        //counter for timeslots elapased
        currTimeSlot = currTimeSlot + 1;
        timeSlotsElapsed = timeSlotsElapsed + 1;
        //System.out.println("total time slots elapsed: " + timeSlotsElapsed);
        //System.out.println("Event " + getEventID() + " TimeSlot " + currTimeSlot);
         
    }
    else{        
        //Event has ended
    }

}

/* ----------------------------------------------------------------------------- */

public void displayEventObsSplit(){
    //eventObsSplitPerSatObj
}

/* ----------------------------------------------------------------------------- */
//time slots observed
//yes No
public void updateCurrTimeSlotObserved(boolean isObserved, int satObserved){
    if(isObserved == true){
        timeSlotsObserved = timeSlotsObserved + 1;
        //System.out.println(timeSlotsObserved + " time slots observed by satellite " + satObserved);
    }
    
    //Search and add
    int index=0;
    boolean satFound = false;
    
    if(!eventObsSplitPerSatObj.isEmpty()){ 
        
        //initializing an iterator
        Iterator<EventObsSplitPerSat> foreach = eventObsSplitPerSatObj.iterator();
                 
        while (foreach.hasNext()){
        EventObsSplitPerSat tempObj = foreach.next();
     
        System.out.println("satID" + tempObj.getSatID() +
                           " satObserved " + satObserved);     
        //Updating based on the satellite which observed the event
        if(tempObj.getSatID() == satObserved){ 
                //eventObsSplitPerSatObj.get(index).updateTimeSlotsObsBySat();
                tempObj.updateTimeSlotsObsBySat(); 
                index++;
                satFound = true;
                break;
            }
        eventObsSplitPerSatObj.get(index).updateEventTimelotsElapsed(timeSlotsElapsed);   
        }
    } /* Is empty check */
    
        /*
         * A New Satellite has observed the event.
         * So it is not yet updated to the table / array list
         */   
        if(satFound==false){
            EventObsSplitPerSat tempObj = new EventObsSplitPerSat(satObserved, timeSlotsElapsed);
            tempObj.updateTimeSlotsObsBySat();
            eventObsSplitPerSatObj.add(tempObj); 
        }                       
}



/* --------------------------------------------------------------------------------- */

public void updateEventStatus(){
    if(!eventObsSplitPerSatObj.isEmpty()){
        
        //initializing an iterator
        Iterator<EventObsSplitPerSat> foreach = eventObsSplitPerSatObj.iterator();
                 
        while(foreach.hasNext()){
            EventObsSplitPerSat tempObj = foreach.next();  
            tempObj.updateEventTimelotsElapsed(timeSlotsElapsed); 
            displayEventObsSplitBySat(tempObj);
            //System.out.println("time slots elapsed:" + timeSlotsElapsed);
        }
    }
}

/* ----------------------------------------------------------------------------- */
//
public void displayEventObsSplitBySat(EventObsSplitPerSat inputTempObj){
    System.out.println("SatID: " + inputTempObj.getSatID() + 
                       " TS observed: " + inputTempObj.getTimeSlotsObsBySat() +
                        " Event active since " + " TS" + inputTempObj.getTotalEventTimeSlotsElapsed() +
                       " % Obs " + inputTempObj.getPercentObsBySat()); 
    
}
/* ----------------------------------------------------------------------------- */
//Calculates the percentage of event observed until date/time
public void calcPercentObserved(){
    percentObserved = ((timeSlotsObserved/timeSlotsElapsed)*100);
}


/* ----------------------------------------------------------------------------- */
//Sets percentage of event observed
//Used only for dummy events
public void setPercentObserved(int inputValue){
  percentObserved = inputValue;
}


/* ----------------------------------------------------------------------------- */
//Returns the percentge of event observed
public double getPercentObserved(){
    return percentObserved;
}

public int getTotalEventTimeSlots(){
    return totalTimeSlots;
}


}//End of Event class
