package mbedApp;

import mbedApp.mbed.HomeAutomator;

public class Main {
    private static Main main;
    
    public Main() {
        HomeAutomator ha = new HomeAutomator();
    }

    public static void main(String[] args) {
        main = new Main();
    }
}
