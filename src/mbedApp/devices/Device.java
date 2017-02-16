package mbedApp.devices;


import mbedApp.ProjectLogger;
import mbedApp.mqtt.MessageClient;

/**
 * Device base class
 *
 * @author josephling
 * @version 1.0 10/02/2017
 */
public class Device implements InterfaceDevice{

    public static final String name = "";
    private int id;

    public Device(int id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return Device.name+id;
    }


    /**
     * gets the name
     * @return name
     */
    public static String getName() {
        return Device.name;
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

