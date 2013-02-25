package com.aniXification.opendatasocial;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.aniXification.opendatasocial.utils.Text;

public class ChartsActivity extends Activity {

	WebView webViewStats;
	ProgressBar pb;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charts);
        
        webViewStats = (WebView) findViewById(R.id.webView_nepal_stats);
        pb = (ProgressBar) findViewById(R.id.progressbar); 
        
        webViewStats.getSettings().setJavaScriptEnabled(true);
        
        //loads the WebView completely zoomed out
        webViewStats.getSettings().setLoadWithOverviewMode(true);
        //webViewMapNepal.getSettings().setUseWideViewPort(true);
        webViewStats.setWebViewClient(new MyWebViewClient());
        webViewStats.setWebChromeClient(new MyWebChromeClient());
        webViewStats.addJavascriptInterface(new JavaScriptInterface(this), "Android");
        webViewStats.loadData(Text.URL_UNDP_NEPAL_CHART, "text/html", "utf-8");
        webViewStats.setWebViewClient(new WebViewClient(){
        	
        	@Override
        	public void onPageFinished(WebView view, String url) {
        		super.onPageFinished(view, url);
        		System.out.println("onPageFinished");
        		pb.setVisibility(View.GONE);
        	}
        	
        });
    }

  //customize your web view client to open links from your own site in the 
    //same web view otherwise just open the default browser activity with the URL
    private class MyWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals("aniXification.com")) {
                return false;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }
     
    private class MyWebChromeClient extends WebChromeClient {
        
    	  //display alert message in Web View
    	  @Override
    	     public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
    	         Log.d(this.getClass().getName(), message);
    	         new AlertDialog.Builder(view.getContext())
    	          .setMessage(message).setCancelable(true).show();
    	         result.confirm();
    	         return true;
    	     }
    	 
    }
    
    public class JavaScriptInterface {
        Context mContext;
    
        // Instantiate the interface and set the context 
        JavaScriptInterface(Context c) {
            mContext = c;
        }
         
        //using Javascript to call the finish activity
        public void closeMyActivity() {
            finish();
        }
         
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.stats, menu);
        return true;
    }
}
