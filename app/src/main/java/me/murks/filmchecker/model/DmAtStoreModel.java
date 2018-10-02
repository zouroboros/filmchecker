package me.murks.filmchecker.model;

import com.google.common.collect.Sets;

import java.util.Calendar;
import java.util.Set;

import me.murks.filmchecker.FilmCheckerApp;
import me.murks.filmchecker.R;

/**
 * Implementation of the {@link StoreModel} interface for austrian dm stores
 * @author zouroboros
 * @date 3/14/18.
 */
public class DmAtStoreModel implements StoreModel {

    private final FilmCheckerApp app;

    public static String StoreId = "dm.at";

    public DmAtStoreModel(FilmCheckerApp app) {
        this.app = app;
    }

    @Override
    public Film getFilm(String shopId, String htNumber, String orderNumber, Calendar date) {
        return new Film(orderNumber, shopId, date, StoreId, null, null);
    }

    @Override
    public int getStoreName() {
        return R.string.dmAtUiName;
    }

    @Override
    public boolean needsRmStoreLocator() {
        return false;
    }

    @Override
    public Set<String> getRequiredFields() {
        return Sets.newHashSet(StoreModel.shopId, StoreModel.orderNumber);
    }

    @Override
    public int getShopIdFieldName() {
        return R.string.add_film_shop_id;
    }

    @Override
    public String getStoreId() {
        return StoreId;
    }
}
