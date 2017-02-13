package mbedApp.room.objects;

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
     * updates the obj and renders it to the frame
     */
    Graphics update(Canvas canvas);

}
