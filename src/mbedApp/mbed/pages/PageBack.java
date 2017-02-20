package mbedApp.mbed.pages;

import mbedApp.mbed.ScreenInterface;

/**
 * PageBack does.............
 *
 * @author josephling
 * @version 1.0 20/02/2017
 */
public class PageBack implements InterfaceUI {
    private int goBackCount;

    public PageBack(int goBackCount) {
        this.goBackCount = goBackCount;
    }

    public PageBack(){
        this.goBackCount = 1;
    }

    @Override
    public void update() {

    }

    @Override
    public void close() {

    }

    @Override
    public void open() {
        for (int i = 0; i < goBackCount; i++) {
            ScreenInterface.goBack();
            // could backfire doing it this way but.... whats the worst that could happen
            // sets changed multiple times to true;
        }
    }
}
