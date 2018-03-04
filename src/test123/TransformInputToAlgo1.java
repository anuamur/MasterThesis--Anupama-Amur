package test123;

import java.util.ArrayList;
import java.util.Iterator;

public class TransformInputToAlgo1 {

    private double[][] costMatrix;
    private ArrayList<Sat> satObjs;
    private ArrayList<Event> eventObjs;
    
    private int nrRows;
    private int nrColumns;
    
    private boolean dummyEventAdded = false;
    private boolean dummySatelliteAdded = false;
    /* --------------------------------------------------------------------------------- */
    public TransformInputToAlgo1(ArrayList<Sat> inputSatObjs, 
                                 ArrayList<Event> inputEventObjs){
        
        //Initializing number of rows and columns
        nrRows=0;
        nrColumns=0;        
               
        satObjs = inputSatObjs;
        eventObjs = inputEventObjs;
    }
    
    
    /* --------------------------------------------------------------------------------- */
    public double[][] transformInput(){
        //satObjs.clear();
        //eventObjs.clear();
        
        //System.out.println("Inside transform input");
        
        dummyEventAdded = false;
        dummySatelliteAdded = false;
        
        nrRows=0;
        nrColumns=0; 
        
        determineNrRowsColumns();
        addDummyEventColumnOrSatelliteRow();
        populateValuesCostMatrix();
        return costMatrix;
    }   
    
    public void removeEvent(int index){
        
    }
    /* --------------------------------------------------------------------------------- */
    private void determineNrRowsColumns(){
    
      //Retrieving number of satellites / Rows
        if(!satObjs.isEmpty()){
            Sat s = satObjs.get(0);
            //System.out.println("no of sat: " + s.getNrSatellites());
            
            nrRows = s.getNrSatellites();
        }

        //Retrieving number of events / Columns
        if(!eventObjs.isEmpty()){
            if(eventObjs.get(0).getEventCount() != 0){
                Event e = eventObjs.get(0);
                //System.out.println("No of events: " + e.getEventCount());
                nrColumns = e.getEventCount();
            }
        }
        /*
        if(nrRows < nrColumns){
            nrRows = nrColumns;
            
            //Assign high values to the extra / dummy event
            //eventObjs.get((nrColumns-1)).setPercentObserved(100);
            dummySatelliteAdded = true;
            
            
        }
        
        if(nrColumns < nrRows){
            nrColumns = nrRows;
            
            dummyEventAdded = true;
        }
          */
        
        
            //Initializing the Cost Matrix
            //costMatrix = null;
            costMatrix = new double [nrRows][nrColumns];
            //System.out.println("Nr Rows: " + nrRows + " & Nr Columns: " + nrColumns);
            
    } /* End of method- determineNrRowsColumns() */
    
    
    /* --------------------------------------------------------------------------------- */
    private void populateValuesCostMatrix(){
        if(!eventObjs.isEmpty()){
            //Iterating through all events
            Iterator<Event> foreachEvent = eventObjs.iterator();
            int j=0;
            
            while (foreachEvent.hasNext()){
                Event ev = foreachEvent.next();
                
                ArrayList<EventObsSplitPerSat> evObsSplitPerSatObj = ev.getEventObsSplitPerSatObj();       
                Iterator<EventObsSplitPerSat> foreachSatObj = evObsSplitPerSatObj.iterator();
                
                while(foreachSatObj.hasNext()){
                    
                    EventObsSplitPerSat eSplit = foreachSatObj.next();
                    int satID = eSplit.getSatID();
                    
                    if(satID <= nrRows){
                        costMatrix[satID][j] = eSplit.getPercentObsBySat();
                    }
                    else{
                        System.out.println("Sat ID Out of Bounds \n");
                    }
                } //end of while loop - iterating through satellites observed by event 'j'
                
                j++;
            }//end of while loop - iterating through events
            
            
            /*
            System.out.println("Cost matrix");
            for(int m=0; m<nrRows; m++){
                for(int n=0; n<nrColumns; n++){
                    System.out.print(costMatrix[m][n] + " ");
                }
                System.out.println();
            }
            */
            
            
        } /* isEmpty Check */
    } /* End of Function */
    
    
    private void addDummyEventColumnOrSatelliteRow(){
        
    }
    
    /* ------------------------------------------------------------------------------- */
    public int getNrRows(){
        return nrRows;
    }
    
    /* ------------------------------------------------------------------------------- */
    public int getNrColumns(){
        return nrColumns;
    }
    
    
    
} /* End of Class */
