package com.rss_reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rss_reader.data.DBService;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;



public class AddFeed extends Activity{
	
	public  String RSS_URL = null;
	public  String TITLE=null;
	public String ID=null;
	public  DBService dbService=null;
	private  Cursor cursor=null;
	
	private  List<Map<String, Object>> data(){
		
		   List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		   
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("retitle", "�Ϸ���ĩ");
	        map.put("reurl", "http://www.infzm.com/rss/home/rss2.0.xml");
	        list.add(map);
	        
	        map = new HashMap<String,Object>();
	        map.put("retitle", "·͸�������İ�");
	        map.put("reurl","http://cn.reuters.feedsportal.com/CNTopGenNews");
	        list.add(map);
	        
	        map = new HashMap<String, Object>();
	        map.put("retitle", "Engadget�й���");
	        map.put("reurl", "http://cn.engadget.com/rss.xml");
	        list.add(map);
	        
	        map = new HashMap<String,Object>();
	        map.put("retitle", "����԰");
	        map.put("reurl", "http://www.dxy.cn/bbs/rss/2.0/all.xml");
	        list.add(map);
	               
	        map = new HashMap<String, Object>();
	        map.put("retitle", "��̲����");
	        map.put("reurl", "http://blog.sina.com.cn/rss/THEBUND.xml");
	        list.add(map);
	        
	        map = new HashMap<String, Object>();
	        map.put("retitle", "������");
	        map.put("reurl", "http://www.guokr.com/rss/");
	        list.add(map);
	        
	        map = new HashMap<String, Object>();
	        map.put("retitle", "��ͼ־");
	        map.put("reurl", "http://feeds.juetuzhi.cn/");
	        list.add(map);
	        
	        
	        map = new HashMap<String, Object>();
	        map.put("retitle", "����������Щ��");
	        map.put("reurl", "http://www.alibuybuy.com/feed");
	        list.add(map);
	        
	        map = new HashMap<String, Object>();
	        map.put("retitle", "36�");
	        map.put("reurl", "http://www.36kr.com/feed/");
	        list.add(map);
	        
	        map = new HashMap<String, Object>();
	        map.put("retitle", "�����");
	        map.put("reurl", "http://news.ifeng.com/rss/");
	        list.add(map);
	        

	        map = new HashMap<String, Object>();
	        map.put("retitle", "��ѶCDC");
	        map.put("reurl", "http://cdc.tencent.com/?feed=rss2");
	        list.add(map);
	        
	        map = new HashMap<String, Object>();
	        map.put("retitle", "�Ա�UED");
	        map.put("reurl", "http://ued.taobao.com/blog/feed/");
	        list.add(map);
	        	
	        return list;
	        }
	
	OnItemClickListener relistener=new OnItemClickListener(){
		  public void onItemClick(AdapterView parent, View v, int position, long id){
			  
			  List<Map<String, Object>> list=data();
			  
			  Map<String, Object> map =  list.get(position);
			
			  TITLE=map.get("retitle").toString();
			  RSS_URL=map.get("reurl").toString();
			  
			  insertData(dbService,TITLE,RSS_URL);
			  
		  }
	};
   
	
	
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
        setContentView(R.layout.addfeed);
        
        dbService=new DBService(this, "rssdata.db", null, 1);
        
        SimpleAdapter readapter=new SimpleAdapter(AddFeed.this,data(),R.layout.relist,
	   			 new String[]{"retitle"},new int[] {R.id.retitle});
        
   	 	ListView recommendList= ((ListView)findViewById(R.id.recommend));
     
   	 	recommendList.setAdapter(readapter);
   	 	recommendList.setOnItemClickListener(relistener);
   	 	cursor=dbService.query("select _id,title,url from rss_info", null);
     
   	 	Button backbutton = (Button) findViewById(R.id.back);

   		backbutton.setOnClickListener(new Button.OnClickListener() {
   			public void onClick(View v) {
   				
   				Intent data = new Intent(AddFeed.this, RssIndex.class);	  
   		        setResult(RESULT_OK, data);  
   		        finish();
   			}
   		});
   		
   		Button addUrl=(Button)findViewById(R.id.addUrl);
   		addUrl.setOnClickListener(new Button.OnClickListener(){
       	
   			public void onClick(View v){
       		
   				AlertDialog.Builder alert = new AlertDialog.Builder(AddFeed.this);
		 
   				LayoutInflater factory = LayoutInflater.from(AddFeed.this);
			 
   				final View textEntryView = factory.inflate(R.layout.alert_dialog_text_entry, null);

   				alert.setView(textEntryView);  
	   	 
   				alert.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
	  	        	   	   
		        	public void onClick(DialogInterface dialog, int whichButton) {
		        	   			 
		        	   	EditText  input_url=(EditText)textEntryView.findViewById(R.id.input_url);
		        	   	EditText   input_title=(EditText)textEntryView.findViewById(R.id.input_title);
		        	   			
		        	    RSS_URL=input_url.getText().toString();
		        	    TITLE=input_title.getText().toString();
		        	   		        		  
		        	    if(RSS_URL==null || TITLE == null)
		        	   		 Toast.makeText(getParent(), "����������", Toast.LENGTH_SHORT).show();
		        	   	else insertData(dbService,TITLE,RSS_URL);
		        	}
		        });
		        	   	   
		        alert.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
		        	 public void onClick(DialogInterface dialog, int whichButton) {
		        	     	      
		        	 }
		        });
		        	     	   
		        alert.show();
       	
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
}
