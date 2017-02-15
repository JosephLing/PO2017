package mbedApp.devices;


/**
 * Device base class
 *
 * @author josephling
 * @version 1.0 10/02/2017
 */
public class Device {

    private String name;
    private int id;

    public Device(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString(){
        return this.name+id;
    }


    /**
     * gets the name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * gets the id of the device
     * @return id
     */
    public int getId() {
        return id;
    }
}

