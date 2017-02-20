package mbedApp;

import mbedApp.mbed.HomeAutomator;

/**
 * Launches HomeAutomator in seperate thread.
 */
public class HomeAutomatorThread extends Thread
{
    public HomeAutomatorThread() {
        // nothing here
    }
    
    public void run() {
        HomeAutomator ha = new HomeAutomator();
    }
}
