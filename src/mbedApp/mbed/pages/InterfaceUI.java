package mbedApp.mbed.pages;


import mbedApp.devices.Light;

/**
 * Created by josephling on 08/02/2017.
 */
public interface InterfaceUI {

    void update();

    void close();

    void open();

    Page getPage();
}
