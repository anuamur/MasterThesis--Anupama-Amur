package test123;

import java.io.File;

import org.orekit.*;  
import org.orekit.data.DataProvidersManager;
import org.orekit.data.DirectoryCrawler;
import org.orekit.errors.OrekitException;
import org.orekit.frames.Frame;
import org.orekit.frames.FramesFactory;
import org.orekit.propagation.SpacecraftState;
import org.orekit.time.AbsoluteDate;
import org.orekit.time.TimeScale;
import org.orekit.time.TimeScalesFactory;
import org.orekit.frames.*;
import org.orekit.utils.*;
import org.orekit.frames.*;
import org.orekit.propagation.analytical.*;
import org.orekit.utils.PVCoordinates.*;

import java.lang.Object.*;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Iterator;

import org.hipparchus.geometry.euclidean.threed.Vector3D;
import org.orekit.orbits.*;
import org.orekit.utils.PVCoordinates;

import java.util.Random;

public class Time {            

    /* ------------------------------------------------------------------------------------------------
     * Variables for storing GUI Parameters
     * ------------------------------------------------------------------------------------------------ */
    
    //Simulation Start Date
    private int yearInitialDate;
    private int monthInitialDate;
    private int dayInitialDate;
    
    
    //Simulation Start Time
    private int hoursInitialTime;
    private int minutesInitialTime;
    private int secondsInitialTime;
    
    //Simulation End Date
    private int yearFinalDate;
    private int monthFinalDate;
    private int dayFinalDate;
    

    //Simulation End Time
    private int hoursFinalTime;
    private int minutesFinalTime;
    private int secondsFinalTime;


    File orekitData;
    DataProvidersManager manager;
    
    private Sat satObj; 
    private SpacecraftState currentStateSat;
    private Frame inertialFrame;
    private TimeScale utc;
    private AbsoluteDate initialDate;
    
    //private double duration = 600.;
    private double duration;
    private AbsoluteDate finalDate;
    public double stepT = 60.;
    private int cpt = 1;  
    private int i = 0;
    
    private double mu =  3.986004415e+14;
    //An Array list for storing events
    private ArrayList<Event> events;
    
    //Counter for events
    int eventCount;
    boolean eventFlag = false;
    //An Array list for storing Satellites
    private ArrayList<Sat> satellites;
    
    private int nrSatellites = 0;
    private int nrEvents = 0;
    private static double simTimeSlotsElapsed = 0;
    
    /* A repository of satellite start positions to randomly pick from */
    //Positions - to be added later (future work)
    private ArrayList<Vector3D> satPositions;
    
    private Random randomGenerator;
    
    //Velocities - to be added later (future work)
    private ArrayList<Vector3D> satVelocities;
    
    /* A repository of satellite start dates to randomly pick from */
    private ArrayList<CustomAbsoluteDate> satStartDates;
    private ArrayList<EventsConfig> eventsConfig;
    
    private double totalTimeSlots = 0;
    
