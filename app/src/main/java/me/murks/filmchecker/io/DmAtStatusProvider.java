package me.murks.filmchecker.io;

/**
 * Implementation of the {@link IStatusProvider} interface for austrian DM stores
 * @author zouroboros
 * @date 3/17/18.
 */
public class DmAtStatusProvider extends DmStatusProvider {
    public DmAtStatusProvider() {
        super("2976");
    }

    @Override
    public String getId() {
        return "dm.at";
    }
}
