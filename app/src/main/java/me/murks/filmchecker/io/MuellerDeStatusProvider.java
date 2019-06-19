package me.murks.filmchecker.io;
/**
 * Implementation of the {@link IStatusProvider} interface for austrian Müller stores
 * @author zouroboros
 */
class MuellerDeStatusProvider extends PhotoPrintitStatusProvider implements IStatusProvider {

    /**
     * Creates a new MuellerAtStatusProvider
     */
    public MuellerDeStatusProvider() {
        super("3018");
    }
}
