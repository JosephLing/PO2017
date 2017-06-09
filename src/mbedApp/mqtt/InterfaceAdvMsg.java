package mbedApp.mqtt;

import java.util.HashMap;

/**
 * InterfaceAdvMsg does.............
 *
 * @author josephling
 * @version 1.0 13/02/2017
 */
public interface InterfaceAdvMsg {
    /**
     * /**
     *
     * @param topic of msg
     * @param name  name of the cmd
     * @param args  name to go with the arg
     * @throws MqttExceptionParsingData if parsed incorrectly
     */
    void getMsg(String topic, String name, HashMap<String, String> args) throws MqttExceptionParsingData;
}
