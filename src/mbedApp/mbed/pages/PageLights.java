package mbedApp.mbed.pages;

import mbedApp.ProjectLogger;
import mbedApp.devices.Device;
import mbedApp.devices.Light;
import mbedApp.mbed.HomeAutomator;
import mbedApp.mbed.display.MenuLights;

import java.util.HashMap;

/**
 * LightsPage does.............
 *
 * @author josephling
 * @version 1.0 20/02/2017
 */
public class PageLights implements InterfaceUI {

    private MenuLights lighting;
    private String[] lightControls;

    public PageLights() {

    }

    @Override
    public void update() {
        if (HomeAutomator.getDevices().size() != lightControls.length-1){
            ProjectLogger.Warning("need to reload lights menu new lights found!");
        }
        lighting.update();
    }

    @Override
    public void close() {
        if (lighting != null){
            lighting.disableControls();
        }
    }

    @Override
    public void open() {
        HashMap<String, Device> temp = HomeAutomator.getDevices();
        lightControls = new String[temp.size()+1];
        int[] index = new int[lightControls.length];
        Light[] lights = new Light[lightControls.length];
        int i = 0;
        for(String deviceName : temp.keySet()) {
            if(deviceName.contains("Light")) {
                lightControls[i] = deviceName;
                index[i] = -1;
                lights[i] = (Light) temp.get(deviceName);
                i++;
            }
        }
        lightControls[lightControls.length-1] = "Back";
        index[index.length-1] = Page.BACK.getIndex();
        lighting = new MenuLights(lightControls, index, lights);
        lighting.enableControls();
    }

    @Override
    public Page getPage(){
        return Page.LIGHTS;
    }
}
