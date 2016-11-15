package me.murks.filmchecker.io;

import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.murks.filmchecker.model.Film;
import me.murks.filmchecker.model.FilmStatus;

/**
 * Class for querying the Rossmann Auftragstatus service
 */
class RossmannStatusProvider implements IStatusProvider {

    /**
     * The url to query for film infos
     */
    private URL url;

    /**
     * Creates a new RossmannStatusProvider
     */
    public RossmannStatusProvider() {
        try {
            url = new URL("http://tracking.orwonet.de/tracking/orderdetails.jsp");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getUiName() {
        return "Rossmann";
    }

    @Override
    public String getId() {
        return this.getClass().getName();
    }

    /**
     * Queries the status of the given film
     * @param film The film whose status wil be queried
     * @return The status of the film order
     * @throws IOException When an exception occurs while querying
     */
    public FilmStatus getFilmStatus(Film film) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        String urlParameter = "bagId=" + film.getOrderNumber() + "&outletId=" + film.getShopId();
        byte[] postData = urlParameter.getBytes();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", Integer.toString(postData.length));
        OutputStream stream = connection.getOutputStream();
        stream.write(postData);
        String response = CharStreams.toString( new InputStreamReader( connection.getInputStream(), "UTF-8" ) );
        connection.disconnect();
        Pattern matchPattern = Pattern.compile("(?:<td class=\"boxHalf\">Auftragsstatus:</td>\\s*<td class=\"boxHalf\">)([\\w \\s \\/ \\.]*)(?:</td>)");
        Matcher match = matchPattern.matcher(response);
        if(match.find()) {
            String status = match.group(1);
            return new FilmStatus(status.trim());
        }
        return new FilmStatus("Error");
    }
}
