package mbedApp.mbed.display;

import mbedApp.ProjectLogger;
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

    public TextBox(String msg, ButtonListener backButton1) {
        super(msg.split("\n"));
        setBackButton(backButton1);
    }

    public TextBox(String msg, ButtonListener backButton1, boolean render){
        super(msg.split("\n"));
        setBackButton(backButton1);
        if (render){
            this.enableControls();
            this.update();
        }

    }

    private void setBackButton(ButtonListener backButtonNew) {
        this.backButton = (isPressed) -> {
            this.disableControls();
            backButtonNew.changed(isPressed);
        };
    }

    @Override
    public void update_main(int index, int count, int y) {
        getLcd().print(0, y, getMsgArray()[index]);
    }

    @Override
    void update_header(int index, int count, int y) {
        //ProjectLogger.Log("TextBox update");

    }

    @Override
    void update_footer(int index, int count, int y) {

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
