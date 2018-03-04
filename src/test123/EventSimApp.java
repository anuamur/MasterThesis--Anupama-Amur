package test123;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Spinner;

public class EventSimApp {

    /**
     * Launch the application.
     * @param args
     */

    /*----------------------------------------------------------------------------------------------------- */
    /* Private variables for EventSimApp */
    public static Time timeObj = new Time();
    private static Text nrSatellites;
    
    //Simulation Start Date variables
    private static DateTime varSimStartDate;
    static int yearSimStartDate=2004;
    static int monthSimStartDate=0;
    static int daySimStartDate=1;
    
    //Simulation Start Time Variables
    private static DateTime varSimStartTime;
    static int hoursSimStartTime=23;
    static int minutesSimStartTime=30;
    static int secondsSimStartTime=00;
        
    //Simulation End Date variables
    private static DateTime varSimEndDate;
    static int yearSimEndDate=2004;
    static int monthSimEndDate=0;
    static int daySimEndDate=1;
    
    //Simulation End Time Variables
    private static DateTime varSimEndTime;
    static int hoursSimEndTime=23;
    static int minutesSimEndTime=59;
    static int secondsSimEndTime=00;
    
    private static Text varTimeSlice;
    private static Text varNrEvents;
    
    /* ---------------------------------------------------------------------------------------------------- */
    
    /* ---------------------------------------------------------------------------------------------------- */
    /* ------------------------------------ main method() ------------------------------------------------- */
    public static void main(String[] args) {
        Display display = Display.getDefault();
        Shell shlParent = new Shell();
        shlParent.setSize(587, 375);
        shlParent.setText("Satellites and Events Simulation App");

        //Satellite Default value
        int intNrSatellites = 1;
        int intNrEvents;
        
/* --------------------------------------------------------------------------------------------------------- */
        shlParent.setLayout(null);
        //Receive number of satellites
        nrSatellites = new Text(shlParent, SWT.BORDER);
        nrSatellites.setText("1");
        nrSatellites.setBounds(203, 162, 41, 21);
     
        nrSatellites.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent arg0) {
                //custom
                String strNrSatellites = (((Text)arg0.widget).getText()).toString();
                //System.out.println("Satellite Number str Value: " + strNrSatellites);
                
                try{
                    int intNrSatellites = Integer.parseInt(strNrSatellites);
                    timeObj.setNrSatellites(intNrSatellites);
                    System.out.println("Number of Satellites Entered: " + timeObj.getNrSatellites());
                }catch (NumberFormatException ex) {
                    System.out.println("Entered value is not numeric \n");
                    System.out.println(ex.getMessage());
                } 
            }
        });
        
        Label lblNrSatellites = new Label(shlParent, SWT.NONE);
        lblNrSatellites.setBounds(70, 165, 110, 15);
        lblNrSatellites.setText("Number of Satellites");
