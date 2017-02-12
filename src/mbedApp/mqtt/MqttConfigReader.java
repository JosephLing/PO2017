package mbedApp.mqtt;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

/**
 * MqttConfigReader does.............
 *
 * @author josephling
 * @version 1.0 10/02/2017
 */
public class MqttConfigReader {


    private String topic        = null;
    private int qos             = 0; // Default to 0
    private String broker       = null;
    private String clientId     = null;

    public MqttConfigReader() {
        readData();
        qos = 0;
        clientId = UUID.randomUUID().toString(); // have a unique client ID
    }

    public void readData(){
        Properties properties = new Properties();
        // grab the file data
        try {
            InputStream input = getClass().getResourceAsStream("mqtt.config");
            properties.load(input);
            
            topic = properties.getProperty("topic");
            broker = properties.getProperty("broker");
        } catch(IOException exception) {
            exception.printStackTrace();
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
