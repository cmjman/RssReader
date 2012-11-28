package com.rss_reader;



import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.view.*;
import android.webkit.*;

public class ActivityShowDescription extends Activity {
	
	private WebView webView;
	
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.showdescription);
		
		String TITLE=null;
		String PUBDATE=null;
		String DESCRIPTION=null;
		String url=null;
		Intent startingIntent = getIntent();
		
		webView=(WebView)findViewById(R.id.web_view);

		if (startingIntent != null) {
			Bundle bundle = startingIntent
					.getBundleExtra("android.intent.extra.rssItem");
			if (bundle == null) {
				TITLE = "不好意思程序出错啦";
			} else {
				TITLE = bundle.getString("title");
					PUBDATE=	 bundle.getString("pubdate");
				
					DESCRIPTION= bundle.getString("description").replace('\n', ' ');
				url=bundle.getString("link");
			}
		} else {
			TITLE = "不好意思程序出错啦";

		}

		TextView atTitle = (TextView)findViewById(R.id.articleTitle);
		atTitle.setText(TITLE);
		
		TextView atPubdate=(TextView)findViewById(R.id.articlePubdate);
		atPubdate.setText(PUBDATE);
		
		TextView atDescription=(TextView)findViewById(R.id.articleDescription);
		atDescription.setText(DESCRIPTION);
		
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(url);
		webView.setInitialScale(10);

		Button backbutton = (Button) findViewById(R.id.back);

		backbutton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}
}
