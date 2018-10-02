package me.murks.filmchecker.model;

import java.net.URL;

/**
 * Class to hold information about an rm store
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
