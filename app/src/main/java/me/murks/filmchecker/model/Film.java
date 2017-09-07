package me.murks.filmchecker.model;

import android.support.annotation.Nullable;

import java.net.URL;
import java.util.Calendar;

/**
 * Model for a film order
 * @author zouroboros
 * @version 0.1 2016-05-29
 */
public class Film {

    private final Long id;
    private final String orderNumber;
    private final String shopId;
    private final Calendar insertDate;
    private final String statusProvider;
    private final URL rmEndpoint;
    private final String htnumber;

    /**
     * Constructs a new Film with the given values without the id
     * @param orderNumber The order number
     * @param shopId The shop id
     * @param insertDate The date the film was entered into the application
     * @param statusProvider The id of the status provider for this film
     * @param rmEndpoint URL of the rm endpoint to query for this order
     * @param htnumber The htnumber of the store
     *
     */
    public Film(String orderNumber, String shopId, Calendar insertDate, String statusProvider, URL rmEndpoint, String htnumber) {
        this.orderNumber = orderNumber;
        this.shopId = shopId;
        this.insertDate = insertDate;
        this.statusProvider = statusProvider;
        this.rmEndpoint = rmEndpoint;
        this.htnumber = htnumber;
        this.id = null;
    }

    /**
     * Constructs a Film with the given values
     * @param id The id of the film
     * @param orderNumber The order number
     * @param shopId The shop id
     * @param insertDate The date the film was entered into the application
     * @param statusProvider The id of the status provider for this film
     * @param rmEndpoint URL of the rm endpoint to query for this order
     * @param htnumber The htnumber of the store
     *
     */
    public Film(long id, String orderNumber, String shopId, Calendar insertDate, String statusProvider, URL rmEndpoint, String htnumber) {
        this.orderNumber = orderNumber;
        this.shopId = shopId;
        this.insertDate = insertDate;
        this.statusProvider = statusProvider;
        this.rmEndpoint = rmEndpoint;
        this.htnumber = htnumber;
        this.id = id;
    }

    /**
     * Returns the order number
     * @return The order number as {@see String}
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * Returns the shop id
     * @return The shop id as {@see String}
     */
    public String getShopId() {
        return shopId;
    }

    /**
     * Returns the insert date of the film
     * @return The insert date as {@see Calendar}
     */
    public Calendar getInsertDate() {
        return insertDate;
    }

    /**
     * Returns the id of the status provider for this film
     * @return The id of the status provider
     */
    public String getStatusProvider() {
        return this.statusProvider;
    }

    /**
     * @return The url of the rm endpoint to query
     */
    public URL getRmEndpoint() {
        return rmEndpoint;
    }

    /**
     * @return The HT number of the store
     */
    public String getHtnumber() {
        return htnumber;
    }

    /**
     * @return The id of this film
     */
    public Long getId() {
        return id;
    }
}
