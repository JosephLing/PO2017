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
//        System.out.println(createUrlConnection("a"));
//        System.out.println(wifiTest());
//
//        System.out.println(createUrlConnection("www.kent.ac.uk/timetabling/ical/127379.ics"));
//        System.out.println(createUrlConnection("webcal://www.kent.ac.uk/timetabling/ical/127379.ics"));

//        getFile("https://calendar.google.com/calendar/ical/josephling11%40gmail.com/private-34ea00d303b31413b8d556dfde44763c/basic.ics");
//        getFile("www.kent.ac.uk/timetabling/ical/127379.ics");
        this.setTitle("Event sub");
        // row size, col 4



        createUrlInput();

        scrollPane = new JScrollPane();
        this.add(scrollPane, BorderLayout.CENTER);
        setSize(500,500);
        this.setVisible(true);

        ArrayList<String> a = getFile("https://p60-calendars.icloud.com/published/2/KQpieZ-gmqxhEDixV83NnwiNFshbA0Kv7YrWnWT6RtG4vEt0ZavpPs_Yx2xnoQA8BOLACjFBfqzO4TiaLQUM1BADNcvK_J3GwAHbUY8btOA");

        createTable(a);
    }

    private void createTable(ArrayList<String> a){
        if (repsonseTable != null){
            scrollPane.remove(repsonseTable);
        }
        Object[][] data = new Object[a.size()][4];
        ArrayList<IcalEvent> b = createCalender(a);
        for (int i = 0; i < b.size(); i++) {
            data[i] = b.get(i).toObjectArray();
        }
        repsonseTable = new JTable(data, new String[]{"summary", "timezone", "date", "start"});

        TableModel dtm = new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return b.size();
            }

            @Override
            public int getColumnCount() {
                return 4;
            }

            @Override
            public Object getValueAt(int i, int i1) {
                return  data[i][i1];
            }
        };
        scrollPane.updateUI();
        repsonseTable.setModel(dtm);
        scrollPane.add(repsonseTable);
        scrollPane.validate();
        scrollPane.repaint();
        validate();
        repaint();

        // http://stackoverflow.com/questions/9706682/swing-jscrollpane-doesnt-refresh-after-changes-in-the-structure-of-a-jtable
        repsonseTable.setFillsViewportHeight(true);

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



        this.add(new JLabel("Connected to the internet: " + wifiTest()), BorderLayout.SOUTH);

    }

    private void onTextInput(){
//        String text = "";
//        text += urlTextField.getText();
//        URLConnection url = createUrlConnection(urlTextField.getText());
//        if (url != null) {
//            text += " url correct";
//        }else{
//            text += " url incorrect";
//        }
        String text = "";
        // "https://p60-calendars.icloud.com/published/2/KQpieZ-gmqxhEDixV83NnwiNFshbA0Kv7YrWnWT6RtG4vEt0ZavpPs_Yx2xnoQA8BOLACjFBfqzO4TiaLQUM1BADNcvK_J3GwAHbUY8btOA"
        ArrayList<String> a = getFile(urlTextField.getText());
        text += a.size();
        if (a.size() != 0){
            createTable(a);
        }else{
            text += "invalid link for ical";
        }
        System.out.println(text);
        response.setText(text);
    }


    private String getUrlString(String urlString){
        if (urlString.contains("webcal://")){
            urlString = urlString.replace("webcal://", "");
        }

        if (!(urlString.contains("http://") || urlString.contains("https://"))) {
            urlString = "http://" + urlString;
        }
        return urlString;
    }


    private URLConnection createUrlConnection(String urlString){
        URL url = null;
        URLConnection conn = null;
        try{
            url = new URL(getUrlString(urlString));
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
            if (conn.getHeaderFields().keySet().size() == 0){
                conn = null;
            }
        }

        return conn;
    }

    public boolean wifiTest(){
        return (createUrlConnection("http://google.com") != null);
    }

    public ArrayList<String> getFile(String urlString){
        URL url = null;
        boolean encodingUsed = true;
        boolean streamCreated = true;
        ArrayList<String> data = new ArrayList<String>();
        if (!urlString.isEmpty()){
            urlString = getUrlString(urlString);
            // create a URL
            try{
                url = new URL(getUrlString(urlString));
            }catch (MalformedURLException e){

            }
        }

        if (url != null){
            InputStream in = null;
            try{
                in = url.openStream();
            }catch (IOException e){
                streamCreated = false;
            }
            if (streamCreated){

                // get the data
                BufferedReader bufferedInputStream = null;
                try{
                    bufferedInputStream =  new BufferedReader(new InputStreamReader(in, "UTF-8"));
                }catch (UnsupportedEncodingException e){
                    encodingUsed = false;
                }
                if (!encodingUsed) {
                    bufferedInputStream = new BufferedReader(new InputStreamReader(in));
                }

                try{
                    String s;
                    while ((s = bufferedInputStream.readLine()) != null) {
                        data.add(s);
                    }
                }catch (IOException e){

                }
                int index = 0;
                boolean invalidContent = false;
                while (index < data.size() && !invalidContent) {
                    if (data.get(index).contains("<title>") && data.get(index).contains("301")) {
                        invalidContent = true;
                    }else{
                        index ++;
                    }
                }

                try {
                    in.close();
                }catch (IOException e){

                }

                if (invalidContent){
                    System.out.println("invalid content");
                    System.out.println(data.get(index));
                }else{
                    return data;
                }
            }
        }else{
            System.out.println("invalid url");
        }

        return data;
    }





    private ArrayList<IcalEvent> createCalender(ArrayList<String> cal){
        long time = System.currentTimeMillis();
        boolean parserError = false;
        int index = 0;
        boolean event = false;
        String version = "";
        ArrayList<IcalEvent> icalEvents = new ArrayList<IcalEvent>();
        int icalIndex = 0;
        String[] eventsSummary;
        String[] eventsDstart;
        if (cal.get(0).equals("BEGIN:VCALENDAR")) {
            if (cal.get(cal.size()-1).equals("END:VCALENDAR")) {
                String[] versionData = cal.get(1).split(":");

                if (versionData.length == 2) {
                    version = versionData[1];
                }
                if (!version.contains("2.0")) {
                    parserError = true;
                    System.out.println("invlaid version");
                }

                if (! parserError){
                    while (index < cal.size() && ! parserError)
                    {
                        if (cal.get(index).contains("BEGIN:VEVENT")){
                            event = true;
                            icalEvents.add(new IcalEvent());
                            while (index < cal.size() && event && !parserError){
                                if (cal.get(index).equals("END:VEVENT")){
                                    event = false;
                                    icalIndex ++;
                                }else if (cal.get(index).contains("SUMMARY")){
                                    eventsSummary = cal.get(index).split("SUMMARY");
                                    if (eventsSummary.length == 2){
                                        icalEvents.get(icalIndex).setSummary(eventsSummary[1]);
                                    }else{
                                        parserError = true;
                                        System.out.println("SUMMARY args");
                                    }
                                } else if (cal.get(index).contains("DTSTART;")) {
                                    eventsDstart = cal.get(index).split("DTSTART;");

                                    if (eventsDstart.length == 2){
                                        eventsDstart = eventsDstart[1].split(":");
                                        if (eventsDstart.length == 2) {
                                            icalEvents.get(icalIndex).setTimezone(eventsDstart[0]);
                                            icalEvents.get(icalIndex).setStart(eventsDstart[1]);
                                        }else{
                                            System.out.println("DTSTART args");
                                            parserError = true;
                                        }
                                    }else{
                                        System.out.println("DSTART");
                                        parserError = true;
                                    }
                                }
                                index ++;
                            }
                        } else{
                            index ++;
                        }
                    }
                }else {
                    System.out.println("could not find version or invalid version");
                }
            }else{
                System.out.println("coulnd't find end");
            }
        }else{
            System.out.println("couldn't find start");
        }
        System.out.println("took " + (System.currentTimeMillis()-time) + " ms");
        System.out.println("error happened? " + parserError);
        System.out.println("event state: " + event);
        System.out.println("events: " + icalEvents.size());
        System.out.println("index: " + index);
        System.out.println("version: " + version);
        icalEvents.stream().forEach(System.out::println);
        return icalEvents;
    }


    private class IcalEvent{

        private String summary;
        private long start;
        private String timezone;
        private String date;

        public IcalEvent() {

        }


        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public long getStart() {
            return start;
        }

        public void setStart(String start) {
            String[] split = start.split("T");
            if (split.length == 2) {
                this.date = split[0];
                this.start = Long.parseLong(split[1]);
            }

        }

        public String getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        @Override
        public String toString() {
            return summary + " | " + timezone + " | " + date + " | " + start;
        }

        public Object[] toObjectArray(){
            return new String[]{summary, timezone, date,  Long.toString(start)};
        }
    }


}
