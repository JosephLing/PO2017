package mbedApp.mbed.pages;

import mbedApp.mbed.display.Menu;

/**
 * MainMenuPage does.............
 *
 * @author josephling
 * @version 1.0 20/02/2017
 */
public class PageMainMenu implements InterfaceUI {

    private Menu mainMenu;
    private String[] itemNames;
    private int[] optionsIndex;

    public PageMainMenu(int[] optionsIndex) {
        this.optionsIndex = optionsIndex;
        itemNames = new String[]{"Lights", "Temprature", "Settings", "Credits", "Back", "Quit"};
    }

    @Override
    public void update() {
//        System.out.println("--update--");
        mainMenu.update();
    }

    @Override
    public void close() {
        mainMenu.disableControls();
    }

    @Override
    public void open() {
//        System.out.println("--open--");
        mainMenu = new Menu(itemNames, optionsIndex);
        mainMenu.enableControls();
    }

    @Override
    public Page getPage() {
        return Page.MAINMENU;
    }
}
