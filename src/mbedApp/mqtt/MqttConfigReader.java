package mbedApp.mqtt;

import sun.util.logging.PlatformLogger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * MqttConfigReader does.............
 *
 * @author josephling
 * @version 1.0 10/02/2017
 */
public class MqttConfigReader {

    private static Logger logger = Logger.getLogger(MessageClient.class.getName());

    private InputStream input;
    private String topic = null;
    private int qos = 0; // Default to 0
    private String broker = null;
    private String clientId = null;


    public MqttConfigReader(){
        this(null);
    }

    public MqttConfigReader(String configFile) {
        if (configFile == null){
            input = getClass().getResourceAsStream("mqtt.config");
        }else {
            try{
                input = new FileInputStream(configFile);
            }catch (FileNotFoundException fileNotFound){

            }
        }
        readData();
        qos = 0;
        clientId = UUID.randomUUID().toString(); // have a unique client ID
    }

    private void readData() {
        Properties properties = new Properties();
        // grab the file data
        try {
            properties.load(input);

            topic = properties.getProperty("topic");
            broker = properties.getProperty("broker");
        } catch (IOException exception) {
            logger.log(Level.WARNING, "failed to load config file into properties");
            System.exit(0);
        }
    }

    public String getTopic() {
        return topic;
    }

    public int getQos() {
        return qos;
    }

    public String getBroker() {
        return broker;
    }

    public String getClientId() {
        return clientId;
    }
}
