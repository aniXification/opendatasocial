package com.aniXification.opendatasocial;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aniXification.opendatasocial.utils.Text;
import com.aniXification.opendatasocial.utils.Utilities;

public class StarterActivity extends Activity implements OnClickListener{

	WebView webViewMapNepal;
	Button btnStat, btnHdi, btnCharts, btnProjects,btnAppInfo;
	ProgressBar pb;
	
    @SuppressLint("SetJavaScriptEnabled")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starter);
        
        webViewMapNepal = (WebView) findViewById(R.id.webView_map_nepal);
        btnStat = (Button) findViewById(R.id.btn_stats);
        btnHdi = (Button) findViewById(R.id.btn_hdi);
        btnCharts = (Button) findViewById(R.id.btn_charts);
        btnProjects = (Button) findViewById(R.id.btn_projects);
        btnAppInfo = (Button) findViewById(R.id.btn_app_info);
        pb = (ProgressBar) findViewById(R.id.progressbar); 
        
        btnStat.setOnClickListener(this);
        btnHdi.setOnClickListener(this);
        btnCharts.setOnClickListener(this);
        btnProjects.setOnClickListener(this);
        btnAppInfo.setOnClickListener(this);

        if(Utilities.checkInternetConnection(getApplicationContext()) == true){
        	webViewMapNepal.getSettings().setJavaScriptEnabled(true);
            
            webViewMapNepal.getSettings().setLoadWithOverviewMode(true);
            //webViewMapNepal.getSettings().setUseWideViewPort(true);
            webViewMapNepal.setWebViewClient(new MyWebViewClient());
            webViewMapNepal.setWebChromeClient(new MyWebChromeClient());
            //webViewMapNepal.addJavascriptInterface(new JavaScriptInterface(this), "Android");
            webViewMapNepal.loadData(Text.URL_UNDP_NEPAL_MAP, "text/html", "utf-8");
            	
            webViewMapNepal.setWebViewClient(new WebViewClient(){
            	        	
            	@Override
            	public void onPageFinished(WebView view, String url) {
            		super.onPageFinished(view, url);
            		System.out.println("onPageFinished");
            		pb.setVisibility(View.GONE);
            	}
            	
            });
     	} else {
     		Toast.makeText(this, "No network available.", Toast.LENGTH_SHORT).show();
     		pb.setVisibility(View.GONE);
     		
     	}

    }
    
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
        
    	  @Override
    	     public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
    	         Log.d(this.getClass().getName(), message);
    	         new AlertDialog.Builder(view.getContext()).setMessage(message).setCancelable(true).show();
    	         result.confirm();
    	         return true;
    	     }
    	 
    }
    
    public class JavaScriptInterface {
        Context mContext;
    
        JavaScriptInterface(Context c) {
            mContext = c;
        }
         
        public void closeMyActivity() {
            finish();
        }
         
    }

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		
		case R.id.btn_stats:
			Log.i(this.getClass().getName(), "STAT");
			
			if(Utilities.checkInternetConnection(this) == true){
				Intent statsINtent = new Intent(StarterActivity.this, StatsActivity.class);
				startActivity(statsINtent);
			} else {
				Toast.makeText(this, "No Network", Toast.LENGTH_SHORT).show();
			}
			
			break;
		
		case R.id.btn_hdi:
			Log.i(this.getClass().getName(), "HDI");
			if(Utilities.checkInternetConnection(this) == true){
				Intent hdiIntent = new Intent(StarterActivity.this, HdiActivity.class);
				startActivity(hdiIntent);
			} else {
				Toast.makeText(this, "No Network", Toast.LENGTH_SHORT).show();
			}
			
			break;
			
		case R.id.btn_charts:
			Log.i(this.getClass().getName(), "CHARTS");
			if(Utilities.checkInternetConnection(this) == true){
				Intent chartsIntent = new Intent(StarterActivity.this, ChartsActivity.class);
				startActivity(chartsIntent);
			} else {
				Toast.makeText(this, "No Network", Toast.LENGTH_SHORT).show();
			}
	
			break;
	
		case R.id.btn_projects:
			Log.i(this.getClass().getName(), "PROJECTS");
			if(Utilities.checkInternetConnection(this) == true){
				Intent projectsIntent = new Intent(StarterActivity.this, ProjectsActivity.class);
				startActivity(projectsIntent);
			} else {
				Toast.makeText(this, "No Network", Toast.LENGTH_SHORT).show();
			}
			break;
			
		case R.id.btn_app_info:
			Log.i(this.getClass().getName(), "APP INFO");
			Intent appInfoIntent = new Intent(StarterActivity.this, AppInfoActivity.class);
			startActivity(appInfoIntent);
			break;

		default:
			break;
		}
		
	}
}
