package mbedApp.room;

import mbedApp.devices.Device;
import mbedApp.devices.Light;
import mbedApp.mqtt.MQTT_TOPIC;
import mbedApp.mqtt.MessageClient;
import mbedApp.room.objects.CanvasObj;
import mbedApp.room.objects.InterfaceScreenObject;
import mbedApp.room.objects.RenderObjs;
import mbedApp.room.objects.LightObj;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * RoomFrame does.............
 *
 * @author josephling
 * @version 1.0 12/02/2017
 */
public class RoomFrame extends JFrame {

    //private ArrayList<LightObj> renderListLight;

    private Canvas canvas;
    private MessageClient messageClient;

    private HashMap<String, LightObj> lights;

    public RoomFrame() {
        messageClient = new MessageClient();
        lights = new HashMap<String, LightObj>();

        init_Jframe();
        registerLights();
        setVisible(true);
        checkForStateChange();
        main();
    }

    public void main(){
        while (true){
            drawEverything();
            canvas.wait(1000);
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
        canvas = new Canvas(500, 500, Color.black);
        setContentPane(canvas.getCanvas());
        pack();
        canvas.setVisible(true);
        init_Canvas();
    }

    private void drawEverything(){
        // Draw lights
        for (LightObj light : lights.values()) {
            if (light.isRegistered()){
                System.out.println(light.getX());
                System.out.println(light.getY());
                light.update(canvas);
            }
        }
    }

    private void init_Canvas(){
        //canvas.draw(this, Color.red, new Ellipse2D.Double(250, 250, 50, 50));
    }

    public void registerLights(){
        messageClient.advanceSubscribe(MQTT_TOPIC.DEVICE_SET,
                (String topic, String name, HashMap<String, String> args)->{
                    System.out.println(name);
                    for (LightObj light : lights.values()) {
                        if (!light.isRegistered()){
                            if ((light.getName()+light.getId()).equals(name)){
                                System.out.println("ah "+ name);
                                light.setRegistered(true);
                            }else{
                                System.out.println(light.toString());
                            }
                        }
                    }
        });

        LightObj light = null;
        Random rnd = new Random();
        for (int i = 0; i < 10; i++) {
            String lightId = null;
            light = new LightObj(i, 100 + i * 10, 10 + i * 10);
            // light.getName()+light.getId() makes something like Light1 - unique address for a device
            lightId = light.getName()+light.getId();
            lights.put(lightId, light);

            // the current issue is that casting to a method that isn't defined in the parent gives a default value...
            // we can negate this by having multiple renderLists of different types... but we don't want that
            // we're gonna do that, for now :)
            lights.get(lightId).getMessageClient()
                    .send(MQTT_TOPIC.DEVICE_REGISTER, "{"+Device.LIGHT+":state="+ lights.get(lightId).isState()+",id="+ lights.get(lightId).getId()+"}");
            sleep(1000);
        }
    }
    
    private void checkForStateChange() {
    
       //receives: topic=devices_change {light1:state=false}
       // response: alters lights hash map to the new change
       messageClient.advanceSubscribe(MQTT_TOPIC.DEVICE_CHANGE, (String topic, String name, HashMap<String, String> args)->{
           if (name.contains("Light")) {
                lights.get(name).setState(Boolean.parseBoolean(args.get("state")));
                lights.get(name).update(canvas);
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



//   lights = new HashMap<String, LightObj>();
//        for (int i = 0; i < 10; i++) {
//            lights.put("Light"+i, new LightObj("Light"+i));
//            registerDevice(lights.get("Light"+i));
//        }
//
//        // receives: topic=devices_register  {start:devices=true}
//        // sends: topic=devices_set {light1:state=false}
//        messageClient.advanceSubscribe(MQTT_TOPIC.DEVICE_REGISTER, (String topic, String name, String[][]args)->{
//            if (name.contains("start")){
//                if (args.length == 1){
//                    if (args[0][0].equals("devices")){
//                        if (Boolean.parseBoolean(args[0][1])){
//                            System.out.println("starting devices registration");
//
//                            final Object[] keys = lights.keySet().toArray();
//                            for (int i = 0; i < lights.keySet().size(); i++) {
//                                System.out.println("a");
//                                messageClient.send(MQTT_TOPIC.DEVICE_SET, "{"+keys[i].toString()+":state="+Boolean.toString(lights.get(keys[i].toString()).isOn())+"}");
//                                System.out.println("b");
//                                try {
//                                    wait(1000);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//
////                                System.out.println(lights.get(keys[i].toString()).getName());
//                            }
//
//                            messageClient.send(MQTT_TOPIC.DEVICE_REGISTER, "{finish:devices=true}");
//                        }else{
//                            System.out.println("device=false");
//                        }
//                    }else{
//                        System.err.println("invalid para");
//                    }
//                }else{
//                    System.err.println("args size incorrect");
//                }
//            }else{
//                System.out.println("could not find start");
//            }
//        });
//
//        // receives: topic=devices_change {light1:state=false}
//        // response: alters lights hash map to the new change
//        messageClient.advanceSubscribe(MQTT_TOPIC.DEVICE_CHANGE, (String topic, String name, String[][]args)->{
//            if (name.contains("Light")){
//                if (args.length == 1){
//                    if (args[0][0].equals("state")){
//                        lights.get(name).setState(Boolean.parseBoolean(args[0][1]));
//                        lights.get(name).update(canvas);
//                    }else{
//                        System.err.println("Could not find state");
//                    }
//                }else{
//                    System.err.println("args wrong length");
//                }
//            }else{
//                System.err.println("wrong name");
//            }
//        });