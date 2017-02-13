package mbedApp.room;

import mbedApp.ProjectLogger;
import mbedApp.mqtt.ClientType;
import mbedApp.mqtt.MessageClient;
import mbedApp.room.objects.InterfaceScreenObject;
import mbedApp.room.objects.LightObj;
import mbedApp.devices.Light;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

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

    public RoomFrame() {
        messageClient = new MessageClient();
        renderList = new ArrayList<InterfaceScreenObject>();
        renderList.add(new LightObj("asdf"));

        init_Jframe();
        init_objects();
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

    public void init_objects(){
        LightObj[] lights = new LightObj[10];
        for (int i = 0; i < lights.length; i++) {
            lights[i] = new LightObj("Light"+i);
            registerDevice(lights[i]);
        }
    }


    private void registerDevice(InterfaceScreenObject object){
        renderList.add(object);
    }
}
