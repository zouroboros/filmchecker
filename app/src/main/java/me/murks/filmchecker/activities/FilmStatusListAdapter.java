package me.murks.filmchecker.activities;

import android.content.Context;
import androidx.annotation.NonNull;
import android.text.format.DateUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.TimeZone;

import me.murks.filmchecker.R;
import me.murks.filmchecker.model.Film;
import me.murks.filmchecker.model.FilmStatus;

/**
 * ListAdapter for the film list
 * @author zouroboros
 */
public class FilmStatusListAdapter extends ArrayAdapter<Pair<Film, FilmStatus>> {

    public FilmStatusListAdapter(Context context) {
        super(context, 0, new LinkedList<Pair<Film, FilmStatus>>());
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
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
        statuscodeView.setSelected(true);

        TextView insertDateView = (TextView)view.findViewById(R.id.insertDate);
        long when = entry.first.getInsertDate().getTimeInMillis();
        long time = when + TimeZone.getDefault().getOffset(when);
        String formattedDate = DateUtils.formatDateTime(view.getContext(), time,
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
        insertDateView.setText(String.format(view.getResources()
                .getString(R.string.film_list_insert_date), formattedDate));

        View deleteButton = view.findViewById(R.id.delete_button);
        deleteButton.setTag(entry.first.getId());

        return view;
    }
}
