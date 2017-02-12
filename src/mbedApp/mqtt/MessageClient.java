package mbedApp.mqtt;

import mbedApp.devices.Light;
import mbedApp.ProjectLogger;
import java.util.Random;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * MessageClient
 *
 * @author josephling
 * @version 1.0 10/02/2017
 */
public class MessageClient {

    private Light[] lights;
    private MqttConfigReader config;
    private boolean connected;
    private MqttClient client;
    
    /**
     * Initialise a new MessageClient by connecting to the broker and setting up required variables.
     * Note: This will exit the whole program if a connection cannot be made to the broker.
     */
    public MessageClient() {
        // Get our configuration options
        config = new MqttConfigReader();
        MemoryPersistence memoryPersistence = new MemoryPersistence();
        try {
            ProjectLogger.Log("Qos var: " + config.getQos());

            // Set up our MQTT client
            client = new MqttClient(config.getBroker(), config.getClientId(), memoryPersistence);
            MqttConnectOptions clientConnectionOptions = new MqttConnectOptions();
            clientConnectionOptions.setCleanSession(true);

            // Actually connect
            client.connect(clientConnectionOptions);

        } catch(MqttException exception) {
            ProjectLogger.Log("Exception encountered when trying to connect to broker");
            exception.printStackTrace();
            System.exit(0);
        }
        ProjectLogger.Log("connected to MQTT successfully");


        // gen test lights
        this.lights = new Light[10];
        Random rnd = new Random();
        for (int i = 0; i < this.lights.length; i++) {
            this.lights[i] = new Light(rnd.nextBoolean(), "light" + Integer.toString(i));
        }
    }
    
    /**
     * Send a message to the MQTT broker (and therefore all connected clients on the same topic)
     */
    public void send(String content) {
        try {
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(config.getQos());
            client.publish(config.getTopic(), message);
        } catch(MqttException exception) {
            ProjectLogger.Log("Exception encountered when trying to send message");
            exception.printStackTrace();
        }
    }
    
    /**
     * Disconnect from the broker.
     */
    public void disconnect() {
        try {
            client.disconnect();
        } catch(MqttException exception) {
            ProjectLogger.Log("Exception encountered when trying to disconnect");
            exception.printStackTrace();
        }
    }

    public Light[] getLights() {
        return lights;
    }


    public void setLight(int index, Light light){
        lights[index] = light;
    }
}
