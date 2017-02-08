import shed.mbed.*;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Originally based on 'Driving Test C' by ash
 */
public class Program {
    // The object for interacting with the FRDM/MBED.
    private MBed mbed;
    private String topic        = "wf44/testing";
    private String content      = "testing";
    private int qos             = 0;
    private String broker       = "tcp://co657-mqtt.kent.ac.uk:1883";
    private String clientId     = "JavaSample";
    private MemoryPersistence persistence = new MemoryPersistence();

    /**
     * Open a connection to the MBED.
     */
    public Program()
    {
        mbed = MBedUtils.getMBed();

    }

    /**
     * The starting point for the interactions.
     */
    public void run()
    {
        Thermometer therm = mbed.getThermometer();
        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: "+broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");

            while(mbed.isOpen()) {
                Double temperature = therm.getTemperature();
                
                System.out.println("Publishing message: "+content);

                MqttMessage message = new MqttMessage(temperature.toString().getBytes());
                message.setQos(qos);
                sampleClient.publish(topic, message);
                System.out.println("Message published");

                sleep(2000);
            }

            sampleClient.disconnect();
            System.out.println("Disconnected");
            System.exit(0);

        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }

    /**
     * Close the connection to the MBED.
     */
    public void finish()
    {
        mbed.close();
    }

    /**
     * A simple support method for sleeping the program.
     * @param millis The number of milliseconds to sleep for.
     */
    private void sleep(int millis)
    {
        try {
            Thread.sleep(millis);
        } 
        catch (InterruptedException ex) {
            // Nothing we can do.
        }
    }

}
