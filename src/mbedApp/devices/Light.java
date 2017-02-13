package mbedApp.devices;


/**
 * Light does.............
 *
 * @author josephling
 * @version 1.0 10/02/2017
 */
public class Light {

    private boolean state;
    private String name;

    public Light(boolean state, String name) {
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

    public void setState(boolean state){
        this.state = state;
    }


    /**
     * gets the name
     * @return name
     */
    public String getName() {
        return name;
    }
}

