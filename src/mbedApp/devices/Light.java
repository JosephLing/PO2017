package mbedApp.devices;


import mbedApp.ProjectLogger;
import mbedApp.mqtt.MQTT_TOPIC;
import mbedApp.mqtt.MessageClient;

/**
 * Light does.............
 *
 * @author josephling
 * @version 1.0 10/02/2017
 */
public class Light extends Device implements InterfaceDevice{


    private boolean state;

    public Light(boolean state, int id) {
        super(id);
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public void parseChange(String[][] args) {
        if (args[0][0].equals("state")){
            state = Boolean.parseBoolean(args[0][1]);
        }
    }

    @Override
    public void register(MessageClient client) {
        client.send(MQTT_TOPIC.DEVICE_REGISTER, "{"+getName()+":id="+getId()+",state="+state+"}");
    }

    public String getName() {
        return LIGHT;
    }

    public static Light parseNewDevice(String[][] args){
        Light newLight = null;
        if (args.length == 1){ // id and state
            if (args[0][0].equals("id")){
                if (args[1][0].equals("state")){
                    newLight =  new Light(Boolean.parseBoolean(args[1][1]), Integer.parseInt(args[0][1]));
                }else{
                    ProjectLogger.Warning("No state found for new light");
                }
            }else{
                ProjectLogger.Warning("No Id found! for new light: " + LIGHT);
            }
        }
        return newLight;
    }
}

