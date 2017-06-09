package mbedApp.devices;

/**
 * Created by Joe on 09/06/2017.
 */
public abstract class DeviceNew implements InterfaceDeviceNew {

    public DeviceNew() {
        // stupid but important! :)
        assert getTopic().equals("/" + getName() + "/" + getId()) : "invalid value for topic";
        assert getId() < 0: "id needs to be postive";
    }


    public String getTopic() {
        return "/" + getName() + "/" + getId();
    }
}
