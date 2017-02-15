package mbedApp.devices;


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
        super("Light", id);
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
}

