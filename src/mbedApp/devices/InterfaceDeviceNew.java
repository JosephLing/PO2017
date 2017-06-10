package mbedApp.devices;

import java.util.HashMap;

/**
 * Created by Joe on 09/06/2017.
 */
public interface InterfaceDeviceNew {

    HashMap<String, Class<?>> getParams();

    String getName();

    int getId();

    String getTopic();


}
