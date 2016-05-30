package me.murks.filmchecker.model;

import java.util.Calendar;
import java.util.Date;

/**
 * Model for a film order
 * @author zouroboros
 * @version 0.1 2016-05-29
 */
public class Film {

    private final String orderNumber;
    private final String shopId;
    private final Calendar insertDate;

    /**
     * Constructs a new Film with the given order number and the given shop id
     * @param orderNumber The order number
     * @param shopId The shop id
     * @param insertDate The date the film was entered into the application
     */
    public Film(String orderNumber, String shopId, Calendar insertDate) {
        this.orderNumber = orderNumber;
        this.shopId = shopId;
        this.insertDate = insertDate;
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
}
