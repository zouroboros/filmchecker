package me.murks.filmchecker.model;

import com.google.common.collect.Sets;

import java.util.Calendar;
import java.util.Set;

import me.murks.filmchecker.R;

/**
 * StoreModel for Rossmann stores in germany
 * @author zouroboros
 */

public class RmStoreModel implements StoreModel {

    public final static String StoreId = "me.murks.filmchecker.io.RossmannStatusProvider";

    private RmQueryModel rmQueryModel;

    @Override
    public Film getFilm(String shopId, String htNumber, String orderNumber, Calendar date) {
        return new Film(orderNumber, shopId, date,
                StoreId,
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

    @Override
    public int getShopIdFieldName() {
        return R.string.shop_id;
    }

    @Override
    public String getStoreId() {
        return StoreId;
    }

    @Override
    public int getStoreUrl() {
        return R.string.rossmannDeTrackingUrl;
    }
}
