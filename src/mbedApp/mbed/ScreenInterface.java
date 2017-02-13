package mbedApp.mbed;

import shed.mbed.ButtonListener;
import mbedApp.mqtt.MessageClient;
import mbedApp.ProjectLogger;

/**
 * Write a description of class ScreenInterface here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ScreenInterface
{
    private Menu mainMenu;
    private LightsMainMenu lightsMainMenu;
    private Menu settings;
    private MessageClient messageClient;

    /**
     * Constructor for objects of class ScreenInterface
     */
    public ScreenInterface(MessageClient messageClient)
    {
        this.messageClient = messageClient;
        mainMenu();
    }

    private void mainMenu(){
        this.messageClient.send("hello world");
        String[] itemNames = {"lights", "temprature", "Settings", "Credits", "Quit"};

        InterfaceUI[] itemCmds = {
                Lights, // lights
                Temprature, // temprature
                Settings,// , // settings
                Credits, // credits
                Quit // quit
        };

        mainMenu = new Menu(itemNames, itemCmds);
        mainMenu.enableControls();
        mainMenu.update();
    }

    private void backToMainMenu(){
        disableAllControls();
        mainMenu.enableControls();
        mainMenu.update();
    }

    /**
     * ButtonListener for the back button to main menu.
     * TODO: maybe make it dynamic?
     */
    private ButtonListener backButtonToMainMenu  = (isPressed) -> {
        if(isPressed) {
            backToMainMenu();
        }
    };

    private InterfaceUI Lights = () -> {
//        lightsMainMenu = new LightsMainMenu(messageClient.getLights());
//        lightsMainMenu.enableControls();
//        HomeAutomator.getMBed().getJoystickFire().addListener(backButtonToMainMenu);
//        lightsMainMenu.update();

    };

    private InterfaceUI Temprature = () -> {
        TextBox textBox = new TextBox("temprature");
        textBox.clear();
        textBox.render();
        HomeAutomator.getMBed().getJoystickFire().addListener(backButtonToMainMenu);
    };

    private InterfaceUI Settings = () -> {
        String[] settingsTitles = {
                "MQTT",
                "Temprature",
                "back"
        };
        InterfaceUI[] settingsCmds = {
                ()->{System.out.println("mqtt stuff");},
                ()->{System.out.println("temprature settings");},
                ()->{backToMainMenu();}
        };
        settings = new Menu(settingsTitles, settingsCmds);
        settings.enableControls();
        settings.update();
    };

    private InterfaceUI Credits = () -> {
        TextBox textBox = new TextBox("Joe\nWill\nPierre\nKhem");
        textBox.clear();
        textBox.render();
        HomeAutomator.getMBed().getJoystickFire().addListener(backButtonToMainMenu);
    };

    private InterfaceUI Quit = ()->{
        ProjectLogger.Log("closing down messageClient and mbed");
        this.messageClient.disconnect();
        HomeAutomator.getMBed().close();
        System.exit(0);
    };
    
    /**
     * disables all the controls
     * Package private
     */
    static void disableAllControls(){
        ProjectLogger.Log("----disabling all controls----");
        HomeAutomator.getMBed().getJoystickDown().removeAllListeners();
        HomeAutomator.getMBed().getJoystickUp().removeAllListeners();
        HomeAutomator.getMBed().getJoystickFire().removeAllListeners();
        HomeAutomator.getMBed().getJoystickLeft().removeAllListeners();
        HomeAutomator.getMBed().getJoystickRight().removeAllListeners();
    }
    
}
