package me.murks.filmchecker.model;

import java.net.URL;

/**
 * Class to hold informations about an rm store
 * @date 9/19/2017
 * @author zouroboros
 */

public class RossmannStoreLink {
    private final String storeName;
    public final URL storeUrl;

    public RossmannStoreLink(String storeName, URL storeUrl) {
        this.storeName = storeName;
        this.storeUrl = storeUrl;
    }

    public String toString() {
        return storeName;
    }
}
