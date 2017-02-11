package mbedApp;


import mbedApp.mqtt.MqttConfigReader;
import shed.mbed.MBed;
import shed.mbed.MBedUtils;
public class Main {

    public static MBed mBed = MBedUtils.getMBed();

    public static void main(String[] args) {
        GUI test = new GUI();
    }
}
