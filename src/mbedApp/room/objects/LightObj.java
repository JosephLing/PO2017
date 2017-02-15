package mbedApp.room.objects;

import mbedApp.devices.Light;
import mbedApp.room.Canvas;

import java.awt.Graphics;

/**
 * Light does.............
 *
 * @author josephling
 * @version 1.0 12/02/2017
 */
public class LightObj extends Light implements InterfaceScreenObject {

    private int x;
    private int y;
    private Graphics graphic;

    public LightObj(String name) {
        super(false, 0);
    }

    @Override
    public Graphics update(Canvas canvas) {
//        if (isOn()){
//
//        }else{
//
//        }
        return null;
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
