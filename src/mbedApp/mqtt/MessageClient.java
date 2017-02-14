package mbedApp.mqtt;

import mbedApp.devices.Light;
import mbedApp.ProjectLogger;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;

/**
 * MessageClient
 *
 * @author josephling
 * @version 1.0 10/02/2017
 */
public class MessageClient {

    private MqttConfigReader config;
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
            ProjectLogger.Log("Connecting to " + config.getBroker() + " with ID " + config.getClientId());

            // Set up our MQTT client
            client = new MqttClient(config.getBroker(), config.getClientId(), memoryPersistence);
            MqttConnectOptions clientConnectionOptions = new MqttConnectOptions();
            clientConnectionOptions.setCleanSession(true);

            // Actually connect
            client.connect(clientConnectionOptions);

        } catch(MqttException exception) {
            ProjectLogger.Log("MQTT Client: Exception encountered when trying to connect to broker");
            exception.printStackTrace();
            System.exit(0);
        }
        ProjectLogger.Log("MQTT Client: connected to broker successfully");
    }

    /**
     * Subscribes to a topic and then applies to listener to the topic.
     * @param topic string added to config.getTopic();
     * @param listener IMqqtMessageListener
     */
    public void subscribe(String topic, IMqttMessageListener listener){
        try {
            client.subscribe(config.getTopic() + "/" + topic, config.getQos(), listener);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * Subscribes to a topic and then applies to listener to the topic.
     * @param topic string added to config.getTopic();
     * @param listener IMqqtMessageListener
     */
    public void advanceSubscribe(MQTT_TOPIC topic, InterfaceAdvMsg listener){
        try {
            client.subscribe(config.getTopic() + "/" + topic.toString(), config.getQos(),
                    (String msgTopic, MqttMessage message)->{
                        // {name:state=true,other=1}
                        final String topic_final = msgTopic;
                        msgTopic = (new String(message.getPayload())).replaceAll("\\s+","").replaceAll("\n", "");
                        if (msgTopic.charAt(0) == '{'){
                            if (msgTopic.charAt(msgTopic.length()-1) == '}'){
                                msgTopic = msgTopic.replaceAll("}","").replaceAll("\\{", "");
                                final String name = msgTopic.split(":")[0];

                                if (msgTopic.split(":").length == 1){
                                    listener.getMsg(topic_final, name, new String[0][0]);
                                }else{
                                    final String[][] test = Arrays.stream(msgTopic.split(":")[1].split(","))
                                            .map(s -> s.split("=")).toArray(size -> new String[size][size]);
                                    for (int i = 0; i < test.length; i++) {
                                       if (test[i].length == 1){
                                           throw new MqttExceptionParsingData("Syntax error ["+topic_final+"]["+name+"]: argument has no value " + test[i][0]);
                                       }
                                    }
                                    listener.getMsg(topic_final, name, test);
                                }
                            }else{
                                throw new MqttExceptionParsingData("Syntax error: no '}' found at the end of " + msgTopic);
                            }
                        }else{
                            throw new MqttExceptionParsingData("Syntax error: no '{' found at the start of " + msgTopic);
                        }
                    }
                    );
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send a message to the MQTT broker (and therefore all connected clients on the same topic)
     * @param topic The topic to send the message to (added to the base topic)
     * @param content The content of the message you want to send
     */
    public void send(MQTT_TOPIC topic, String content) {
        ProjectLogger.Log("sending message: " + content + " to " + config.getTopic() + "/" + topic);
        try {
            client.publish(config.getTopic()+"/"+topic, content.getBytes(), 0, false);
        } catch(MqttException exception) {
            ProjectLogger.Log("MQTT Client: Exception encountered when trying to send message");
            exception.printStackTrace();
        }
    }

    /**
     * Disconnect from the broker.
     */
    public void disconnect() {
        ProjectLogger.Log("MQTT Client: disconnecting from broker");
        try {
            client.disconnect();
        } catch(MqttException exception) {
            ProjectLogger.Log("MQTT Client: Exception encountered when trying to disconnect");
            exception.printStackTrace();
        }
    }
}
