package mbedApp.devices;

/**
 * Created by Joe on 09/06/2017.
 */
public class TestDevice extends DeviceNew {


    private String name;
    private int id;

    public TestDevice() {
        name = getClass().getName();
        id = 0;
    }

    @Override
    public String getParams() {
        return "testParam=1";
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
