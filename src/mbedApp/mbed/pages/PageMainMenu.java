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
    private String[] itemNames;
    private int[] optionsIndex;

    public PageMainMenu(int[] optionsIndex) {
        this.optionsIndex = optionsIndex;
        itemNames = new String[]{"Lights", "Temprature", "Settings", "Credits","Back", "Quit"};
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
        mainMenu = new Menu(itemNames, optionsIndex);
        ProjectLogger.Log("CONTROLS ENABLED!!!");
        mainMenu.enableControls();
    }

    @Override
    public Page getPage(){
        return Page.MAINMENU;
    }
}
