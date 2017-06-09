package mbedApp.mbed.pages;

import mbedApp.ProjectLogger;

/**
 * PageBack does.............
 *
 * @author josephling
 * @version 1.0 20/02/2017
 */
public class PageBack implements InterfaceUI {

    @Override
    public void update() {

    }

    @Override
    public void close() {

    }

    @Override
    public void open() {
        ProjectLogger.Warning("Warning never should reach here!");
    }

    @Override
    public Page getPage() {
        return Page.BACK;
    }
}
