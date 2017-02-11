package mbedApp.mqtt;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

/**
 * MqttConfigReader does.............
 *
 * @author josephling
 * @version 1.0 10/02/2017
 */
public class MqttConfigReader {


    private  String topic        = null;
    private  int qos             = 0; // Default to 0
    private  String broker       = null;
    private  String clientId     = null;

    public MqttConfigReader() {
        readData();
    }

    public void readData(){
        Properties properties = new Properties();
        // grab the file data
        try {
            InputStream input = getClass().getResourceAsStream("mqtt.config");
            properties.load(input);
            
            topic = properties.getProperty("topic");
            qos = Integer.parseInt(properties.getProperty("qos"));
            broker = properties.getProperty("broker");
            clientId = properties.getProperty("clientId");
        } catch(IOException exception) {
            exception.printStackTrace();
        }
     }

    public String getTopic() {
        return topic;
    }

    public  int getQos() {
        return qos;
    }

    public  String getBroker() {
        return broker;
    }

    public  String getClientId() {
        return clientId;
    }
}
