package mbedApp.mbed.pages;
import mbedApp.mbed.ScreenInterface;
import mbedApp.mbed.display.Menu;
import mbedApp.mbed.display.TextBox;
import mbedApp.mqtt.InterfaceAdvMsg;
import mbedApp.mqtt.MQTT_TOPIC;
import shed.mbed.ButtonListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * PageEvents does.............
 *
 * @author josephling
 * @version 1.0 03/04/2017
 */
public class PageEvents implements InterfaceUI {

    private TextBox textBox;
    private String[][] events;
    private ButtonListener backbutton;
    private String eventsString;

    public PageEvents(){
        // creates a listener to all changes of events
        // then puts then in a 2 dim array [argname, value]
//        ScreenInterface.getMessageClient().advanceSubscribe(
//                MQTT_TOPIC.EVENTS, (String topic, String name, HashMap<String, String> args)->{
//                    events = args.keySet().stream()
//                            .map(a->{return new String[]{a, args.get(a)};})
//                            .toArray(a -> new String[args.size()][1]);
//                }
//        );

        ScreenInterface.getMessageClient().advanceSubscribe(MQTT_TOPIC.EVENTS,
                (String topic, String name, HashMap<String, String> args)->{
                    eventsString = "No. events: " + args.size() + "\n";
                    eventsString +=  (args.keySet()
                            .stream()
                            .map(a->{return "\narg: " + a + "\n"+(new java.util.Date((long)Long.parseLong(args.get(a))*1000)).toString();})
                            .collect(Collectors.joining("\n--------")));

                });

        backbutton = (isPressed) -> {
            if (isPressed){
                ScreenInterface.setCurrentPage(getPage().getBackIndex());
            }
        };
    }

    @Override
    public void update() {
        textBox.update();
    }

    @Override
    public void close() {
        if (textBox != null){
            textBox.disableControls();
        }
    }

    @Override
    public void open() {
        textBox = new TextBox(eventsString, backbutton);
        textBox.enableControls();
        System.out.println(eventsString);
    }

    @Override
    public Page getPage() {
        return Page.EVENTS;
    }
}
