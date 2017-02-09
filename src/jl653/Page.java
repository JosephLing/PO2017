//package jl653;
//
//import java.util.Arrays;
//
///**
// * Page does.............
// *
// * @author josephling
// * @version 1.0 08/02/2017
// */
//public enum Page {
//
//
//
////    PAGES   (1, () -> {
////        Menu menu = new Menu(null, Page.getNames(), Page.getCmds());
//////        menu.update();
//////        mBed.getJoystickUp().addListener((isPressed) -> {
//////            if(isPressed) {
//////                foo.decreaseSelected();
//////            }});
//////
//////        mBed.getJoystickDown().addListener((isPressed) -> {
//////            if(isPressed) {
//////                foo.increaseSelected();
//////            }});
//////
//////        mBed.getJoystickFire().addListener((isPressed) -> {
//////            if(isPressed) {
//////                foo.runSelected();
//////            }});
////    });
//
//    private int index;
//    private interfaceUI display;
//    Page(int index, interfaceUI display){
//        this.index = index;
//        this.display = display;
//    }
//
//    public int getIndex() {
//        return index;
//    }
//
//    public void render(){
//        this.display.update();
//    }
//
//    public interfaceUI getDisplay() {
//        return display;
//    }
//
//    public static String[] getNames(){
//        String[] output = new String[Page.values().length];
//        for (int i = 0; i < Page.values().length; i++) {
//            output[i] = Page.values()[i].name();
//        }
//        return output;
//    }
//
//    public static interfaceUI[] getCmds(){
//        interfaceUI[] output = new interfaceUI[Page.values().length];
//        for (int i = 0; i < Page.values().length; i++) {
//            output[i] = Page.values()[i].getDisplay();
//        }
//        return output;
//    }
//}
