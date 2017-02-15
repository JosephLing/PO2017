package mbedApp.devices;


/**
 * Device base class
 *
 * @author josephling
 * @version 1.0 10/02/2017
 */
public class Device {

    private boolean state;
    private String name;

    public Device(boolean state, String name) {
        this.state = state;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }

    public boolean isOn(){
        return state;
    }

    public void toggle(){
        state = !state;
    }
    
    /**
     * gets the name
     * @return name
     */
    public String getName() {
        return name;
    }
}

