package mbedApp.devices;

import java.util.HashMap;

/**
 * Created by Joe on 09/06/2017.
 */
public class TestDevice extends DeviceNew {


    private String name;
    private int id;
    private boolean state;

    /**
     * asdfads
     * @param state bool
     */
    public TestDevice(boolean state) {
        name = getClass().getName();
        this.state = state;
        id = 0;
    }

    @Override
    public HashMap<String, Class<?>> getParams() {
        return DeviceNew.genParams(this.getClass());
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
