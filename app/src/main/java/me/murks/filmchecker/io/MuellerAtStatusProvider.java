package me.murks.filmchecker.io;

/**
 * Implementation of the {@link IStatusProvider} interface for austrian Müller stores
 * @author zouroboros
 */
class MuellerAtStatusProvider extends MuellerStatusProvider implements IStatusProvider {
    /**
     * Creates a new MuellerAtStatusProvider
     */
    public MuellerAtStatusProvider() {
        super("3241");
    }
}
