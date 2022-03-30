package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBar;
    TextView txtTnC;
    String activityTitle, url;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);



        progressBar = findViewById(R.id.wvProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        initializeFeild();


        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.getSettings().setUseWideViewPort(true);



        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);

                super.onPageFinished(view, url);

            }
        });



        webView.loadUrl(url);
        //"https://armtechnical123.wixsite.com/dharamshalainfo/general"





    }

    public void initializeFeild(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            activityTitle = bundle.getString("activityTitle");
            txtTnC = findViewById(R.id.txtTnC);
            txtTnC.setText(activityTitle);

            url = bundle.getString("url");
        }
        else{
            Toast.makeText(getApplicationContext(), "bundle empty", Toast.LENGTH_SHORT).show();
        }

    }



}