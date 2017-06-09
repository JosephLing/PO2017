package mbedApp.gui.events;

/**
 * Created by Joe on 28/03/2017.
 */
public class EventCalendar {

    private String summary;
    private String start;
    private String timezone;
    private String date;


    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setDateAndStart(String start) {
        String[] split = start.split("T");
        if (split.length == 2) {
            this.date = split[0];
            this.start = split[1];
        } else {
            this.date = "none";
            this.start = "none";
        }

    }

    public void setDate(String date) {
        this.date = date;
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

    public String toMqtt() {
        return date + ":" + "timezone=" + timezone + ",start=" + start + ",description=" + summary;
    }


    public Object[] toObjectArray() {
        return new String[]{summary, timezone, date, start};
    }


}
