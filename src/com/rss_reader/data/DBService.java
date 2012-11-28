package com.rss_reader.data;

import android.database.*;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;


public class DBService extends SQLiteOpenHelper{
	
	public void onCreate(SQLiteDatabase db){
	
		db.execSQL("create table if not exists rss_info("
                 + "_id integer  primary key autoincrement,"
              + "title varchar(20),"
                 + "url varchar(20)); "); 
    }
	
	public DBService(Context context, String name, CursorFactory factory,
            int version) {
			super(context, name, factory, version);
		
	}
	
	public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
		
	}
	
	public Cursor query(String sql,String[] args){
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor=db.rawQuery(sql, args);
		return cursor;
	}
}
