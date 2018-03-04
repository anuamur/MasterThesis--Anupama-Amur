//package test123;
//import test123.Sat;
//
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.events.MouseAdapter;
//import org.eclipse.swt.events.MouseEvent;
//import org.eclipse.swt.widgets.Button;
//import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.Shell;
//import org.orekit.time.AbsoluteDate;
//
//public class EventSim {
//
////Classes declaration
//  private
//  //Not Required here
//    //static test123.Sat satObj = new Sat();
// 
//     /**
//     * Launch the application.
//     * @param args
//     */  
//public static void main(String[] args) {        
//        Display display = Display.getDefault(); 
//        Shell shell = new Shell();
//        shell.setSize(450, 300);
//        shell.setText("Events and Satellite Simulation");
//        
//
//        
//        Button btnNewButton = new Button(shell, SWT.NONE);
//        btnNewButton.addMouseListener(new MouseAdapter() {
//            public void mouseDoubleClick(MouseEvent arg0) {
//                Time t = new Time();
//                //After clicking on Run Button                
//                System.out.println("Hello \n");
//                t.timeTick();
//            }
//        });
//        btnNewButton.setBounds(27, 138, 75, 25);
//        btnNewButton.setText("Run");
//  
//        shell.open();
//        shell.layout();
//        while (!shell.isDisposed()) {
//            if (!display.readAndDispatch()) {
//                display.sleep();
//            }
//        }
//    }
//}
