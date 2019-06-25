package me.murks.filmchecker.io;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import me.murks.filmchecker.model.RmQueryModel;
import me.murks.filmchecker.model.RossmannStoreLink;

/**
 * Class for interacting with the rossmann website
 * @author zouroboros
 * @version 9/11/17
 */

public class RossmannApi {

    private URL storesEndpoint;

    public RossmannApi() {
        try {
            storesEndpoint = new URL("https://shop.rossmann-fotowelt.de/tracking/outlet.jsp");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public List<RossmannStoreLink> queryStores(String plz) throws IOException, URISyntaxException {
        String response = HttpHelper.post(storesEndpoint, Collections.singletonMap("zip", plz));
        Document dom = Jsoup.parse(response);
        Elements outletElements = dom.select("#outletList .outletAddress");
        List<RossmannStoreLink> stores = new LinkedList<>();
        for (Element ele: outletElements) {
            String storePlz = ele.select(".outletAddressZip").first().text();
            String city = ele.select(".outletAddressCity").first().text();
            String street = ele.select(".outletAddressStreet").first().text();
            String link = ele.select(".outletAddressZip").select("a").attr("href");
            URI storeUri = storesEndpoint.toURI().resolve(link);
            stores.add(new RossmannStoreLink(street + ", " + city + ", " + " " + storePlz, storeUri.toURL()));
        }
        return stores;
    }

    public RmQueryModel queryStoreQueryMethod(URL storeURL) throws IOException, URISyntaxException {
        String response = HttpHelper.get(storeURL);
        Document storeDocument = Jsoup.parse(response);
        URI formURL = storeURL.toURI().resolve(findFormUrl(storeDocument));
        return parseQueryModel(formURL);
    }

    private URI findFormUrl(Document document) throws URISyntaxException {
        Elements allElements = document.getAllElements();
        for (Element element: allElements) {
            if(element.hasText() && element.text().contains("Auftragstasche")) {
                Elements links = element.select("a");
                if(links.size() == 1) {
                    return new URI(links.first().attr("href"));
                }
            }
        }
        return null;
    }

    private RmQueryModel parseQueryModel(URI formUrl) throws IOException {
        Document formDocument = Jsoup.parse(HttpHelper.get(formUrl.toURL()));
        Element form = formDocument.select("form").first();
        URI endPointUri = formUrl.resolve(form.attr("action"));
        Boolean htMode = form.text().contains("HT-Nr.");
        return new RmQueryModel(htMode, endPointUri.toURL());
    }
}
