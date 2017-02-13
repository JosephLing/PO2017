package mbedApp.mqtt;

/**
 * Used to specifiy what message came from where.
 * So that it is easier to specify.
 *
 * Created by josephling on 13/02/2017.
 */
public enum ClientType {
    MBED,
    ROOM;


    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

}
