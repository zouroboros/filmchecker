package me.murks.filmchecker.io;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;

import me.murks.filmchecker.model.Film;

/**
 * Class for interacting with an SQLite Database for storing and retrieving {@see Film}s
 * @author zouroboros
 * @version 0.1 2016-05-29
 */
public class FilmDb extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "FilmCheckerApp.db";

    private static final String FILM_TABLE = "films";

    private static final String ORDER_NUMBER_COLUMN = "orderNumber";
    private static final String SHOP_ID_COLUMN = "shopId";
    private static final String INSERT_DATE_COLUMN = "insertDate";
    private static final String PROVIDER_COLUMN = "provider";

    /**
     * Constructs a FilmDb for the given {@see Context}
     * @param context The current context
     */
    public FilmDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + FILM_TABLE + " (id integer primary key, "
                + ORDER_NUMBER_COLUMN + " text not null,"
                + SHOP_ID_COLUMN + " text not null,"
                + INSERT_DATE_COLUMN + " long not null,"
                + PROVIDER_COLUMN + " text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 2 && newVersion > 1) {
            long currentTime = System.currentTimeMillis();
            db.execSQL("alter table "+ FILM_TABLE +" add column " + INSERT_DATE_COLUMN
                    + " long not null default "+ currentTime + "");
        }

        if(oldVersion < 3 && newVersion > 2) {
            db.execSQL("alter table "+ FILM_TABLE +" add column " + PROVIDER_COLUMN
                    + " text not null default 'me.murks.filmchecker.io.RossmannStatusProvider'");
        }
    }

    /**
     * Adds a film to the database
     * @param film The film to add
     */
    public void addFilm(Film film) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ORDER_NUMBER_COLUMN, film.getOrderNumber());
        values.put(SHOP_ID_COLUMN, film.getShopId());
        values.put(INSERT_DATE_COLUMN, film.getInsertDate().getTimeInMillis());
        values.put(PROVIDER_COLUMN, film.getStatusProvider());
        db.insert(FILM_TABLE, null, values);
        db.close();
    }

    /**
     * Returns all films in the database
     * @return All films as {@see Collection}
     */
    public Collection<Film> getFilms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(FILM_TABLE,
                new String[]{ORDER_NUMBER_COLUMN, SHOP_ID_COLUMN, INSERT_DATE_COLUMN, PROVIDER_COLUMN},
                "",
                new String[0],
                null,
                null,
                INSERT_DATE_COLUMN +" desc");
        cursor.moveToFirst();
        Collection<Film> films = new LinkedList<>();
        while(!cursor.isAfterLast()) {
            String orderNumber = cursor.getString(cursor.getColumnIndex(ORDER_NUMBER_COLUMN));
            String shopId = cursor.getString(cursor.getColumnIndex(SHOP_ID_COLUMN));
            Calendar insertDate = Calendar.getInstance();
            insertDate.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(INSERT_DATE_COLUMN)));
            String provider = cursor.getString(cursor.getColumnIndex(PROVIDER_COLUMN));
            films.add(new Film(orderNumber, shopId, insertDate, provider));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return films;
    }

    /**
     * Deletes the given film
     * @param film The film to remove from the database
     */
    public void deleteFilm(Film film) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FILM_TABLE, ORDER_NUMBER_COLUMN + " = ? and " + SHOP_ID_COLUMN + " = ?",
                new String[]{film.getOrderNumber(), film.getShopId()});
        db.close();
    }
}
