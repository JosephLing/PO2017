package mbedApp.mqtt;

import java.util.HashMap;
import mbedApp.Main;
/**
 * MQTT_TESTING does.............
 *
 * @author josephling
 * @version 1.0 16/03/2017
 */
public class MQTT_TESTING {

    private static MessageClient client;

    private static TEST client(){
        client = new MessageClient();
        TEST client_reg = new TEST(false);
        client.advanceSubscribe(MQTT_TOPIC.DEVICE_REGISTER,
                (String topic, String name, HashMap<String, String> args)->{
                    if (args.get("reg") != null){
                        client_reg.setReg(Boolean.parseBoolean(args.get("reg")));
                    }
                });
        return client_reg;
    }

    private static void server(){
        MessageClient server = new MessageClient();
        MessageClient server_reg = new MessageClient();

        server.advanceSubscribe(MQTT_TOPIC.DEVICE_SET,
                (String topic, String name, HashMap<String, String> args)->{
                    server_reg.send(MQTT_TOPIC.DEVICE_REGISTER, "{dev1:reg=true}");
                });

    }


    public static void testing(){
        TEST client_reg;
        int count;


        client_reg = client();
        server();
        count = 0;
        while (count < 10 && !client_reg.isReg()){
            if (!client_reg.isReg()){
                client.send(MQTT_TOPIC.DEVICE_SET, "{dev1:state=true}");
            }else{
                System.out.println("registered");
            }
            Main.sleep(1000);
            count ++;
        }
        if (count >= 9){
            System.err.println("error device not req");
        }else{
            System.err.println("success");
        }

        server();
        client_reg = client();
        count = 0;
        while (count < 10 && !client_reg.isReg()){
            if (!client_reg.isReg()){
                client.send(MQTT_TOPIC.DEVICE_SET, "{dev1:state=true}");
            }else{
                System.out.println("registered");
            }
            Main.sleep(1000);
            count ++;
        }
        if (count >= 9){
            System.err.println("error device not req");
        }else{
            System.err.println("success");
        }
    }


    private static class TEST{
        private boolean reg;

        public TEST(boolean reg) {
            this.reg = reg;
        }

        public boolean isReg() {
            return reg;
        }

        public void setReg(boolean reg) {
            this.reg = reg;
        }
    }
}
