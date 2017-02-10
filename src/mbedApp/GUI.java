package jl653;

import shed.mbed.MBed;
import shed.mbed.MBedUtils;

/**
 * GUI does.............
 *
 * @author josephling
 * @version 1.0 09/02/2017
 */
public class GUI {


    public static MBed mBed = MBedUtils.getMBed();

    public GUI(){
        mainMenu();
    }

    public void mainMenu(){

        String[] itemNames = {"lights", "temprature", "Settings", "Credits", "Quit"};

        interfaceUI[] itemCmds = {
                ()->{System.out.println("load next item 1");}, // lights
                ()->{System.out.println("load next item 2");}, // temprature
                ()->{System.out.println("load next item 3");}, // settings
                Credits(), // credits
                ()->{} // quit
        };

        Menu mainMenu = new Menu(itemNames, itemCmds);
        mainMenu.setMenuCmd(
                ()->{
                    System.out.println("closing");
                    mBed.close();
                }, 4);
        mainMenu.enableControls();
        mainMenu.update();


    }

    public interfaceUI Credits(){
        interfaceUI cmd = () -> {

            TextBox textBox = new TextBox("Lights:\nLamp\nTHing");
            textBox.clear();
            textBox.render();
        };
        return cmd;
    }
}
