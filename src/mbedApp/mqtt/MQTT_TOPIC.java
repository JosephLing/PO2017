package mbedApp.mqtt;

/**
 * Used to specifiy what message came from where.
 * So that it is easier to specify.
 *
 * Created by josephling on 13/02/2017.
 */
public enum MQTT_TOPIC {
    DEVICE_REGISTER,
    DEVICE_SET,
    DEVICE_CHANGE,
    CAT,
    TEMPERATURE;


    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

}
