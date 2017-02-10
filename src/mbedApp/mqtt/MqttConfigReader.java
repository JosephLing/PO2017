package mbedApp.mqtt;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;

/**
 * MqttConfigReader does.............
 *
 * @author josephling
 * @version 1.0 10/02/2017
 */
public class MqttConfigReader {


    private  String topic        = null;
    private  String content      = null;
    private  int qos             = -1;
    private  String broker       = null;
    private  String clientId     = null;


    public void readData(){
        // grab the file data
        InputStream input = getClass().getResourceAsStream("mqtt.config");
        String data = "";
        try{
            int i =  input.read();
            while (i != -1){
                data += Character.toString ((char) i);
                i = input.read();

            }
        }catch (IOException e){
            e.printStackTrace();

        }
        // map it to a multi dimensional array
        String[][] mainData = new String[5][1];
        int count = 0;
        for (String line : data.split("\n")) {
            mainData[count] = line.split("=");
            count ++;
        }

        // search the data for the varaibles
        for (int i = 0; i < mainData.length; i++) {
            switch (mainData[0][0]){
                case "topic":
                    topic = mainData[i][1];
                    break;
                case "content":
                    content = mainData[i][1];
                    break;
                case "qos":
                    qos =  Integer.parseInt(mainData[i][1]);
                    break;
                case "broker":
                    broker = mainData[i][1];
                    break;
                case "clientId":
                    clientId = mainData[i][1];
                    break;
            }
        }
    }

    public String getTopic() {
        return topic;
    }

    public  String getContent() {
        return content;
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
