package mbedApp.mbed;

import mbedApp.ProjectLogger;
import mbedApp.mbed.display.Menu;
import mbedApp.mbed.display.TextBox;
import mbedApp.mbed.pages.*;
import mbedApp.mqtt.MessageClient;
import java.util.Arrays;
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
                new PageStart(1),        // 0
                new PageMainMenu(new int[]{2, 3, 4, 5, 6, 7}),                   // 1
                new PageLights(),       // 2
                new PageTemprature(),   // 3
                new PageCredits(),      // 4
                new PageSettings(new int[]{5, 5, 6}),     // 5
                new PageBack(),         // 6
                new PageQuit()          // 7

        };
        ScreenInterface.currentPage = 0;
        running = true;
        changed = true;

        HomeAutomator.getMBed().getSwitch2().addListener((isPressed)->{
            if (isPressed){
                ProjectLogger.Log("going back");
                ScreenInterface.setCurrentPage(-1);
            }
        });
        ProjectLogger.Log("running main loop");
        while (currentPage >= 0 && running && HomeAutomator.getMBed().isOpen()){
            //ProjectLogger.Log("udapte");
            
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

    public static void setCurrentPage(int value){
        if (currentPage+value >= 0 && currentPage+value < page.length){
            currentPage += value;
            change();
        }
    }

    public static void goToPage(int value){
        if (value >= 0 && value < page.length){
            currentPage = value;
            change();
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
}
