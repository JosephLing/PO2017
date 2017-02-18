package mbedApp.mbed;

import mbedApp.mbed.HomeAutomator;
import mbedApp.mbed.ScreenInterface;
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
    
    
    private boolean alterTemprature;
    private boolean mbedTemprature;
    private MessageClient messageClient;
    /**
     * Constructor for objects of class Temperature
     */
    public Temperature(MessageClient messageClient)
    {
        mbedTemprature = true;
        alterTemprature = true;
        this.messageClient = messageClient;
    }

    


    /**
     * Every time the temperature changes check it, if it's below the minimum, send the temperature to the room and the new temp
     * that should be set by the thermostat.
     */
    private void checkTempChange() {
        //TODO: not have this as in infinite loop and a way to alter it [x]
        while(HomeAutomator.getMBed().isOpen() && alterTemprature) {
            Double temp = getCurrentTemp();
            if(temp > MIN_ROOM_TEMP && temp < MAX_ROOM_TEMP) {
                messageClient.send(MQTT_TOPIC.TEMPERATURE, "{temp:new=21}");
                ScreenInterface.sleep(750);
            }else{
                // so we sleep longer when the temprature
                ScreenInterface.sleep(2000);
            }

        }
    }
    
    public Double getCurrentTemp() {
        if (mbedTemprature){
            return HomeAutomator.getMBed().getThermometer().getTemperature();
        }else{
            // we need to a way to simulate the room temprature changing
            return 0.0;
        }
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

    public boolean isAlterTemprature() {
        return alterTemprature;
    }

    public void setAlterTemprature(boolean alterTemprature) {
        this.alterTemprature = alterTemprature;
    }



}