        /* ------------------------------------------------------------------------------- */
        public Time(){

            //Creating a dynamic array of Satellites
            satellites = new ArrayList<Sat>();
            
            //Creating a dynamic array of Events
            events = new ArrayList<Event>();
            
            satPositions = new ArrayList<Vector3D>();
            //Possible Satellite Start Position 1
            Vector3D position  = new Vector3D(36.1, -95.9, 201);
            satPositions.add(position);
            
            /* ------------------------------------------------------------------------- */
            //Possible Satellite Start Dates
            
            satStartDates = new ArrayList<CustomAbsoluteDate>();
            
            //AbsoluteDate entry = new Sat(2004, 02, 01, 22, 10, 00);
            CustomAbsoluteDate newEntry1 = new CustomAbsoluteDate(2004, 01, 01, 23, 30, 00);
            satStartDates.add(newEntry1);
            
            CustomAbsoluteDate newEntry2 = new CustomAbsoluteDate(1924, 5, 8, 22, 56, 00);
            satStartDates.add(newEntry2);
            
            CustomAbsoluteDate newEntry3 = new CustomAbsoluteDate(1964, 05, 13, 11, 30, 00);
            satStartDates.add(newEntry3);
            
            CustomAbsoluteDate newEntry4 = new CustomAbsoluteDate(1961, 1, 24, 14, 30, 00);
            satStartDates.add(newEntry4);
            
            CustomAbsoluteDate newEntry5 = new CustomAbsoluteDate(1998, 11, 25, 15, 30, 00);
            satStartDates.add(newEntry5);
            
            CustomAbsoluteDate newEntry6 = new CustomAbsoluteDate(1952, 12, 18, 16, 30, 00);
            satStartDates.add(newEntry6);
            
            CustomAbsoluteDate newEntry7 = new CustomAbsoluteDate(1991, 1, 22, 5, 30, 00);
            satStartDates.add(newEntry7);
            
            CustomAbsoluteDate newEntry8 = new CustomAbsoluteDate(1961, 07, 11, 20, 30, 00);
            satStartDates.add(newEntry8);
            
            CustomAbsoluteDate newEntry9 = new CustomAbsoluteDate(1985, 10, 13, 14, 20, 00);
            satStartDates.add(newEntry9);
            
            CustomAbsoluteDate newEntry10 = new CustomAbsoluteDate(1990, 04, 18, 9, 18, 00);
            satStartDates.add(newEntry10);
            
            randomGenerator = new Random();
            
            
            /* -------------------------------------------------------------------------- */

        } //End of constructor

        
        /* ----------------------------------------------------------------------------------------- */
        public void initializeSimulationTimes(){

                initialDate = new AbsoluteDate(yearInitialDate, monthInitialDate, dayInitialDate, 
                        hoursInitialTime, minutesInitialTime, secondsInitialTime, utc);
                   
          finalDate = new AbsoluteDate(yearFinalDate, monthFinalDate, dayFinalDate, 
                      hoursFinalTime, minutesFinalTime, secondsFinalTime, utc);
          duration = finalDate.durationFrom(initialDate);
            
            totalTimeSlots = (duration / stepT);
            
        }
        
        /* ------------------------------------------------------------------------------------------ */
        
        public void setUpRandomEventsData(){
            
            AbsoluteDate eventStartDate, eventEndDate;
            int eventStartTimeSlot, eventEndTimeSlot;
            
            eventsConfig = new ArrayList<EventsConfig>();
            
            for(int m=0; m<10; m++){
                
                EventsConfig newEntry = new EventsConfig();
                
                /* -------------------------------------------------------------- */
                //Adding Event m in the possible events list
                
                //Determine Event m Start Date/Time
                eventStartTimeSlot = randomGenerator.nextInt( (int) totalTimeSlots );            
                eventStartDate = initialDate.shiftedBy((eventStartTimeSlot * stepT));
                
                newEntry.setEventStartDate(eventStartDate);

                //Conversion from double to int
                int intTotalTimeSlots = (int) totalTimeSlots ;

                //Determine Event m End Date/Time
                eventEndTimeSlot = 
                    (randomGenerator.nextInt((intTotalTimeSlots - eventStartTimeSlot) + 1 ) + eventStartTimeSlot);            
                eventEndDate = initialDate.shiftedBy((eventEndTimeSlot * stepT));
                newEntry.setEventEndDate(eventEndDate);

                
                switch(m){
                case 0: 
                    //Set Event 0 latitude and longitude
                    //Location somehere in Rome
                    newEntry.setInputEventLatitude(41.898098);
                    newEntry.setInputEventLongitude(12.472124);                    
                    eventsConfig.add(newEntry);                    
                    break;
                    
                case 1:
                    //Set Event 1 latitude and longitude
                    //Location in North Atlantic Ocean, Bermuda Triangle
                    newEntry.setInputEventLatitude(24.9195);
                    newEntry.setInputEventLongitude(-70.91892);                   
                    eventsConfig.add(newEntry);                    
                    break;
                    
                case 2:
                    //Set Event 2 latitude and longitude
                    //Location Somewhere in Canada
                    newEntry.setInputEventLatitude(59.1270159);
                    newEntry.setInputEventLongitude(-127.5204847);                    
                    eventsConfig.add(newEntry);                    
                    break;
                    
                case 3:
                    //Set Event 3 latitude and longitude
                    //Location somewhere in Madagascar
                    newEntry.setInputEventLatitude(-24.133544);
                    newEntry.setInputEventLongitude(45.096702);                    
                    eventsConfig.add(newEntry);                    
                    break;
                    
                case 4:
                    //Set Event 4 latitude and longitude
                    //Location somewhere in Northern India
                    newEntry.setInputEventLatitude(59.1270159);
                    newEntry.setInputEventLongitude(127.5204847);                    
                    eventsConfig.add(newEntry);                    
                    break;
                    
                case 5:
                    //Set Event 5 latitude and longitude
                    //Location in extreme North of Russia near Kara Sea
                    newEntry.setInputEventLatitude(74.98024);
                    newEntry.setInputEventLongitude(94.315452);                    
                    eventsConfig.add(newEntry);                    
                    break;
                    
                case 6:
                    //Set Event 6 latitude and longitude
                    //Location somewhere in Japan
                    newEntry.setInputEventLatitude(59.1270159);
                    newEntry.setInputEventLongitude(-127.5204847);                    
                    eventsConfig.add(newEntry);                    
                    break;
                    
                case 7:
                    //Set Event 7 latitude and longitude
                    //Location somewhere in Syria
                    newEntry.setInputEventLatitude(36.375055);
                    newEntry.setInputEventLongitude(40.711915);                    
                    eventsConfig.add(newEntry);                    
                    break;
                    
                case 8:
                    //Set Event 8 latitude and longitude
                    //Location somewhere in Iceland
                    newEntry.setInputEventLatitude(59.1270159);
                    newEntry.setInputEventLongitude(-127.5204847);                    
                    eventsConfig.add(newEntry);                    
                    break;
                    
                case 9:
                    //Set Event 9 latitude and longitude
                    //Location somewhere in New Zealand
                    newEntry.setInputEventLatitude(-45.749672);
                    newEntry.setInputEventLongitude(168.537841);                    
                    eventsConfig.add(newEntry);                    
                    break;
                    
                default:
                    //Do Nothing
                    break;
                }

                System.out.println("Event: " + m + 
                                   " Start: " + eventStartTimeSlot + 
                                   " End: " + eventEndTimeSlot + 
                                   " Lat: " + eventsConfig.get(m).getInputEventLatitude() +
                                   " Lon: " + eventsConfig.get(m).getInputEventLongitude());                
                
            } /* end of for loop */            
            
        } /* End of method set up random events */
        
        
        
