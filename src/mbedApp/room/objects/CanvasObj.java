package mbedApp.room.objects;

import mbedApp.devices.Device;
import mbedApp.mqtt.MessageClient;
import mbedApp.room.Canvas;

/**
 * Created by Joe on 21/02/2017.
 */
public class CanvasObj {


    private int x;
    private int y;

    private boolean registered;

    private MessageClient messageClient;

    private Device device;

    private InterfaceRender renderMethod;


    public CanvasObj(Device device, InterfaceRender renderMethod) {
        this.x = 0;
        this.y = 0;
        this.registered = false;
        this.device = device;
        this.renderMethod = renderMethod;
        this.messageClient = new MessageClient();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * sets the x var
     * @param newX int
     */
    public void setX(int newX){
        this.x = newX;
    }

    /**
     * sets the y var
     * @param newY int
     */
    public void setY(int newY){
        this.y = newY;
    }

    /**
     *
     * @return messageClient
     */
    public MessageClient getMessageClient() {
        return messageClient;
    }





    public Device getDevice() {
        return device;
    }

    /**
     *
     * @return boolean value if it has been registered
     */
    public boolean isRegistered(){
        return registered;
    }

    /**
     *
     * @param state boolean
     */
    public void setRegistered(boolean state){
        registered = state;
    }

    /**
     * updates the obj and renders it to the frame
     * @param canavs custom canvas
     */
    public void update(Canvas canavs){
        renderMethod.render(canavs);
    }

}
