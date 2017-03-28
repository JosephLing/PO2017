package mbedApp.gui.events;
import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public EventFrame() throws HeadlessException {
        this.setTitle("Event sub");

        createUrlInput();
        createTableInit();
        setSize(500,500);
        this.setVisible(true);

        ArrayList<String> a = WifiCalendar.getIcal("https://p60-calendars.icloud.com/published/2/KQpieZ-gmqxhEDixV83NnwiNFshbA0Kv7YrWnWT6RtG4vEt0ZavpPs_Yx2xnoQA8BOLACjFBfqzO4TiaLQUM1BADNcvK_J3GwAHbUY8btOA");

        createTable(a);
    }

    private void createTableInit(){
        // DefaultTableModel
        DefaultTableModel model = new DefaultTableModel(new Object[1][4], new String[]{"summary", "timezone", "date", "start"});
        //
        repsonseTable = new JTable(model);
        repsonseTable.getModel().getRowCount();
        scrollPane = new JScrollPane();
        scrollPane.add(repsonseTable);
        repsonseTable.setFillsViewportHeight(true);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    private void createTable(ArrayList<String> a){
        if (repsonseTable != null){
            scrollPane.remove(repsonseTable);
        }
        Calender calender = Calender.createCalender(a);
        if (calender != null) {
            Object[][] data = calender.render();
            DefaultTableModel model = new DefaultTableModel(new Object[0][4], new String[]{"summary", "timezone", "date", "start"});


//            System.out.println(repsonseTable.getRowCount());
//            repsonseTable.addRowSelectionInterval(0,data.length);
//            System.out.println(repsonseTable.getRowCount());
//            System.out.println(repsonseTable.getColumnCount());

            for (int i = 0; i < data.length; i++) {
                model.addRow(data[i]);
                System.out.println(data[i][0].toString());
            }
            repsonseTable.setModel(model);
            System.out.println(repsonseTable.getRowCount());
            scrollPane.repaint();
            scrollPane.validate();
            validate();
            repaint();

        }

//            repsonseTable = new JTable(data, new String[]{"summary", "timezone", "date", "start"});
//            TableModel dtm = new AbstractTableModel() {
//
//
//                @Override
//                public int getRowCount() {
//                    return data.length;
//                }
//
//                @Override
//                public int getColumnCount() {
//                    return data[0].length;
//                }
//
//                @Override
//                public Object getValueAt(int i, int i1) {
//                    return  data[i][i1];
//                }
//
//
//
//            };
//        }

//



//        repsonseTable.setModel(dtm);


        // http://stackoverflow.com/questions/9706682/swing-jscrollpane-doesnt-refresh-after-changes-in-the-structure-of-a-jtable

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
        String text = "";
        // "https://p60-calendars.icloud.com/published/2/KQpieZ-gmqxhEDixV83NnwiNFshbA0Kv7YrWnWT6RtG4vEt0ZavpPs_Yx2xnoQA8BOLACjFBfqzO4TiaLQUM1BADNcvK_J3GwAHbUY8btOA"
        ArrayList<String> a = WifiCalendar.getIcal(urlTextField.getText());
        text += a.size();
        if (a.size() != 0){
            createTable(a);
        }else{
            text += "invalid link for ical";
        }
        System.out.println(text);
        response.setText(text);
    }




}
