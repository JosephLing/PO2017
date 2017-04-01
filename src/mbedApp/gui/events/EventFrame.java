package mbedApp.gui.events;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;

/**
 * EventFrame does.............
 *
 * @author josephling
 * @version 1.0 25/03/2017
 */
public class EventFrame extends JFrame {

    private static String[] TABLEHEADERS = new String[]{"summary", "timezone", "date", "start"};

    private JTextField urlTextField;
    private JTextPane response;
    private JTable repsonseTable;
    private JScrollPane scrollPane;
    private String responseText;

    private JTextArea mqttPreview;
    private JButton submitMQTT;
    private JButton createMqtt;

    private Calender calender;
    private String mqttData;
    private String mqttCalendar;


    public EventFrame() throws HeadlessException {
        this.setTitle("Event sub");
        responseText = "";
        mqttCalendar = "";
        createUrlInput();
        createTableInit();
        createMQTTSpace();
        setSize(750,750);
        this.setVisible(true);
        calender = null;
        mqttData = "";
        pack();
        renderText();
    }

    private void createTableInit(){
        // DefaultTableModel
        DefaultTableModel model = new DefaultTableModel(new Object[2][3], TABLEHEADERS);
        //
        repsonseTable = new JTable(model);
        repsonseTable.getModel().getRowCount();
        scrollPane = new JScrollPane(repsonseTable);
//        scrollPane.add(repsonseTable);
        repsonseTable.setFillsViewportHeight(true);
//        this.add(scrollPane, BorderLayout.CENTER);
        this.add(scrollPane);
    }


    private void createMQTTSpace(){
        JPanel container = new JPanel();
        mqttPreview = new JTextArea("no mqtt data selected", 5, 42);
        mqttPreview.setEditable(false);
        mqttPreview.setAutoscrolls(true);
        mqttPreview.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(mqttPreview, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.createVerticalScrollBar();
        container.add(scrollPane, BorderLayout.SOUTH);
        submitMQTT = new JButton("send mqtt events");
        submitMQTT.addActionListener((ActionEvent e)->{sendMqttData();});
        submitMQTT.setEnabled(false);
        createMqtt = new JButton("create mqtt message");
        createMqtt.addActionListener((ActionEvent e)->{createMqttData();});
        createMqtt.setEnabled(false);
        container.add(submitMQTT);
        container.add(createMqtt, BorderLayout.WEST);
        this.add(container, BorderLayout.SOUTH);
    }



    private void createUrlInput(){
        JPanel panel = new JPanel();


        urlTextField = new JTextField("", 42);
        urlTextField.addActionListener((ActionEvent e)->{
            onTextInput();});
        panel.add(urlTextField, BorderLayout.WEST);
        JButton fileChooser = new JButton("choose file");
        fileChooser.addActionListener((ActionEvent e)->{
            chooseFile();});
        panel.add(fileChooser, BorderLayout.EAST);
        response = new JTextPane();
        response.setContentType("text/html");
        response.setEditable(false);
        JPanel panel1 = new JPanel();
        panel1.add(response);
        this.add(panel1, BorderLayout.EAST);
        this.add(panel, BorderLayout.NORTH);
        this.setJMenuBar(new JMenuBar());
        this.getJMenuBar().add(new JMenuItem("Connected to the internet: " + WifiCalendar.wifiTest()));

    }

    private void makeCalendarTable(ArrayList<String> a){
        if (a.size() != 0) {
            calender = Calender.createCalender(a);
            if (calender != null) {
                createTable(calender.render());
            }
        }

    }





    private void chooseFile(){
        boolean fileWorked = false;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.name")));
        this.add(fileChooser);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            if (selectedFile.getName().endsWith(".ical") || selectedFile.getName().endsWith(".ics")) {
                BufferedReader reader = null;
                try{
                    reader = new BufferedReader(new FileReader(selectedFile.getAbsolutePath()));
                }catch (FileNotFoundException e){
                    responseText = "File not found: " + selectedFile.getAbsolutePath();
                }
                String line;
                ArrayList<String> data = new ArrayList<>();
                if (reader != null) {
                    try{
                        while ((line = reader.readLine()) != null)
                        {
                            data.add(line);
                        }
                    }catch (IOException e){
                        responseText = "invalid data found in file: " + selectedFile.getName();
                    }
//                    data.stream().forEach(System.out::println);
                    renderTable(data);
                    try{
                        reader.close();
                    }catch (IOException e){
                        responseText = "Failed to close file: " + selectedFile.getName();
                    }
                    if (responseText.isEmpty()){
                        fileWorked = true;
                        // stupid solution but i am tired and its getting late...
                    }
                }
            }else{
                responseText = "Invalid file type: " + selectedFile.getName() + "\nPath: " + selectedFile.getAbsolutePath();
            }

        }
        if (!fileWorked){
            renderText();
        }

        this.remove(fileChooser);

    }


