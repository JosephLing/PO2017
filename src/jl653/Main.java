package jl653;


import shed.mbed.MBed;
import shed.mbed.MBedUtils;

public class Main {


    public static MBed mBed = MBedUtils.getMBed();


    public static void main(String[] args) {
        GUI test = new GUI();
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
