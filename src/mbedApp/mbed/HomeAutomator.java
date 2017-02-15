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
    private static final int MINIMUM_ROOM_TEMPERATURE = 18;
    
    /**
     * Creates the Mbed controller and creates the main menu when
     * loaded. Starts of with generating a messaging client to access the data.
     */
    public HomeAutomator() {
        genMBed();

        messageClient = new MessageClient();

        devices = new ArrayList<>();
        
        messageClient.advanceSubscribe(MQTT_TOPIC.DEVICE_SET, (String topic, String name, String[][]args) -> {
            if (name.contains("Light")){
                if (args[0][0].equals("state")){
                    devices.add(new Light(Boolean.parseBoolean(args[0][1]), name));
                    System.out.println(name);
                    System.out.println(devices.size());
                }else{
                    System.out.println("invalid args");
                }
            }else{
                System.out.println("Looking for Lights none found");
            }
        });
        messageClient.send(MQTT_TOPIC.DEVICE_REGISTER, "{start:devices=true}");

        messageClient.advanceSubscribe(MQTT_TOPIC.DEVICE_REGISTER, (String topic, String name, String[][]args) -> {
            if (name.contains("start")){
                if (args[0][0].equals("devices")){
                    if (Boolean.parseBoolean(args[0][1])){
                        messageClient.send(MQTT_TOPIC.DEVICE_CHANGE, "{Light1:state=false}");
                    }
                }
            }
        });

        //        while (true){
        //            System.out.println(lights.size());
        //            sleep(1000);
        //        }

        screenInterface = new ScreenInterface(messageClient);
    }
    
    /**
    * Every time a potentiometer changes send the value using the Messaging
    * Client
    */
    private PotentiometerListener tempPot  = (value) -> {
        messageClient.send(MQTT_TOPIC.TEMPERATURE, "{temp:new=" + Double.toString(value) + "}");
    };
    
    /**
     * Every time the temperature changes check it, if it's below the minimum, send the temperature to the room and the new temp
     * that should be set by the thermostat.
     */
    private void checkTempChange() {
        while(true) {
            Thermometer thermometer = mbed.getThermometer();
            Double temp = thermometer.getTemperature();
            if(temp < MINIMUM_ROOM_TEMPERATURE) {
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
     * Get the ArrayList of devices
     */
    public ArrayList getDevices() {
        return devices;
    }

    /**
     * Pause the program for a specified amount of miliseconds
     * @param milis the number of miliseconds to pause for
     */
    public static void sleep(long millis){
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException ex) {
            // Nothing we can do.
        }

    }

    public void test(){
//        messageClient.advanceSubscribe(MQTT_TOPIC.CAT, (String topic, String name, String[][]args)->{
//
//        });
//        messageClient.send("testing all the things}", "cat");
//        messageClient.send("{testing all the things", "cat");
//        messageClient.send("{name}", "cat");
//        messageClient.send("{name:}", "cat");
//        messageClient.send("{name:arg1}", "cat");
//        messageClient.send("{testname:arg1=va1}", "cat");
//        messageClient.send("{testname:arg1=va1,arg2=va2}", "cat");
    }
}
