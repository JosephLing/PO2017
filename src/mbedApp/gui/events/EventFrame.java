package mbedApp.gui.events;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * EventFrame does.............
 *
 * @author josephling
 * @version 1.0 25/03/2017
 */
public class EventFrame extends JFrame {

    private JTextField urlTextField;
    private JLabel response;
    private JTable repsonseTable;
    private JScrollPane scrollPane;
    private String responseText;

    public EventFrame() throws HeadlessException {
        this.setTitle("Event sub");
        responseText = "";
        createUrlInput();
        createTableInit();
        setSize(500,500);
        this.setVisible(true);

//        ArrayList<String> a = WifiCalendar.getIcal("https://p60-calendars.icloud.com/published/2/KQpieZ-gmqxhEDixV83NnwiNFshbA0Kv7YrWnWT6RtG4vEt0ZavpPs_Yx2xnoQA8BOLACjFBfqzO4TiaLQUM1BADNcvK_J3GwAHbUY8btOA");

//        createTable(a);
        renderText();
    }

    private void createTableInit(){
        // DefaultTableModel
        DefaultTableModel model = new DefaultTableModel(new Object[2][3], new String[]{"summary", "timezone", "date", "start"});
        //
        repsonseTable = new JTable(model);
        repsonseTable.getModel().getRowCount();
        scrollPane = new JScrollPane(repsonseTable);
//        scrollPane.add(repsonseTable);
        repsonseTable.setFillsViewportHeight(true);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    private void createTable(ArrayList<String> a){
        Object[][] data = null;
        if (a.size() != 0) {
            Calender calender = Calender.createCalender(a);
            if (calender != null) {
                data = calender.render();
            }
        }
        if (data == null) {
            data = new Object[1][3];
            for (int i = 0; i < data.length; i++) {
                data[i] = new Object[3];
                for (int i1 = 0; i1 < data[i].length; i1++) {
                    data[i][i1] = "a";
                }
            }
        }
        repsonseTable = new JTable(data, new String[]{"summary", "timezone", "date", "start"});
//        scrollPane.add(repsonseTable);
        scrollPane.setViewportView(repsonseTable);
        repsonseTable.setFillsViewportHeight(true);
        scrollPane.revalidate();
        scrollPane.repaint();
        validate();
        repaint();
    }


    private void createUrlInput(){

        urlTextField = new JTextField();
        urlTextField.setSize(150,20);
        urlTextField.addActionListener((ActionEvent e)->{
            onTextInput();});
        this.add(urlTextField, BorderLayout.BEFORE_FIRST_LINE);
        response = new JLabel();
        response.setMinimumSize(new Dimension(150,20));
        this.add(response, BorderLayout.EAST);



        this.add(new JLabel("Connected to the internet: " + WifiCalendar.wifiTest()), BorderLayout.SOUTH);

    }

    private void onTextInput(){

        // "https://p60-calendars.icloud.com/published/2/KQpieZ-gmqxhEDixV83NnwiNFshbA0Kv7YrWnWT6RtG4vEt0ZavpPs_Yx2xnoQA8BOLACjFBfqzO4TiaLQUM1BADNcvK_J3GwAHbUY8btOA"
        ArrayList<String> a = WifiCalendar.getIcal(urlTextField.getText());
        responseText += "file size: " + a.size() + "\n";
        if (a.size() != 0){
            createTable(a);
        }else{
            responseText += "invalid link for ical\n";
        }
        renderText();
    }

    private void renderText(){
        response.setText("<html>"+(responseText+Calender.getDebugging()+WifiCalendar.getDebugging()).replace("\n","<br>")+"</html>");
        responseText = "";
    }




}
