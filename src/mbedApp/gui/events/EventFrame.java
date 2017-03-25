package mbedApp.gui.events;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * EventFrame does.............
 *
 * @author josephling
 * @version 1.0 25/03/2017
 */
public class EventFrame extends JFrame {

    private JTextField urlTextField;
    private JLabel response;

    public EventFrame() throws HeadlessException {
        System.out.println(testUrl("a"));
        System.out.println(wifiTest());
        this.setTitle("Event sub");

        createUrlInput();
        setSize(500,500);
        this.setVisible(true);
    }

    private void createUrlInput(){

        urlTextField = new JTextField();
        urlTextField.setSize(150,20);
        urlTextField.addActionListener((ActionEvent e)->{test();});
        this.add(urlTextField, BorderLayout.CENTER);
        response = new JLabel();
        response.setMinimumSize(new Dimension(150,20));
        this.add(response, BorderLayout.CENTER);

    }

    private void test(){
        String text = "";
        text += urlTextField.getText();
        URLConnection url = testUrl(urlTextField.getText());
        if (url != null) {
            text += " url correct";
        }else{
            text += " url incorrect";
        }
        response.setText(text);


    }

    private URLConnection testUrl(String urlString){
        URL url = null;
        URLConnection conn = null;
        try{
            if (urlString.contains("http://") || urlString.contains("https://")) {
                url = new URL(urlString);
            }else{
                url = new URL("http://" + urlString);
            }
        }catch (MalformedURLException e){

        }
        if (url != null){
            try{
                conn = url.openConnection();
                conn.connect();
            }catch (IOException e){
            }
        }
        if (conn != null){
            System.out.println("----");
            for(String key: conn.getHeaderFields().keySet()){
                System.out.println(key + " " + conn.getHeaderFields().get(key));
            }
//            System.out.println();
        }
        if (conn.getHeaderFields().keySet().size() == 0){
            conn = null;
        }
        return conn;
    }

    public boolean wifiTest(){
        return (testUrl("http://google.com") != null);
    }


}
