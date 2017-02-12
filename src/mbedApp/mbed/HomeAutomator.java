package mbedApp.mbed;

import mbedApp.ProjectLogger;
import shed.mbed.ButtonListener;
import shed.mbed.MBed;
import shed.mbed.MBedUtils;

/**
 * HomeAutomator does.............
 *
 * @author josephling
 * @version 1.0 09/02/2017
 */
public class HomeAutomator {
    
    private static MBed mbed;
    private ScreenInterface screenInterface;

    /**
     * Creates the Mbed controller and creates the main menu when
     * loaded. Starts of with generating an mqtt client to access the data.
     */
    public HomeAutomator(){
        genMBed();
        screenInterface = new ScreenInterface();
    }
    
    private void genMBed() {
        mbed = MBedUtils.getMBed();
    }
    
    public static MBed getMBed() {
        return mbed;
    }

    
    public static void sleep(long millis){
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException ex) {
            // Nothing we can do.
        }

    }

    /**
     * disables all the controls
     * Package private
     */
    static void disableAllControls(){
        ProjectLogger.Log("----disabling all controls----");
        HomeAutomator.getMBed().getJoystickDown().removeAllListeners();
        HomeAutomator.getMBed().getJoystickUp().removeAllListeners();
        HomeAutomator.getMBed().getJoystickFire().removeAllListeners();
        HomeAutomator.getMBed().getJoystickLeft().removeAllListeners();
        HomeAutomator.getMBed().getJoystickRight().removeAllListeners();

    }
}
