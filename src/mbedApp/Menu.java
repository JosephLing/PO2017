package mbedApp;

 

import shed.mbed.ButtonListener;
import shed.mbed.LCD;
import shed.mbed.PixelColor;


/**
 * Menu does.............
 *
 * @author josephling
 * @version 1.0 08/02/2017
 */
public class Menu implements InterfaceMenu {

    private int screenSize = 10;

    private int screenWidth;
    private int screenHeight;

    private int selected;
    private int menuSpacing;

    private String[] menuName;
    private interfaceUI[] menuCmd;

    private LCD lcd;

    private ButtonListener fire;
    private ButtonListener up;
    private ButtonListener down;

    /**
     *
     * @param menuName
     * @param menuCmd
     */
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

        // might need to disable later on
        GUI.disableAllControls();

        up = (isPressed) -> {
            if(isPressed) {
                this.decreaseSelected();
                this.update();
            }};

        down = (isPressed) -> {
            if(isPressed) {
                this.increaseSelected();
                this.update();
            }};

        fire = (isPressed) -> {
            if(isPressed) {
                this.runSelected();
            }};
    }

    /**
     *
     * @param newMenuName
     * @param index
     */
    public void setMenuName(String newMenuName, int index) {
        if (index < menuName.length){
            menuName[index] = newMenuName;
        }

        this.menuName = menuName;
    }

    /**
     *
     * @param newMenuCmd
     * @param index
     */
    public void setMenuCmd(interfaceUI newMenuCmd, int index) {
       if (index < menuCmd.length){
           menuCmd[index] = newMenuCmd;
       }
    }

    /**
     *
     */
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
            lcd.print(35, y+2, menuName[index]);
            if (index == selected){
                lcd.drawCircle(20, y+menuSpacing/2, 4, PixelColor.BLACK);
            }
            count ++;
            index ++;
        }
        y = menuSpacing * count;
        lcd.drawLine(0,y,screenWidth,y, PixelColor.BLACK);

    }

    /**
     *
     */
    public void runSelected(){
        this.disableControls();
        this.menuCmd[selected].update();
    }

    /**
     *
     */
    public void increaseSelected(){
        if (selected + 1 < menuCmd.length){
            selected ++;
        }
    }

    /**
     *
     */
    public void decreaseSelected(){
        if (selected - 1 >= 0){
            selected --;
        }
    }

    /**
     *
     */
    public void enableControls(){
        ProjectLogger.Log("loading up menu controls");
        GUI.mBed.getJoystickUp().addListener(up);
        GUI.mBed.getJoystickDown().addListener(down);
        GUI.mBed.getJoystickFire().addListener(fire);
    }

    /**
     * disables the controls
     */
    public void disableControls(){
        ProjectLogger.Log("disabling menu controls");
        GUI.mBed.getJoystickDown().removeListener(down);
        GUI.mBed.getJoystickUp().removeListener(up);
        GUI.mBed.getJoystickFire().removeListener(fire);

    }
}
