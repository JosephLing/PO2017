package mbedApp.mbed;

import mbedApp.mbed.HomeAutomator;
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
    
    private MessageClient messageClient;

    /**
     * Constructor for objects of class Temperature
     * @param messageClient MessageClient
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
        TemperatureThread t = new TemperatureThread();
        t.start();
    }
    
    /**
     * Get the current temperature from the MBed
     * @return Double type value of temp
     */
    public static Double getCurrentTemp() {
        return HomeAutomator.getMBed().getThermometer().getTemperature();
    }
    
    /**
    * Every time a potentiometer changes send the value using the Messaging
    * Client
    */
    public void checkTempPot() {
        Potentiometer pot = HomeAutomator.getMBed().getPotentiometer1();
        pot.setEpsilon(0.1); // The value that the potentiometer has to change by to be registered by the listener
        pot.addListener((double value) -> {
            value = value * 10;
            messageClient.send(MQTT_TOPIC.TEMPERATURE, "{temp:new=" + Double.toString(value) + "}");
        });
    }
}
