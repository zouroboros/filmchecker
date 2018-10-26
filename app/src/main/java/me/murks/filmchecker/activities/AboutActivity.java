package me.murks.filmchecker.activities;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import me.murks.filmchecker.R;

/**
 * Activity to show information about FilmChecker
 */
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        WebView web = findViewById(R.id.helpWebView);
        web.loadUrl("file:///android_asset/about/about.html");
    }


}
