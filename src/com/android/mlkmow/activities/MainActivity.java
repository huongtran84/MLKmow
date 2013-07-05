package com.android.mlkmow.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.android.mlkmow.MlkApplication;
import com.android.mlkmow.R;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class MainActivity extends Activity {
	private MlkApplication app;
	// facebook app id
	private static String APP_ID ="387441644695024";
	 // Instance of Facebook Class
    private Facebook facebook;
    @SuppressWarnings("deprecation")
	private AsyncFacebookRunner mAsyncRunner;
    String FILENAME = "AndroidSSO_data";
    private SharedPreferences mPrefs;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		app = (MlkApplication)getApplication();
		facebook = new Facebook(APP_ID);
        mAsyncRunner = new AsyncFacebookRunner(facebook);
		setUp();

	}

	private void setUp() {
		Button signInBtn = (Button) findViewById(R.id.signin);
		Button emailBtn = (Button) findViewById(R.id.email);
		Button twitter = (Button) findViewById(R.id.twitter);
		Button faceBtn = (Button) findViewById(R.id.facebook);
		Typeface face = Typeface.createFromAsset(getAssets(), "coaster.ttf");
		signInBtn.setTypeface(face);
		emailBtn.setTypeface(face);
		twitter.setTypeface(face);
		faceBtn.setTypeface(face);
		twitter.setOnClickListener(loginTwitterListener);
		
		faceBtn.setOnClickListener(loginFaceBookListener);

	}
	View.OnClickListener loginFaceBookListener = new View.OnClickListener() {
	    @Override
	    public void onClick(View v) {
	            loginToFacebook();
	        }

		
	};
	@SuppressWarnings("deprecation")
	private void loginToFacebook() {
		 mPrefs = getPreferences(MODE_PRIVATE);
		    String access_token = mPrefs.getString("access_token", null);
		    long expires = mPrefs.getLong("access_expires", 0);
		 
		    if (access_token != null) {
		        facebook.setAccessToken(access_token);
		    }
		 
		    if (expires != 0) {
		        facebook.setAccessExpires(expires);
		    }
		 
		    if (!facebook.isSessionValid()) {
		        facebook.authorize(this,
		                new String[] { "email", "publish_stream" },
		                new DialogListener() {
		 
		                    @Override
		                    public void onCancel() {
		                        // Function to handle cancel event
		                    }
		 
		                    @Override
		                    public void onComplete(Bundle values) {
		                        // Function to handle complete event
		                        // Edit Preferences and update facebook acess_token
		                        SharedPreferences.Editor editor = mPrefs.edit();
		                        editor.putString("access_token",
		                                facebook.getAccessToken());
		                        editor.putLong("access_expires",
		                                facebook.getAccessExpires());
		                        editor.commit();
		                    }
		 
		                    @Override
		                    public void onError(DialogError error) {
		                        // Function to handle error
		 
		                    }
		 
		                    @Override
		                    public void onFacebookError(FacebookError fberror) {
		                        // Function to handle Facebook errors
		 
		                    }
		 
		                });
		    }
	}
	OnClickListener loginTwitterListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			beginAuthorization();
			if(app.isAuthorized()){
				Intent intent = new Intent(MainActivity.this,ConfirmRegisterTwitterActivity.class);
				startActivity(intent);
			}
		}
	};
	private void beginAuthorization() {
		Intent intent = new Intent(this, AuthorizationActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
	}

}
