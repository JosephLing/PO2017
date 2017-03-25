package mbedApp.mbed;

import mbedApp.mqtt.MQTT_TOPIC;
import mbedApp.mqtt.MessageClient;
import shed.mbed.Potentiometer;
import sun.misc.DoubleConsts;
import sun.misc.FloatConsts;


/**
 * Controls temperature functionality of the MBed
 */
public class Temperature
{   private static int MIN_ROOM_TEMP = 21;
    private static int MAX_ROOM_TEMP = 25;
    
    private MessageClient messageClient;
    private double temp;


    /**
     * Constructor for objects of class Temperature
     * @param messageClient MessageClient
     */
    public Temperature(MessageClient messageClient)
    {
        this.messageClient = new MessageClient();
        setCurrentTemp();
    }

    /**
     *
     * @return number of ms to take before checking the temprature again.
     */
    public int sendUpateSignal() {
        setCurrentTemp();
        if(temp < MIN_ROOM_TEMP || temp > MAX_ROOM_TEMP) {
//            messageClient.send(MQTT_TOPIC.TEMPERATURE, "{temp:new=21,current=" + temp + "}");
            messageClient.send(MQTT_TOPIC.TEMPERATURE, "{temp:current=" + temp + "}", true);
            return 50000;
        }else{
            messageClient.send(MQTT_TOPIC.TEMPERATURE, "{temp:current=" + temp + "}", true);
            return 20000;
        }
    }


    /**
     * sets the current temperature from the MBed
     */
    private void setCurrentTemp() {
        temp = HomeAutomator.getMBed().getThermometer().getTemperature();
        ScreenInterface.sleep(50);
    }

    public Double getTemprature(){
        return temp;
    }
}
