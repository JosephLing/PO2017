package mbedApp.devices;


import mbedApp.ProjectLogger;

import java.util.HashMap;

/**
 * Light does.............
 *
 * @author josephling
 * @version 1.0 10/02/2017
 */
public class Thermostat extends Device implements InterfaceDevice {


    private float temperature;

    public Thermostat(float temperature, int id) {
        super(id);
        setTemperature(temperature);
    }

    public static Thermostat parseNewDevice(HashMap<String, String> args) {
        Thermostat newThermostat = null;
        String id = args.get("id");
        String temperature = args.get("temperature");
        if (id != null) {
            if (temperature != null) {
                newThermostat = new Thermostat(Float.parseFloat(temperature), Integer.parseInt(id));
            } else {
                ProjectLogger.Warning("No state found for new thermostat");
            }

        } else {
            ProjectLogger.Warning("No Id found! for new thermostat: " + THERMOSTAT);
        }
        return newThermostat;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

//    //@Override
//    public void register(MessageClient client) {
//        super.register(client); // so if we call it here will it use getName() from Device or Light?
//        // as that could break the code if it grabs the "Device"
//        client.send(MQTT_TOPIC.DEVICE_REGISTER, "{"+getName()+":id="+getId()+",temperature="+temperature+"}");
//    }

    @Override
    public void parseChange(HashMap<String, String> args) {

        String tempratureString = args.get("temperature");
        if (isDevice_registered()) {
            if (tempratureString != null) {
                temperature = Float.parseFloat(tempratureString);
            }
        }

    }

    public String getName() {
        return THERMOSTAT;
    }
}

