package mbedApp.mbed;

import mbedApp.ProjectLogger;
import mbedApp.devices.Light;
import mbedApp.devices.Device;
import mbedApp.devices.Temperature;
import mbedApp.mqtt.MQTT_TOPIC;
import shed.mbed.MBed;
import shed.mbed.MBedUtils;
import mbedApp.mqtt.MessageClient;

import java.util.HashMap;

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
    private HashMap<String, Device> devices;
    private Temperature temperature;



    /**
     * Creates the Mbed controller and creates the update_main menu when
     * loaded. Starts of with generating a messaging client to access the data.
     */
    public HomeAutomator() {
        genMBed();
        messageClient = new MessageClient();
        devices = new HashMap<String, Device>();
        screenInterface = new ScreenInterface(messageClient);
        temperature = new Temperature(messageClient);
    }

    private void setUpSubscriptions(){
//        MQTT_TOPIC.DEVICE_REGISTER
        // so we register by name+id so then we can .parse() it later
        messageClient.advanceSubscribe(MQTT_TOPIC.DEVICE_REGISTER,
                (String topic, String name, String[][]args)->{
                    switch (name){
                        case Device.LIGHT:
                            if (Device.parseNewDeviceId(name, args) != null){
                                devices.put(Device.parseNewDeviceId(name, args), Light.parseNewDevice(args));
                                messageClient.send(MQTT_TOPIC.DEVICE_SET, "{"+Device.parseNewDeviceId(name, args)+":registered:true}");
                            }
                            break;

                        default:
                            ProjectLogger.Warning("No device found for: " + name);
                    }
                });



//        MQTT_TOPIC.DEVICE_CHANGE
        // parse the name and id then run the .parse(String[][])


        messageClient.advanceSubscribe(MQTT_TOPIC.DEVICE_CHANGE,
                (String topic, String name, String[][]args)->{
                    if (devices.get(name) != null){
                        devices.get(name).parseChange(args);
                    }
        });

//        MQTT_TOPIC.TEMPERATURE
        //  we need to register the temprature device first
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
