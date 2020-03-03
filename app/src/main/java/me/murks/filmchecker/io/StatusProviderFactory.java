package me.murks.filmchecker.io;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import me.murks.filmchecker.model.BipaAtStoreModel;
import me.murks.filmchecker.model.CeweStoreModel;
import me.murks.filmchecker.model.DmAtStoreModel;
import me.murks.filmchecker.model.DmDeStoreModel;
import me.murks.filmchecker.model.MuellerAtStoreModel;
import me.murks.filmchecker.model.MuellerDeStoreModel;
import me.murks.filmchecker.model.RmStoreModel;

/**
 * Class for getting {@see IStatusProvider} implementations
 * @author zouroboros
 * @version 15/9/17 0.2
 */
public class StatusProviderFactory {

    private final Map<String, IStatusProvider> statusProvider;

    public StatusProviderFactory() {
        statusProvider = new TreeMap<>();
        statusProvider.put(DmDeStoreModel.StoreId, new DmStatusProvider());
        statusProvider.put(RmStoreModel.StoreId, new RossmannStatusProvider());
        statusProvider.put(DmAtStoreModel.StoreId, new DmAtStatusProvider());
        statusProvider.put(MuellerAtStoreModel.StoreId, new MuellerAtStatusProvider());
        statusProvider.put(MuellerDeStoreModel.StoreId, new MuellerDeStatusProvider());
        statusProvider.put(BipaAtStoreModel.StoreId, new BipaAtStatusProvider());
        statusProvider.put(CeweStoreModel.StoreId,new CeweStatusProvider());
    }

    /**
     * Returns all available {@see IStatusProvider} implementations
     * @return Collection of IStatusProvider
     */
    public Collection<IStatusProvider> getFilmStatusProvider() {
        return statusProvider.values();
    }

    /**
     * Get's an {@see IStatusProvider} implementation by his id
     * @param id The id of the status provider
     * @return A IStatusProvider instance
     */
    public IStatusProvider getStatusProviderById(String id) {
        return statusProvider.get(id);
    }
}
