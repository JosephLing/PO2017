package mbedApp.mbed;

import mbedApp.ProjectLogger;
import mbedApp.devices.Light;
import mbedApp.devices.Device;
import mbedApp.mqtt.MQTT_TOPIC;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import shed.mbed.ButtonListener;
import shed.mbed.Potentiometer;
import shed.mbed.PotentiometerListener;
import shed.mbed.Thermometer;
import shed.mbed.MBed;
import shed.mbed.MBedUtils;
import mbedApp.mqtt.MessageClient;

import java.util.ArrayList;

/**
 * HomeAutomator does.............
 *
 * @author josephling
 * @version 1.0 09/02/2017
 */
public class HomeAutomator {

    //------------------------------------------------------------------------//
    private static MBed mbed;

    /**
     * Generate a new MBed object, be it a physical device or emulator
     */
    private static void genMBed() {
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


    private ScreenInterface screenInterface;
    private MessageClient messageClient;
    private ArrayList<Device> devices;

    // temprature settings
    private int MIN_ROOM_TEMP = 18;
    private int MAX_ROOM_TEMP = 25;
    private boolean alterTemprature = true;

    /**
     * Creates the Mbed controller and creates the main menu when
     * loaded. Starts of with generating a messaging client to access the data.
     */
    public HomeAutomator() {
        genMBed();
        messageClient = new MessageClient();
        screenInterface = new ScreenInterface(messageClient);


    }
    
    /**
    * Every time a potentiometer changes send the value using the Messaging
    * Client
    */
    private PotentiometerListener tempPot  = (value) -> {
        messageClient.send(MQTT_TOPIC.TEMPERATURE, "{temp:new=" + Double.toString(value) + "}");
        //TODO: should probably sleep this if it always calls or calls a lot
    };
    
    /**
     * Every time the temperature changes check it, if it's below the minimum, send the temperature to the room and the new temp
     * that should be set by the thermostat.
     */
    private void checkTempChange() {
        //TODO: not have this as in infinite loop and a way to alter it [x]
        while(mbed.isOpen() && alterTemprature) {
            Thermometer thermometer = mbed.getThermometer();
            Double temp = thermometer.getTemperature();
            if(temp < MIN_ROOM_TEMP) {
                messageClient.send(MQTT_TOPIC.TEMPERATURE, "{temp:new=21}");
            }
            sleep(1000);
        }
    }

    /**
     * gets the message client used by the HomeAutomator.
     * @return MessageClient
     */
    public MessageClient getMessageClient() {
        return messageClient;
    }

    /**
     * Pause the program for a specified amount of miliseconds
     * @param millis the number of miliseconds to pause for
     */
    public static void sleep(long millis){
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException ex) {
            // Nothing we can do.
        }

    }
}
