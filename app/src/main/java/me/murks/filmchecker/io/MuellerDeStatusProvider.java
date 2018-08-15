package me.murks.filmchecker.io;

import com.google.common.io.CharStreams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import me.murks.filmchecker.model.Film;
import me.murks.filmchecker.model.FilmStatus;

/**
 * Implementation of the {@link IStatusProvider} interface for austrian Müller stores
 * @author zouroboros
 * @date 4/8/18.
 */
public class MuellerDeStatusProvider extends MuellerStatusProvider implements IStatusProvider {

    /**
     * Creates a new MuellerAtStatusProvider
     */
    public MuellerDeStatusProvider() {
        super("3018");
    }
}
