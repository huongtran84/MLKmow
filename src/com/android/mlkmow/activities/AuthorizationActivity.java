package com.android.mlkmow.activities;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.mlkmow.MlkApplication;
import com.android.mlkmow.R;

public class AuthorizationActivity extends Activity {

	  private MlkApplication app;
	  private WebView webView;
	  
	  private WebViewClient webViewClient = new WebViewClient() {
	    @Override
	    public void onLoadResource(WebView view, String url) {
	      // the URL we're looking for looks like this:
	      // http://otweet.com/authenticated?oauth_token=1234567890qwertyuiop
	      Uri uri = Uri.parse(url);
	      if (uri.getHost().equals("http://hyperpma.com")) {
	        String token = uri.getQueryParameter("oauth_token");
	        if (null != token) {
	          webView.setVisibility(View.INVISIBLE);
	          app.authorized();
	          finish();
	        } else {
	          // tell user to try again 
	        }
	      } else {
	        super.onLoadResource(view, url);
	      }
	    }
	  };

	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    app = (MlkApplication)getApplication();
	    setContentView(R.layout.authorization_view);
	    setUpViews();
	  }
	  
	  @Override
	  protected void onResume() {
	    super.onResume();
	    new Authorization().execute();
	  
	  }
	  

	  private void setUpViews() {
	    webView = (WebView)findViewById(R.id.web_view);
	    webView.setWebViewClient(webViewClient);
	  }
	  public class Authorization extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			  String authURL = app.beginAuthorization();
			    webView.loadUrl(authURL);
			return null;
		}
		  
	  }
}
