package mbedApp;

import shed.mbed.PixelColor;
import shed.mbed.ButtonListener;
import shed.mbed.LCD;
/**
 * LightsMainMenu does.............
 *
 * @author josephling
 * @version 1.0 10/02/2017
 */
public class LightsMainMenu implements InterfaceMenu {

    private int screenSize = 10;

    private int screenWidth;
    private int screenHeight;

    private int menuSpacing;
    private int selected;

    private LCD lcd;

    private Light[] lights;

    private ButtonListener up;
    private ButtonListener down;
    private ButtonListener fire;
    private ButtonListener switchLight;

    public LightsMainMenu(Light[] lights) {
        this.lights = lights;
        selected = 0;
        menuSpacing = 10;
        lcd = Main.mBed.getLCD();
        screenWidth = lcd.getWidth();
        screenHeight = lcd.getHeight();
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

        switchLight = (isPressed) -> {
          if(isPressed){
              lights[selected].toggle();
              this.update();
          }
        };
    }

    @Override
    public void update() {
        lcd.clear();
        int maxDisplay = screenHeight / menuSpacing;

        int index = 0;
        if (maxDisplay < lights.length){
            if (selected >= maxDisplay){
                index = selected - maxDisplay + 1;
            }
        }
        int y = 0;
        int count = 1;
        // heading
        lcd.print(0, 0, "Options On|Off");

        // background
        // lcd.fillRectangle(50, menuSpacing*count, screenWidth,maxDisplay*menuSpacing, PixelColor.BLACK);


        while (y < screenHeight && count < maxDisplay && index < lights.length){
            y = menuSpacing * count;

            lcd.print(0, y+2, lights[index].toString());
            if (lights[index].isOn()){
                lcd.print(65,y+2,"[x]");
                lcd.print(90,y+2,"[ ]");
            }else{
                lcd.print(90,y+2,"[x]");
                lcd.print(65,y+2,"[ ]");
            }

            if (index == selected){

            }
            count ++;
            index ++;
        }
    }

    @Override
    public void disableControls() {
        GUI.mBed.getJoystickDown().removeListener(down);
        GUI.mBed.getJoystickUp().removeListener(up);
        GUI.mBed.getJoystickLeft().removeListener(switchLight);
        GUI.mBed.getJoystickRight().removeListener(switchLight);

    }

    @Override
    public void enableControls() {
        GUI.mBed.getJoystickDown().addListener(down);
        GUI.mBed.getJoystickUp().addListener(up);
        GUI.mBed.getJoystickLeft().addListener(switchLight);
        GUI.mBed.getJoystickRight().addListener(switchLight);
    }

    @Override
    public void decreaseSelected() {
        if (selected-1 >= 0){
            selected --;
        }
    }

    @Override
    public void increaseSelected() {
        if (selected+1 < lights.length){
            selected ++;
        }
    }

    private void foo(){

    }


    @Override
    public void runSelected() {

    }
}
