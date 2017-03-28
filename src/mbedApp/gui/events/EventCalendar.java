package mbedApp.gui.events;

import java.util.ArrayList;

/**
 * Created by Joe on 28/03/2017.
 */
public class EventCalendar {

    private String summary;
    private long start;
    private String timezone;
    private String date;



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
