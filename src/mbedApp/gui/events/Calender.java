package mbedApp.gui.events;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by Joe on 28/03/2017.
 */
public class Calender {

    private static String debugging = "";

    public static String getDebugging() {
        String deg = debugging;
        debugging = "";
        return deg;
    }

    public static void setDebugging(String newDebugging){
        debugging = newDebugging;
    }

    private ArrayList<EventCalendar> events;

    public Calender(ArrayList<EventCalendar>events){
        this.events = events;
    }

    public Object[][] render(){
        return this.events.stream().map(EventCalendar::toObjectArray).toArray(a->new Object[this.events.size()][4]);
    }


    public static Calender createCalender(ArrayList<String> cal) {
        setDebugging("");
        ArrayList<String> debugging = new ArrayList<String>();
        long time = System.currentTimeMillis();
        boolean parserError = false;
        int index = 0;
        boolean event = false;
        String version = "";
        ArrayList<EventCalendar> icalEvents = new ArrayList<EventCalendar>();
        int icalIndex = 0;
        String[] eventsSummary;
        String[] eventsDstart;
        if (cal.get(0).equals("BEGIN:VCALENDAR")) {
            if (cal.get(cal.size() - 1).equals("END:VCALENDAR")) {
                String[] versionData = cal.get(1).split(":");

                if (versionData.length == 2) {
                    version = versionData[1];
                }
                if (!version.contains("2.0")) {
                    parserError = true;
                    debugging.add("invlaid version");
                }

                if (!parserError) {
                    while (index < cal.size() && !parserError) {
                        if (cal.get(index).contains("BEGIN:VEVENT")) {
                            event = true;
                            icalEvents.add(new EventCalendar());
                            while (index < cal.size() && event && !parserError) {
                                if (cal.get(index).equals("END:VEVENT")) {
                                    event = false;
                                    icalIndex++;
                                } else if (cal.get(index).contains("SUMMARY")) {
                                    eventsSummary = cal.get(index).split("SUMMARY");
                                    if (eventsSummary.length == 2) {
                                        icalEvents.get(icalIndex).setSummary(eventsSummary[1]);
                                    } else {
                                        parserError = true;
                                        debugging.add("SUMMARY args");
                                    }
                                } else if (cal.get(index).contains("DTSTART;")) {
                                    eventsDstart = cal.get(index).split("DTSTART;");

                                    if (eventsDstart.length == 2) {
                                        eventsDstart = eventsDstart[1].split(":");
                                        if (eventsDstart.length == 2) {
                                            icalEvents.get(icalIndex).setTimezone(eventsDstart[0]);
                                            icalEvents.get(icalIndex).setStart(eventsDstart[1]);
                                        } else {
                                            debugging.add("DTSTART args");
                                            parserError = true;
                                        }
                                    } else {
                                        debugging.add("DSTART");
                                        parserError = true;
                                    }
                                }
                                index++;
                            }
                        } else {
                            index++;
                        }
                    }
                } else {
                    debugging.add("could not find version or invalid version");
                }
            } else {
                debugging.add("coulnd't find end");
            }
        } else {
            debugging.add("couldn't find start");
        }
        debugging.add("took " + (System.currentTimeMillis() - time) + " ms\nerror happened? " + parserError+
                "\nevent state: " + event+"\nevents: " + icalEvents.size()+"\nindex: " + index+"\nical version: " + version);
//        icalEvents.forEach(a->{debugging.add(a.toString());});
        setDebugging(debugging.stream().map(Object::toString)
                .collect(Collectors.joining("\n")));
        if (icalEvents != null){
            return new Calender(icalEvents);
        }
        return null;
    }
}
