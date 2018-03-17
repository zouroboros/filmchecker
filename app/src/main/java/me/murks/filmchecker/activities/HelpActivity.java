package me.murks.filmchecker.activities;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import me.murks.filmchecker.R;

public class HelpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        WebView web = (WebView) findViewById(R.id.helpWebView);
        web.loadUrl("file:///android_asset/help/help_de.html");
    }


}
