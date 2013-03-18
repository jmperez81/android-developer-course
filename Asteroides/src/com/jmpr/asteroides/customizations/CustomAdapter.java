package com.jmpr.asteroides.customizations;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmpr.asteroides.R;

public class CustomAdapter extends BaseAdapter {

	private final Activity activity;
	private final List<String> list;

	public CustomAdapter(Activity activity, List<String> list) {
		super();
		this.activity = activity;
		this.list = list;
	}

	/**
	 * Builds and returns a new view Object using the associated layout for the
	 * object in position specified as parameter. Optionally the base view
	 * convertView can be used for the view generation.
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = activity.getLayoutInflater();
		View view = inflater.inflate(R.layout.listviewelement, null, true);
		
		// Builds the TextView
		TextView textView = (TextView) view.findViewById(R.id.listViewText);
		textView.setText(list.get(position));
		
		// Builds the ImageView
		ImageView imageView = (ImageView) view.findViewById(R.id.icon);
		switch (Math.round((float) Math.random() * 3)) {
		case 0:
			imageView.setImageResource(R.drawable.asteroid1);
			break;
		case 1:
			imageView.setImageResource(R.drawable.asteroid2);
			break;
		default:
			imageView.setImageResource(R.drawable.asteroid3);
			break;
		}
		return view;
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}
}
