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
    public ScreenInterface()
    {
        mainMenu();
        messageClient = new MessageClient();
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
                    HomeAutomator.getMBed().close();
                }, 4);
        mainMenu.enableControls();
        mainMenu.update();


    }

    private void backToMainMenu(){
        disableAllControls();
        mainMenu.enableControls();
        mainMenu.update();
    }

    private interfaceUI Lights(){
        interfaceUI cmd = () -> {
            lightsMainMenu = new LightsMainMenu(messageClient.getLights());
            lightsMainMenu.enableControls();
            HomeAutomator.getMBed().getJoystickFire().addListener(backButtonToMainMenu);
            lightsMainMenu.update();

        };
        return cmd;
    }

    private interfaceUI Temprature(){
        interfaceUI cmd = () -> {
            TextBox textBox = new TextBox("temprature");
            textBox.clear();
            textBox.render();
            HomeAutomator.getMBed().getJoystickFire().addListener(backButtonToMainMenu);
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
            HomeAutomator.getMBed().getJoystickFire().addListener(backButtonToMainMenu);
        };
        return cmd;
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
