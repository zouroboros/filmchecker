package me.murks.filmchecker.model;

/**
 * Model for a film order
 * @author zouroboros
 * @version 0.1 2016-05-29
 */
public class Film {

    private final String orderNumber;
    private final String shopId;

    /**
     * Constructs a new Film with the given order number and the given shop id
     * @param orderNumber The order number
     * @param shopId The shop id
     */
    public Film(String orderNumber, String shopId) {
        this.orderNumber = orderNumber;
        this.shopId = shopId;
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
}
