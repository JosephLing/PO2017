package mbedApp.mqtt;

/**
 * InterfaceAdvMsg does.............
 *
 * @author josephling
 * @version 1.0 13/02/2017
 */
public interface InterfaceAdvMsg {
    /**
     *
     * @param topic
     * @param name name of the cmd
     * @param args name to go with the arg
//     * @param values value of the name
     */
    void getMsg (String topic, String name, String[][]args) throws MqttExceptionParsingData;
}
