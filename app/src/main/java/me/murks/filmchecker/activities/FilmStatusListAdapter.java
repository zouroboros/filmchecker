package me.murks.filmchecker.activities;

import androidx.annotation.NonNull;
import android.text.format.DateUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import androidx.recyclerview.widget.RecyclerView;
import me.murks.filmchecker.R;
import me.murks.filmchecker.model.Film;
import me.murks.filmchecker.model.FilmStatus;

/**
 * Adapter for the film list
 * @author zouroboros
 */
public class FilmStatusListAdapter extends RecyclerView.Adapter<FilmStatusListAdapter.FilmStatusView> {

    private List<Pair<Film, FilmStatus>> films;

    public FilmStatusListAdapter() {
        super();
        films = new LinkedList<>();
    }

    @NonNull
    @Override
    public FilmStatusView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.film_list_item, parent, false);
        return new FilmStatusView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmStatusView holder, int position) {
        Pair<Film, FilmStatus> entry = films.get(position);
        holder.orderNumber.setText(entry.first.getOrderNumber());

        holder.shopId.setText(entry.first.getShopId());

        holder.statuscode.setText(entry.second.getStatus());
        holder.statuscode.setSelected(true);

        long when = entry.first.getInsertDate().getTimeInMillis();
        long time = when + TimeZone.getDefault().getOffset(when);
        String formattedDate = DateUtils.formatDateTime(holder.itemView.getContext(), time,
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
        holder.insertDate.setText(String.format(holder.itemView.getResources()
                .getString(R.string.film_list_insert_date), formattedDate));
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public void setFilms(List<Pair<Film, FilmStatus>> newFilms) {
        films = newFilms;
        notifyDataSetChanged();
    }

    class FilmStatusView extends RecyclerView.ViewHolder {

        public final TextView orderNumber, shopId, statuscode, insertDate;

        public FilmStatusView(@NonNull View itemView) {
            super(itemView);
            orderNumber = itemView.findViewById(R.id.orderNumber);
            shopId = itemView.findViewById(R.id.shopId);
            statuscode = itemView.findViewById(R.id.statusCode);
            insertDate = itemView.findViewById(R.id.insertDate);
        }
    }
}
