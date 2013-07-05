package com.android.mlkmow;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;
import android.app.Application;

import com.android.mlkmow.authorization.AuthHelper;

public class MlkApplication extends Application {
private AuthHelper authHelper;
private Twitter twitter;
private RequestToken currentRequestToken;

@Override
public void onCreate() {
	super.onCreate();
	authHelper = new AuthHelper(this);
	twitter = new TwitterFactory().getInstance();
    authHelper.configureAuth(twitter);
}
public Twitter getTwitter() {
    return twitter;
  }

  public boolean isAuthorized() {
    return authHelper.hasAccessToken();
  }
  
  public String beginAuthorization() {
    try {
      if (null == currentRequestToken) {
        currentRequestToken = twitter.getOAuthRequestToken();
      }
      return currentRequestToken.getAuthorizationURL();
    } catch (TwitterException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  public boolean authorize(String pin) {
    try {
      AccessToken accessToken = twitter.getOAuthAccessToken(currentRequestToken, pin);
      authHelper.storeAccessToken(accessToken);
      return true;
    } catch (TwitterException e) {
      throw new RuntimeException("Unable to authorize user", e); 
    }
  }

  public void authorized() {
    try {
      AccessToken accessToken = twitter.getOAuthAccessToken();
      authHelper.storeAccessToken(accessToken);
    } catch (TwitterException e) {
      throw new RuntimeException("Unable to authorize user", e); 
    }
    
  }
}