        /* ------------------------------------------------------------------------------------------ */
        public void nEventsToBeCreated(){
            int nrEventsEntered = this.getNrEvents();
           
            Random r = new Random();
            int[] nRandomNumbers = r.ints(nrEventsEntered, 0, eventsConfig.size()).toArray();
            
            for(int k=0; k<nrEventsEntered; k++){
                eventsConfig.get(nRandomNumbers[k]).setIsEventCreated();
                
                System.out.println("Event Nr to be created: " + nRandomNumbers[k]);
            }
        }
        
        
        
        
        /* ------------------------------------------------------------------------------------------ */
        public void createSatellites(){
            
            for(int k=0; k<nrSatellites; k++){
                //for(int k=0; k<1; k++){
                //Creating 'n' number of satellites, based on UI input
                int index = randomGenerator.nextInt(satStartDates.size());
                CustomAbsoluteDate satStartDate = satStartDates.get(index);
                
                satStartDates.remove(index);
                
                satObj = new Sat(satStartDate.getYear(), satStartDate.getMonth(), satStartDate.getDay(),
                                 satStartDate.getHours(), satStartDate.getMinutes(), satStartDate.getSeconds());
                /* Receive input from user later */
                satObj.setSatVisibilityRadius(10000);
                
                satellites.add(satObj);           
            }  /* end of for loop */
            
/*            
            //Sat2
            satObj = new Sat(2004, 02, 01, 22, 10, 00);
             
             Receive input from user later 
            satObj.setSatVisibilityRadius(10000);
            
            satellites.add(satObj);
            
                //Satellite2
                Vector3D position  = new Vector3D(-6142438.668, 3492467.560, -25767.25680);
            //Vector3D position  = new Vector3D(36.1, -95.9, 201);    
            //int index = randomGenerator.nextInt(satPositions.size());
            //Vector3D position = satPositions.get(index);
            
            Vector3D velocity  = new Vector3D(505.8479685, 942.7809215, 7435.922231);
                
                
                
                satObj = new Sat(position,velocity);
                satObj.setSatVisibilityRadius(1000);
                satellites.add(satObj);
                 
                satObj = satellites.get(0);
                System.out.println("Sat Count after Sat creation: " + satObj.getNrSatellites());
                
                
                //Temp Code
                Vector3D position2  = new Vector3D(14.071218, 104.799466, 92);
                Vector3D velocity2  = new Vector3D(505.8479685, 942.7809215, 7435.922231);
                
                
                double llh2[];
                llh2 = new double[3];
                
                double ecef2[];
                ecef2 = new double[3];
                ECEF2LLA b = new ECEF2LLA();
                ecef2 = b.lla2ecef(llh2);
                
                System.out.println("Event 2 Loc in ECEF: " + llh2[0] + 
                                    " " + llh2[1] +
                                    " " + llh2[2]);
                
              */  
        } // End of Create Satellites method
        
        
        
        
        
        
        
/* -------------------------------------------------------------------------------------------------- */        
        public void createEvents(TimeScale inputUTC, AbsoluteDate inputExtrapDate){
            
            //Iterate through all possible events in eventsConfig 
            Iterator<EventsConfig> foreach = eventsConfig.iterator();
            while (foreach.hasNext()){
                EventsConfig tempEntry = foreach.next();
                if((inputExtrapDate.compareTo(tempEntry.getEventEndDate()) == 0)
                        && tempEntry.getIsEventCreated()){
                    
                    Event e = new Event(tempEntry.getInputEventLatitude(),
                                        tempEntry.getInputEventLongitude(), 
                                        25, //Event Radius
                                        tempEntry.getEventStartDate(), 
                                        tempEntry.getEventEndDate(), 
                                        stepT);            
                    
                    events.add(e);
                } //if 
                
            } //End of while
            
/*            eventFlag = false;
            
            //AbsoluteDate extrapDate = inputExtrapDate;
            AbsoluteDate event1StartDate = new AbsoluteDate(2004, 01, 01, 23, 45, 00.000, inputUTC);
                                    
            if(inputExtrapDate.compareTo(event1StartDate) == 0){
                eventFlag = true;
                event1StartDate = new AbsoluteDate(2004, 01, 01, 23, 45, 00.000, inputUTC);
                AbsoluteDate event1EndDate = new AbsoluteDate(2004, 01, 01, 23, 49, 00.000, inputUTC);
                Event e = new Event(-4.8578, 124.4913, 25, event1StartDate, event1EndDate, stepT);            
                events.add(e);
            }  
            
            AbsoluteDate event2StartDate = new AbsoluteDate(2004, 01, 01, 23, 46, 00.000, inputUTC);
            
            if(inputExtrapDate.compareTo(event2StartDate) == 0){
                eventFlag = true;
                event2StartDate = new AbsoluteDate(2004, 01, 01, 23, 46, 00.000, inputUTC);
                AbsoluteDate event2EndDate = new AbsoluteDate(2004, 01, 01, 23, 50, 00.000, inputUTC);
                Event e = new Event(-6.2045, 142.7606, 25, event2StartDate, event2EndDate, stepT);            
                events.add(e);
            }*/              
            
        } /* end of createEvents method */
        
/* -------------------------------------------------------------------------------------------------- */            
/* All the action happen here. Watch out! */
        
