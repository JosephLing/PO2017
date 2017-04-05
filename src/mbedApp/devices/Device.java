package mbedApp.devices;


import mbedApp.ProjectLogger;
import mbedApp.mqtt.MQTT_TOPIC;
import mbedApp.mqtt.MessageClient;

import java.util.HashMap;

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
    private MessageClient client;

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
    public void parseChange(HashMap<String, String> args) {

    }

    @Override
    public void register(MessageClient client) {
        this.client = client;
        assert true: "this shouldn't be being called Device";
        client.advanceSubscribe(MQTT_TOPIC.DEVICE_SET,
                (String topic, String name, HashMap<String, String> args)->{
            if (name.equals(getName()+getId())){
                String state = args.get("registered");
                if (state != null){
                    setDevice_registered(Boolean.parseBoolean(state));
                    System.out.println(name);
                }
            }
        });
    }

    public MessageClient getClient() {
        return client;
    }

    public static Device parseNewDevice(HashMap<String, String> args){
        return null;
    }

    public static String parseNewDeviceId(String name, HashMap<String, String> args){
        String newId = null;
        if (args.get("id") != null){
            newId = name + args.get("id");
        }else{
            ProjectLogger.Warning("No Id found! for new light: " + name);
        }
        return newId;
    }
}

