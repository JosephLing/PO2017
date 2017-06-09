package mbedApp.gui.room.objects;

import mbedApp.gui.room.Canvas;
import mbedApp.mqtt.MessageClient;

/**
 * Created by josephling on 12/02/2017.
 */
public interface InterfaceScreenObject {

    /**
     * @return x coordinate
     */
    int getX();

    /**
     * sets the x var
     *
     * @param newX int
     */
    void setX(int newX);

    /**
     * @return x coordinate
     */
    int getY();

    /**
     * sets the y var
     *
     * @param newY int
     */
    void setY(int newY);

    /**
     * @param client MessageClient
     */
    void addClient(MessageClient client);


    void register_client();

    /**
     * @return boolean value if it has been registered
     */
    boolean isRegistered();

    /**
     * @param state boolean
     */
    void setRegistered(boolean state);

    /**
     * updates the obj and renders it to the frame
     *
     * @param canvas custom Canvas object used to write to
     */
    void update(Canvas canvas);
}
