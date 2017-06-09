package mbedApp.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by Joe on 09/06/2017.
 */
public class MQTT {

    private static Logger logger = Logger.getLogger(MessageClient.class.getName());


    private MqttConfigReader config;
    private MqttClient client;


    public MQTT() {
        // Get our configuration options
        boolean success = true;
        config = new MqttConfigReader();
        MemoryPersistence memoryPersistence = new MemoryPersistence();
        try {
            logger.log(Level.INFO, "Connecting to " + config.getBroker() + " with ID " + config.getClientId());

            // Set up our MQTT client
            client = new MqttClient(config.getBroker(), config.getClientId(), memoryPersistence);
            MqttConnectOptions clientConnectionOptions = new MqttConnectOptions();
            clientConnectionOptions.setCleanSession(true);

            // Actually connect
            client.connect(clientConnectionOptions);

        } catch (MqttException exception) {
            logger.log(Level.INFO, "MQTT Client: Exception encountered when trying to connect to broker");
            exception.printStackTrace();
            success = false;
        }
        if (success) {
            logger.log(Level.INFO, "MQTT Client: connected to broker successfully");
        }
    }

    protected MqttClient getClient() {
        return client;
    }


    /**
     * Subscribes to a topic and then applies to listener to the topic.
     *
     * @param topic    string added to config.getTopic();
     * @param listener IMqqtMessageListener
     */
    protected void subscribe(String topic, IMqttMessageListener listener) {
        try {
            client.subscribe(config.getTopic() + "/" + topic, config.getQos(), listener);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    /**
     * converts MQTT_TOPIC.toString() then calls advanceSubscribe, note: checkout todo at the top of the page
     *
     * @param topic    MQTT_TOPIC
     * @param listener InterfaceAdvMsg
     */
    protected void advanceSubscribe(MQTT_TOPIC topic, InterfaceAdvMsg listener) {
        advanceSubscribe(topic.toString(), listener);
    }

    /**
     * Subscribes to a topic and then applies to listener to the topic.
     * This is the format of a cmd {name:state=true,other=1}
     * InterfaceAdvMsg takes these values: (String topic, String name, String[][]args)
     * topic = config.getTopic() + "/" + topic.toString();
     * name = would be "name" for {name:state=true,other=1} or {ThisBitIsTheName:state=true,other=1}
     * or "ThisBitIsTheName" its the piece of text that comes after the '{' and before ':'.
     * <p>
     * After that we get the args:
     * <li>its formatted so {name:arg=value,arg2=value2} -> [[arg, value], [arg2, value2]]</li>
     * <li>it won't work if you do {name:arg=value,value2}</li>
     * <li>you can't have {name:arg={....}} as it won't be able to seperate them</li>
     * <li>you can have as many arguments you like {name:argN=valN.....}</li>
     *
     * @param topic    string added to config.getTopic();
     * @param listener IMqqtMessageListener
     */
    protected void advanceSubscribe(String topic, InterfaceAdvMsg listener) {
        try {
            client.subscribe(config.getTopic() + "/" + topic, config.getQos(),
                    (String msgTopic, MqttMessage message) -> {
                        // so what we want to do is output the topic, but we also want to have
                        // a String local var that isn't final so we copy topic into a final
                        // then use msgTopic as a local String var
                        final String topic_final = msgTopic;
                        // here we just get rid of all the unwanted chars and convert the msg to a String
                        msgTopic = (new String(message.getPayload())).replaceAll("\\s+", "").replaceAll("\n", "");
                        if (msgTopic.charAt(0) == '{') {
                            // wants important to note here is that we require a '}' at the end
                            // we do not end the program as soon as we come across one
                            if (msgTopic.charAt(msgTopic.length() - 1) == '}') {
                                msgTopic = msgTopic.replaceAll("}", "").replaceAll("\\{", "");
                                final String name = msgTopic.split(":")[0];

                                if (msgTopic.split(":").length == 1) {
                                    // in this case there is just {name}
                                    listener.getMsg(topic_final, name, new HashMap<String, String>());
                                } else {
                                    // we convert {name:state=true,other=1} to a String[][]
                                    // first step we grab "state=true,other=1"
                                    // then we split it by ','
                                    // then split it by '='
                                    final String[][] test = Arrays.stream(msgTopic.split(":")[1].split(","))
                                            .map(s -> s.split("=")).toArray(size -> new String[size][size]);

                                    // so here we check to see whether or not its just one value
                                    // per argument e.g. we want this {name:state=true,other=1}
                                    // NOT {name:state=true=false,other=1}
                                    // if it is wrong we throw an Exception
                                    for (int i = 0; i < test.length; i++) {
                                        if (test[i].length == 1) {
                                            throw new MqttExceptionParsingData("Syntax error [" + topic_final + "][" + name + "]: argument has no value " + test[i][0]);
                                        }
                                    }
                                    // we take the String[][] and convert it to HashMap<String, String>
                                    final HashMap<String, String> map =
                                            (HashMap<String, String>) Arrays.stream(test)
                                                    .collect(Collectors.toMap(e -> e[0], e -> e[1]));

                                    listener.getMsg(topic_final, name, map);
                                }
                            } else {
                                throw new MqttExceptionParsingData("Syntax error: no '}' found at the end of " + msgTopic);
                            }
                        } else {
                            throw new MqttExceptionParsingData("Syntax error: no '{' found at the start of " + msgTopic);
                        }
                    }
            );
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    /**
     * formats current topic to be combined with the MQTT topic and a device
     *
     * @param topic  MQTT_TOPIC
     * @param device string
     * @return string topic
     */
    private String getTopic(String topic, String device) {
        return config.getTopic() + "/" + topic + "/" + device;
    }

    /**
     * formats current topic to be combined with the MQTT topic
     *
     * @param topic MQTT_TOPIC
     * @return string topic
     */
    private String getTopic(String topic) {
        return config.getTopic() + "/" + topic;
    }

    /**
     * Send a message to the MQTT broker (and therefore all connected clients on the same topic)
     *
     * @param topic   The topic to send the message to (added to the base topic)
     * @param content The content of the message you want to send
     */
    protected void send(String topic, String content) {
        logger.log(Level.INFO, "sending message: " + content + " to " + config.getTopic() + "/" + topic);
        try {
            client.publish(getTopic(topic), content.getBytes(), 0, false);
        } catch (MqttException exception) {
            logger.log(Level.INFO, "MQTT Client: Exception encountered when trying to send message");
            exception.printStackTrace();
        }
    }


    /**
     * Send a message to the MQTT broker (and therefore all connected clients on the same topic)
     *
     * @param topic    The topic to send the message to (added to the base topic)
     * @param content  The content of the message you want to send
     * @param retained boolean message held on that topic until another message is sent that is
     *                 retained
     */
    protected void send(String topic, String content, boolean retained) {
        logger.log(Level.INFO, "sending message: " + content + " to " + config.getTopic() + "/" + topic);
        try {
            client.publish(getTopic(topic), content.getBytes(), 0, retained);
        } catch (MqttException exception) {
            logger.log(Level.INFO, "MQTT Client: Exception encountered when trying to send message");
            exception.printStackTrace();
        }
    }

    /**
     * clears the retained message for a given topic
     *
     * @param topic string of the topic
     */
    protected void clearRetained(String topic) {
        send(topic, "{blank}", true);
    }

    /**
     * Disconnect from the broker.
     */
    protected void disconnect() {
        logger.log(Level.INFO, "MQTT Client: disconnecting from broker");
        try {
            client.disconnect();
        } catch (MqttException exception) {
            logger.log(Level.INFO, "MQTT Client: Exception encountered when trying to disconnect");
            exception.printStackTrace();
        }

    }

}
