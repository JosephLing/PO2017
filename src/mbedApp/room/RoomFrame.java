package mbedApp.room;

import mbedApp.devices.Device;
import mbedApp.devices.Light;
import mbedApp.mqtt.MQTT_TOPIC;
import mbedApp.mqtt.MessageClient;
import mbedApp.room.objects.CanvasObj;
import mbedApp.room.objects.InterfaceScreenObject;
import mbedApp.room.objects.RenderObjs;

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

    private ArrayList<CanvasObj> renderList;

    private Canvas canvas;
    private MessageClient messageClient;

//    private HashMap<String, LightObj> lights;

    public RoomFrame() {
        messageClient = new MessageClient();
        renderList = new ArrayList<CanvasObj>();

        init_Jframe();
        registerLights();
        setVisible(true);
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
            if (renderList.get(i).isRegistered()){
                System.out.println("asdf");
                renderList.get(i).update(canvas);
            }
        }
    }

    private void init_Canvas(){
//        canvas.draw(this, Color.red, new Ellipse2D.Double(250, 250, 50, 50));
    }

    public void registerLights(){
        messageClient.advanceSubscribe(MQTT_TOPIC.DEVICE_SET,
                (String topic, String name, HashMap<String, String> args)->{
                    System.out.println(name);
//                    renderList.stream()
//                            .filter(a -> !a.isRegistered())
//                            .filter(b -> b.getName().equals(name))
//                            .forEach(c -> c.setRegistered(true));
                    for (int i = 0; i < renderList.size(); i++) {
                        if (!renderList.get(i).isRegistered()){
                            if ((renderList.get(i).getDevice().getName()+renderList.get(i).getDevice().getId()).equals(name)){
                                System.out.println("ah "+ name);
                                renderList.get(i).setRegistered(true);
                            }else{
                                System.out.println(renderList.get(i).toString());
                            }
                        }
                    }
//                    boolean found = false;
//                    int count = 0;
//                    while (!found && count < renderList.size()){
//                        if (!renderList.get(count).isRegistered()){
//                            Device foo = ((Device) renderList.get(count));
////                            System.out.println(foo.getName() + foo.getId() + " " + name);
//                            if ((foo.getName()+foo.getId()).equals(name)){
//                                renderList.get(count).setRegistered(true);
//                            }
//                        }else{
//                            count ++;
//                        }
//                    }
        });

        Light light = null;
        Random rnd = new Random();
        for (int i = 0; i < 10; i++) {
            boolean t = rnd.nextBoolean();
            System.out.println(t);
            light = new Light(t, i);

            renderList.add(new CanvasObj( light, RenderObjs.LIGHT.getInterfaceRender()));

            // the current issue is that casting to a method that isn't defined in the parent gives a default value...
            // we can negate this by having multiple renderLists of different types... but we don't want that
            renderList.get(i)
                    .getMessageClient()
                    .send(MQTT_TOPIC.DEVICE_REGISTER, "{"+Device.LIGHT+":state="+((Light)renderList.get(i).getDevice()).isState()+",id="+((Light)renderList.get(i).getDevice()).getId()+"}");

//            lights[i].addClient(new MessageClient());
//            lights[i].getClient().send(MQTT_TOPIC.DEVICE_REGISTER, "{"+Device.LIGHT+":state="+lights[i].isState()+",id="+i+"}");
            //                                messageClient.send(MQTT_TOPIC.DEVICE_SET, "{"+Device.parseNewDeviceId(name, args)+":registered:true}");
//            lights[i].register_client();
//            renderList.add(lights[i]);
            sleep(1000);
        }



//
//                    if (name.contains(Device.LIGHT)){
//                        String reg = args.get("registered");
//                        if (reg != null){
//                            setRegistered(!isRegistered());
//                        }
//                    }


//        messageClient.advanceSubscribe(MQTT_TOPIC.DEVICE_SET,
//                (String topic, String name, HashMap<String, String> args)->{
//
//                });
    }


    private void registerDevice(InterfaceScreenObject object){
//        renderList.add(object);
        object.addClient(new MessageClient());
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