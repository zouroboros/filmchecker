package me.murks.filmchecker.io;

import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.murks.filmchecker.model.Film;
import me.murks.filmchecker.model.FilmStatus;

/**
 * Created by mark on 28.05.16.
 */
public class RossmanStatusProvider {

    private URL url;

    public RossmanStatusProvider() {
        try {
            url = new URL("http://www.allcop.com/auftrags_auskunft/script.php");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public FilmStatus getStatus(Film film) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        String urlParameter = "auftragsnr=" + film.getOrderNumber() + "&filialnr=" + film.getShopId();
        byte[] postData = urlParameter.getBytes();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", Integer.toString(postData.length));
        OutputStream stream = connection.getOutputStream();
        stream.write(postData);
        String response = CharStreams.toString( new InputStreamReader( connection.getInputStream(), "UTF-8" ) );
        connection.disconnect();
        Pattern matchPattern = Pattern.compile("(?:Status\\:<\\/th><td>)([a-z A-Z 1-9]*)(?:<\\/td>)");
        Matcher match = matchPattern.matcher(response);
        if(match.find()) {
            String status = match.group(1);
            return new FilmStatus(status.trim());
        }
        return new FilmStatus("Error");
    }
}
