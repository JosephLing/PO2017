package mbedApp.gui.room.objects;

import mbedApp.ProjectLogger;
import mbedApp.devices.Device;
import mbedApp.devices.Thermostat;
import mbedApp.gui.room.Canvas;
import mbedApp.mqtt.MQTT_TOPIC;
import mbedApp.mqtt.MessageClient;

import java.awt.*;
import java.util.HashMap;


/**
 * Thermostat class, used to demo the temperature (but not actually do anything)
 */
public class ThermostatObj extends Thermostat implements InterfaceScreenObject {

    private int x;
    private int y;
    private Graphics graphic;
    private MessageClient client;
    private boolean registered;
    private Double temp;
    private Double currentTemp;

    public ThermostatObj(int id, int x, int y) {
        super(0, id);
        client = new MessageClient();
        this.x = x;
        this.y = y;
        temp = 0.0;
        currentTemp = 0.0;
    }

    public void update(Canvas canvas) {
        if (registered){
            canvas.drawText(this, "Requested temperature: " + temp + "*C", x, y);
            //canvas.drawText(this, "Current Temperature: " + currentTemp + "*C", x, y+10);
        }else{
            ProjectLogger.Warning("Thermostat not yet registered");
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
    
    public void setTemperature(Double temp) {
        this.temp = temp;
    }
    
    public void setCurrentTemperature(Double currentTemp) {
        this.currentTemp = currentTemp;
    }

    public void register_client() {
        client.advanceSubscribe(MQTT_TOPIC.DEVICE_SET,
                (String topic, String name, HashMap<String, String> args)->{
                    if (name.contains(Device.THERMOSTAT) && name.split(Device.THERMOSTAT)[1].equals(Integer.toString(getId()))){
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