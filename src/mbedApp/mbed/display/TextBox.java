package mbedApp.mbed.display;

import mbedApp.mbed.HomeAutomator;
import shed.mbed.ButtonListener;

/**
 * TextBox does.............
 *
 * @author josephling
 * @version 1.0 16/02/2017
 */
public class TextBox extends ScrollableText {

    private ButtonListener backButton;

    public TextBox(String msg, ButtonListener backButton) {
        super(msg.split("\n"));
        this.backButton = backButton;
        this.enableControls();
        this.update();
    }

    @Override
    public void update_main(int index, int count, int y) {
        getLcd().print(0, y, getMsgArray()[index]);
    }

    @Override
    public void enableControls() {
        super.enableControls();
        HomeAutomator.getMBed().getJoystickFire().addListener(backButton);
    }

    @Override
    public void disableControls() {
        super.disableControls();
        HomeAutomator.getMBed().getJoystickFire().removeListener(backButton);
    }
}