        //Change method name to run or something
        public void timeTick(){
            
            
            
            //Creating 'n' number of satellites
            //Value for 'n' entered in UI
            createSatellites();

            try{                                
                /* Preparing Satellite data for Propagation */
                
                //Change this to a more generic file path
                orekitData = new File("D:\\Anupama Master Thesis\\SGP4");
                manager = DataProvidersManager.getInstance();        
                manager.addProvider(new DirectoryCrawler(orekitData));
                
                //definition of EME2000 inertial frame
                inertialFrame = FramesFactory.getEME2000();
                
                utc = TimeScalesFactory.getUTC();
                
                
                /* -------------------------------------------------------------------------------- */
                //Setting the simulation parameters - 
                //initialDate = Simulation Start Date time
                //finalDate = Simulation End Date time
                
                //initialDate = new AbsoluteDate(2004, 01, 01, 23, 30, 00.000, utc);                

                initializeSimulationTimes();
                
                setUpRandomEventsData();    
                
                nEventsToBeCreated();
                
                System.out.println("Duration " + duration);
                
                
                System.out.println("Simulation Start Date/Time: " 
                                    + getDayInitialDate() + "-" 
                                    + getMonthInitialDate() + "-" 
                                    + getYearInitialDate() + " "
                                    + getHoursInitialTime() + ":"
                                    + getMinutesInitialTime() + ":"
                                    + getMinutesInitialTime());
                
                /*
                System.out.println("Simulation End Date/Time: " 
                        + getDayFinalDate() + "-" 
                        + getMonthFinalDate() + "-" 
                        + getYearFinalDate() + " "
                        + getHoursFinalTime() + ":"
                        + getMinutesFinalTime() + ":"
                        + getMinutesFinalTime());
                */
                //System.out.println("Initial Date: " + initialDate + " Final Date: " + finalDate);
                /* -------------------------------------------------------------------------------- */
            

            } //end of try block
            
            catch(OrekitException e1) {
            System.out.println(" Exception: " + e1); 
            }

            /* --------------------------------------------------------------------------------- */
            
            TransformInputToAlgo1 trnsInput = new TransformInputToAlgo1(satellites,events);            
            /* For loop for Simulation start time to end time            
               Each iteration of the for loop denotes a time slice */
           
            for(AbsoluteDate extrapDate = initialDate;
                    extrapDate.compareTo(finalDate) <= 0;
                    extrapDate = extrapDate.shiftedBy(stepT)){

                //creating events
                this.createEvents(utc, extrapDate);                
                
                
                
                
                //Putting a blank line
 
                    System.out.println("\n");
                    System.out.println("Current Time Slice-" + extrapDate);

                
                /* ------------------- Iterate through all satellites ------------------- */
                //add additional check if satellite list is empty
                    
                    if(!satellites.isEmpty()){
                        satObj = satellites.get(0);
                        System.out.println("#Satellites in the System:" + satObj.getNrSatellites());
                    }
                    
                    //System.out.println("Latitude");
                    for(int k=0; k < (nrSatellites); k++){
                    //for(int k=0; k < 3; k++){    
                          satObj = satellites.get(k);

                              //System.out.println("#Satellites in the System:" + satObj.getNrSatellites());

                        /* Propagate method: Satellite propagates to the next discrete timeslice
                           This method brings the notion of time for satellites */
                        
                        currentStateSat = satObj.propagate(extrapDate);

                            //System.out.println("sat:" + k + " " + currentStateSat.getPVCoordinates()); 

                        /* Search events in visibility range in 'this' time slice */
                        
                        satObj.updateEventsInVisibilityRange(events);

                   }

                      
                      /* --------------------------------------------------------------------------------- */
                      //Calculate the the number of events in the system
                      if(!events.isEmpty()){                      
                      eventCount = 0;
                        //since getEventCount return a static variable, can call get method of any event 
                          Event eventTemp = events.get(0);
                          eventCount = eventTemp.getEventCount();
                          System.out.println("Event(s) in the system-" + eventCount);
                      }
                      
                 /* ------------------- Iterate through all events ------------------- */
                      
          
            if(!events.isEmpty()){
                
                      for(int e=0; e < eventCount; e++){
                          Event eventObj = events.get(e);
                          
                          /* Each event object takes necessary steps in this method 
                             to move to the next discrete timeslice
                             These methods brings the notion of time for events */
                          eventObj.setCurrDateTime(extrapDate);
                          //Current time greater than event end time
                          if((eventObj.getEventCurrDateTime().compareTo(eventObj.getEventEndDateTime()) > 0)){
                              System.out.println("Event "+ eventObj.getEventID() + " no longer active.");
                              //System.out.println("Event Curr time: " + eventObj.getEventCurrDateTime()
                              //                 + " Event End time " + eventObj.getEventEndDateTime());

                              
                              //Instead of removing, just mark the event flag as deleted
                              //events.remove(e);
                              eventObj.setAmIDeleted(true);
                              
                          }
                          eventObj.progressEventTime();
                          eventObj.displayEventObsSplit();                     
                      } /* End of Is empty check */
            }
            
           
            if(!events.isEmpty()){
                 
                Event eventTemp = events.get(0);
                eventCount = eventTemp.getEventCount(); 
                
                for(int e=0; e < eventCount; e++){
                    Event eventObj = events.get(e);
                    if((eventObj.getAmIDeleted()) == true){
                      eventCount = eventObj.reduceEventCount();
                      events.remove(e);                                            
                    }
                }
            }
            

                      /* Counter to keep track of the total simulation timeslices elapsed */
                      simTimeSlotsElapsed = simTimeSlotsElapsed + 1;
                      
                      
                      /* Execute Hungarian Algorithm */

                      double[][] costMatrix = trnsInput.transformInput();
                        
                      int nrRowsCM = trnsInput.getNrRows();
                      int nrColumnsCM = trnsInput.getNrColumns();
                      
                      System.out.println();
                      System.out.println("Hungarian Algorithm: #Rows-" + nrRowsCM + " #Columns-" + nrColumnsCM);
                      
                      
                      HungarianAlgorithm Halgo = new HungarianAlgorithm(costMatrix);
                      int[] result = Halgo.execute();
                      
                      int lengthtemp = 0;
                      
                      if(nrRowsCM > nrColumnsCM){
                          lengthtemp = nrRowsCM;
                      }else
                      {
                          lengthtemp = nrColumnsCM;
                      }
                      
                      
                      for(int i = 0; i<lengthtemp; i++){
                          if (result[i] >= 0){
                              if(i==0){
                                System.out.println("Hungarian Algorithm Result array");
                              }
                              System.out.println("Satellite-" + i + " is observing event-" + result[i]);
                          }
                          
                      }                      
                      
                      for(int i=0; 
                              i<(satellites.get(0).getNrSatellites()); 
                              i++){
                          
                              
                              //ensuring that the dummy event created is not assigned to any satellites
                              //dummy event is created to match number of rows and columns

                                  //System.out.println("sat" + i + " assigned to event " + result[i]);
                                  //System.out.println("Hungarian Algorithm Assignment");
                                  if(!events.isEmpty()){
                                      //minus 1 not a valid assignment
                                      if(result[i]>=0){
                                          //System.out.println("Event ID" + events.get(result[i]).getEventID());
                                          events.get(result[i]).updateCurrTimeSlotObserved(true, i);
                                          events.get(result[i]).updateEventStatus();
                                          System.out.println("Event: " + result[i] + " is being observed by Satellite: " + i);
                                      }
                          } 
                      }
                      if(!events.isEmpty()){
                          for(int e=0; 
                                  e<(events.get(0).getEventCount());
                                  e++){
                              System.out.println("Event ID:" + events.get(e).getEventID());
                              events.get(e).updateEventStatus();
                          } 
                      } 
                      
                      if(!((nrRowsCM == 0) || (nrColumnsCM == 0))){
                          costMatrix = trnsInput.transformInput();
                          System.out.println();
                          System.out.println("Hungarian Algorithm Cost matrix");
                          for(int m=0; m<nrRowsCM; m++){
                              for(int n=0; n<nrColumnsCM; n++){
                                  System.out.print(costMatrix[m][n] + " ");
                              }
                              System.out.println();
                          } 
                      }
            } /* end of looping through simulation time*/            
                                
        } /* end of timeTick() method*/
        
/* --------------------------------------------------------------------------------------------------
 * Methods to get/set GUI parameters
 * -------------------------------------------------------------------------------------------------- */        
        
