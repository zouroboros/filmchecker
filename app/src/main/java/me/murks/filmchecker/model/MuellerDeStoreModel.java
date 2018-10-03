package me.murks.filmchecker.model;

import com.google.common.collect.Sets;

import java.util.Calendar;
import java.util.Set;

import me.murks.filmchecker.R;

/**
 * Implementation of the {@link StoreModel} interface for german Müller stores
 * @author zouroboros
 */
public class MuellerDeStoreModel implements StoreModel {

    public static final String StoreId = "www.mueller.de";

    @Override
    public Film getFilm(String shopId, String htNumber, String orderNumber, Calendar date) {
        return new Film(orderNumber, shopId, date, StoreId, null, null);
    }

    @Override
    public int getStoreName() {
        return R.string.muellerDeUiName;
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
        return R.string.customerNumberLabel;
    }

    @Override
    public String getStoreId() {
        return StoreId;
    }
}
