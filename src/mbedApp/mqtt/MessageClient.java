package mbedApp.mqtt;

import mbedApp.devices.InterfaceDeviceNew;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * MessageClient
 *
 * @author josephling
 * @version 1.0 10/02/2017
 */

// TODO: think about changing the data type of topic??? we want it to be able to reference unique topics but have it be a dynamic collection of static names


public class MessageClient extends MQTT implements Runnable {


    private static Logger logger = Logger.getLogger(MessageClient.class.getName());
    private final int timeout;
    private final int sleepTime;
    private boolean registered;
    private String server;
    private InterfaceDeviceNew device;
    private long timeAlive;
    private int maxRegisterAttempts;

    /**
     * used to create the default client
     *
     * @param server maybe not needed??
     * @param device InterfaceDeviceNew
     */
    public MessageClient(MQTT_TOPIC server, InterfaceDeviceNew device) {

        super();
        // not used but needs to be defined
        timeout = 2000;
        sleepTime = 2500;
        maxRegisterAttempts = 5;


        if (getClient() != null) {
            if (getClient().isConnected()) {
                initClient(server, device);
            }
        }
    }


    /**
     * @param server
     * @param device
     */
    private void initClient(MQTT_TOPIC server, InterfaceDeviceNew device) {
        this.server = server.toString();
        this.device = device;
        registered = false;

        // stores the time of the latest ping
        timeAlive = 0;


        // register device
        this.advanceSubscribe(MQTT_TOPIC.DEVICE_SET + device.getTopic(),
                (String topic, String name, HashMap<String, String> args) -> {
                    switch (name) {
                        case "register":
                            if (Boolean.parseBoolean(args.get("registered"))) {
                                if (!registered) {
                                    registered = true;
                                } else {
                                    logger.log(Level.WARNING, "device already registered");
                                }
                            }
                            break;

                        case "ping":
                            ping();
                            timeAlive = System.nanoTime(); // so we store the last ping we sent.
                            break;

                        default:
                            logger.log(Level.WARNING, "Warning MessageClient for Device: " + device.getTopic() + " no response found");
                    }
                });

        // keeping this alive
        this.advanceSubscribe(MQTT_TOPIC.DEVICE_SET + device.getTopic(),
                (String topic, String name, HashMap<String, String> args) -> {

                });
        registerDevice();
    }

    @Override
    public void run() {
        assert getClient() != null : "client is null for " + device.getTopic() + "thread";

        logger.log(Level.INFO, "created thread for " + device.getTopic());
        while (getClient().isConnected()) {
            mainloop();
        }
        logger.log(Level.INFO, "finished thread for " + device.getTopic());

    }

    /**
     * mainloop of the thread
     */
    private void mainloop() {
        if (registered) {
            if (System.nanoTime() > timeAlive - timeout) {
                logger.log(Level.INFO, "Device [] timed out from server");
                disconnect();
            }
        } else {
            if (maxRegisterAttempts > 0) {
                registerDevice();
                maxRegisterAttempts--;
            } else {
                disconnect();
            }
        }
        logger.log(Level.INFO, "sleeping for a " + sleepTime + " ms");
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException error) {
            logger.log(Level.WARNING, "thread sleeping didn't work.... for device: " + device.getTopic());
        }
    }


    /**
     * used to respond to the server
     */
    private void ping() {
        if (registered) {
            // so we will respond instanly this could be a mistake later on...
            // we want though to leave the control up to the server client
            this.send(MQTT_TOPIC.KEEP_ALIVE.toString() + device.getTopic(),
                    "{pingResponse:device= " + device.getTopic() + "}"
            );
        }
    }

    private void registerDevice() {
        if (!registered) {
            this.send(server, "{" + device.getName() + ":id=" + device.getId() + "," + device.getParams() + "}");
        }
    }


}