        /* ------------------------------ get Number of Satellites ----------------------------------- */
        public int getNrSatellites(){
            return nrSatellites;
        }
        
        
        /* ------------------------------ set Number of Satellites ----------------------------------- */
        public void setNrSatellites(int inputNrSatellites){
            nrSatellites = inputNrSatellites;
        }
        
        /* ------------------------------ get Number of Events ----------------------------------- */
        public int getNrEvents(){
            return nrEvents;
        }
        
        
        /* ------------------------------ set Number of Events ----------------------------------- */
        public void setNrEvents(int inputNrEvents){
            nrEvents = inputNrEvents;
        }
        
        /* ------------------------------------------------------------------------------------------*
         * Initial date methods                                                                      *
         * ------------------------------------------------------------------------------------------*/
        
        /* ------------------------------------------------------------------------------------------*/
        //set simulation Initial date 

        public void setInitialDate(int inputYearInitialDate, 
                                   int inputMonthInitialDate, 
                                   int inputDayInitialDate){
            
            yearInitialDate = inputYearInitialDate;
            monthInitialDate = inputMonthInitialDate + 1;
            dayInitialDate = inputDayInitialDate;
        
        }
        
        /* ------------------------------------------------------------------------------------------*/
        //get year of Initial date 
        
        public int getYearInitialDate(){
            return yearInitialDate;
        }
        
