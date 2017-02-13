package mbedApp.mbed;

import mbedApp.ProjectLogger;
import mbedApp.devices.Light;
import mbedApp.mqtt.ClientType;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import shed.mbed.ButtonListener;
import shed.mbed.Potentiometer;
import shed.mbed.PotentiometerListener;
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
    /**
     * Creates the Mbed controller and creates the main menu when
     * loaded. Starts of with generating an mqtt client to access the data.
     */
    public HomeAutomator() {
//        genMBed();
        messageClient = new MessageClient();

        ArrayList<Light> lights = new ArrayList<Light>();
        messageClient.advanceSubscribe("devices_set", (String topic, String name, String[][]args) -> {
            if (name.contains("Light")){
                if (args[0][0].equals("state")){
                    lights.add(new Light(Boolean.parseBoolean(args[0][1]), name));
                    System.out.println(name);
                }
            }
        });
        messageClient.send("{start:devices=true}", "devices_register");

        messageClient.advanceSubscribe("device_register", (String topic, String name, String[][]args) -> {
            if (name.contains("start")){
                if (args[0][0].equals("devices")){
                    if (Boolean.parseBoolean(args[0][1])){
                        messageClient.send("{Light1:state=false}","devices_change");
                    }
                }
            }
        });

        while (true){
            System.out.println(lights.size());
            sleep(1000);
        }


//        screenInterface = new ScreenInterface(messageClient);
    }

    /**
     * gets the message client used by the HomeAutomator.
     * @return MessageClient
     */
    public MessageClient getMessageClient() {
        return messageClient;
    }


    public static void sleep(long millis){
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException ex) {
            // Nothing we can do.
        }

    }

    public void test(){
        messageClient.advanceSubscribe("cat", (String topic, String name, String[][]args)->{

        });
        messageClient.send("testing all the things}", "cat");
        messageClient.send("{testing all the things", "cat");
        messageClient.send("{name}", "cat");
        messageClient.send("{name:}", "cat");
        messageClient.send("{name:arg1}", "cat");
        messageClient.send("{testname:arg1=va1}", "cat");
        messageClient.send("{testname:arg1=va1,arg2=va2}", "cat");
    }
}
