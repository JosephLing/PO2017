package mbedApp.room;

import mbedApp.ProjectLogger;

import javax.swing.*;

/**
 * RoomFrame does.............
 *
 * @author josephling
 * @version 1.0 12/02/2017
 */
public class RoomFrame extends JFrame {

    public RoomFrame() {
        ProjectLogger.Log("creating RoomFrame");
        setTitle("Simple example");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
