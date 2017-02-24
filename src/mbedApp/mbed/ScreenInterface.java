package mbedApp.mbed;

import mbedApp.ProjectLogger;
import mbedApp.mbed.display.Menu;
import mbedApp.mbed.pages.*;
import mbedApp.mqtt.MessageClient;

import static mbedApp.mbed.pages.Page.*;

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
    private static InterfaceUI[] pages;


    public static void main(){
        pages = new InterfaceUI[]{
                new PageStart(1),        // 0
                new PageMainMenu(new int[]{
                        LIGHTS.getIndex(),
                        TEMP.getIndex(),
                        SETTINGS.getIndex(),
                        CREDITS.getIndex(),
                        BACK.getIndex(),
                        QUIT.getIndex()
                }),                   // 1
                new PageLights(),       // 2
                new PageTemprature(),   // 3
                new PageCredits(),      // 4
                new PageSettings(new int[]{TEST.getIndex(),TEST.getIndex(),  BACK.getIndex()}),     // 5
                new PageBack(),         // 6
                new PageQuit(),          // 7
                new PageTest()          // 8

        };
        ScreenInterface.currentPage = 0;
        running = true;
        changed = true;

        HomeAutomator.getMBed().getSwitch2().addListener((isPressed)->{
            if (isPressed){
                ProjectLogger.Log("going back");
                System.err.println("Current location: " + pages[currentPage].getPage().name());
                ScreenInterface.goToPage(pages[currentPage].getPage().getBackIndex());
            }
        });
        ProjectLogger.Log("running main loop");



        while (currentPage >= 0 && running && HomeAutomator.getMBed().isOpen()){
            //ProjectLogger.Log("udapte");
            
            if (changed){
                ProjectLogger.Log("----change----" + currentPage);
                pages[currentPage].open();
                changed = false;
            }
            pages[currentPage].update();
            sleep(1000);

        }
    }


    private static void change(){
        changed = true;
        pages[currentPage].close();
    }

    public static void setCurrentPage(int value){
        if (currentPage+value >= 0 && currentPage+value < pages.length){
            currentPage += value;
            change();
        }
    }

    public static void goToPage(int value){
        if (value >= 0 && value < pages.length){
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
