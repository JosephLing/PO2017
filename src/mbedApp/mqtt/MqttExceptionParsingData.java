package mbedApp.mqtt;

/**
 * MqttExceptionParsingData does.............
 *
 * @author josephling
 * @version 1.0 13/02/2017
 */
public class MqttExceptionParsingData extends Exception {
    public MqttExceptionParsingData(String message) {
        super(message);
        this.printStackTrace();
    }

}
