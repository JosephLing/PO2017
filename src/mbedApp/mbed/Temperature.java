package mbedApp.mbed;

import mbedApp.mbed.HomeAutomator;
import mbedApp.mbed.ScreenInterface;
import mbedApp.mqtt.MessageClient;
import shed.mbed.PotentiometerListener;
import shed.mbed.Potentiometer;
import shed.mbed.Thermometer;
import mbedApp.mqtt.MQTT_TOPIC;

/**
 * Controls temperature functionality of the MBed
 */
public class Temperature
{
    // temprature settings
    private static int MIN_ROOM_TEMP = 21;
    private static int MAX_ROOM_TEMP = 25;
    
    private MessageClient messageClient;
    /**
     * Constructor for objects of class Temperature
     */
    public Temperature(MessageClient messageClient)
    {
        this.messageClient = messageClient;
    }

    /**
     * Every time the temperature changes check it, if it's below the minimum, send the temperature to the room and the new temp
     * that should be set by the thermostat.
     */
    public void checkTempChange() {
        //TODO: not have this as in infinite loop and a way to alter it [x]
        while(HomeAutomator.getMBed().isOpen()) {
            Double temp = getCurrentTemp();
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
    
    /**
     * Get the current temperature from the MBed
     */
    public Double getCurrentTemp() {
        return HomeAutomator.getMBed().getThermometer().getTemperature();
    }
    
    /**
    * Every time a potentiometer changes send the value using the Messaging
    * Client
    */
    public void checkTempPot() {
        Potentiometer pot = HomeAutomator.getMBed().getPotentiometer1();
        pot.addListener((double value) -> {
            value = value * 10;
            messageClient.send(MQTT_TOPIC.TEMPERATURE, "{temp:new=" + Double.toString(value) + "}");
            // TODO: Convert pot value (0-1) to an increase in 10^c or so (maybe *10 the value?)
            //TODO: should probably sleep this if it always calls or calls a lot
        });
    }
}