/* ----------------------------------------------------------------------------------------------------------- */
        
        //Receive Simulation Start Date
        varSimStartDate = new DateTime(shlParent, SWT.BORDER);
        varSimStartDate.setBounds(203, 43, 87, 21);
        varSimStartDate.setDate(yearSimStartDate, monthSimStartDate, daySimStartDate);
        
        timeObj.setInitialDate(varSimStartDate.getYear(), (varSimStartDate.getMonth()), varSimStartDate.getDay());
        timeObj.displayInitialDate();
        
        varSimStartDate.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent arg0) {
                //System.out.println("Simulation Start Date: " + varSimStartDate);            
                
                yearSimStartDate = varSimStartDate.getYear();                
                monthSimStartDate = varSimStartDate.getMonth();
                daySimStartDate = varSimStartDate.getDay();
                
                timeObj.setInitialDate(varSimStartDate.getYear(), varSimStartDate.getMonth(), varSimStartDate.getDay());
                timeObj.displayInitialDate();
                
            }
        });

        /* --------------------------------------------------------------------- */
        //Receive Simulation Start Time
        
        varSimStartTime = new DateTime(shlParent, SWT.BORDER | SWT.TIME);
        varSimStartTime.setBounds(315, 43, 94, 21);
        varSimStartTime.setTime(hoursSimStartTime, minutesSimStartTime, secondsSimStartTime);

        timeObj.setInitialTime(varSimStartTime.getHours(), varSimStartTime.getMinutes(), varSimStartTime.getSeconds());
        timeObj.displayInitialTime();
        
        varSimStartTime.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent arg0) {
                hoursSimStartTime = varSimStartTime.getHours();
                minutesSimStartTime = varSimStartTime.getMinutes();
                secondsSimStartTime = varSimStartTime.getSeconds();
            
                timeObj.setInitialTime(varSimStartTime.getHours(), varSimStartTime.getMinutes(), varSimStartTime.getSeconds());
                timeObj.displayInitialTime();
            }
        });
        
        /* -------------------------------------------------------------------- */
        
        Label lblSimulationStartDateTime = new Label(shlParent, SWT.NONE);
        lblSimulationStartDateTime.setBounds(12, 49, 173, 15);
        lblSimulationStartDateTime.setText("Simulation Start Date and Time");
        
        Label lblSimulationEndDateTime = new Label(shlParent, SWT.NONE);
        lblSimulationEndDateTime.setBounds(12, 87, 160, 15);
        lblSimulationEndDateTime.setText("Simulation End Date and Time");
        
        
        /* ------------------------------------------------------------------- */
        
        varSimEndDate = new DateTime(shlParent, SWT.BORDER);
        varSimEndDate.setBounds(203, 81, 87, 21);
        varSimEndDate.setDate(yearSimEndDate, monthSimEndDate, daySimEndDate);
        
        timeObj.setFinalDate(varSimEndDate.getYear(), varSimEndDate.getMonth(), varSimEndDate.getDay());
        timeObj.displayFinalDate();
        
        varSimEndDate.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent arg0) {
                yearSimEndDate = varSimEndDate.getYear();
                monthSimEndDate = varSimEndDate.getMonth();
                daySimEndDate = varSimEndDate.getDay();
                
                timeObj.setFinalDate(varSimEndDate.getYear(), varSimEndDate.getMonth(), varSimEndDate.getDay());
                timeObj.displayFinalDate();
            }
        });
        
        /* ------------------------------------------------------------------- */
        
        varSimEndTime = new DateTime(shlParent, SWT.BORDER | SWT.TIME);
        varSimEndTime.setBounds(315, 81, 94, 21);
        
        varSimEndTime.setTime(hoursSimEndTime, minutesSimEndTime, secondsSimEndTime);

        timeObj.setFinalTime(varSimEndTime.getHours(), varSimEndTime.getMinutes(), varSimEndTime.getSeconds());
        timeObj.displayFinalTime();
        
        varSimEndTime.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent arg0) {
                hoursSimEndTime = varSimEndTime.getHours();
                minutesSimEndTime = varSimEndTime.getMinutes();
                secondsSimEndTime = varSimEndTime.getSeconds();
            
                timeObj.setFinalTime(varSimEndTime.getHours(), varSimEndTime.getMinutes(), varSimEndTime.getSeconds());
                timeObj.displayFinalTime();
            }
        });
        
        /* ------------------------------------------------------------------ */
        
        Label lblNrEvents = new Label(shlParent, SWT.NONE);
        lblNrEvents.setBounds(80, 206, 100, 15);
        lblNrEvents.setText("Number of Events");
        
/* ------------------------------------------------------------------------------------------------- */
        
        varTimeSlice = new Text(shlParent, SWT.BORDER);
        varTimeSlice.setBounds(203, 121, 41, 21);

        varTimeSlice.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent arg0) {
                String strTimeSlice = (((Text)arg0.widget).getText()).toString();

                
                try{
                    double doubleTimeSlice = Double.parseDouble(strTimeSlice);
                    //passing event number to time object
                    timeObj.setTimeSlice(doubleTimeSlice);
                    System.out.println("Time Slice entered: " + timeObj.getTimeSlice());
               }catch (NumberFormatException ex) {
                    System.out.println("Entered value is not numeric \n");
                    System.out.println(ex.getMessage());
               }
                
            }
        });
        
        Label lblTimeSlice = new Label(shlParent, SWT.NONE);
        lblTimeSlice.setBounds(120, 127, 55, 15);
        lblTimeSlice.setText("Time Slice");
        
        Menu menu = new Menu(shlParent, SWT.BAR);
        shlParent.setMenuBar(menu);
        
        varNrEvents = new Text(shlParent, SWT.BORDER);
        varNrEvents.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent arg0) {
                String strNrEvents = (((Text)arg0.widget).getText()).toString();

                
                try{
                    int intNrEvents = Integer.parseInt(strNrEvents);
                    //passing event number to time object
                    timeObj.setNrEvents(intNrEvents);
                    System.out.println("Number of Events: " + timeObj.getNrEvents());
               }catch (NumberFormatException ex) {
                    System.out.println("Entered value is not numeric \n");
                    System.out.println(ex.getMessage());
               }
                
                
            }
        });
        varNrEvents.setBounds(203, 203, 41, 21);
        
        
/* --------------------------------------------------------------------------------------------------------- */        
        Button btnRun = new Button(shlParent, SWT.NONE);
        btnRun.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDoubleClick(MouseEvent arg0) {
                timeObj.timeTick();
            }
        });
        btnRun.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent arg0) {

            }
        });
        btnRun.setBounds(160, 282, 75, 25);
        btnRun.setText("Run");
        
        Menu menu_1 = new Menu(shlParent);
        shlParent.setMenu(menu_1);
/* ---------------------------------------------------------------------------------------------------------- */
        
        
        shlParent.open();
        shlParent.layout();
        while (!shlParent.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }
}
