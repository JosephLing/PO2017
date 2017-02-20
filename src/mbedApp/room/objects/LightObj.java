package mbedApp.room.objects;

import mbedApp.devices.Device;
import mbedApp.devices.Light;
import mbedApp.mqtt.MQTT_TOPIC;
import mbedApp.mqtt.MessageClient;
import mbedApp.room.Canvas;

import java.awt.Graphics;

/**
 * Light does.............
 *
 * @author josephling
 * @version 1.0 12/02/2017
 */
public class LightObj extends Light implements InterfaceScreenObject {

    private int x;
    private int y;
    private Graphics graphic;
    private MessageClient client;
    private boolean registered;

    public LightObj(String name) {
        super(false, 0);
    }

    @Override
    public Graphics update(Canvas canvas) {
//        if (isOn()){
//
//        }else{
//
//        }
        return null;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setX(int newX) {
        x = newX;
    }

    @Override
    public void setY(int newY) {
        y = newY;
    }

    @Override
    public void addClient(MessageClient client) {
        this.client = client;
    }

    @Override
    public MessageClient getClient() {
        return client;
    }

    @Override
    public boolean isRegistered() {
        return registered;
    }

    @Override
    public void register_client() {
        client.advanceSubscribe(MQTT_TOPIC.DEVICE_SET,
                (String topic, String name, String[][]args)->{
                    if (name.contains(Device.LIGHT) && name.split(Device.LIGHT)[1].equals(Integer.toString(getId()))){
                        if (args.length == 1){
                            if (args[0][0].equals("registered")){
                                setRegistered(!isRegistered());
                            }
                        }
                    }
                });
    }

    @Override
    public void setRegistered(boolean state) {
        registered = state;
    }
}
