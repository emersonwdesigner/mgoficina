package com.mgoficina;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ServidorActivity extends Activity {
	 private WebView mWebView;
	 DataBaseHandler db = new DataBaseHandler(this);
	 
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
	        mWebView = new WebView(this);
	        mWebView.loadUrl("http://appoficina.atwebpages.com/dados?codigo="+db.getCodigo());
	        mWebView.setWebViewClient(new WebViewClient() {
	            public boolean shouldOverrideUrlLoading(WebView view, String url) {
	                view.loadUrl(url);
	                return true;
	            }
	        });
	 
	        this.setContentView(mWebView);
	    }
	 
	    @Override
	    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
	        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
	            mWebView.goBack();
	            return true;
	        }
	        return super.onKeyDown(keyCode, event);
	    }
	}
