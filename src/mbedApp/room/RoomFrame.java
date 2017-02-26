package mbedApp.room;

import mbedApp.ProjectLogger;
import mbedApp.devices.Device;
import mbedApp.mqtt.MQTT_TOPIC;
import mbedApp.mqtt.MessageClient;
import mbedApp.room.objects.LightObj;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Random;

/**
 * RoomFrame does.............
 *
 * @author josephling
 * @version 1.0 12/02/2017
 */
public class RoomFrame extends JFrame {


    private Canvas canvas;
    private MessageClient messageClient;

    private HashMap<String, LightObj> lights;
    private int lights_registeredCount;

    public RoomFrame() {
        messageClient = new MessageClient();

        init_Jframe();
        setVisible(true);

        // set up lights
        lights = new HashMap<String, LightObj>();
        lights_registeredCount = 0;
        init_lights();
        init_deviceChange();


        main();
    }

    public void main(){
        while (true){
            drawEverything();
            canvas.wait(1000);
            if (lights_registeredCount != lights.size()){
                register_lights();
            }
        }
    }

    /**
     * initialises the application window
     */
    private void init_Jframe(){
        setTitle("Example Room");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        canvas = new Canvas(500, 500, Color.white);
        setContentPane(canvas.getCanvas());
        pack();
        canvas.setVisible(true);
    }

    private void drawEverything(){
        // Draw lights
        for (LightObj light : lights.values()) {
            if (light.isRegistered()){
                light.update(canvas);
            }
        }
    }

    public void init_lights(){
        messageClient.advanceSubscribe(MQTT_TOPIC.DEVICE_SET,
                (String topic, String name, HashMap<String, String> args)->{
                    System.out.println(name);
                    for (LightObj light : lights.values()) {
                        if (!light.isRegistered()){
                            if ((light.getName()+light.getId()).equals(name)){
                                light.setRegistered(true);
                                lights_registeredCount ++;
                            }
                        }
                    }
        });

        LightObj light = null;
        for (int i = 0; i < 10; i++) {
            light = new LightObj(i, 10 + i * 50, 10);
            lights.put(light.getName()+light.getId(), light);
        }

        register_lights();
    }

    /**
     * registers all the lights that haven't yet been registered
     * this is important for example if their isn't a controller for the devices yet.
     */
    private void register_lights(){
        ProjectLogger.Log("register lights");
        String lightId;
        for (LightObj lightObj : lights.values()) {
            lightId = lightObj.getName()+lightObj.getId();
            if (!lightObj.isRegistered()){
                lights.get(lightId).getMessageClient()
                        .send(MQTT_TOPIC.DEVICE_REGISTER,
                                "{"+Device.LIGHT+":state="+ lightObj.isState()+",id="+ lightObj.getId()+"}");
            }
        }
        sleep(1000);

    }

    /**
     * receives: topic=devices_change {light1:state=false}
     * response: alters lights hash map to the new change
     */
    private void init_deviceChange() {
       messageClient.advanceSubscribe(MQTT_TOPIC.DEVICE_CHANGE, (String topic, String name, HashMap<String, String> args)->{
           if (name.contains("Light")) {
                lights.get(name).setState(Boolean.parseBoolean(args.get("state")));
           } else{
               System.err.println("wrong name");
           }
       });
    
    }
    
    /**
     * Pause the program for a specified amount of miliseconds
     * @param millis the number of miliseconds to pause for
     */
    public static void sleep(long millis){
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException ex) {
            // Nothing we can do.
        }

    }
}