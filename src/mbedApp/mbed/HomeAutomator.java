package mbedApp.mbed;

import mbedApp.ProjectLogger;
import mbedApp.mqtt.MessageClient;
import shed.mbed.ButtonListener;
import shed.mbed.MBed;
import shed.mbed.MBedUtils;

/**
 * HomeAutomator does.............
 *
 * @author josephling
 * @version 1.0 09/02/2017
 */
public class HomeAutomator {


    public static MBed mBed;

    private static void genMbed(){
        HomeAutomator.mBed = MBedUtils.getMBed();
    }

    private MessageClient mqttClient;

    private Menu mainMenu;
    private LightsMainMenu lightsMainMenu;
    private Menu settings;

    /**
     * Creates the Mbed controller and creates the main menu when
     * loaded. Starts of with generating an mqtt client to access the data.
     */
    public HomeAutomator(){
        HomeAutomator.genMbed();
        mqttClient = new MessageClient();
        mainMenu();
    }

    private ButtonListener backButtonToMainMenu  = (isPressed) -> {
        if(isPressed) {
            backToMainMenu();
        }
    };


    public void mainMenu(){

        String[] itemNames = {"lights", "temprature", "Settings", "Credits", "Quit"};

        interfaceUI[] itemCmds = {
                Lights(), // lights
                Temprature(), // temprature
                Settings(),// , // settings
                Credits(), // credits
                ()->{} // quit
        };

        mainMenu = new Menu(itemNames, itemCmds);
        mainMenu.setMenuCmd(
                ()->{
                    System.out.println("closing");
                    mBed.close();
                }, 4);
        mainMenu.enableControls();
        mainMenu.update();


    }

    private void backToMainMenu(){
        HomeAutomator.disableAllControls();
        mainMenu.enableControls();
        mainMenu.update();
    }

    private interfaceUI Lights(){
        interfaceUI cmd = () -> {
            lightsMainMenu = new LightsMainMenu(mqttClient.getLights());
            lightsMainMenu.enableControls();
            mBed.getJoystickFire().addListener(backButtonToMainMenu);
            lightsMainMenu.update();

        };
        return cmd;
    }

    private interfaceUI Temprature(){
        interfaceUI cmd = () -> {
            TextBox textBox = new TextBox("temprature");
            textBox.clear();
            textBox.render();
            mBed.getJoystickFire().addListener(backButtonToMainMenu);
        };
        return cmd;
    }

    private interfaceUI Settings(){
        interfaceUI cmd = () -> {
            String[] settingsTitles = {
                    "MQTT",
                    "Temprature",
                    "back"
            };
            interfaceUI[] settingsCmds = {
                    ()->{System.out.println("mqtt stuff");},
                    ()->{System.out.println("temprature settings");},
                    ()->{backToMainMenu();}
            };
            settings = new Menu(settingsTitles, settingsCmds);
            settings.enableControls();
            settings.update();
        };
        return cmd;
    }

    private interfaceUI Credits(){
        interfaceUI cmd = () -> {

            TextBox textBox = new TextBox("Joe\nWill\nPierre\nKhem");
            textBox.clear();
            textBox.render();
            mBed.getJoystickFire().addListener(backButtonToMainMenu);
        };
        return cmd;
    }


    public static void sleep(long millis){
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException ex) {
            // Nothing we can do.
        }

    }

    /**
     * disables all the controls
     */
    public static void disableAllControls(){
        ProjectLogger.Log("----disabling all controls----");
        HomeAutomator.mBed.getJoystickDown().removeAllListeners();
        HomeAutomator.mBed.getJoystickUp().removeAllListeners();
        HomeAutomator.mBed.getJoystickFire().removeAllListeners();
        HomeAutomator.mBed.getJoystickLeft().removeAllListeners();
        HomeAutomator.mBed.getJoystickRight().removeAllListeners();

    }
}
