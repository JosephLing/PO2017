package mbedApp.mbed.pages;

import mbedApp.mbed.display.Menu;

/**
 * SettingsPage does.............
 *
 * @author josephling
 * @version 1.0 20/02/2017
 */
public class PageSettings implements InterfaceUI {

    private Menu settings;

    public PageSettings(int[] indexPage) {
        String[] settingsTitles = {
                "MQTT",
                "Events",
                "back"
        };
//        InterfaceUI[] settingsCmds = {
//                ()->{System.out.println("mqtt stuff");},
//                ()->{System.out.println("temprature settings");};
////                ()->{backToMainMenu();}
//        };
        settings = new Menu(settingsTitles, indexPage);
    }

    @Override
    public void update() {
        settings.update();

    }

    @Override
    public void close() {
        settings.disableControls();
    }

    @Override
    public void open() {
        settings.enableControls();

    }

    @Override
    public Page getPage(){
        return Page.SETTINGS;
    }
}
