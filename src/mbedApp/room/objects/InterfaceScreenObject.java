package mbedApp.room.objects;

import mbedApp.mqtt.MessageClient;
import mbedApp.room.Canvas;

import java.awt.Graphics;

/**
 * Created by josephling on 12/02/2017.
 */
public interface InterfaceScreenObject {

    /**
     * @return x coordinate
     */
    int getX();

    /**
     * @return x coordinate
     */
    int getY();

    /**
     * sets the x var
     * @param newX
     */
    void setX(int newX);

    /**
     * sets the y var
     * @param newY
     */
    void setY(int newY);

    /**
     *
     * @param client MessageClient
     */
    void addClient(MessageClient client);

    /**
     *
     * @return MessageClient
     */
    MessageClient getClient();


    void register_client();

    /**
     *
     * @return
     */
    boolean isRegistered();

    /**
     *
     * @param state
     */
    void setRegistered(boolean state);

    /**
     * updates the obj and renders it to the frame
     */
    Graphics update(Canvas canvas);

}
