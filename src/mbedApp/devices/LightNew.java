package mbedApp.devices;

/**
 * Created by Joe on 09/06/2017.
 */
public class LightNew extends DeviceNew {


    private int id;
    private String name;

    public LightNew() {
        name = getClass().getName();
    }

    @Override
    public String getParams() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }
}
