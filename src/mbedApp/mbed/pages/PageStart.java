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

    public PageStart(int nextPage) {
        /*
            menu  status:on no. dev: 10

            current temp: 20
            desired temp: 25
        */
        ButtonListener mainMenu = (isPressed) -> {
            if (isPressed){
                ScreenInterface.setCurrentPage(nextPage);
            }
        };
        textBox = new TextBox("Wake Up: 08:00\n0\nDevices Reg.", mainMenu);

    }

    @Override
    public void update() {
        textBox.setText("Wake Up: 08:00\n"+ HomeAutomator.getDevices().size()+"\nDevices Reg.");
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

    @Override
    public Page getPage(){
        return Page.START;
    }
}
