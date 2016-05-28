package me.murks.filmchecker.model;

/**
 * Created by mark on 28.05.16.
 */
public class Film {
    private final String orderNumber;
    private final String shopId;

    public Film(String orderNumber, String shopId) {
        this.orderNumber = orderNumber;
        this.shopId = shopId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getShopId() {
        return shopId;
    }
}
