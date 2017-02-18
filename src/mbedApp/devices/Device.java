package mbedApp.devices;


import mbedApp.ProjectLogger;
import mbedApp.mqtt.MQTT_TOPIC;
import mbedApp.mqtt.MessageClient;

/**
 * Device base class
 *
 * @author josephling
 * @version 1.0 10/02/2017
 */
public class Device implements InterfaceDevice{

    public static final String LIGHT = "Light";
    public static final String TEMP = "Temp";
    public static final String THERMOSTAT = "Thermostat";
    public static final String DEVICE = "DEV";


    private int id;
    private boolean device_registered;
    public Device(int id) {
        this.id = id;
        device_registered = false;

    }

    public boolean isDevice_registered() {
        return device_registered;
    }

    public void setDevice_registered(boolean device_registered) {
        this.device_registered = device_registered;
    }

    @Override
    public String toString(){
        return getName()+id;
    }


    /**
     * gets the name
     * @return name
     */
    public String getName() {
        return DEVICE;
    }

    /**
     * gets the id of the device
     * @return id
     */
    public int getId() {
        return id;
    }

    @Override
    public void parseChange(String[][] args) {

    }

    @Override
    public void register(MessageClient client) {
        client.advanceSubscribe(MQTT_TOPIC.DEVICE_SET,  (String topic, String name, String[][]args)->{
            if (name.equals(getName()+getId())){
                if (args.length == 1){
                    if (args[0][0].equals("registered")){
                        setDevice_registered(Boolean.parseBoolean(args[0][1]));
                    }
                }
            }
        });
    }


    public static Device parseNewDevice(String[][] args){
        return null;
    }

    public static String parseNewDeviceId(String name, String[][] args){
        String newId = null;
        if (args.length == 1){ // id and state
            if (args[0][0].equals("id")){
                newId = name + args[0][1];
            }else{
                ProjectLogger.Warning("No Id found! for new light: " + name);
            }
        }
        return newId;
    }
}

