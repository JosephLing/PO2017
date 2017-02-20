package mbedApp.mbed.pages;

import mbedApp.ProjectLogger;
import mbedApp.mbed.display.Menu;

/**
 * MainMenuPage does.............
 *
 * @author josephling
 * @version 1.0 20/02/2017
 */
public class PageMainMenu implements InterfaceUI{

    private Menu mainMenu;


    public PageMainMenu() {
        String[] itemNames = {"lights", "temprature", "Settings", "Credits","Back", "Quit"};
        mainMenu = new Menu(itemNames, null);
    }

    @Override
    public void update() {
        mainMenu.update();
    }

    @Override
    public void close() {
        mainMenu.disableControls();
    }

    @Override
    public void open() {
        ProjectLogger.Log("CONTROLS ENABLED!!!");
        mainMenu.enableControls();
    }
}
