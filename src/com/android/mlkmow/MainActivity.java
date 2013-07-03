package com.android.mlkmow;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUp();

	}

	private void setUp() {
		Button signInBtn = (Button) findViewById(R.id.signin);
		Button emailBtn = (Button) findViewById(R.id.email);
		Button twitter = (Button) findViewById(R.id.twitter);
		Button faceBtn = (Button) findViewById(R.id.facebook);
		Typeface face = Typeface.createFromAsset(getAssets(),
				"coaster.ttf");
		signInBtn.setTypeface(face);
		emailBtn.setTypeface(face);
		twitter.setTypeface(face);
		faceBtn.setTypeface(face);
	}

}
