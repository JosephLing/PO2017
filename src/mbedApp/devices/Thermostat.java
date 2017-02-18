package mbedApp.devices;


import mbedApp.ProjectLogger;
import mbedApp.mqtt.MQTT_TOPIC;
import mbedApp.mqtt.MessageClient;

/**
 * Light does.............
 *
 * @author josephling
 * @version 1.0 10/02/2017
 */
public class Thermostat extends Device implements InterfaceDevice{


    private float temperature;
    
    public Thermostat(float temperature, int id) {
        super(id);
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    @Override
    public void parseChange(String[][] args) {

        if (isDevice_registered()){
            if (args.length == 1){
                if (args[0][0].equals("temperature")){
                    temperature = Float.parseFloat(args[0][1]);
                }
            }
        }

    }

    @Override
    public void register(MessageClient client) {
        super.register(client); // so if we call it here will it use getName() from Device or Light?
        // as that could break the code if it grabs the "Device"
        client.send(MQTT_TOPIC.DEVICE_REGISTER, "{"+getName()+":id="+getId()+",temperature="+temperature+"}");
    }

    public String getName() {
        return THERMOSTAT;
    }

    public static Thermostat parseNewDevice(String[][] args){
        Thermostat newThermostat = null;
        if (args.length == 1){ // id and state
            if (args[0][0].equals("id")){
                if (args[1][0].equals("temperature")){
                    newThermostat =  new Thermostat(Float.parseFloat(args[1][1]), Integer.parseInt(args[0][1]));
                }else{
                    ProjectLogger.Warning("No temperature found for new thermostat");
                }
            }else{
                ProjectLogger.Warning("No Id found! for new thermostat");
            }
        }
        return newThermostat;
    }
}

