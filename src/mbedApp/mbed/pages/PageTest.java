package mbedApp.mbed.pages;

/**
 * Created by jl653 on 24/02/17.
 */
public class PageTest implements InterfaceUI {

    @Override
    public void update() {

    }

    @Override
    public void close() {

    }

    @Override
    public void open() {
        System.out.println("asdfsdaf");
        System.out.println("asdfsdaf");
        System.out.println("asdfsdaf");
        System.out.println("asdfsdaf");
        System.out.println("asdfsdaf");
    }

    @Override
    public Page getPage() {
        return Page.TEST;
    }
}
