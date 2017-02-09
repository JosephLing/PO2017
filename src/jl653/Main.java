package jl653;


import shed.mbed.MBed;
import shed.mbed.MBedUtils;

import java.util.HashMap;

public class Main {


    public static MBed mBed = MBedUtils.getMBed();


    public static void main(String[] args) {


        String[] itemNames = {"test", "test2", "test3", "test4", "test5"};

        interfaceUI[] itemCmds = {
                ()->{System.out.println("load next item 1");},
                ()->{System.out.println("load next item 2");},
                ()->{System.out.println("load next item 3");},
                ()->{System.out.println("load next item 4");},
                ()->{System.out.println("load next item 5");}
        };

        Menu foo = new Menu(itemNames, itemCmds);

        foo.update();

        mBed.getJoystickUp().addListener((isPressed) -> {
            if(isPressed) {
                foo.decreaseSelected();
                foo.update();
            }});

        mBed.getJoystickDown().addListener((isPressed) -> {
            if(isPressed) {
                foo.increaseSelected();
                foo.update();
            }});

        mBed.getJoystickFire().addListener((isPressed) -> {
            if(isPressed) {
                foo.runSelected();
                foo.update();
            }});
    }

    public static void sleep(long millis){
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException ex) {
            // Nothing we can do.
        }
    }
}
