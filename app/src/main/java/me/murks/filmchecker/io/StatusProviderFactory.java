package me.murks.filmchecker.io;

import java.util.Arrays;
import java.util.Collection;

/**
 * Class for getting {@see IStatusProvider} implementations
 * @author zouroboros
 * @version 15/9/17 0.2
 */
public class StatusProviderFactory {

    /**
     * Returns all available {@see IStatusProvider} implementations
     * @return Collection of IStatusProvider
     */
    public Collection<IStatusProvider> getFilmStatusProvider() {
        return Arrays.asList(new RossmannStatusProvider(),
                new DmStatusProvider(),
                new DmAtStatusProvider());
    }

    /**
     * Get's an {@see IStatusProvider} implementation bs his id
     * @param id The id of the status provider
     * @return A IStatusProvider instance
     */
    public IStatusProvider getStatusProviderById(String id) {
        for(IStatusProvider provider : this.getFilmStatusProvider()) {
            if(provider.getId().equals(id)) {
                return provider;
            }
        }
        throw new IllegalArgumentException("No provider with id:" + id + " found.");
    }

    public IStatusProvider getDmStatusProvider() {
        return new DmStatusProvider();
    }

    public IStatusProvider getRmStatusProvider() {
        return new RossmannStatusProvider();
    }
}
