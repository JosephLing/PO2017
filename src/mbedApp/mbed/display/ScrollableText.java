package mbedApp.mbed.display;

import mbedApp.ProjectLogger;
import mbedApp.mbed.HomeAutomator;
import shed.mbed.ButtonListener;
import shed.mbed.LCD;

/**
 * ScrollableText does.............
 *
 * @author josephling
 * @version 1.0 16/02/2017
 */
public abstract class ScrollableText {



    private int screenWidth;
    private int screenHeight;

    private int selected;
    private int menuSpacing;

    private String[] msgArray;

    private LCD lcd;

    private ButtonListener up;
    private ButtonListener down;


    /**
     * Constructor:
     * @param msg String[] data that you want to display per line
     */
    public ScrollableText(String[] msg) {
        this.msgArray = msg;
        selected = 0;
        lcd = HomeAutomator.getMBed().getLCD();
        screenWidth = lcd.getWidth();
        screenHeight = lcd.getHeight();
        menuSpacing = 10;

        down = (isPressed) -> {
            if(isPressed) {
                if (selected+1 < msg.length){
                    selected ++;
                }
                this.update();
            }};

        up = (isPressed) -> {
            if(isPressed) {
                if (selected-1 >= 0){
                    selected --;
                }
                this.update();
            }};
    }

    /**
     * Uses a InterfaceScrollableText to allow you set what is done at the for the update_header, update_main and
     * update_footer. Currently will only display only 3 values.
     */
    public void update(){
        ProjectLogger.Log("update loop");
        getLcd().clear();
        int maxDisplay = screenHeight / menuSpacing;
        int index = 0;
        if (maxDisplay < msgArray.length){
            if (selected >= maxDisplay){
                index = selected - maxDisplay + 1;
            }
        }
        int y = 0;
        int count = 0;
        update_header(index, count, y);
        while (y < screenHeight && count < maxDisplay && index < msgArray.length){
            y = menuSpacing * count;
            update_main(index, count, y);
            count ++;
            index ++;
        }
        y = menuSpacing * count;
        update_footer(index, count, y);
    }

    /**
     * enables the controls
     */
    public void enableControls(){
        ProjectLogger.Log("enabling controls for scrollable text");
        HomeAutomator.getMBed().getJoystickUp().addListener(up);
        HomeAutomator.getMBed().getJoystickDown().addListener(down);
    }

    /**
     * disables the controls
     */
    public void disableControls(){
        ProjectLogger.Log("disabling controls for scrollable text");
        HomeAutomator.getMBed().getJoystickDown().removeListener(down);
        HomeAutomator.getMBed().getJoystickUp().removeListener(up);
    }


    public int getSelected() {
        return selected;
    }

    public String[] getMsgArray() {
        return msgArray;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getMenuSpacing() {
        return menuSpacing;
    }

    public LCD getLcd() {
        sleep(50);
        return lcd;
    }

    /**
     * update method: update_header of out to lcd
     * @param index int
     * @param count int
     * @param y int
     */
    abstract void update_header(int index, int count, int y);

    /**
     * update method: update_main of out to lcd
     * @param index int
     * @param count int
     * @param y int
     */
    abstract void update_main(int index, int count, int y);

    /**
     * update method: update_footer of out to lcd
     * @param index int
     * @param count int
     * @param y int
     */
    abstract void update_footer(int index, int count, int y);

    private void sleep(long milliseconds) {
        try {
            // normally 50 milliseconds
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
