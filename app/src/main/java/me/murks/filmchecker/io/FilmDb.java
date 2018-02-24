package me.murks.filmchecker.io;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.net.MalformedURLException;
import java.net.URL;
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
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "FilmCheckerApp.db";

    private static final String FILM_TABLE = "films";

    private static final String ORDER_NUMBER_COLUMN = "orderNumber";
    private static final String SHOP_ID_COLUMN = "shopId";
    private static final String INSERT_DATE_COLUMN = "insertDate";
    private static final String PROVIDER_COLUMN = "provider";
    private static final String RM_ENDPOINT_COLUMN = "rmEndpoint";
    private static final String HT_NUMBER_COLUMN = "htNumber";
    private static final String ID_COLUMN = "id";

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
                + SHOP_ID_COLUMN + " text null,"
                + INSERT_DATE_COLUMN + " long not null,"
                + PROVIDER_COLUMN + " text not null,"
                + RM_ENDPOINT_COLUMN + " text,"
                + HT_NUMBER_COLUMN + " text)"
                );
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

        if(oldVersion < 4 && newVersion > 3) {
            db.beginTransaction();
            db.execSQL("alter table " + FILM_TABLE + " add column " + RM_ENDPOINT_COLUMN + " text;" +
                    "alter table " + FILM_TABLE + " add column " + HT_NUMBER_COLUMN + " text;" +
                    "alter table " + FILM_TABLE + " rename to " + FILM_TABLE + "old;" +
                    "create table " + FILM_TABLE + " (id integer primary key, "
                        + ORDER_NUMBER_COLUMN + " text not null,"
                        + SHOP_ID_COLUMN + " text null,"
                        + INSERT_DATE_COLUMN + " long not null,"
                        + PROVIDER_COLUMN + " text not null,"
                        + RM_ENDPOINT_COLUMN + " text,"
                        + HT_NUMBER_COLUMN + " text);"
                    + "insert into " + FILM_TABLE + "select "
                        + ORDER_NUMBER_COLUMN + " "
                        + SHOP_ID_COLUMN + " "
                        + INSERT_DATE_COLUMN + " "
                        + PROVIDER_COLUMN + " "
                        + RM_ENDPOINT_COLUMN + " "
                        + HT_NUMBER_COLUMN + " from " + FILM_TABLE + "old;"
                    + " drop table " + FILM_TABLE + "old");
            db.setTransactionSuccessful();
            db.endTransaction();
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
        values.put(RM_ENDPOINT_COLUMN, film.getRmEndpoint() != null ? film.getRmEndpoint().toString() : null);
        values.put(HT_NUMBER_COLUMN, film.getHtnumber());
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
                new String[]{ID_COLUMN, ORDER_NUMBER_COLUMN, SHOP_ID_COLUMN, INSERT_DATE_COLUMN, PROVIDER_COLUMN,
                        RM_ENDPOINT_COLUMN, HT_NUMBER_COLUMN},
                "",
                new String[0],
                null,
                null,
                INSERT_DATE_COLUMN +" desc");
        cursor.moveToFirst();
        Collection<Film> films = new LinkedList<>();
        while(!cursor.isAfterLast()) {
            Long id = cursor.getLong(cursor.getColumnIndex(ID_COLUMN));
            String orderNumber = cursor.getString(cursor.getColumnIndex(ORDER_NUMBER_COLUMN));
            String shopId = cursor.getString(cursor.getColumnIndex(SHOP_ID_COLUMN));
            Calendar insertDate = Calendar.getInstance();
            insertDate.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(INSERT_DATE_COLUMN)));
            String provider = cursor.getString(cursor.getColumnIndex(PROVIDER_COLUMN));
            String rmEndpoint = cursor.getString(cursor.getColumnIndex(RM_ENDPOINT_COLUMN));
            String htNumber = cursor.getString(cursor.getColumnIndex(HT_NUMBER_COLUMN));
            try {
                URL endpoint = null;
                if(rmEndpoint != null) {
                    endpoint = new URL(rmEndpoint);
                }
                films.add(new Film(id, orderNumber, shopId, insertDate, provider, endpoint, htNumber));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
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
        db.delete(FILM_TABLE, ID_COLUMN + " = ?",
                new String[]{film.getId().toString()});
        db.close();
    }
}
