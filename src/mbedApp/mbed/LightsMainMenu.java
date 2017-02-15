package mbedApp.mbed;


import mbedApp.devices.Light;
import mbedApp.Main;
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
        lcd = HomeAutomator.getMBed().getLCD();
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
//                lights[selected].toggle();
                this.update();
            }
        };
    }

    @Override
    public void update() {
        // TODO: work out how on earth this to only render two items at a time
        // TODO: OR just render on and off better
        lcd.clear();
        int maxDisplay = screenHeight /   menuSpacing; // hard coded for now as we have header (screenHeight / menuSpacing)
        int index = 0;
        if (maxDisplay < lights.length){
            if (selected >= maxDisplay){
                index = selected - maxDisplay;
                System.out.println("selected: " + selected + " index: " + index);
            }
        }
        int y = 0;
        int count = 0;
        while (y < screenHeight && count < maxDisplay && index < lights.length){
            y = menuSpacing * count;
            if (lights[index].isState()){
                lcd.print(65,y+2,"[x]");
                lcd.print(90,y+2,"[ ]");
            }else{
                lcd.print(90,y+2,"[x]");
                lcd.print(65,y+2,"[ ]");
            }

            if (index == selected){
                lcd.print(0, y+2, "*"+lights[index].toString());
            }else{
                lcd.print(0, y+2, " "+lights[index].toString());
            }
            count ++;
            index ++;
        }
    }

    @Override
    public void disableControls() {
        HomeAutomator.getMBed().getJoystickDown().removeListener(down);
        HomeAutomator.getMBed().getJoystickUp().removeListener(up);
        HomeAutomator.getMBed().getJoystickLeft().removeListener(switchLight);
        HomeAutomator.getMBed().getJoystickRight().removeListener(switchLight);

    }

    @Override
    public void enableControls() {
        HomeAutomator.getMBed().getJoystickDown().addListener(down);
        HomeAutomator.getMBed().getJoystickUp().addListener(up);
        HomeAutomator.getMBed().getJoystickLeft().addListener(switchLight);
        HomeAutomator.getMBed().getJoystickRight().addListener(switchLight);
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

    @Override
    public void runSelected() {

    }
}
