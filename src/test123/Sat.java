package test123;
import org.orekit.*;  
import org.orekit.utils.PVCoordinates;
import org.hipparchus.geometry.euclidean.threed.Vector3D;
import org.orekit.orbits.CartesianOrbit;
//import org.hipparchus.geometry.euclidean.threed.Vector3D;
//import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.orekit.orbits.Orbit;
import org.orekit.propagation.*;    
import org.orekit.attitudes.*;
import org.orekit.propagation.AbstractPropagator.*;
import org.orekit.orbits.*;
import org.orekit.frames.*;
import org.orekit.time.*;
import org.orekit.propagation.*;
import org.orekit.propagation.analytical.*;
import org.orekit.errors.OrekitException;

import java.lang.Object.*; 
import java.lang.*;
import java.io.*;

//import org.apache.commons.math3.*;  
//import org.apache.commons.math3.geometry.Vector;
import org.orekit.time.*;
import org.orekit.orbits.*;

import java.lang.String;
import java.util.ArrayList;
import java.util.Iterator;

import org.orekit.bodies.*;
//import test123/orekit/orbits/PositionAngle;
import org.orekit.orbits.PositionAngle;
import org.orekit.data.*;
import org.orekit.orbits.*;

import java.text.DecimalFormat;

/* Class Sat */
public class Sat { 

    //Private Data Members
    
    //Satellite Identifier
    int satID;
    
    //Keeps count of the number of satellites
    static int satelliteCounter = 0;
    
    //An Array list for storing 'a' satellite's data
    private ArrayList<SatelliteData> satData;
       
    File orekitData;
    Frame inertialFrame;
    TimeScale utc;
    
    AbsoluteDate initialDate;
    AbsoluteDate finalDate;
    AbsoluteDate extrapDate;
    
    KeplerianPropagator kepler;
    Orbit initialOrbit;
    
    double stepT = 60.;
    double duration = 600.;
    int cpt = 1;
    
    DataProvidersManager manager;
    SpacecraftState currentState;
    
    double mu =  3.986004415e+14;

    double a = 24396159;                 // semi major axis in meters
    double e = 0.72831215;               // eccentricity
    double i = Math.toRadians(7);        // inclination
    double omega = Math.toRadians(180);  // perigee argument
    double raan = Math.toRadians(261);   // right ascension of ascending node
    double lM = 0;                       // mean anomaly
    
    ArrayList<Event> eventsInVisibilityRange;
    double currSatLatitude;
    double currSatLongitude;
    double currSatHeight;
    double satVisibilityRadius = 0;
    
    
/* ------------------------------------------------------------------------------------------------ */    
//Constructor1
        public Sat(int yearInitialDate, int monthInitialDate, int dayInitialDate,
                        int hoursInitialDate, int minutesInitialDate, int secondsInitialDate){
        
        //Initializing the satellite object with ID        
        satID = satelliteCounter;
        
        //Keeps track of the number of satellites created
        //Used in assigning Satellite ID
        satelliteCounter = satelliteCounter + 1;  
        
        //Setting the visibility range for the satellite
        //could later change it to receive input from the GUI as well
        //satVisibilityRadius = 1000;
        
        satData = new ArrayList<SatelliteData>();
        
        //Creating a dynamic array to store events in visibility Range
        eventsInVisibilityRange = new ArrayList<Event>();
        
        try{               
            //Change this to a more generic file path
            orekitData = new File("D:\\Anupama Master Thesis\\SGP4");
            manager = DataProvidersManager.getInstance();        
            manager.addProvider(new DirectoryCrawler(orekitData));
            
            //definition of EME2000 inertial frame
            inertialFrame = FramesFactory.getEME2000();
            
            //Initial State

            utc = TimeScalesFactory.getUTC();
            //initialDate = new AbsoluteDate(2004, 01, 01, 23, 30, 00.000, utc);
            initialDate = new AbsoluteDate(yearInitialDate, monthInitialDate, dayInitialDate,
                                            hoursInitialDate, minutesInitialDate, secondsInitialDate, utc);
            //Orbit Definition
            initialOrbit = new KeplerianOrbit(a, e, i, omega, raan, lM, PositionAngle.MEAN,
                    inertialFrame, initialDate, mu);    
            
            kepler = new KeplerianPropagator(initialOrbit);
            kepler.setSlaveMode();

            duration = 600.;
            finalDate = initialDate.shiftedBy(duration);
            }
            catch(OrekitException e1){
                System.out.println(" Exception: \n" + e1);
            }
    }