        /* ------------------------------------------------------------------------------------------*/
        //get month of Initial date 
        
        public int getMonthInitialDate(){
               return monthInitialDate;
        }
        
        /* ------------------------------------------------------------------------------------------*/
        //get Day of Initial date 
        
        public int getDayInitialDate(){
            return dayInitialDate;
        }
        
        /* ------------------------------------------------------------------------------------------*/
        //Display Initial Date Entered
        
        public void displayInitialDate(){
            System.out.println("Sim Start Date: " + getYearInitialDate() + " " + getMonthInitialDate() + " " + getDayInitialDate());
        }

        
        /* ------------------------------------------------------------------------------------------
         * Final Date methods
         * ------------------------------------------------------------------------------------------ */
        //set simulation Final date 

        public void setFinalDate(int inputYearFinalDate, 
                                       int inputMonthFinalDate, 
                                       int inputDayFinalDate){
            yearFinalDate = inputYearFinalDate;
            monthFinalDate = inputMonthFinalDate + 1;
            dayFinalDate = inputDayFinalDate;
        }
        
        /* ------------------------------------------------------------------------------------------*/
        //get year of final date 
        
        public int getYearFinalDate(){
            return yearFinalDate;
        }
        
        /* ------------------------------------------------------------------------------------------*/
        //get month of final date 
        
