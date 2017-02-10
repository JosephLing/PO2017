package mbedApp.mttq;

import mbedApp.Light;
import java.util.Random;
/**
 * MttqClient does.............
 *
 * @author josephling
 * @version 1.0 10/02/2017
 */
public class MttqClient {

    private Light[] lights;

    public MttqClient() {
        this.lights = new Light[10];
        Random rnd = new Random();
        for (int i = 0; i < this.lights.length; i++) {
            this.lights[i] = new Light(rnd.nextBoolean(), "light" + Integer.toString(i));
        }
    }

    public Light[] getLights() {
        return lights;
    }

    public void setLight(int index, Light light){
        lights[index] = light;
    }
}
