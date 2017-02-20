package mbedApp.mbed.pages;

import mbedApp.mbed.display.TextBox;

/**
 * TempraturePage does.............
 *
 * @author josephling
 * @version 1.0 20/02/2017
 */
public class PageTemprature implements InterfaceUI {

    private TextBox textBox;

    public PageTemprature() {

        textBox = new TextBox("temprature", null);
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
}
