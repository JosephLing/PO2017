package mbedApp.mbed.pages;

import mbedApp.mbed.HomeAutomator;
import mbedApp.mbed.display.TextBox;
import shed.mbed.Potentiometer;
import shed.mbed.PotentiometerListener;

/**
 * TempraturePage does.............
 *
 * @author josephling
 * @version 1.0 20/02/2017
 */
public class PageTemprature implements InterfaceUI {

    private TextBox textBox;
    private Potentiometer potentiometer;
    private PotentiometerListener potentiometerListener;

    public PageTemprature() {
        potentiometer = HomeAutomator.getMBed().getPotentiometer1();
        potentiometerListener = (double value) -> {
            System.out.println("asf" + value);
//            messageClient.send(MQTT_TOPIC.TEMPERATURE, "{temp:new=" + Double.toString(value*10) + "}");
        };
    }

    private String getText(){
        return "Current Temperature\n" + HomeAutomator.getTemperatureDev().getTemprature() + "C";
    }

    @Override
    public void update() {
        textBox.setText(getText());
        textBox.update();
    }

    @Override
    public void close() {
        textBox.disableControls();
        potentiometer.removeListener(potentiometerListener);
    }

    @Override
    public void open() {
        textBox = new TextBox(getText(), null);
        potentiometer.setEpsilon(0.1); // The value that the potentiometer has to change by to be registered by the listener
        // Every time a potentiometer changes send the value using the Messaging Client
        potentiometer.addListener(potentiometerListener);
        textBox.enableControls();
    }

    @Override
    public Page getPage(){
        return Page.TEMP;
    }
}
