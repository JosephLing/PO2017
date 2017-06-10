package mbedApp.devices;

import mbedApp.mqtt.MessageClient;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by Joe on 09/06/2017.
 */
public abstract class DeviceNew implements InterfaceDeviceNew {


    private static Logger logger = Logger.getLogger(MessageClient.class.getName());


    public DeviceNew() {
        // stupid but important! :)
        assert getTopic().equals("/" + getName() + "/" + getId()) : "invalid value for topic";
        assert getId() < 0: "id needs to be postive";
    }



    public String getTopic() {
        return "/" + getName() + "/" + getId();
    }


    @Override
    public String toString() {
        return "Name: " + getName() + " Id: " + getId() + " params: " + getParams().toString();
    }

    public static HashMap<String, Class<?>> genParams(Class tClass){
        HashMap<String, Class<?>> params = new HashMap<String, Class<?>>();
        Constructor[] constructors = tClass.getConstructors();
        Parameter[] parameters;
        for (int i = 0; i <constructors.length; i++) {
            // if constructor has params and has more than currently stored amount fo params do something!
            parameters = constructors[i].getParameters();
            if (parameters.length > params.size()){

                for (int j = 0; j < parameters.length; j++) {
                    params.put(parameters[j].getName(), parameters[j].getType());
                }
            }
        }
        if (params.size() > 0) {
            if (params.get("arg0") != null){
                //TODO: check if this is the way we want to do this
                // https://stackoverflow.com/questions/21455403/how-to-get-method-parameter-names-in-java-8-using-reflection
                logger.log(Level.WARNING, "using default parameter names argN, use -parameter when compiling for actual names");

            }
        }

        return params;
    }

}
