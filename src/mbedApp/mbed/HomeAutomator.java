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
    
    private static MBed mbed;

    private MessageClient mqttClient;

    private Menu mainMenu;
    private LightsMainMenu lightsMainMenu;
    private Menu settings;

    /**
     * Creates the Mbed controller and creates the main menu when
     * loaded. Starts of with generating an mqtt client to access the data.
     */
    public HomeAutomator(){
        genMBed();
        mqttClient = new MessageClient();
        mainMenu();
    }
    
    private void genMBed() {
        mbed = MBedUtils.getMBed();
    }
    
    public static MBed getMBed() {
        return mbed;
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
                    mbed.close();
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
            mbed.getJoystickFire().addListener(backButtonToMainMenu);
            lightsMainMenu.update();

        };
        return cmd;
    }

    private interfaceUI Temprature(){
        interfaceUI cmd = () -> {
            TextBox textBox = new TextBox("temprature");
            textBox.clear();
            textBox.render();
            mbed.getJoystickFire().addListener(backButtonToMainMenu);
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
            mbed.getJoystickFire().addListener(backButtonToMainMenu);
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
