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

    private ButtonListener fireButton;

    public TextBox(String msg, ButtonListener backButton1) {
        super(msg.split("\n"));
        setFireButton(backButton1);
    }

    public TextBox(String msg, ButtonListener backButton1, boolean render){
        super(msg.split("\n"));
        setFireButton(backButton1);
        if (render){
            this.enableControls();
            this.update();
        }

    }

    public void setText(String msg){
        this.setMsgArray(msg.split("\n"));
    }

    private void setFireButton(ButtonListener backButtonNew) {
        if (backButtonNew != null){
            this.fireButton = (isPressed) -> {
                this.disableControls();
                backButtonNew.changed(isPressed);
            };
        }else{
            System.out.println("dummy button created");
            this.fireButton = (isPressed) -> {};
        }

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
        HomeAutomator.getMBed().getJoystickFire().addListener(fireButton);
    }

    @Override
    public void disableControls() {
        super.disableControls();
        HomeAutomator.getMBed().getJoystickFire().removeListener(fireButton);
    }
}
