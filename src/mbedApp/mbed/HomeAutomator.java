package mbedApp.mbed;

import mbedApp.ProjectLogger;
import mbedApp.devices.Device;
import mbedApp.devices.Light;
import mbedApp.devices.Thermostat;
import mbedApp.mqtt.MQTT_TOPIC;
import mbedApp.mqtt.MessageClient;
import shed.mbed.MBed;
import shed.mbed.MBedUtils;

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
     * @return mbed
     */
    public static MBed getMBed() {
        return mbed;
    }
    //------------------------------------------------------------------------//


    private ScreenInterface screenInterface;
    private MessageClient messageClient;
    private static HashMap<String, Device> devices;
    private static Temperature temperature;



    /**
     * Creates the Mbed controller and creates the update_main menu when
     * loaded. Starts of with generating a messaging client to access the data.
     */
    public HomeAutomator() {
        genMBed();
        messageClient = new MessageClient();
        devices = new HashMap<String, Device>();
        temperature = new Temperature(messageClient);

        settingUpDeviceRegisteration();
        // This has to be last as it's blocking execution of other things!
        ScreenInterface.main();
    }

    private void settingUpDeviceRegisteration(){
        System.out.println("settting up sub");

        //MQTT_TOPIC.DEVICE_REGISTER
        // so we register by name+id so then we can .parse() it later
        MessageClient deviceSet = new MessageClient();
        messageClient.advanceSubscribe(MQTT_TOPIC.DEVICE_REGISTER,
                (String topic, String name, HashMap<String, String> args)->{
                    String deviceId = Device.parseNewDeviceId(name, args);
                    switch (name){
                        case Device.LIGHT:
                            if (!devices.containsKey(deviceId)){
                                devices.put(deviceId, Light.parseNewDevice(args));
                                deviceSet.send(MQTT_TOPIC.DEVICE_SET, "{"+deviceId+":registered=true}");
                            }
                            break;
                        case Device.THERMOSTAT:
                            if (!devices.containsKey(deviceId)){
                                devices.put(Device.parseNewDeviceId(name, args), Thermostat.parseNewDevice(args));

                                deviceSet.send(MQTT_TOPIC.DEVICE_SET, "{"+deviceId+":registered=true}");
                            }
                            break;
                        default:
                            ProjectLogger.Warning("No device found for: " + name);
                    }
                });
    }

    public static HashMap<String, Device> getDevices() {
        return devices;
    }


    public static Temperature getTemperatureDev() {
        return temperature;
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
