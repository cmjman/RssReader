package com.rss_reader;


import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.*;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.*;
import android.content.Context;

import com.rss_reader.data.RSSFeed;
import com.rss_reader.data.RSSItem;
import com.rss_reader.sax.RSSHandler;

public class ActivityMain extends Activity implements OnItemClickListener
{
	public String RSS_URL = null;
	public String RSS_TITLE=null;
	public String ID=null;
	public int POSITION;
	private RSSFeed feed = null;
	public ConnectivityManager manager;  
	public NetworkInfo networkinfo;  

	public Uri imgUri=null;
	
	public void onCreate(Bundle icicle) {
        
		super.onCreate(icicle);
        setContentView(R.layout.main);
        Intent startingIntent = getIntent();

		if (startingIntent != null) {
			Bundle bundle = startingIntent
					.getBundleExtra("android.intent.extra.inputurl");
			if (bundle != null) {
				RSS_TITLE=bundle.getString("title");
				RSS_URL = bundle.getString("url") ;
				ID=bundle.getString("_id");
				POSITION=bundle.getInt("position");
			}
		}
		
		manager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);    
	        networkinfo = manager.getActiveNetworkInfo(); 
		
	    if (networkinfo == null || !networkinfo.isAvailable()) {    
                Toast.makeText(this, "无网络信号，请检查网络设置", Toast.LENGTH_SHORT).show();  
            }
		feed = getFeed(RSS_URL);
		
		TextView text=(TextView) findViewById(R.id.RssTitle);
		text.setText(RSS_TITLE);
		
		ImageView Rsslogo=(ImageView)findViewById(R.id.RssLogo);
		
		imgUri=Uri.parse("file:///data/data/com.rss_reader/files/"+ID+".png");
		
		Rsslogo.setImageURI(imgUri);
		
	    showListView();
	    
	    Button backbutton = (Button) findViewById(R.id.back);

		backbutton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent data = new Intent(ActivityMain.this, RssIndex.class);
				finish();
			}
		});
    }

  
	private RSSFeed getFeed(String urlString)
    {
    	try
    	{
    	   URL url = new URL(urlString);

           SAXParserFactory factory = SAXParserFactory.newInstance();
           SAXParser parser = factory.newSAXParser();
           XMLReader xmlreader = parser.getXMLReader();
           
           RSSHandler rssHandler = new RSSHandler();
           xmlreader.setContentHandler(rssHandler);

           InputSource is = new InputSource(url.openStream());    
           xmlreader.parse(is);
  
           return rssHandler.getFeed();
    	}
    	catch (Exception ee)
    	{

    		return null;
    	}
    }

    
    private void showListView()
    {
        ListView itemlist = (ListView) findViewById(R.id.itemlist);     
        if (feed == null)
        {
        	setTitle("未连接网络 或者 访问的RSS无效");
        	return;
        }

       SimpleAdapter adapter = new SimpleAdapter(this, feed.getAllItemsForListView(),
       		 R.layout.mainlist, new String[] { RSSItem.TITLE,RSSItem.PUBDATE },
       		 new int[] { android.R.id.text1 , android.R.id.text2});
        
       	itemlist.setAdapter(adapter);
        itemlist.setOnItemClickListener (this); 
        itemlist.setSelection(0);
     }
    
    public void onItemClick(AdapterView parent, View v, int position, long id) 
     {
    	 Intent itemintent = new Intent(this,ActivityShowDescription.class);
         
    	 Bundle b = new Bundle();
    	 b.putString("title", feed.getItem(position).getTitle());
    	 b.putString("description", feed.getItem(position).getDescription());
    	 b.putString("link", feed.getItem(position).getLink());
    	 b.putString("pubdate", feed.getItem(position).getPubDate());	 
    	 try{
    	 	    URL desturl   =   new   URL(feed.getImageUrl()); 
    	     	byte buffer[] = new byte[1024];
    	         DataInputStream dis = new DataInputStream(desturl.openStream());
    	         FileOutputStream os=this.openFileOutput(ID+".png",Activity.MODE_WORLD_WRITEABLE);
    	         int len = -1;
    	         while((len = dis.read(buffer))!=-1){
    	             os.write(buffer, 0, len);
    	         }
    	         os.close();
    	         dis.close();
    	  		}
    	 	 catch(Exception e){
    	 	}
    	itemintent.putExtra("android.intent.extra.rssItem", b);
         startActivityForResult(itemintent, 1);
     }
}
    
     
    
