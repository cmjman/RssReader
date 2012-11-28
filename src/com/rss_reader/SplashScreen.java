package com.rss_reader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;
 
public class SplashScreen extends Activity {

	
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
 
		setContentView(R.layout.splashscreen);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				
				Intent mainIntent = new Intent(SplashScreen.this,
						RssIndex.class);
				SplashScreen.this.startActivity(mainIntent);
				SplashScreen.this.finish();
			}
		}, 2500);
	}
}
