package mbedApp.mqtt;

/**
 * Created by Joe on 09/06/2017.
 */
public enum MessageProtocols {
    REGISTER("", "NAME"),
    PING("", "pingResponse");

    private String send;
    private String response;

    MessageProtocols(String send, String response) {
        this.send = send;
        this.response = response;
    }

    public String getSend() {
        return (this.send.equals("") ? this.name() : this.send);
    }

    public String getResponse() {
        return (this.response.equals("") ? this.name() : this.response);
    }
}
