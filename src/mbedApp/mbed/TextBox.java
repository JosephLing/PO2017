package mbedApp.mbed;

import mbedApp.Main;
import shed.mbed.LCD;
/**
 * TextBox does.............
 *
 * @author josephling
 * @version 1.0 09/02/2017
 */
public class TextBox {

    private LCD lcd;
    private String[] text;

    public TextBox(String text){
        lcd = HomeAutomator.getMBed().getLCD();
        this.text = text.split("\n");
    }

    public void render(){
        for (int i = 0; i < text.length; i++) {
            ScreenInterface.sleep(50);
            lcd.print(0, i*10, text[i]);
        }
    }

    public void clear(){
        ScreenInterface.sleep(50);
        lcd.clear();
    }

}
