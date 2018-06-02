package com.example.laptop.sketchtaku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class Newstab extends AppCompatActivity {
    WebView news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newstab);

        news = (WebView) findViewById(R.id.newsview);
        news.loadUrl("https://otakumode.com/news/anime");

        WebSettings webSettings = news.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }

    @Override
    public void onBackPressed() {
        if (news.canGoBack()) {
            news.goBack();
        } else {
            super.onBackPressed();

        }
    }
}
