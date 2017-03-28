package mbedApp.gui.events;

import java.util.ArrayList;

/**
 * Created by Joe on 28/03/2017.
 */
public class Calender {

    private ArrayList<EventCalendar> events;

    public Calender(ArrayList<EventCalendar>events){
        this.events = events;
    }

    public Object[][] render(){
        return this.events.stream().map(EventCalendar::toObjectArray).toArray(a->new Object[this.events.size()][4]);
    }


    public static Calender createCalender(ArrayList<String> cal) {
        StringBuilder debugging = new StringBuilder();
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
                    debugging.append("invlaid version");
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
                                        debugging.append("SUMMARY args");
                                    }
                                } else if (cal.get(index).contains("DTSTART;")) {
                                    eventsDstart = cal.get(index).split("DTSTART;");

                                    if (eventsDstart.length == 2) {
                                        eventsDstart = eventsDstart[1].split(":");
                                        if (eventsDstart.length == 2) {
                                            icalEvents.get(icalIndex).setTimezone(eventsDstart[0]);
                                            icalEvents.get(icalIndex).setStart(eventsDstart[1]);
                                        } else {
                                            debugging.append("DTSTART args");
                                            parserError = true;
                                        }
                                    } else {
                                        debugging.append("DSTART");
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
                    debugging.append("could not find version or invalid version");
                }
            } else {
                debugging.append("coulnd't find end");
            }
        } else {
            debugging.append("couldn't find start");
        }
        debugging.append("took " + (System.currentTimeMillis() - time) + " ms\nerror happened? " + parserError+
                "\nevent state: " + event+"\nevents: " + icalEvents.size()+"\nindex: " + index+"\nversion: " + version);
        icalEvents.forEach(debugging::append);
        if (icalEvents != null){
            return new Calender(icalEvents);
        }
        return null;
    }
}
