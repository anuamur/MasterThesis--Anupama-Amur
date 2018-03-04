package test123;

public class EventObsSplitPerSat{

    /*
     * Data Members: Provide a split of satellites that have been involved in 
     * observing 'this' event
    
    |-------|-------------------|----------------------------|-----------------|
    | SatID | timeSlotsObsBySat | totalEventTimeSlotsElapsed | percentObsBySat |
    |-------|-------------------|----------------------------|-----------------|
    |       |                   |                            |                 |
    |-------|-------------------|----------------------------|-----------------|
    
     */
    
    //Satellite which observed the event
    private int satID;
    
    //number of time slots that the satellite observed
    private int timeSlotsObsBySat;
    
    //total number of time slots for the event
    private static double totalEventTimeSlotsElapsed;
    
    //percentage of event observed by satellite 'satID'
    private double percentObsBySat;
        
    
    /* ----------------------------------------------------------------------------
     * Methods:
     * --------------------------------------------------------------------------- */
    /*
    public EventObsSplitPerSat(int inputTotalEventTimeSlots){
        totalEventTimeSlotsElapsed =  inputTotalEventTimeSlots;      
    }
    */
    
    public EventObsSplitPerSat(int inputSatID, double inputTotalEventTimeSlotsElapsed){
        satID = inputSatID;
        totalEventTimeSlotsElapsed = inputTotalEventTimeSlotsElapsed; 
    }
    
    public void updateEventTimelotsElapsed(int inputTotalEventTimeSlotsElapsed){
        totalEventTimeSlotsElapsed =  inputTotalEventTimeSlotsElapsed;
        updatePercentObsBySat();
        System.out.println("total time slots elapsed: " + totalEventTimeSlotsElapsed);
    }
/* ------------------------------ get methods ----------------------------------- */

    public int getSatID(){
        return satID;
    }
    
    public int getTimeSlotsObsBySat(){
        return timeSlotsObsBySat;
    }
    
    public double getTotalEventTimeSlotsElapsed(){
        return totalEventTimeSlotsElapsed;
    }
    
    public double getPercentObsBySat(){
        return percentObsBySat; 
    }
    
    /* ------------------------------ set methods ----------------------------------- */
    
    public void setSatID(int inputSatID){
        satID = inputSatID;
    }
    
    public void updateTimeSlotsObsBySat(){
        timeSlotsObsBySat = timeSlotsObsBySat + 1;
        updatePercentObsBySat();
    }
    
    private void updatePercentObsBySat(){
        if(totalEventTimeSlotsElapsed == 0){
            percentObsBySat = 0;
        }  
        else {
            percentObsBySat = ((timeSlotsObsBySat/totalEventTimeSlotsElapsed)*100);
            //System.out.println("time Slots obs by Sat: " + timeSlotsObsBySat +
            //                    " & total time slots elapsed: " + totalEventTimeSlotsElapsed);
        }        
    }


} /* End of class */