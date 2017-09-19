package me.murks.filmchecker.io;

import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * Class containing static helper methods for creating http requests
 * @author zouroboros
 * @version 1 9/11/17.
 */

class HttpHelper {
    public static String post(URL url, Map<String, String> params) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        String urlParameter = "";
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if(urlParameter.length() > 0) {
                urlParameter += "&";
            }
            urlParameter += entry.getKey() + "=" + entry.getValue();
        }
        byte[] postData = urlParameter.getBytes();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", Integer.toString(postData.length));
        OutputStream stream = connection.getOutputStream();
        stream.write(postData);
        String response = CharStreams.toString( new InputStreamReader( connection.getInputStream(), getEncoding(connection)));
        connection.disconnect();
        return response;
    }

    public static String get(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        return CharStreams.toString( new InputStreamReader( connection.getInputStream(), getEncoding(connection)));
    }

    private static String getEncoding(URLConnection connection) {
        String contentType = connection.getHeaderField("content-type");
        String[] fields = contentType.split(";");
        String encoding = "UTF-8";
        for (String s : fields) {
            if (s.startsWith("charset=")) {
                encoding = s.split("=")[1];
            }
        }
        return encoding;
    }
}
