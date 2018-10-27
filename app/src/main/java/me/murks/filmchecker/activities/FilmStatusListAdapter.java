package me.murks.filmchecker.activities;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.time.Instant;
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
    private View.OnClickListener listener;

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
    public void onBindViewHolder(@NonNull final FilmStatusView holder, int position) {
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Film film = films.get(holder.getAdapterPosition()).first;
                Intent intent = new Intent(holder.itemView.getContext(), FilmActivity.class);
                intent.putExtra(FilmActivity.FILM_ID_INTENT_EXTRA, film.getId());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    /**
     * Sets the films and their status information
     * @param newFilms The films and status information
     */
    public void setFilms(List<Pair<Film, FilmStatus>> newFilms) {
        films = newFilms;
        notifyDataSetChanged();
    }

    /**
     * Returns the list of films. Changes to this list are not automatically detected! You need to
     * call the appropriate notify method by yourself!
     * {@link RecyclerView.Adapter#notifyDataSetChanged()}
     * {@link RecyclerView.Adapter#notifyItemChanged(int)}
     * {@link RecyclerView.Adapter#notifyItemInserted(int)}
     * {@link RecyclerView.Adapter#notifyItemMoved(int, int)}
     * {@link RecyclerView.Adapter#notifyItemRangeRemoved(int, int)}
     * @return List of films and their status information
     */
    public List<Pair<Film, FilmStatus>> getFilms() {
        return films;
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
