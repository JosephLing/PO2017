package mbedApp.mqtt;

import mbedApp.Light;
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
    private MemoryPersistence memoryPersistence;
    
    public MessageClient() {
        this.lights = new Light[10];
        Random rnd = new Random();
        for (int i = 0; i < this.lights.length; i++) {
            this.lights[i] = new Light(rnd.nextBoolean(), "light" + Integer.toString(i));
        }
        
        MqttConfigReader config = new MqttConfigReader();
        MemoryPersistence memoryPersistence = new MemoryPersistence();
        try {
            MqttClient client = new MqttClient(config.getBroker(), config.getClientId(), memoryPersistence);
            MqttConnectOptions clientConnectionOptions = new MqttConnectOptions();
            clientConnectionOptions.setCleanSession(true);
            client.connect(clientConnectionOptions);
        } catch(MqttException exception) {
            ProjectLogger.Log("Exception encountered when trying to connect to broker");
            exception.printStackTrace();
            System.exit(0);
        }
    }

    public Light[] getLights() {
        return lights;
    }

    public void setLight(int index, Light light){
        lights[index] = light;
    }
    
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
    
    public void disconnect() {
        try {
            client.disconnect();
        } catch(MqttException exception) {
            ProjectLogger.Log("Exception encountered when trying to disconnect");
            exception.printStackTrace();
        }
    }
}
