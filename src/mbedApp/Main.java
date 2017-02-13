package mbedApp;

import mbedApp.mbed.HomeAutomator;
import mbedApp.room.RoomMain;

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
                default:
                    ProjectLogger.Log("no option found in args");
            }
        }
    }

    public static void currentlyTesting(){
        createRoomApp();
        createMbedApp();
    }

    public static void createRoomApp(){
        RoomMain.main();
    }

    public static void createMbedApp(){
        HomeAutomator test = new HomeAutomator();
    }
}
