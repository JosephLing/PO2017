package mbedApp.mbed;

import mbedApp.mbed.pages.*;
import mbedApp.mbed.display.Menu;
import mbedApp.mbed.display.TextBox;
import mbedApp.ProjectLogger;
import mbedApp.mqtt.MessageClient;
import shed.mbed.ButtonListener;
import java.util.ArrayList;

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


    private static boolean running;
    private static boolean changed;

    private static int currentPage;
    private static InterfaceUI[] page;


    public static void main(){
        page = new InterfaceUI[]{
                new PageStart(),
                new PageMainMenu(),
                new PageLights(),
                new PageTemprature(),
                new PageCredits(),
                new PageSettings(),


                new PageQuit()

        };
        ScreenInterface.currentPage = 0;
        running = true;
        changed = true;

        HomeAutomator.getMBed().getSwitch2().addListener((isPressed)->{
            if (isPressed){
                ProjectLogger.Log("going back");
                goBack();
            }
        });
        ProjectLogger.Log("running main loop");
        while (currentPage >= 0 && running && HomeAutomator.getMBed().isOpen()){
            ProjectLogger.Log("udapte");
            if (changed){
                ProjectLogger.Log("----change----" + currentPage);
                page[currentPage].open();
                changed = false;
            }
            page[currentPage].update();
            sleep(1000);

        }
    }


    private static void change(){
        changed = true;
        page[currentPage].close();
    }

    public static void goBack(){
        if (currentPage != 0){
            change();
            currentPage --;
        }
    }

    public static void goUp(){
        if (currentPage+1 < page.length){
            change();
            currentPage ++;
        }
    }

    /**
     * disables all the controls
     * Package private
     */
    protected void disableAllControls(){
        ProjectLogger.Log("----disabling all controls----");
        HomeAutomator.getMBed().getJoystickDown().removeAllListeners();
        HomeAutomator.getMBed().getJoystickUp().removeAllListeners();
        HomeAutomator.getMBed().getJoystickFire().removeAllListeners();
        HomeAutomator.getMBed().getJoystickLeft().removeAllListeners();
        HomeAutomator.getMBed().getJoystickRight().removeAllListeners();
        HomeAutomator.getMBed().getSwitch2().removeAllListeners();
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

    private void Quit() {
        ProjectLogger.Log("closing down messageClient and mbed");
        TextBox msg = new TextBox("\nQuitting", null);
        msg.update();
        sleep(2000);
        this.messageClient.disconnect();
        HomeAutomator.getMBed().close();
        System.exit(0);
    }
}
