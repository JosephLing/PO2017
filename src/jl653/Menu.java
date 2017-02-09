package jl653;

import shed.mbed.MBed;
import shed.mbed.LCD;
import shed.mbed.PixelColor;


/**
 * Menu does.............
 *
 * @author josephling
 * @version 1.0 08/02/2017
 */
public class Menu implements interfaceUI {

    private int screenSize = 10;

    private int screenWidth;
    private int screenHeight;

    private int selected;
    private int menuSpacing;

    private String[] menuName;
    private interfaceUI[] menuCmd;

    private LCD lcd;

    public Menu(String[] menuName, interfaceUI[] menuCmd) {

        lcd = Main.mBed.getLCD();
        screenWidth = lcd.getWidth();
        screenHeight = lcd.getHeight();

        if (menuName.length != menuCmd.length){
            System.out.println("Error menuName is not equal menuButton");
        }
        selected = 0;
        menuSpacing = 10;

        this.menuCmd = menuCmd;
        this.menuName = menuName;
    }




    public void update(){
        // drawLine(int startX, int startY, int endX, int endY, PixelColor pixel)
        lcd.clear();
        int maxDisplay = screenHeight / menuSpacing;

        int index = 0;
        if (maxDisplay < menuName.length){
            if (selected >= maxDisplay){
                index = selected - maxDisplay + 1;
            }
        }
        int y = 0;
        int count = 0;
        while (y < screenHeight && count < maxDisplay && index < menuName.length){
            y = menuSpacing * count;
            lcd.drawLine(0,y,screenWidth,y, PixelColor.BLACK);
            lcd.print(50, y+2, menuName[index]);
            if (index == selected){
                lcd.drawCircle(25, y+menuSpacing/2, 4, PixelColor.BLACK);
            }
            count ++;
            index ++;
        }
        y = menuSpacing * count;
        lcd.drawLine(0,y,screenWidth,y, PixelColor.BLACK);

    }

    public void runSelected(){
        this.menuCmd[selected].update();
    }

    public void increaseSelected(){
        if (selected + 1 < menuCmd.length){
            selected ++;
        }
    }

    public void decreaseSelected(){
        if (selected - 1 >= 0){
            selected --;
        }
    }
}
