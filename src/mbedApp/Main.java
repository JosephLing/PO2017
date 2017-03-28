package mbedApp;

import mbedApp.gui.events.EventFrame;
import mbedApp.mqtt.MQTT_TESTING;
import shed.mbed.MBedStateException;

public class Main {
    private static Main main;
    
    public Main() {
        currentlyTesting();
    }

    public static void main(String[] args) {
        if (args.length == 0){
            currentlyTesting();
        }else{
            switch (args[0]){
                case "room":
                    createRoomApp();
                    break;
                case "mbed":
                    createMbedApp();
                    break;
                case "testing":
                    MQTT_TESTING.testing();
                    break;
                case "events":
                    createEventApp();
                    break;
                default:
                    ProjectLogger.Log("no option found in args");
            }
        }
    }

    public static void createEventApp(){
        EventFrame eventFrame = new EventFrame();
    }

    public static void currentlyTesting(){
        createMbedApp();
//        sleep(1000);
        createRoomApp();
    }

    public static void createRoomApp(){
        RoomThread r = new RoomThread();
        r.start();
    }

    public static void createMbedApp(){
        try {
            HomeAutomatorThread h = new HomeAutomatorThread();
            h.start();
        } catch (MBedStateException e) {
            System.out.println("MBed closed");
        }
        
    }
    
    /**
     * Pause the program for a specified amount of miliseconds
     * @param millis the number of miliseconds to pause for
     */
    public static void sleep(long millis){
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException ex) {
            // Nothing we can do.
        }

    }

}