    /* ------------------------------------------------------------------------------------------------ */    
  //Constructor2
      public Sat(org.hipparchus.geometry.euclidean.threed.Vector3D inputPosition, 
                 org.hipparchus.geometry.euclidean.threed.Vector3D inputVelocity){
          
          //Initializing the satellite object with ID        
          satID = satelliteCounter;
          
          //Keeps track of the number of satellites created
          //Used in assigning Satellite ID
          satelliteCounter = satelliteCounter + 1;  
          
          //Setting the visibility range for the satellite
          //could later change it to receive input from the GUI as well
          //satVisibilityRadius = 1000;
          
          satData = new ArrayList<SatelliteData>();
          
          //Creating a dynamic array to store events in visibility Range
          eventsInVisibilityRange = new ArrayList<Event>();
          
          try{               
              //Change this to a more generic file path
              orekitData = new File("D:\\Anupama Master Thesis\\SGP4");
              manager = DataProvidersManager.getInstance();        
              manager.addProvider(new DirectoryCrawler(orekitData));
              
              //definition of EME2000 inertial frame
              inertialFrame = FramesFactory.getEME2000();
              
              //Initial State

              utc = TimeScalesFactory.getUTC();
              initialDate = new AbsoluteDate(2004, 01, 01, 23, 30, 00.000, utc);
              
              PVCoordinates pvCoordinates = new PVCoordinates(inputPosition, inputVelocity);
              AbsoluteDate initialDate = new AbsoluteDate(2004, 01, 01, 23, 30, 00.000, TimeScalesFactory.getUTC());
              Frame inertialFrame = FramesFactory.getEME2000();
              Orbit initialOrbit =
                      new CartesianOrbit(pvCoordinates, inertialFrame, initialDate, mu);
              
              
              
              //Orbit Definition
              //initialOrbit = new KeplerianOrbit(a, e, i, omega, raan, lM, PositionAngle.MEAN,
              //        inertialFrame, initialDate, mu);    
              
              kepler = new KeplerianPropagator(initialOrbit);
              kepler.setSlaveMode();

              duration = 600.;
              finalDate = initialDate.shiftedBy(duration);
              }
              catch(OrekitException e1){
                  System.out.println(" Exception: \n" + e1);
              }
      }
      
/* ------------------------------------------------------------------------------------------------------ */
//Constructor which takes PV Coordinates as input
    
/* ------------------------------------------------------------------------------------------------------ */
public static int getNrSatellites(){
    return satelliteCounter;
}      
      
      
      
