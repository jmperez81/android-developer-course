package com.jmpr.examples.contentproviderreaddemo;

import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog.Calls;
import android.text.format.DateFormat;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Content provider write, we are adding a new call in phone call log
		ContentValues values = new ContentValues();
        values.put(Calls.DATE, new Date().getTime() );
        values.put(Calls.NUMBER, "555555555");
        values.put(Calls.DURATION, "55");
        values.put(Calls.TYPE, Calls.INCOMING_TYPE);
        Uri newElement = getContentResolver().insert(
                        Calls.CONTENT_URI, values);
		
        // Content provider update
        ContentValues values2 = new ContentValues();
        values2.put(Calls.NUMBER, "444444444");
        getContentResolver().update(Calls.CONTENT_URI, values2, "number='555555555'", null);
        
        // Content provider delete
        getContentResolver().delete(Calls.CONTENT_URI, "number='444444444'", null);
        
		// Content provider read
		String[] callType = { "", "incoming", "outcoming", "missed" };
		TextView output = (TextView) findViewById(R.id.output);
		Uri calls = Uri.parse("content://call_log/calls");
		
		// All calls returned if no filters passed
		// Cursor c = managedQuery(calls, null, null, null, null);
		
		// Alternatively, we can "customize" the query
		String[] projection = new String[] {
			       Calls.DATE, Calls.DURATION, Calls.NUMBER, Calls.TYPE };
			String[] argsSelecc = new String[] {"1"};     
			Cursor c = managedQuery(
			       calls,			// ContentProvider URI 
			       projection,		// Columns of interest
			       "type = ?",    	// WHERE
			       argsSelecc,    	// WHERE params
			       "date DESC");  	// Date sort
		
		while (c.moveToNext()) {
			output.append("\n"
					+ DateFormat.format("dd/MM/yy k:mm (",
							c.getLong(c.getColumnIndex(Calls.DATE)))
					+ c.getString(c.getColumnIndex(Calls.DURATION))
					+ ") "
					+ c.getString(c.getColumnIndex(Calls.NUMBER))
					+ ", "
					+ callType[Integer.parseInt(c.getString(c
							.getColumnIndex(Calls.TYPE)))]);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
