package mbedApp.mbed;

import mbedApp.mqtt.MQTT_TOPIC;
import mbedApp.mqtt.MessageClient;

/**
 * TemperatureThread is used for running our inifite temperature checking loop
 */
public class TemperatureThread extends Thread
{
    // temprature settings
    private static int MIN_ROOM_TEMP = 21;
    private static int MAX_ROOM_TEMP = 25;
    
    private MessageClient messageClient;
    
    /**
     * Constructor for objects of class TemperatureThread
     */
    public TemperatureThread()
    {
        messageClient = new MessageClient();
    }

    public void run() {
        //TODO: not have this as in infinite loop and a way to alter it [x]
        while(HomeAutomator.getMBed().isOpen()) {
            Double temp = Temperature.getCurrentTemp();
            System.out.println(temp);
            if(temp < MIN_ROOM_TEMP || temp > MAX_ROOM_TEMP) {
                messageClient.send(MQTT_TOPIC.TEMPERATURE, "{temp:new=21}");
                HomeAutomator.sleep(10000);
            }else{
                // so we sleep longer when the temprature
                HomeAutomator.sleep(10000);
            }

        }
    }
}
