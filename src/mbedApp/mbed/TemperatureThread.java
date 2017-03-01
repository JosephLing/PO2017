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
        while(HomeAutomator.getMBed().isOpen()) {
            Double temp = Temperature.getCurrentTemp();
            System.out.println(temp);
            if(temp < MIN_ROOM_TEMP || temp > MAX_ROOM_TEMP) {
                messageClient.send(MQTT_TOPIC.TEMPERATURE, "{temp:new=21,current=" + temp + "}");
                HomeAutomator.sleep(20000);
            }else{
                messageClient.send(MQTT_TOPIC.TEMPERATURE, "{temp:current=" + temp + "}");
                HomeAutomator.sleep(20000);
            }

        }
    }
}
