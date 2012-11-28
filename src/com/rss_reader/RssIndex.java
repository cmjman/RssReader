package com.rss_reader;

import java.io.FileInputStream;
import java.util.*;

import org.apache.http.util.EncodingUtils;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.*;
import android.app.AlertDialog;
import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.rss_reader.data.*;


public class RssIndex extends Activity{
	  
	
	public  String RSS_URL = null;
	public  String TITLE=null;
	public String ID=null;

	public  DBService dbService=null;
	
	public static final int menu_delete=Menu.FIRST;
	
	private  Cursor cursor=null;
	private CheckBoxCursorAdapter adapter=null;
	private GridView gridView=null;
	
	public void onCreate(Bundle icicle) {
		
			super.onCreate(icicle);
	        setContentView(R.layout.rssindex);
	        dbService=new DBService(this, "rssdata.db", null, 1);
	        
	        Button input_button=(Button)findViewById(R.id.input_button);
	       input_button.setOnClickListener(new Button.OnClickListener(){
	        	
	        	public void onClick(View v){
	        		
	        		Intent intent = new Intent(RssIndex.this,AddFeed.class);
	        	  	startActivityForResult(intent, 1);
	        	     		 
	        	}
	        }
	     );
	        
	        
	      cursor=dbService.query("select _id,title,url from rss_info", null);
	      adapter=new CheckBoxCursorAdapter(this,R.layout.rssurl,
	        		cursor,new String[]{"title"},new int[]{R.id.title},R.id.checkBox,"_id",R.id.logo);

	     gridView=(GridView)findViewById(R.id.rssurls);
	     gridView.setAdapter(adapter);
	      gridView.setOnItemClickListener(new  OnItemClickListener()
	      {
	      
	    	  public void onItemClick(AdapterView parent, View v, int position, long id)
	    	  {
	        	   	Intent intent = new Intent(RssIndex.this,ActivityMain.class);
	        	   	String   URL,TITLE;
	        	   	int  s= cursor.getColumnIndexOrThrow("url");
	        	   	int  t=cursor.getColumnIndexOrThrow("title");
	        	   	int i=cursor.getColumnIndexOrThrow("_id");
	        	   	cursor.moveToPosition(position);
	        	   	 
	        	   	URL=cursor.getString(s);
	        	   	TITLE=cursor.getString(t);
	        	   	ID=cursor.getString(i);
	        	   	Bundle b = new Bundle();
	        	   	b.putString("url", URL);
	        	   	b.putString("title", TITLE);
	        	   	b.putString("_id",ID);
	        	   	b.putInt("position", position);
	       
	        	   	intent.putExtra("android.intent.extra.inputurl", b);
	        	       
	        	        startActivityForResult(intent, 0);
	        	}
	     });
	 }
	
	 public void insertData(DBService dbService,String t,String u){
       
         SQLiteDatabase db = dbService.getWritableDatabase();

         ContentValues values = new ContentValues();
         values.put("title", t);
         values.put("url", u);
         db.insert("rss_info", "_id", values);
         cursor.requery();
    }
	 
	public boolean onCreateOptionsMenu(Menu menu){
		
		super.onCreateOptionsMenu(menu);
		
		//View checkBox=findViewById(R.id.checkBox);
	
		//if(checkBox.getVisibility()==View.INVISIBLE)
			//checkBox.setVisibility(View.VISIBLE);
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		
		switch(item.getItemId()){
			case R.id.menu_delete:
				actionClickMenuItem1();break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void actionClickMenuItem1(){
		SQLiteDatabase db = dbService.getWritableDatabase();
	
		ArrayList<Integer>	array=adapter.getSelectedItems();
		
		Iterator iterator=array.iterator();
		
		while(iterator.hasNext()){
			db.delete("rss_info", "_id=?", new String[]{iterator.next().toString()});
			cursor.requery();
		}
		
		//findViewById(R.id.checkBox).setVisibility(View.INVISIBLE);
	
	}
	
	protected void onActivityResult(int requestCode, int resultCode,  
            Intent data){  
			switch (resultCode){  
			
			case RESULT_OK:  
				cursor.requery();
		}  
	}  
}
