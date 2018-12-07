package com.example.bryan.minnanokanji;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

public class WebPage extends AppCompatActivity {
    private String url = "https://www.erin.ne.jp/es/";
    private WebView web;
    private MixpanelAPI mixpanel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_page);

        mixpanel = MixpanelAPI.getInstance(this,"4b4d6b196066ba9a609c13fb5a11360c");
        mixpanel.track("Ventana de página web de la embajada de Japón",null); //Realiza la actividad de mixpanel
        mixpanel.flush();

        web = findViewById(R.id.paginaJapon);
        web.setWebViewClient(new MyAppWebViewClient());

        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web.loadUrl(url);

    }

    /*public void onDestroy() {

        super.onDestroy();
    }*/

    public void cerrarVentanaClicked(View view){
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (web.canGoBack()) {
                        web.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    public class MyAppWebViewClient extends WebViewClient{

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
