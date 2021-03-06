package me.murks.filmchecker.io;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.murks.filmchecker.model.Film;
import me.murks.filmchecker.model.FilmStatus;

/**
 * Class for querying the Rossmann Auftragstatus service
 */
class RossmannStatusProvider implements IStatusProvider {

    /**
     * Queries the status of the given film
     * @param film The film whose status wil be queried
     * @return The status of the film order
     * @throws IOException When an exception occurs while querying
     */
    public FilmStatus getFilmStatus(Film film) throws IOException {
        URL url = new URL("https://shop.rossmann-fotowelt.de/tracking/orderdetails.jsp");
        if(film.getRmEndpoint() != null) {
            url = film.getRmEndpoint();
        }

        if(film.getShopId() != null) {
            Map<String, String> params = new HashMap<>();
            params.put("bagId", film.getOrderNumber());
            params.put("outletId", film.getShopId());
            String response = HttpHelper.post(url, params);
            Pattern matchPattern = Pattern.compile("(?:<td class=\"boxHalf\">Auftragsstatus:" +
                    "</td>\\s*<td class=\"boxHalf\">)([\\w \\s \\/ \\.]*)(?:</td>)");
            Matcher match = matchPattern.matcher(response);

            if(match.find()) {
                String status = match.group(1);
                return new FilmStatus(status.trim(), null, null);
            }

            throw new IOException();
        } else {
            Map<String, String> params = new HashMap<>();
            params.put("AUFNR_A", film.getOrderNumber());
            params.put("FIRMA", film.getHtNumber().substring(0, 2));
            params.put("HDNR", film.getHtNumber().substring(2));
            String response = HttpHelper.post(url, params);
            Document dom = Jsoup.parse(response);
            return new FilmStatus(dom.select(".trackingContBox div[align='center']").text(),
                    null, null);
        }
    }
}
