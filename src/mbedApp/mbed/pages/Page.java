package mbedApp.mbed.pages;

import java.time.format.TextStyle;

/**
 * Created by jl653 on 24/02/17.
 */
public enum Page {

    START       (0, 0),
    MAINMENU    (1, 0),
    LIGHTS      (2, 1),
    TEMP        (3, 1),
    SETTINGS    (4, 1),
    CREDITS     (5, 1),
    BACK        (6, -1),
    QUIT        (7, -1),
    TEST        (8, 0);


    private final int currentIndex;
    private final int backIndex;

    Page(int currentIndex, int backIndex) {
        this.currentIndex = currentIndex;
        this.backIndex = backIndex;
    }

    public int getIndex() {
        return currentIndex;
    }

    public int getBackIndex() {
        return backIndex;
    }
}
