package mbedApp.room.objects;

import mbedApp.ProjectLogger;
import mbedApp.devices.Device;
import mbedApp.devices.Light;
import mbedApp.mqtt.MQTT_TOPIC;
import mbedApp.mqtt.MessageClient;
import mbedApp.room.Canvas;

import java.awt.Graphics;
import java.util.HashMap;

import java.awt.*;
import java.awt.geom.*;



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

    public LightObj(int id, int x, int y) {
        super(false, id);
        client = new MessageClient();
        this.x = x;
        this.y = y;
    }

    public void update(Canvas canvas) {
        if (registered){
            if (isState()) {
                canvas.draw(this, Color.green, new Ellipse2D.Double(x, y, 20, 20));
                //canvas.draw(this, Color.blue, new Rectangle2D.Double(x, y, 10, 5));
                //canvas.draw(this, Color.blue, new Rectangle2D.Double(x, y + 10, 5, 5));
            } else {
                canvas.draw(this, Color.red, new Ellipse2D.Double(x, y, 20, 20));
                //canvas.draw(this, Color.blue, new Rectangle2D.Double(x, y, 10, 5));
                //canvas.draw(this, Color.blue, new Rectangle2D.Double(x, y + 10, 5, 5));
            }
        }else{
            ProjectLogger.Warning("light not yet registered");
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int newX) {
        x = newX;
    }

    public void setY(int newY) {
        y = newY;
    }

    public void addClient(MessageClient client) {
        this.client = client;
    }

    public MessageClient getMessageClient() {
        return client;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void register_client() {
        client.advanceSubscribe(MQTT_TOPIC.DEVICE_SET,
                (String topic, String name, HashMap<String, String> args)->{
                    if (name.contains(Device.LIGHT) && name.split(Device.LIGHT)[1].equals(Integer.toString(getId()))){
                        String reg = args.get("registered");
                        if (reg != null){
                            setRegistered(!isRegistered());
                        }
                    }
                });
    }

    public void setRegistered(boolean state) {
        registered = state;
    }
}