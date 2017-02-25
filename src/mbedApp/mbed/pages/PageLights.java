package mbedApp.mbed.pages;

import mbedApp.mbed.HomeAutomator;
import mbedApp.mbed.display.Menu;
import mbedApp.mbed.display.TextBox;
import java.util.HashMap;
import mbedApp.devices.Device;

/**
 * LightsPage does.............
 *
 * @author josephling
 * @version 1.0 20/02/2017
 */
public class PageLights implements InterfaceUI {
    private TextBox textBox;
    private Menu lighting;
    private HashMap<String, Device> devices;
    private String[] lightControls;
    
    public PageLights(int[] indexPage) {
        String[] lightControls = {
            "Back"
        };
        
        lighting = new Menu(lightControls, indexPage);
        //textBox = new TextBox("Lighting Control", null);
    }

    @Override
    public void update() {
        devices = HomeAutomator.getDevices();
        int i = 1;
        for(String deviceName : devices.keySet()) {
            if(deviceName.contains("Light")) {
                lightControls[i] = deviceName;
                i++;
            }
        }
        
        lighting.update();
    }

    @Override
    public void close() {
        lighting.disableControls();
    }

    @Override
    public void open() {
        lighting.enableControls();
    }

    @Override
    public Page getPage(){
        return Page.LIGHTS;
    }
}
