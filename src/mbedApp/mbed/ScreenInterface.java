package mbedApp.mbed;

import mbedApp.ProjectLogger;
import mbedApp.mbed.pages.*;
import mbedApp.mqtt.MessageClient;

import static mbedApp.mbed.pages.Page.*;

/**
 * This runs the mainloop conrolling the pages.
 *
 * In runs of using InterfaceUI methods so that you can control.
 * So that you can open, close and update a page.
 *
 * These are stored in a array and accessed via the data stored
 * in the Enum Page.
 *
 * To create a new page simply:
 * <ul>
 *     <li>create a PageYourNameForIt class</li>
 *     <li>implement InterfaceUI to the page</li>
 *     <li>if your using a TextBox or Menu create them in the constructor
 *      (Examples should be avaible in the current code)</li>
 *      <li>or create an edge case like PageBack or PageQuit</li>
 *      <li>make sure to add closing down all the listeners of whatever display object you create.</li>
 *      <li>then add the index to ScreenInferface.</li>
 *      <li>if it is part of a menu make sure in the enum to set the
 *      correct back button</li>
 * </ul>
 * -
 * 
 * @author (Joe Ling)
 * @version (23 02 2017)
 */
public class ScreenInterface
{
    private static MessageClient messageClient;


    private static boolean running;
    private static boolean changed;

    private static int currentPage;
    private static InterfaceUI[] pages;


    public static void main(){
        messageClient = new MessageClient();
        pages = new InterfaceUI[10];

        // 0 Start
        pages[START.getIndex()]     = new PageStart(MAINMENU.getIndex());

        // 1 Main Menu
        pages[MAINMENU.getIndex()] = new PageMainMenu(new int[]{
                LIGHTS.getIndex(),
                TEMP.getIndex(),
                SETTINGS.getIndex(),
                CREDITS.getIndex(),
                BACK.getIndex(),
                QUIT.getIndex()
        });

        // 2 lights menu
        pages[LIGHTS.getIndex()] = new PageLights();

        // 3 temprature stuff
        pages[TEMP.getIndex()] = new PageTemprature();

        // 4 settings
        pages[SETTINGS.getIndex()] = new PageSettings(new int[]{
                TEST.getIndex(),
                EVENTS.getIndex(),
                BACK.getIndex()
        });

        // 5 credits
        pages[CREDITS.getIndex()] = new PageCredits();

        // 6 back
        pages[BACK.getIndex()] = new PageBack();

        // 7 quit
        pages[QUIT.getIndex()] = new PageQuit();

        // 8 test
        pages[TEST.getIndex()] = new PageTest();

        pages[EVENTS.getIndex()] = new PageEvents();

        ScreenInterface.currentPage = 0;
        running = true;
        changed = true;

        HomeAutomator.getMBed().getSwitch2().addListener((isPressed)->{
            if (isPressed){
                ProjectLogger.Log("Closing: " + pages[currentPage].getPage().name());
                ScreenInterface.goToPage(pages[currentPage].getPage().getBackIndex());
            }
        });
        ProjectLogger.Log("running main loop");

        int wait = 1000; // wait for a 1000ms
        int ms = HomeAutomator.getTemperatureDev().sendUpateSignal();
        int timepassed = 0;
        while (currentPage >= 0 && running && HomeAutomator.getMBed().isOpen()){
            //ProjectLogger.Log("udapte");
            
            if (changed){
                ProjectLogger.Log("Opening" + pages[currentPage].getPage().name());
                pages[currentPage].open();
                changed = false;
            }

            if (ms < timepassed){
                timepassed = 0;
                ms = HomeAutomator.getTemperatureDev().sendUpateSignal();
            }else{
                timepassed += wait;
            }
            pages[currentPage].update();
            sleep(wait);

        }
    }


    private static void change(){
        changed = true;
        System.out.println(pages[currentPage]);
        pages[currentPage].close();
    }

    public static void setCurrentPage(int value){
        if (currentPage+value >= 0 && currentPage+value < pages.length){
            change();
            currentPage += value;
        }
    }

    public static void goToPage(int value){
        if (value == BACK.getIndex()){
            value = ScreenInterface.getCurrentPageIndex().getPage().getBackIndex();
            if (value != BACK.getIndex()){
                goToPage(value);
            }else{
                assert true: "Error: PageBack went back to itself!";
            }
        } else if (value >= 0 && value < pages.length){
            change();
            currentPage = value;
        }
    }

    public static InterfaceUI getCurrentPageIndex(){
        return pages[currentPage];
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

    public static MessageClient getMessageClient() {
        return messageClient;
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
