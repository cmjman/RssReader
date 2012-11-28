package com.rss_reader.data;

import java.io.File;
import java.util.ArrayList;  
import android.content.Context;  
import android.content.UriMatcher;
import android.database.Cursor;  
import android.net.Uri;
import android.os.Environment;
import android.view.View;  
import android.view.View.OnClickListener;  
import android.view.ViewGroup;  
import android.widget.CheckBox;  
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;  
 
public class CheckBoxCursorAdapter extends SimpleCursorAdapter {  
    private ArrayList<Integer> selection = new ArrayList<Integer>();   
    private int mCheckBoxId = 0;
    private int mImageViewId=0;
    private String mIdColumn;  
    private CheckBox checkbox=null;
    private ImageView imageview=null;
    private Uri uri=null;
      
    public CheckBoxCursorAdapter(Context context, int layout, Cursor c,  
            String[] from, int[] to, int checkBoxId, String idColumn,int imageViewId) {  
        super(context, layout, c, from, to);  
        mCheckBoxId = checkBoxId;  
        mIdColumn = idColumn;  
        mImageViewId=imageViewId;
    }  
 
    @Override 
    public int getCount() {  
        return super.getCount();  
    }  
 
    @Override 
    public Object getItem(int position) {  
        return super.getItem(position);  
    }  
 
  
    public long getItemId(int position) {  
        return super.getItemId(position);  
    }  
 
  
    public View getView(final int position, View convertView, ViewGroup parent) {  
        View view = super.getView(position, convertView, parent);  
        checkbox = (CheckBox)view.findViewById(mCheckBoxId); 
      // if(checkbox.getVisibility()==View.VISIBLE)
        //	checkbox.setVisibility(View.INVISIBLE);
        checkbox.setOnClickListener(new OnClickListener(){  
     
            public void onClick(View v) {  
                Cursor cursor = getCursor();  
                cursor.moveToPosition(position);  
                int rowId = cursor.getInt(cursor.getColumnIndexOrThrow(mIdColumn));  
                int index = selection.indexOf(rowId);  
                if (index != -1) {    
                    selection.remove(index);    
                } else {    
                    selection.add(rowId);    
                }    
            }  
        });  
        Cursor cursor = getCursor();  
        cursor.moveToPosition(position);  
        int rowId = cursor.getInt(cursor.getColumnIndexOrThrow(mIdColumn));  
        if (selection.indexOf(rowId)!= -1) {    
            checkbox.setChecked(true);    
        } else {    
            checkbox.setChecked(false);    
        }    
        
      imageview=(ImageView)view.findViewById(mImageViewId);
       
      int  id=cursor.getInt(cursor.getColumnIndexOrThrow(mIdColumn));
      uri=Uri.parse("file:///data/data/com.rss_reader/files/"+id+".png");
     // File file=new File(uri.toString());
        	
     // if(file.exists())
        //	uri=Uri.parse("file:///data/data/com.rss_reader/files/null.png");
     
       imageview.setImageURI(uri);
       return view;  
    }  
      
    public ArrayList<Integer> getSelectedItems(){  
        return selection;  
    }  
    
}  