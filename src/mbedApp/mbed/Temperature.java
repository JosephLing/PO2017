package mbedApp.mbed;

import mbedApp.mbed.HomeAutomator;
import mbedApp.mqtt.MessageClient;
import shed.mbed.PotentiometerListener;
import shed.mbed.Thermometer;
import mbedApp.mqtt.MQTT_TOPIC;

/**
 * Controls temperature functionality of the MBed
 */
public class Temperature
{
    // temprature settings
    private static int MIN_ROOM_TEMP = 18;
    private static int MAX_ROOM_TEMP = 25;
    
    
    private boolean alterTemprature = true;
    private MessageClient messageClient;
    /**
     * Constructor for objects of class Temperature
     */
    public Temperature(MessageClient messageClient)
    {
        this.messageClient = messageClient;
    }

    
    /**
     * sleeps the current thread for
     * @param ms number of milliseconds
     */
    public static void sleep(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Every time the temperature changes check it, if it's below the minimum, send the temperature to the room and the new temp
     * that should be set by the thermostat.
     */
    private void checkTempChange() {
        //TODO: not have this as in infinite loop and a way to alter it [x]
        while(HomeAutomator.getMBed().isOpen() && alterTemprature) {
            Thermometer thermometer = HomeAutomator.getMBed().getThermometer();
            Double temp = thermometer.getTemperature();
            if(temp > MIN_ROOM_TEMP && temp < MAX_ROOM_TEMP) {
                messageClient.send(MQTT_TOPIC.TEMPERATURE, "{temp:new=21}");
                sleep(750);
            }else{
                // so we sleep longer when the temprature
                sleep(2000);
            }

        }
    }
    
    public Double getCurrentTemp() {
        Thermometer thermometer = HomeAutomator.getMBed().getThermometer();
        Double temp = thermometer.getTemperature();
        return temp;
    }
    
    
    /**
    * Every time a potentiometer changes send the value using the Messaging
    * Client
    */
    private PotentiometerListener tempPot  = (value) -> {
        System.out.println("asdf" + value);
        // messageClient.send(MQTT_TOPIC.TEMPERATURE, "{temp:new=" + Double.toString(value) + "}");
        // TODO: Convert pot value (0-1) to an increase in 10^c or so (maybe *10 the value?)
        //TODO: should probably sleep this if it always calls or calls a lot
    };
}
