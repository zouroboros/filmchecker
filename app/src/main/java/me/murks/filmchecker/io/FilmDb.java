package me.murks.filmchecker.io;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Collection;
import java.util.LinkedList;

import me.murks.filmchecker.model.Film;

/**
 * Created by mark on 28.05.16.
 */
public class FilmDb extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FilmCheckerApp.db";

    private static final String FILM_TABLE = "films";

    private static final String ORDER_NUMBER_COLUMN = "orderNumber";
    private static final String SHOP_ID_COLUMN = "shopId";

    public FilmDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + FILM_TABLE + " (id integer primary key, "
                + ORDER_NUMBER_COLUMN + " text,"
                + SHOP_ID_COLUMN + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void addFilm(Film film) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ORDER_NUMBER_COLUMN, film.getOrderNumber());
        values.put(SHOP_ID_COLUMN, film.getShopId());

        db.insert(FILM_TABLE, null, values);
        db.close();
    }

    public Collection<Film> getFilms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(FILM_TABLE,
                new String[]{ORDER_NUMBER_COLUMN, SHOP_ID_COLUMN},
                new String(),
                new String[0],
                null,
                null,
                "id desc");
        cursor.moveToFirst();
        Collection<Film> films = new LinkedList<>();
        while(!cursor.isAfterLast()) {
            String orderNumber = cursor.getString(cursor.getColumnIndex(ORDER_NUMBER_COLUMN));
            String shopId = cursor.getString(cursor.getColumnIndex(SHOP_ID_COLUMN));
            films.add(new Film(orderNumber, shopId));
            cursor.moveToNext();
        }
        db.close();
        return films;
    }

    public void deleteFilm(Film film) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FILM_TABLE, ORDER_NUMBER_COLUMN + " = ? and " + SHOP_ID_COLUMN + " = ?",
                new String[]{film.getOrderNumber(), film.getShopId()});
        db.close();
    }
}
