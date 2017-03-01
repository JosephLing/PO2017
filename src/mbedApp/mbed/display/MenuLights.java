package mbedApp.mbed.display;

import mbedApp.devices.Light;
import mbedApp.mbed.HomeAutomator;
import mbedApp.mbed.ScreenInterface;
import mbedApp.mqtt.MQTT_TOPIC;
import shed.mbed.ButtonListener;
import shed.mbed.PixelColor;

/**
 * Created by Joe on 25/02/2017.
 */
public class MenuLights extends Menu {

    private Light[] lights;
    private ButtonListener left;
    private ButtonListener right;

    public MenuLights(String[] msg, int[] menuIndex, Light[] lights) {
        super(msg, menuIndex);
        this.lights = lights;

        // right = true
        // left = false

        left = (isPressed) ->{
            if (isPressed){
                Light light =this.lights[getSelected()];
                if (light != null){
                    if (!light.isState()){
                        light.setState(true);
                        changeLightState(light);
                    }
                }
            }
        };
        right = (isPressed) ->{
            if (isPressed){
                Light light =this.lights[getSelected()];
                if (light != null){
                    if (light.isState()){
                        light.setState(false);
                        changeLightState(light);
                    }
                }
            }
        };
    }

    private void changeLightState(Light light){
        ScreenInterface.getMessageClient().send(
                MQTT_TOPIC.DEVICE_CHANGE,
                "{"+light.getName()+light.getId()+":state="+light.isState()+"}"
        );
    }


    @Override
    protected void update_main(int index, int count, int y) {
        if (lights[index] != null){ // so not the back button
            String output = "[x][ ]";
            if (!lights[index].isState()){
                output = "[ ][x]";
            }
            getLcd().print(80, y+2, output);
        }
        getLcd().drawLine(0,y,getScreenWidth(),y, PixelColor.BLACK);
        getLcd().print(25, y+2, getMsgArray()[index]);

        if (index == getSelected()){
            getLcd().drawCircle(20, y+getMenuSpacing()/2, 4, PixelColor.BLACK);
        }
    }

    @Override
    public void enableControls() {
        super.enableControls();
        HomeAutomator.getMBed().getJoystickLeft().addListener(left);
        HomeAutomator.getMBed().getJoystickRight().addListener(right);

    }

    @Override
    public void disableControls() {
        super.disableControls();
        HomeAutomator.getMBed().getJoystickLeft().removeListener(left);
        HomeAutomator.getMBed().getJoystickRight().removeListener(right);
    }
}
