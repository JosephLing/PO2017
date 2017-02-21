package mbedApp.mbed.pages;

import mbedApp.mbed.HomeAutomator;
import mbedApp.mbed.ScreenInterface;
import mbedApp.mbed.display.TextBox;
import shed.mbed.ButtonListener;

/**
 * StartPage does.............
 *
 * @author josephling
 * @version 1.0 20/02/2017
 */
public class PageStart implements InterfaceUI {


    private TextBox textBox;

    public PageStart() {
        /*
            menu  status:on no. dev: 10

            current temp: 20
            desired temp: 25
        */
        ButtonListener mainMenu = (isPressed) -> {
            if (isPressed){
                ScreenInterface.goUp();
            }
        };
        textBox = new TextBox("Home Automation System\n0\nDevices Registered", mainMenu);

    }

    @Override
    public void update() {
        textBox.setText("Home Automation System\n"+ HomeAutomator.getDevices().size()+"\nDevices Registered");
        textBox.update();
    }

    @Override
    public void close() {
        textBox.disableControls();
    }

    @Override
    public void open() {
        textBox.enableControls();
    }
}
