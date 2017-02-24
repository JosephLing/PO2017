package mbedApp.mbed.pages;

import mbedApp.mbed.display.TextBox;
import mbedApp.mbed.Temperature;

/**
 * TempraturePage does.............
 *
 * @author josephling
 * @version 1.0 20/02/2017
 */
public class PageTemprature implements InterfaceUI {

    private TextBox textBox;

    public PageTemprature() {

        textBox = new TextBox("Current Temperature\n" + Temperature.getCurrentTemp() + "C", null);
    }

    @Override
    public void update() {
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
        return Page.TEMP;
    }
}
