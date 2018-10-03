package me.murks.filmchecker.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Model containing information about the api endpoint for rossmann (rm) filmorders
 * Created by zouroboros on 9/15/17.
 */

public class RmQueryModel implements Parcelable {
    public final Boolean htNumber;
    public final URL queryEndpoint;

    public RmQueryModel(Boolean htNumber, URL queryEndpoint) {
        this.htNumber = htNumber;
        this.queryEndpoint = queryEndpoint;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.htNumber);
        dest.writeString(this.queryEndpoint.toString());
    }

    private RmQueryModel(Parcel in) {
        this.htNumber = (Boolean) in.readValue(Boolean.class.getClassLoader());
        try {
            this.queryEndpoint = new URL(in.readString());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static final Parcelable.Creator<RmQueryModel> CREATOR = new Parcelable.Creator<RmQueryModel>() {
        @Override
        public RmQueryModel createFromParcel(Parcel source) {
            return new RmQueryModel(source);
        }

        @Override
        public RmQueryModel[] newArray(int size) {
            return new RmQueryModel[size];
        }
    };
}
