package mbedApp.gui.events;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Joe on 28/03/2017.
 */
public class WifiCalendar {

    private static String debugging = "";

    public static String getDebugging() {
        String deg = debugging;
        debugging = "";
        return deg;
    }

    public static void setDebugging(String newDebugging) {
        debugging = newDebugging;
    }

    public static String getUrlString(String urlString) {
        if (urlString.contains("webcal://")) {
            urlString = urlString.replace("webcal://", "");
        }

        if (!(urlString.contains("http://") || urlString.contains("https://"))) {
            urlString = "http://" + urlString;
        }
        return urlString;
    }


    public static URLConnection createUrlConnection(String urlString) {
        URL url = null;
        URLConnection conn = null;
        try {
            url = new URL(getUrlString(urlString));
        } catch (MalformedURLException e) {

        }
        if (url != null) {
            try {
                conn = url.openConnection();
                conn.connect();
            } catch (IOException e) {
            }
        }
        if (conn != null) {
            if (conn.getHeaderFields().keySet().size() == 0) {
                conn = null;
            }
        }

        return conn;
    }

    public static boolean wifiTest() {
        return (createUrlConnection("http://google.com") != null);
    }

    public static ArrayList<String> getIcal(String urlString) {
        URL url = null;
        boolean encodingUsed = true;
        boolean streamCreated = true;
        ArrayList<String> data = new ArrayList<String>();
        if (!urlString.isEmpty()) {
            urlString = getUrlString(urlString);
            // create a URL
            try {
                url = new URL(getUrlString(urlString));
            } catch (MalformedURLException e) {

            }
        }

        if (url != null) {
            InputStream in = null;
            try {
                in = url.openStream();
            } catch (IOException e) {
                streamCreated = false;
            }
            if (streamCreated) {

                // get the data
                BufferedReader bufferedInputStream = null;
                try {
                    bufferedInputStream = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    encodingUsed = false;
                }
                if (!encodingUsed) {
                    bufferedInputStream = new BufferedReader(new InputStreamReader(in));
                }

                try {
                    String s;
                    while ((s = bufferedInputStream.readLine()) != null) {
                        data.add(s);
                    }
                } catch (IOException e) {

                }
                int index = 0;
                boolean invalidContent = false;
                while (index < data.size() && !invalidContent) {
                    if (data.get(index).contains("<title>") && data.get(index).contains("301")) {
                        invalidContent = true;
                    } else {
                        index++;
                    }
                }

                try {
                    in.close();
                } catch (IOException e) {

                }

                if (invalidContent) {
                    setDebugging("invalid content: " + data.get(index));
                } else {
                    return data;
                }
            }
        } else {
            setDebugging("invalid url");
        }

        return data;
    }

}
