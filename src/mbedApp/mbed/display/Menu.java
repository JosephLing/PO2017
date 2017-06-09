package mbedApp.mbed.display;

import mbedApp.mbed.HomeAutomator;
import mbedApp.mbed.ScreenInterface;
import shed.mbed.ButtonListener;
import shed.mbed.PixelColor;

/**
 * Menu does.............
 *
 * @author josephling
 * @version 1.0 16/02/2017
 */
public class Menu extends ScrollableText {

    private ButtonListener fire;

    private int[] menuIndex;

    public Menu(String[] msg, int[] menuIndex) {
        super(msg);
        this.menuIndex = menuIndex;
        fire = (isPressed) -> {
            if (isPressed) {
                this.runSelected();
            }
        };
        assert msg.length == menuIndex.length : "msg array not the same size as menuIndex";
    }

    public void runSelected() {
        int selected = menuIndex[getSelected()];
        if (selected != -1) {
            this.disableControls();
            ScreenInterface.goToPage(selected);
        }
    }

    public int[] getMenuIndex() {
        return menuIndex;
    }

    @Override
    public void enableControls() {
        super.enableControls();
        HomeAutomator.getMBed().getJoystickFire().addListener(fire);
    }

    @Override
    public void disableControls() {
        super.disableControls();
        HomeAutomator.getMBed().getJoystickFire().removeListener(fire);
    }


    @Override
    protected void update_main(int index, int count, int y) {
        //ProjectLogger.Log("Menu update");
        getLcd().drawLine(0, y, getScreenWidth(), y, PixelColor.BLACK);
        getLcd().print(35, y + 2, getMsgArray()[index]);

        if (index == getSelected()) {
            getLcd().drawCircle(20, y + getMenuSpacing() / 2, 4, PixelColor.BLACK);
        }
    }

    @Override
    protected void update_footer(int index, int count, int y) {
        getLcd().drawLine(0, y, getScreenWidth(), y, PixelColor.BLACK);
    }

    @Override
    void update_header(int index, int count, int y) {

    }
}
