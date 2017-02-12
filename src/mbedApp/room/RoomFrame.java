package mbedApp.room;

import mbedApp.ProjectLogger;
import mbedApp.room.objects.InterfaceScreenObject;
import mbedApp.room.objects.LightObj;

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

    public RoomFrame() {
        renderList = new ArrayList<InterfaceScreenObject>();
        renderList.add(new LightObj("asdf"));

        init_Jframe();
        init_Canvas();
        setVisible(true);
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
    }

    private void init_Canvas(){
        canvas.draw(this, Color.red, new Ellipse2D.Double(250, 250, 50, 50));
    }



}
