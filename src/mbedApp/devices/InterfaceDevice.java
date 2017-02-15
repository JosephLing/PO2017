package mbedApp.devices;

import mbedApp.mqtt.MessageClient;

/**
 * InterfaceDevice does.............
 *
 * @author josephling
 * @version 1.0 15/02/2017
 */
public interface InterfaceDevice {

    void parseChange(String[][] args);

    void register(MessageClient client);
}
