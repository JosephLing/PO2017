package mbedApp;

import mbedApp.mttq.MttqClient;
import shed.mbed.ButtonListener;
import shed.mbed.MBed;
import shed.mbed.MBedUtils;

/**
 * GUI does.............
 *
 * @author josephling
 * @version 1.0 09/02/2017
 */
public class GUI {


    public static MBed mBed = MBedUtils.getMBed();

    private MttqClient mttqClient;

    private Menu mainMenu;
    private LightsMainMenu lightsMainMenu;

    private ButtonListener backButtonToMainMenu  = (isPressed) -> {
        if(isPressed) {
            GUI.disableAllControls();
            mainMenu.enableControls();
            mainMenu.update();
        }
    };

    public GUI(){
        mttqClient = new MttqClient();

        mainMenu();
    }



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

    private interfaceUI Credits(){
        interfaceUI cmd = () -> {

            TextBox textBox = new TextBox("Joe\nWill\nPierre\nKhem");
            textBox.clear();
            textBox.render();
            mBed.getJoystickFire().addListener(backButtonToMainMenu);
        };
        return cmd;
    }

    private interfaceUI Lights(){
        interfaceUI cmd = () -> {
            lightsMainMenu = new LightsMainMenu(mttqClient.getLights());
            lightsMainMenu.enableControls();
            mBed.getJoystickFire().addListener(backButtonToMainMenu);
            lightsMainMenu.update();

        };
        return cmd;
    }

    private interfaceUI Settings(){
        interfaceUI cmd = () -> {
            TextBox textBox = new TextBox("settings");
            textBox.clear();
            textBox.render();
            mBed.getJoystickFire().addListener(backButtonToMainMenu);
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
        GUI.mBed.getJoystickDown().removeAllListeners();
        GUI.mBed.getJoystickUp().removeAllListeners();
        GUI.mBed.getJoystickFire().removeAllListeners();
        GUI.mBed.getJoystickLeft().removeAllListeners();
        GUI.mBed.getJoystickRight().removeAllListeners();

    }
}
