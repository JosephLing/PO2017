package mbedApp.mbed.pages;

import mbedApp.ProjectLogger;
import mbedApp.mbed.HomeAutomator;
import mbedApp.mbed.ScreenInterface;
import mbedApp.mbed.display.TextBox;

/**
 * PageQuit does.............
 *
 * @author josephling
 * @version 1.0 20/02/2017
 */
public class PageQuit implements InterfaceUI {

    private TextBox textBox;

    public PageQuit() {
        textBox = new TextBox("\nQuitting", null);

    }

    @Override
    public void update() {
    }

    @Override
    public void close() {

    }

    @Override
    public void open() {
        ProjectLogger.Log("closing down messageClient and mbed");
        textBox.update();
        ScreenInterface.sleep(2000);
        //TODO: implement messageClient closing
//        this.messageClient.disconnect();

        HomeAutomator.getMBed().close();
        System.exit(0);
    }

    @Override
    public Page getPage() {
        return Page.QUIT;
    }
}
