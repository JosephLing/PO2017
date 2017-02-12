package mbedApp.mbed;

 

/**
 * Created by josephling on 10/02/2017.
 */
public interface InterfaceMenu {

    /**
     * This renders a menu to LCD.
     * It also controls the currently selected mbed controls.
     */


    /**
     * renders the menu, TODO: enable header for menu
     */
    void update();

    /**
     * disables the controls used by this menu
     */
    void disableControls();

    /**
     * enables the controls used by this menu
     */
    void enableControls();

    /**
     * moves the currently selected index of the array down
     */
    void decreaseSelected();

    /**
     * moves the currently selected index of the array up
     */
    void increaseSelected();

    /**
     * runs the currently selected index element
     */
    void runSelected();

}
