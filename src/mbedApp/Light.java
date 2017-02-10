package mbedApp;


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
}

