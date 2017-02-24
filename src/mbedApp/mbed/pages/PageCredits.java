package mbedApp.mbed.pages;

import mbedApp.mbed.display.TextBox;

/**
 * CreditsPage does.............
 *
 * @author josephling
 * @version 1.0 20/02/2017
 */
public class PageCredits implements InterfaceUI {

    private TextBox textBox;

    public PageCredits() {
        textBox = new TextBox("Joe\nWill\nPierre\nKhem", null);
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
        return Page.CREDITS;
    }
}
