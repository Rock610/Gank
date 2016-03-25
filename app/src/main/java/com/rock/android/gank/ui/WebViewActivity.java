package com.rock.android.gank.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.rock.android.gank.R;
import com.rock.android.gank.ui.base.ToolbarActivity;

public class WebViewActivity extends ToolbarActivity {

    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_web_view;
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView = (WebView) findViewById(R.id.theWebView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        String url = getIntent().getStringExtra("url");

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        webView.setWebChromeClient(new ChromeClient());

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(!TextUtils.isEmpty(url)){
                    view.loadUrl(url);
                }
                return true;
            }
        });
        webView.loadUrl(url);

    }

    private class ChromeClient extends WebChromeClient {

        @Override public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            }
            else {
                progressBar.setVisibility(View.VISIBLE);
            }
        }


        @Override public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setTitle(title);
        }
    }
}
