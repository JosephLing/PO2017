package mbedApp;


import java.util.logging.Logger;

/**
 * Logger does.............
 *
 * @author josephling
 * @version 1.0 10/02/2017
 */
public class ProjectLogger {

    private static final Logger  LOGGER = Logger.getLogger("mbedjl653");
    {

    }

    public static void Log(String msg){
        //        LOGGER.info(msg);
        System.out.println("Logging: " + msg);
    }

    public static void Warning(String msg){
        System.err.println("Logging: " + msg);
        //        LOGGER.warning(msg);
    }
}
