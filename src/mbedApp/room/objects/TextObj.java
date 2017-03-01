package mbedApp.room.objects;

import mbedApp.ProjectLogger;
import mbedApp.devices.Device;
import mbedApp.devices.Light;
import mbedApp.devices.Thermostat;
import mbedApp.mqtt.MQTT_TOPIC;
import mbedApp.mqtt.MessageClient;
import mbedApp.room.Canvas;

import java.awt.Graphics;
import java.util.HashMap;

import java.awt.*;
import java.awt.geom.*;

/**
 * Write a description of class TextObj here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TextObj
{
    private int x;
    private int y;
    
    public TextObj(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void update(Canvas canvas, String s) {
        canvas.drawText(this, s, x, y);
            //canvas.drawText(this, "Current Temperature: " + currentTemp + "*C", x, y+10);
    }
}
