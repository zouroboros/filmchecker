package me.murks.filmchecker.model;

import java.util.Calendar;
import java.util.Set;

/**
 * Interface that describes store model.
 *
 * A store model describes how a film is added depending on the store where the film is developed
 * @author zouroboros
 */
public interface StoreModel {
    String htNumber = "htNumber";
    String shopId = "shopId";
    String orderNumber = "orderNumber";

    /***
     * Creates a new {@link Film} object using the supplied data
     * @param shopId The id of the shop
     * @param htNumber The ht number
     * @param orderNumber The order number
     * @param date The date the film was added
     * @return New film
     */
    Film getFilm(String shopId, String htNumber, String orderNumber, Calendar date);

    /***
     * Gets id of string to describe the name of this store in the ui.
     * @return Id of ui string
     */
    int getStoreName();

    /***
     * True if this model needs a {@link RmQueryModel}
     * @return If this model needs a {@link RmQueryModel}
     */
    boolean needsRmStoreLocator();

    /**
     * Returns a set of fields that are required to create a new film.
     * The strings ars the constants in {@link StoreModel} interface.
     * @return Set of required field ids.
     */
    Set<String> getRequiredFields();

    /**
     * Returns the id of a string resource that provides the label for the store id field
     * @return Id of ui string
     */
    int getShopIdFieldName();

    /**
     * Returns a unique string to identify this store model
     * @return Unique id
     */
    String getStoreId();

    /**
     * Returns the id of string resource that contains a url to open the store's tracking page.
     * @return The id or string resource
     */
    int getStoreUrl();
}
