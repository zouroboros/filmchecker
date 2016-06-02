package me.murks.filmchecker.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Collection;

import me.murks.filmchecker.io.IStatusProvider;

/**
 * Adapter implementation for selecting {@see IStatusProvider}
 * @author zouroboros
 * @version 2016-02-06 0.1
 */
class StatusProviderAdapter extends ArrayAdapter<IStatusProvider> {

    /**
     * Creates a new StatusProviderAdapter
     * @param context The current context
     * @param provider Collection of IStatusProvider
     */
    public StatusProviderAdapter(Context context, Collection<IStatusProvider> provider) {
        super(context, 0, provider.toArray(new IStatusProvider[0]));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IStatusProvider provider = super.getItem(position);
        TextView view = (TextView) convertView;
        if (view == null) {
            view = (TextView) LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
        }
        view.setText(provider.getUiName());
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        IStatusProvider provider = super.getItem(position);
        TextView view = (TextView) convertView;
        if (view == null) {
            view = (TextView) LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }
        view.setText(provider.getUiName());
        return view;
    }
}
