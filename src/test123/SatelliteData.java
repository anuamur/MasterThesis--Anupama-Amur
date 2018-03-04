package test123;

import org.orekit.time.AbsoluteDate;

public class SatelliteData {
    private AbsoluteDate currDateTimeSat;
    private double latitude;
    private double longitude;
    private double height;

    /*--------------------------------- Get Methods ------------------------------------------ */
    public void setCurrDateTimeSat(AbsoluteDate inputcurrDateTimeSat){
        currDateTimeSat = inputcurrDateTimeSat;
    }    
    public void setLatitude(double inputLatitude){
        latitude = inputLatitude;
    }

    public void setLongitude(double inputLongitude){
        longitude = inputLongitude;
    }
    
    public void setHeight(double inputHeight){
        height = inputHeight;
    }
    
    /*--------------------------------- Set Methods ------------------------------------------ */
    public AbsoluteDate getCurrDateTimeSat(){
        return currDateTimeSat;
    }    
    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }
    
    public double getHeight(){
        return height;
    }
}
