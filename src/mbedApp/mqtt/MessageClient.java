package mbedApp.mqtt;

import mbedApp.devices.Light;
import mbedApp.ProjectLogger;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Random;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * MessageClient
 *
 * @author josephling
 * @version 1.0 10/02/2017
 */
public class MessageClient {

    private MqttConfigReader config;
    private MqttClient client;
    private String clientTopic;
    /**
     * Initialise a new MessageClient by connecting to the broker and setting up required variables.
     * Note: This will exit the whole program if a connection cannot be made to the broker.
     */
    public MessageClient(ClientType clientType)  {
        // Get our configuration options
        config = new MqttConfigReader();
        clientTopic = config.getTopic() + "/" + clientType + "/";
        MemoryPersistence memoryPersistence = new MemoryPersistence();
        try {
            ProjectLogger.Log("["+clientType+"] Qos var: " + config.getQos());

            // Set up our MQTT client
            client = new MqttClient(config.getBroker(), config.getClientId(), memoryPersistence);
            MqttConnectOptions clientConnectionOptions = new MqttConnectOptions();
            clientConnectionOptions.setCleanSession(true);

            // Actually connect
            client.connect(clientConnectionOptions);

        } catch(MqttException exception) {
            ProjectLogger.Log("["+clientTopic+"] Exception encountered when trying to connect to broker");
            exception.printStackTrace();
            System.exit(0);
        }
        ProjectLogger.Log("["+clientTopic+"] connected to MQTT successfully");
    }

    /**
     * Subscribes to a topic and then applies to listener to the topic.
     * @param topic string added to config.getTopic();
     * @param listener IMqqtMessageListener
     */
    public void subscribe(String topic, IMqttMessageListener listener){
        try {
            client.subscribe(clientTopic+topic, config.getQos(), listener);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * Subscribes to a topic and then applies to listener to the topic.
     * @param topic string added to config.getTopic();
     * @param listener IMqqtMessageListener
     */
    public void advanceSubscribe(String topic, InterfaceAdvMsg listener){
        try {
            client.subscribe(clientTopic+topic,
                    config.getQos(),
                    (String msgTopic, MqttMessage message)->{
                        // do fancy stuff
                        // put msg into the string[] array
                        listener.getMsg(msgTopic, new String[1]);
                    }
                    );
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    /**
     * Send a message to the MQTT broker (and therefore all connected clients on the same topic)
     */
    public void send(String content) {
        try {
            client.publish(clientTopic, content.getBytes(), 0, false);
        } catch(MqttException exception) {
            ProjectLogger.Log("["+clientTopic+"] Exception encountered when trying to send message");
            exception.printStackTrace();
        }
    }

    /**
     * Send a message to the MQTT broker (and therefore all connected clients on the same topic)
     */
    public void send(String content, String topic) {
        ProjectLogger.Log("sending: " + clientTopic+topic + " msg: " + content);
        try {
            client.publish(clientTopic+topic, content.getBytes(), 0, false);
        } catch(MqttException exception) {
            ProjectLogger.Log("["+clientTopic+"] Exception encountered when trying to send message");
            exception.printStackTrace();
        }
    }

    /**
     * Disconnect from the broker.
     */
    public void disconnect() {
        ProjectLogger.Log("["+clientTopic+"] disconnecting Mqtt connection");
        try {
            client.disconnect();
        } catch(MqttException exception) {
            ProjectLogger.Log("["+clientTopic+"] Exception encountered when trying to disconnect");
            exception.printStackTrace();
        }
    }

}
