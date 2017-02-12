package mbedApp.mbed;

import mbedApp.ProjectLogger;
import shed.mbed.ButtonListener;
import shed.mbed.Potentiometer;
import shed.mbed.PotentiometerListener;
import shed.mbed.MBed;
import shed.mbed.MBedUtils;
import mbedApp.mqtt.MessageClient;

/**
 * HomeAutomator does.............
 *
 * @author josephling
 * @version 1.0 09/02/2017
 */
public class HomeAutomator {
    
    private static MBed mbed;
    private ScreenInterface screenInterface;
    private MessageClient messageClient;

    /**
     * Creates the Mbed controller and creates the main menu when
     * loaded. Starts of with generating an mqtt client to access the data.
     */
    public HomeAutomator() {
        genMBed();
        screenInterface = new ScreenInterface();
        messageClient = new MessageClient();
        dimmer();
    }
    
    /**
     * Generate a new MBed object, be it a physical device or emulator
     */
    private void genMBed() {
        mbed = MBedUtils.getMBed();
    }
    
    /**
     * Get our MBed object - you don't need to initialise a HomeAutomator class
     * to use this method.
     */
    public static MBed getMBed() {
        return mbed;
    }
    
    //------------------------------------------------------------------------//
    
    /**
     * Every time a potentiometer changes send the value using the Messaging
     * Client
     */
    private PotentiometerListener pot  = (value) -> {
        messageClient.send(Double.toString(value));
    };
    
    /**
     * Act as a dimmer switch for the lights - constantly check the status of
     * the potentiometers and change the brightness of the lights based on this
     */
    private void dimmer() {
        mbed.getPotentiometer1().addListener(pot);
    }

    
    public static void sleep(long millis){
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException ex) {
            // Nothing we can do.
        }

    }
}
