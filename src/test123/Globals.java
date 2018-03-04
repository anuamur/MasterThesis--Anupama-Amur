package test123;

import org.orekit.orbits.Orbit;
import org.orekit.propagation.SpacecraftState;
import org.orekit.time.AbsoluteDate;
import org.orekit.time.TimeScale;

public class Globals {
public static TimeScale utc; 
public static double stepT = 60.;
public static double duration = 600.;

//public static AbsoluteDate extrapDate;

public static AbsoluteDate initialDate; 
public static AbsoluteDate finalDate;
public static SpacecraftState currentState; //change it later to non-static
public static int cpt = 1;
//public static org.orekit.frames.Frame inertialFrame;

    Globals(){
        //initialDate = new AbsoluteDate(2004, 01, 01, 23, 30, 00.000, utc);
    }
}
