package mbedApp.mbed;

import mbedApp.ProjectLogger;
import mbedApp.mqtt.ClientType;
import org.eclipse.paho.client.mqttv3.MqttMessage;
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
        messageClient = new MessageClient(ClientType.MBED);

        messageClient.advanceSubscribe("cat", (String topic, String name, String[][]args)->{

        });
        messageClient.send("testing all the things}", "cat");
        messageClient.send("{testing all the things", "cat");
        messageClient.send("{name}", "cat");
        messageClient.send("{name:}", "cat");
        messageClient.send("{name:arg1}", "cat");
        messageClient.send("{testname:arg1=va1}", "cat");
        messageClient.send("{testname:arg1=va1,arg2=va2}", "cat");

//        messageClient = new MessageClient(ClientType.MBED);
//        messageClient.subscribe("cat", (String topic, MqttMessage message)->{
//            System.out.println("Response: " + new String(message.getPayload()));
//        });
//        messageClient.send("cat", "cat");
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
}
