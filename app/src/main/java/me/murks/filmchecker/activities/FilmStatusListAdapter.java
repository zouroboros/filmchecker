package me.murks.filmchecker.activities;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.LinkedList;

import me.murks.filmchecker.R;
import me.murks.filmchecker.model.Film;
import me.murks.filmchecker.model.FilmStatus;

/**
 * Created by mark on 28.05.16.
 */
public class FilmStatusListAdapter extends ArrayAdapter<Pair<Film, FilmStatus>> {

    public FilmStatusListAdapter(Context context) {
        super(context, 0, new LinkedList<Pair<Film, FilmStatus>>());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pair<Film, FilmStatus> entry = super.getItem(position);
        View view = convertView;
        if(view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_film_status_list_item, parent, false);
        }
        TextView orderNumberView = (TextView)view.findViewById(R.id.orderNumber);
        orderNumberView.setText(entry.first.getOrderNumber());

        TextView shopIdView = (TextView)view.findViewById(R.id.shopId);
        shopIdView.setText(entry.first.getShopId());

        TextView statuscodeView = (TextView)view.findViewById(R.id.statusCode);
        statuscodeView.setText(entry.second.getStatus());
        return view;
    }
}
