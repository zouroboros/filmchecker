package me.murks.filmchecker.io;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by mark on 01.06.16.
 */
public class StatusProviderFactory {
    public Collection<IStatusProvider> getFilmStatusProvider() {
        return Arrays.<IStatusProvider>asList(new RossmannStatusProvider(),
                new DmStatusProvider());
    }

    public IStatusProvider getStatusProviderById(String id) {
        for(IStatusProvider provider : this.getFilmStatusProvider()) {
            if(provider.getId().equals(id)) {
                return provider;
            }
        }
        throw new IllegalArgumentException("No provider with id:" + id + " found.");
    }
}