    private void createTable(Object[][] data){
        repsonseTable = new JTable(data, TABLEHEADERS);
        scrollPane.setViewportView(repsonseTable);
        repsonseTable.setFillsViewportHeight(true);
        scrollPane.revalidate();
        scrollPane.repaint();
        validate();
        repaint();
    }



    private void renderTable(ArrayList<String> a){
        responseText += "n. lines: " + a.size() + "\n";
        if (a.size() != 0){
            makeCalendarTable(a);
            createMqtt.setEnabled(true);
        }else{
            // so if the table is being rendered but we don't want it to be there
            if (calender != null) {
                createTable(new Object[0][3]);
            }
            responseText += "invalid link for ical\n";
            calender = null;
            createMqtt.setEnabled(false);

        }
        createMqttDataPreview();
        renderText();
    }


    private void onTextInput(){
            // "https://p60-calendars.icloud.com/published/2/KQpieZ-gmqxhEDixV83NnwiNFshbA0Kv7YrWnWT6RtG4vEt0ZavpPs_Yx2xnoQA8BOLACjFBfqzO4TiaLQUM1BADNcvK_J3GwAHbUY8btOA"
        renderTable(WifiCalendar.getIcal(urlTextField.getText()));

    }

    private void renderText(){
        response.setText("<html>Debug message:  <br>"+(responseText+Calender.getDebugging()+WifiCalendar.getDebugging()).replace("\n","<br>")+"</html>");
        responseText = "";

    }


    public void createMqttDataPreview(){
        if (mqttCalendar != null){
            if (!mqttCalendar.isEmpty()) {
                mqttPreview.setText(mqttCalendar);
                submitMQTT.setEnabled(true);
            }else{
                mqttPreview.setText("no rows selected");
                submitMQTT.setEnabled(false);
            }
        }else{
            mqttPreview.setText("no rows selected");
            submitMQTT.setEnabled(false);
        }
    }

    private void sendMqttData(){
        System.out.println("Sending data: " + mqttPreview.getText());
        createTable(new Object[1][4]);
        calender = null;
        mqttCalendar = "";
        createMqttDataPreview();
    }



    private void createMqttData(){
        StringBuilder events = new StringBuilder();
        for (int i : repsonseTable.getSelectedRows()) {
            // timezone: (String)repsonseTable.getModel().getValueAt(i, 1)
            // date: (String)repsonseTable.getModel().getValueAt(i, 2)
            // time: Long.parseLong((String) repsonseTable.getModel().getValueAt(i, 3))
            events.append(
                    "event"+i + "="
                            +(String)repsonseTable.getModel().getValueAt(i, 1)
                            +(String)repsonseTable.getModel().getValueAt(i, 2)
                            +Long.parseLong((String) repsonseTable.getModel().getValueAt(i, 3))
                            +","
            );
        }
        mqttCalendar = "{events:" + events.toString() + "}";
        createMqttDataPreview();
    }




}
