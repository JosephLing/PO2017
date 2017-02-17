package mbedApp.mbed;

import mbedApp.mbed.display.InterfaceUI;
import mbedApp.mbed.display.Menu;
import mbedApp.mbed.display.TextBox;
import mbedApp.ProjectLogger;
import mbedApp.devices.Temperature;
import mbedApp.mqtt.MessageClient;
import shed.mbed.ButtonListener;

/**
 * Write a description of class ScreenInterface here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ScreenInterface
{
    private Menu mainMenu;
    private Menu settings;
    private MessageClient messageClient;
    private Temperature temperature;

    /**
     * Constructor for objects of class ScreenInterface
     */
    public ScreenInterface(MessageClient messageClient)
    {
        this.messageClient = messageClient;
        mainStart();
    }

    //----------------------------------------------------------------------------------------------

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

    /**
     * sleeps the current thread for
     * @param ms number of milliseconds
     */
    public static void sleep(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------

    private InterfaceUI Quit = ()->{
        ProjectLogger.Log("closing down messageClient and mbed");
        TextBox msg = new TextBox("\nQuitting", null);
        msg.update();
        sleep(2000);
        this.messageClient.disconnect();
        HomeAutomator.getMBed().close();
        System.exit(0);
    };

    //----------------------------------------------------------------------------------------------

    public void mainStart(){
        /*
            menu  status:on no. dev: 10

            current temp: 20
            desired temp: 25
        */
        TextBox textBox = new TextBox("[menu] status:on dev:00\nc. temp:00\nd.temp:00",(ispressed)->{
            if (ispressed) {
                this.mainMenu();
            }
        } );
        textBox.enableControls();
        textBox.update();
    }


    //----------------------------------------------------------------------------------------------
    private void mainMenu(){


        String[] itemNames = {"lights", "temprature", "Settings", "Credits","Back", "Quit"};

        InterfaceUI[] itemCmds = {
                Lights, // lights
                Temprature, // temprature
                Settings,// , // settings
                Credits, // credits
                BackToStart, // back to the start page
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
     * ButtonListener for the back button to update_main menu.
     * TODO: maybe make it dynamic?
     */
    private ButtonListener backButtonToMainMenu  = (isPressed) -> {
        if(isPressed) {
            backToMainMenu();
        }
    };

    private InterfaceUI Lights = () -> {
        //TODO: enable this to work with the new light setup
        TextBox textBox = new TextBox("lights", backButtonToMainMenu);
        textBox.enableControls();
        textBox.update();
    };

    private InterfaceUI Temprature = () -> {
        TextBox textBox = new TextBox("temprature", backButtonToMainMenu);
        textBox.enableControls();
        textBox.update();
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
        TextBox textBox = new TextBox("Joe\nWill\nPierre\nKhem", backButtonToMainMenu);
        textBox.enableControls();
        textBox.update();

    };

    private InterfaceUI BackToStart = ()->{
        disableAllControls();
        mainStart();
    };

}
