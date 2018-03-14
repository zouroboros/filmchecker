package me.murks.filmchecker.model;

import com.google.common.collect.Sets;

import java.util.Calendar;
import java.util.Set;

import me.murks.filmchecker.FilmCheckerApp;
import me.murks.filmchecker.R;

/**
 * @author zouroboros
 * @date 3/14/18.
 */

public class RmStoreModel implements StoreModel {

    private final FilmCheckerApp app;
    private RmQueryModel rmQueryModel;

    public RmStoreModel(FilmCheckerApp app) {
        this.app = app;
    }

    @Override
    public Film getFilm(String shopId, String htNumber, String orderNumber, Calendar date) {
        return new Film(orderNumber, shopId, date,
                app.getStatusProvider().getRmStatusProvider().getId(),
                rmQueryModel.queryEndpoint, htNumber);
    }

    @Override
    public int getStoreName() {
        return R.string.rossmannUiName;
    }

    @Override
    public boolean needsRmStoreLocator() {
        return true;
    }

    @Override
    public Set<String> getRequiredFields() {
        if(rmQueryModel.htNumber) {
            return Sets.newHashSet(StoreModel.orderNumber, StoreModel.htNumber);
        }
        return Sets.newHashSet(StoreModel.shopId, StoreModel.orderNumber);
    }

    public void setRmQueryModel(RmQueryModel rmQueryModel) {
        this.rmQueryModel = rmQueryModel;
    }
}
