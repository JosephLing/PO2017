//package mbedApp.room.objects;
//
//import mbedApp.devices.Device;
//import mbedApp.devices.Light;
//import mbedApp.mqtt.MQTT_TOPIC;
//import mbedApp.mqtt.MessageClient;
//import mbedApp.room.Canvas;
//
//import java.awt.*;
//import java.awt.geom.Ellipse2D;
//import java.util.HashMap;
//import java.util.Random;
//
///**
// * Light does.............
// *
// * @author josephling
// * @version 1.0 12/02/2017
// */
//public class LightObj extends Light, CanvasObj {
//
//    private int x;
//    private int y;
//    private Graphics graphic;
//    private MessageClient client;
//    private boolean registered;
//
//    public LightObj(String name) {
//        super(false, 0);
//        Random rand = new Random();
//        x = rand.nextInt(400);
//        y = rand.nextInt(400);
//    }
//
//
//
//    @Override
//    public Graphics update(Canvas canvas) {
//        Color color = Color.red;
//        if (this.isState()){
//            color = Color.green;
//        }
//        canvas.draw(this, color, new Ellipse2D.Double(getX(), getY(), 10, 10));
//        return null;
//    }
//
//    @Override
//    public int getX() {
//        return x;
//    }
//
//    @Override
//    public int getY() {
//        return y;
//    }
//
//    @Override
//    public void setX(int newX) {
//        x = newX;
//    }
//
//    @Override
//    public void setY(int newY) {
//        y = newY;
//    }
//
//    @Override
//    public void addClient(MessageClient client) {
//        this.client = client;
//    }
//
//    @Override
//    public MessageClient getClient() {
//        return client;
//    }
//
//    @Override
//    public boolean isRegistered() {
//        return registered;
//    }
//
//    @Override
//    public void register_client() {
//
//    }
//
//    @Override
//    public void setRegistered(boolean state) {
//        registered = state;
//    }
//}
