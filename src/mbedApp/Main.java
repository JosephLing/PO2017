package mbedApp;

import mbedApp.devices.InterfaceDeviceNew;
import mbedApp.devices.TestDevice;
//import mbedApp.gui.events.EventFrame;
import mbedApp.mqtt.MQTT_TESTING;
import mbedApp.mqtt.MQTT_TOPIC;
import mbedApp.mqtt.MessageClient;
import shed.mbed.MBedStateException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public class Main {
    private static Main main;

    public Main() {
        currentlyTesting();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            currentlyTesting();
        } else {
            switch (args[0]) {
//                case "room":
//                    createRoomApp();
//                    break;
//                case "mbed":
//                    createMbedApp();
//                    break;
//                case "testing":
//                    MQTT_TESTING.testing();
//                    break;
//                case "events":
//                    createEventApp();
//                    break;
                default:
                    ProjectLogger.Log("no option found in args\n args: room, mbed, testing, events");
            }
        }
    }

//    public static void createEventApp() {
//        EventFrame eventFrame = new EventFrame();
//    }

    public static void currentlyTesting() {
//        createMbedApp();
////        sleep(1000);
//        createRoomApp();
        test();
//        InterfaceDeviceNew test = new TestDevice();
//        MessageClient client = new MessageClient(MQTT_TOPIC.CAT, test);
//        client.run();
    }

//    public static void createRoomApp() {
//        RoomThread r = new RoomThread();
//        r.start();
//    }
//
//    public static void createMbedApp() {
//        try {
//            HomeAutomatorThread h = new HomeAutomatorThread();
//            h.start();
//        } catch (MBedStateException e) {
//            System.out.println("MBed closed");
//        }
//
//    }

    /**
     * Pause the program for a specified amount of miliseconds
     *
     * @param millis the number of miliseconds to pause for
     */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            // Nothing we can do.
        }

    }

    public static void test(){
//        String  fmt = "%24s: %s%n";

        TestDevice a = new TestDevice(false);
        System.out.println(a);




//        Constructor c =  a.getClass().getConstructors()[0];
//        System.out.format("%s%n", c.toGenericString());
//        Parameter[] params = c.getParameters();
//        System.out.format(fmt, "Number of parameters", params.length);
//        for (int i = 0; i < params.length; i++) {
//            printParameter(params[i]);
//        }
    }

    public static void printParameter(Parameter p) {
        String  fmt = "%24s: %s%n";
        for (Annotation annotation : p.getAnnotations()) {
            System.out.println(annotation);
        }
        System.out.format(fmt, "Parameter class", p.getType());
        System.out.format(fmt, "Parameter name", p.getName());
        System.out.format(fmt, "Modifiers", p.getModifiers());
        System.out.format(fmt, "Is implicit?", p.isImplicit());
        System.out.format(fmt, "Is name present?", p.isNamePresent());
        System.out.format(fmt, "Is synthetic?", p.isSynthetic());
    }





}

