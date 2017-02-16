package mbedApp.mbed;

import mbedApp.devices.Light;
import mbedApp.mqtt.MQTT_TOPIC;
import shed.mbed.ButtonListener;
import mbedApp.mqtt.MessageClient;
import mbedApp.ProjectLogger;
import shed.mbed.PotentiometerListener;
import shed.mbed.Thermometer;

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


    // temprature settings
    private int MIN_ROOM_TEMP = 18;
    private int MAX_ROOM_TEMP = 25;
    private boolean alterTemprature = true;
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
        TextBox msg = new TextBox("\nQuitting");
        msg.clear();
        msg.render();
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
        alterTemprature = true;
        HomeAutomator.getMBed().getJoystickFire().addListener((ispressed)->{
            if (ispressed) {
                this.mainMenu();
            }
        });
        TextBox textBox = new TextBox("[menu] status:on dev:00\nc. temp:00\nd.temp:00");
        textBox.clear();
        textBox.render();
    }

    /**
     * Every time a potentiometer changes send the value using the Messaging
     * Client
     */
    private PotentiometerListener tempPot  = (value) -> {
        System.out.println("asdf" + value);
//        messageClient.send(MQTT_TOPIC.TEMPERATURE, "{temp:new=" + Double.toString(value) + "}");
        //TODO: should probably sleep this if it always calls or calls a lot
    };

    /**
     * Every time the temperature changes check it, if it's below the minimum, send the temperature to the room and the new temp
     * that should be set by the thermostat.
     */
    private void checkTempChange() {
        //TODO: not have this as in infinite loop and a way to alter it [x]
        while(HomeAutomator.getMBed().isOpen() && alterTemprature) {
            Thermometer thermometer = HomeAutomator.getMBed().getThermometer();
            Double temp = thermometer.getTemperature();
            if(temp > MIN_ROOM_TEMP && temp < MAX_ROOM_TEMP) {
                messageClient.send(MQTT_TOPIC.TEMPERATURE, "{temp:new=21}");
                sleep(750);
            }else{
                // so we sleep longer when the temprature
                sleep(2000);
            }

        }
    }


    //----------------------------------------------------------------------------------------------
    private void mainMenu(){

        // temprature settings
        alterTemprature = false;
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
     * ButtonListener for the back button to main menu.
     * TODO: maybe make it dynamic?
     */
    private ButtonListener backButtonToMainMenu  = (isPressed) -> {
        if(isPressed) {
            backToMainMenu();
        }
    };

    private InterfaceUI Lights = () -> {
        //TODO: enable this to work with the new light setup
        TextBox textBox = new TextBox("lights");
        textBox.clear();
        textBox.render();
//        lightsMainMenu = new LightsMainMenu(new Light[1]);
//        lightsMainMenu.enableControls();
//        HomeAutomator.getMBed().getJoystickFire().addListener(backButtonToMainMenu);
//        lightsMainMenu.update();
        HomeAutomator.getMBed().getJoystickFire().addListener(backButtonToMainMenu);


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

    private InterfaceUI BackToStart = ()->{
        disableAllControls();
        mainStart();
    };

}
