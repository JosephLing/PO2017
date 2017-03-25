package mbedApp.devices;


import mbedApp.ProjectLogger;
import mbedApp.mqtt.MQTT_TOPIC;
import mbedApp.mqtt.MessageClient;

import java.util.HashMap;

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
    public void parseChange(HashMap<String, String> args) {

        if (isDevice_registered()){
            String stringState = args.get("state");
            if (stringState != null){
                state = Boolean.parseBoolean(stringState);

            }
        }

    }

//    @Override
//    public void register(MessageClient client) {
//        super.register(client); // so if we call it here will it use getName() from Device or Light?
//        // as that could break the code if it grabs the "Device"
//        client.send(MQTT_TOPIC.DEVICE_REGISTER, "{"+getName()+":id="+getId()+",state="+state+"}");
//    }

    public String getName() {
        return LIGHT;
    }

    public static Light parseNewDevice(HashMap<String, String> args){
        Light newLight = null;
        String id = args.get("id");
        String state = args.get("state");
        if (id != null){
            if (state != null){
                newLight =  new Light(Boolean.parseBoolean(state), Integer.parseInt(id));
            }else{
                ProjectLogger.Warning("No state found for new light");
            }

        }else{
            ProjectLogger.Warning("No Id found! for new light: " + LIGHT);
        }
        return newLight;
    }
}

