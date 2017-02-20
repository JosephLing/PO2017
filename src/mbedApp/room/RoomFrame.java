package mbedApp.room;

import mbedApp.ProjectLogger;
import mbedApp.mqtt.MQTT_TOPIC;
import mbedApp.mqtt.MessageClient;
import mbedApp.room.objects.InterfaceScreenObject;
import mbedApp.room.objects.LightObj;
import mbedApp.devices.Light;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import mbedApp.devices.Device;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * RoomFrame does.............
 *
 * @author josephling
 * @version 1.0 12/02/2017
 */
public class RoomFrame extends JFrame {

    private ArrayList<InterfaceScreenObject> renderList;

    private Canvas canvas;
    private MessageClient messageClient;

//    private HashMap<String, LightObj> lights;

    public RoomFrame() {
        messageClient = new MessageClient();
        renderList = new ArrayList<InterfaceScreenObject>();
        renderList.add(new LightObj("asdf"));

        init_Jframe();
        registerLights();
        setVisible(true);
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


//        addWindowListener(new WindowAdapter()
//        {
//            public void windowClosing(WindowEvent e)
//            {
//                System.out.println("asf");
//            }
//        });

    }

    private void drawEverything(){
        for (int i = 0; i < renderList.size(); i++) {
            renderList.get(i).update(canvas);
        }
    }

    private void init_Canvas(){
        canvas.draw(this, Color.red, new Ellipse2D.Double(250, 250, 50, 50));
    }

    public void registerLights(){
        LightObj[] lights = new LightObj[10];
        for (int i = 0; i < lights.length; i++) {
            lights[i] = new LightObj(Device.LIGHT);
            lights[i].addClient(new MessageClient());
            lights[i].getClient().send(MQTT_TOPIC.DEVICE_REGISTER, "{"+Device.LIGHT+":state="+lights[i].isState()+",id="+i+"}");
            //                                messageClient.send(MQTT_TOPIC.DEVICE_SET, "{"+Device.parseNewDeviceId(name, args)+":registered:true}");
            lights[i].register_client();
        }
    }


    private void registerDevice(InterfaceScreenObject object){
        renderList.add(object);
        object.addClient(new MessageClient());
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