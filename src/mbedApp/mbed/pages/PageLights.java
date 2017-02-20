package mbedApp.mbed.pages;

import mbedApp.mbed.display.TextBox;

/**
 * LightsPage does.............
 *
 * @author josephling
 * @version 1.0 20/02/2017
 */
public class PageLights implements InterfaceUI {
    private TextBox textBox;

    public PageLights() {
        textBox = new TextBox("lights", null);
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
