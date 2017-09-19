package me.murks.filmchecker.model;

import java.net.URL;

/**
 * Model containing information about the api endpoint for rossmann (rm) filmorders
 * Created by zouroboros on 9/15/17.
 */

public class RmQueryModel {
    public final Boolean htNumber;
    public final URL queryEndpoint;

    public RmQueryModel(Boolean htNumber, URL queryEndpoint) {
        this.htNumber = htNumber;
        this.queryEndpoint = queryEndpoint;
    }
}
