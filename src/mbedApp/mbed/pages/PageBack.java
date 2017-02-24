package mbedApp.mbed.pages;

import mbedApp.mbed.ScreenInterface;
import java.util.function.Predicate;
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
        ScreenInterface.setCurrentPage(-1);
    }

    @Override
    public Page getPage(){
        return Page.BACK;
    }
}