        public int getMonthFinalDate(){
               return monthFinalDate;
        }
        
        /* ------------------------------------------------------------------------------------------*/
        //get Day of final date 
        
        public int getDayFinalDate(){
            return dayFinalDate;
        }
        
        /* ------------------------------------------------------------------------------------------*/
        //Display Final Date Entered
        
        public void displayFinalDate(){
            System.out.println("Sim End Date entered: " + getYearFinalDate() + " " + getMonthFinalDate() + " " + getDayFinalDate());
        }
        
        
        
        /* ------------------------------------------------------------------------------------------
         * Initial Time methods
         * ------------------------------------------------------------------------------------------ */
        //set simulation Initial time 

        public void setInitialTime(int inputHoursInitialTime, 
                                   int inputMinutesInitialTime, 
                                   int inputSecondsInitialTime){
            hoursInitialTime = inputHoursInitialTime;
            minutesInitialTime = inputMinutesInitialTime;
            secondsInitialTime = inputSecondsInitialTime;
        }
        
        /* ------------------------------------------------------------------------------------------*/
        //get hours of initial time 
        
        public int getHoursInitialTime(){
            return hoursInitialTime;
        }
        
        /* ------------------------------------------------------------------------------------------*/
        //get minutes of initial time 
        
        public int getMinutesInitialTime(){
               return minutesInitialTime;
        }
        
        /* ------------------------------------------------------------------------------------------*/
        //get seconds of initial time 
        
        public int getSecondsInitialTime(){
            return secondsInitialTime;
        }
        
        /* ------------------------------------------------------------------------------------------*/
        //Display Initial Time Entered
        
        public void displayInitialTime(){
            System.out.println("Sim Start Time entered: " 
                              + getHoursInitialTime() + " " 
                              + getMinutesInitialTime() + " " 
                              + getSecondsInitialTime());
        }        
                
        /* ------------------------------------------------------------------------------------------
         * Final Time methods
         * ------------------------------------------------------------------------------------------ */
        //set simulation Final time 

        public void setFinalTime(int inputHoursFinalTime,
                                       int inputMinutesFinalTime,
                                       int inputSecondsFinalTime){
            hoursFinalTime = inputHoursFinalTime;
            minutesFinalTime = inputMinutesFinalTime;
            secondsFinalTime = inputSecondsFinalTime;
        }
        
        /* ------------------------------------------------------------------------------------------*/
        //get hours of final time 
        
        public int getHoursFinalTime(){
            return hoursFinalTime;
        }
        
        /* ------------------------------------------------------------------------------------------*/
        //get minutes of final time 
        
        public int getMinutesFinalTime(){
               return minutesFinalTime;
        }
        
        /* ------------------------------------------------------------------------------------------*/
        //get seconds of final time 
        
        public int getSecondsFinalTime(){
            return secondsFinalTime;
        }
        
        /* ------------------------------------------------------------------------------------------*/
        //Display Final Time Entered
        
        public void displayFinalTime(){
            System.out.println("Sim End Time entered: " + getHoursFinalTime() + " " + getMinutesFinalTime() + " " + getSecondsFinalTime());
        }
        
        /* ------------------------------------------------------------------------------------------*/
        //Get Time Slice
        public double getTimeSlice(){
            return stepT;
        }
        
        
        /* ------------------------------------------------------------------------------------------*/
        //Set Time Slice
        public void setTimeSlice(double inputTimeSlice){
            stepT = inputTimeSlice;
        }
        
 
        /* ------------------------------------------------------------------------------------------*/
        
        
        
} /* end of Time Class */
