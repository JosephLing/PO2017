package mbedApp.room.objects;

import mbedApp.devices.Light;

/**
 * Light does.............
 *
 * @author josephling
 * @version 1.0 12/02/2017
 */
public class LightObj extends Light implements InterfaceScreenObject {

    private int x;
    private int y;


    public LightObj(boolean state, String name) {
        super(state, name);
    }

    @Override
    public void update() {
        if (isOn()){

        }else{

        }
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setX(int newX) {
        x = newX;
    }

    @Override
    public void setY(int newY) {
        y = newY;
    }
}
