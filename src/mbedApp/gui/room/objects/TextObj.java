package mbedApp.gui.room.objects;

import mbedApp.gui.room.Canvas;

/**
 * Write a description of class TextObj here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TextObj
{
    private int x;
    private int y;
    
    public TextObj(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void update(Canvas canvas, String s) {
        canvas.drawText(this, s, x, y);
            //canvas.drawText(this, "Current Temperature: " + currentTemp + "*C", x, y+10);
    }
}