       //Public Methods
       public SpacecraftState propagate(AbsoluteDate inputExtrapDate){
           try{
               
           currentState = kepler.propagate(inputExtrapDate);
           
           extrapDate = inputExtrapDate;
           
           //Update current position
           updateCurrentPosition();
           
           //DecimalFormat df = new DecimalFormat("####0.00");
           
           //Printing Position at each time interval
           //double a = 1123456789.123456789;
           //DecimalFormat df2 = new DecimalFormat("0.0000");
           //df2.format(a);
           //System.out.println("DF: " + a);
           //System.out.printf("%1$.2f", a);
           
           double satLatitude = getCurrSatLatitude();
           double satLongitude = getCurrSatLongitude();
           double satHeight = getCurrHeight();
           
           System.out.println();
           System.out.printf("Sat ID: %d | Latitude:  %.2f | Longitude: %.2f | Height: %.2f ", 
                             satID, satLatitude, satLongitude, satHeight);
           
           
/*           System.out.println(" | Latitude: " + satLatitude +  
                              " | Longitude: " + getCurrSatLongitude() + 
                              " | Height: "    + getCurrHeight() +
                              " |");*/
           }
           catch(OrekitException e){
               System.out.println("Error in retrieving Satellite Coordinates: " + e);
           }
           return currentState;
       } // end of propagate function
 
/* --------------------------- Get the current position of the satellite ------------------------------- */       
//Extract PV Coordinates in String format and convert to ECEF format to LLH
       
public void updateCurrentPosition(){
    
    String strPVCoordinates = new String(currentState.getPVCoordinates().toString());
    //System.out.println("strPVVoordinates:" + currentState.getPVCoordinates().toString());
    
    strPVCoordinates = strPVCoordinates.replaceAll("[{}()PVA]", "");
    //System.out.println("Str PV co: after replaceAll" + strPVCoordinates);
    String arrayPVCoordinates[] = strPVCoordinates.split(",");
    
    double latitudeECEF = Double.parseDouble(arrayPVCoordinates[1]);
    double longitudeECEF = Double.parseDouble(arrayPVCoordinates[2]);
    double heightECEF = Double.parseDouble(arrayPVCoordinates[3]);
    //System.out.println("EECF- lat: " + latitudeECEF + "long:" + longitudeECEF + "height: " + heightECEF);

    /* ------------------------ Convert ECEF to LLA -------------------------- */
    ECEF2LLA a = new ECEF2LLA();
    
    double ecef[];
    ecef = new double[3];
    ecef[0] = latitudeECEF;
    
    ecef[1] = longitudeECEF;
    ecef[2] = heightECEF;
    
    double llh[];
    llh = new double[3];
    
    llh = a.convertecef2lla(ecef);
   /* ----------------------------------------------------------------------- */
    currSatLatitude = llh[0];
    currSatLongitude = llh[1];
    currSatHeight = llh[2];
   /* ----------------------------------------------------------------------- */
    
    /* ------------------ Storing co-ordinates in Array --------------------- */
    SatelliteData satDataTemp = new SatelliteData();                    
    
    satDataTemp.setCurrDateTimeSat(extrapDate);
    satDataTemp.setLatitude(llh[0]);
    satDataTemp.setLongitude(llh[1]);
    satDataTemp.setHeight(llh[2]);
    
    satData.add(satDataTemp);
    //i++;
    
}


/* -------------------------------------------------------------------------------------------- */
public double getCurrSatLatitude(){
    return currSatLatitude;
}


/* -------------------------------------------------------------------------------------------- */
public double getCurrSatLongitude(){
    return currSatLongitude;
}


/* -------------------------------------------------------------------------------------------- */
public double getCurrHeight(){
    return currSatHeight;
}


/* -------------------------------------------------------------------------------------------- */
public void displaySatPropagation(){
    /*------------- Iterator for retrieving the stored Satellite Propagation Values ----------- */
    int j = 0;
    Iterator<SatelliteData> foreach = satData.iterator();
    System.out.println("Date/Time                  " 
            + "Latitude               " 
            + "Longitude                " 
            + "Height");
    
    while (foreach.hasNext()){
        SatelliteData tempSatData = foreach.next();
        j++;

        System.out.println(tempSatData.getCurrDateTimeSat() + "    " 
                           + tempSatData.getLatitude() + "    "
                           + tempSatData.getLongitude() + "    "
                           + tempSatData.getHeight());
    } /* end of while loop */            
}



/* ------------------------------- Return SatelliteID ------------------------------------------ */       
       public int getSatNo(){
           return satID;
       }

/* -------------------------------- Clear Events in the visibility range ----------------------- */
//Used before computing the events in visibility range
//This helps to clear the events previously in visibility range
       void clearEventsInVisibilityList(){
           
       }
/* -------------------------------- Search for events in visibility range ------------------------------- */       
       //Add input parameter - array of Event Objects
void updateEventsInVisibilityRange(ArrayList<Event> inputEvents){
    //determine events in visibility range

    /* loop through all the events and for every event, determine if the event is in the 
       visibility range of "this" satellite. Performed by comapring the distance b/w 
       current position of satellite and event location */
    int j = 0;
    
    double evLatitude;
    double evLongitude;
    
    double satAndEventDist;
    
    //Remove all events from previous time slots, before searching for events in 'this' time slot
    eventsInVisibilityRange.clear();
    
    Iterator<Event> foreach = inputEvents.iterator();
    while (foreach.hasNext()){
        Event ev = foreach.next();
        
        satAndEventDist = 0;
        
        evLatitude = ev.getEventLatitude();
        evLongitude = ev.getEventLongitude();
        
        satAndEventDist = distance(evLatitude, evLongitude, currSatLatitude, currSatLongitude, "K");
        
        if(satAndEventDist <= satVisibilityRadius){
            //Add to the list of events in visibility range
            eventsInVisibilityRange.add(ev);  
            System.out.println("Event: "+ ev.getEventID() +" in visibility Range of Satellite-" + satID);
        }
        
        j++;
    }//end of while loop  

 }      

/* -------------------------------- get/set visibility radius ----------------------- */

public void setSatVisibilityRadius(double isatVisibilityRadius){
    satVisibilityRadius = isatVisibilityRadius;
}


public double getSatVisibilityRadius(){
    return satVisibilityRadius;
}


//The methods- distance, deg2rad and rad2deg in the below section have been used from the website:
//https://www.geodatasource.com/developers/java
/*::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
/*::                                                                         :*/
/*::  This routine calculates the distance between two points (given the     :*/
/*::  latitude/longitude of those points). It is being used to calculate     :*/
/*::  the distance between two locations using GeoDataSource (TM) prodducts  :*/
/*::                                                                         :*/
/*::  Definitions:                                                           :*/
/*::    South latitudes are negative, east longitudes are positive           :*/
/*::                                                                         :*/
/*::  Passed to function:                                                    :*/
/*::    lat1, lon1 = Latitude and Longitude of point 1 (in decimal degrees)  :*/
/*::    lat2, lon2 = Latitude and Longitude of point 2 (in decimal degrees)  :*/
/*::    unit = the unit you desire for results                               :*/
/*::           where: 'M' is statute miles (default)                         :*/
/*::                  'K' is kilometers                                      :*/
/*::                  'N' is nautical miles                                  :*/
/*::  Worldwide cities and other features databases with latitude longitude  :*/
/*::  are available at https://www.geodatasource.com                          :*/
/*::                                                                         :*/
/*::  For enquiries, please contact sales@geodatasource.com                  :*/
/*::                                                                         :*/
/*::  Official Web site: https://www.geodatasource.com                        :*/
/*::                                                                         :*/
/*::           GeoDataSource.com (C) All Rights Reserved 2017                :*/
/*::                                                                         :*/
/*::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/

private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
    double theta = lon1 - lon2;
    double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
    dist = Math.acos(dist);
    dist = rad2deg(dist);
    dist = dist * 60 * 1.1515;
    if (unit == "K") {
            dist = dist * 1.609344;
    } else if (unit == "N") {
            dist = dist * 0.8684;
    }
 
    return (dist);
}

/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
/*::    This function converts decimal degrees to radians                                                :*/
/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
private static double deg2rad(double deg) {
    return (deg * Math.PI / 180.0);
}

/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
/*::    This function converts radians to decimal degrees                                                :*/
/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
private static double rad2deg(double rad) {
    return (rad * 180 / Math.PI);
}


} //end of class