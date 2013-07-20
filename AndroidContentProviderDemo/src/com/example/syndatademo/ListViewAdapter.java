package com.example.syndatademo;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.syndatademo.business.model.Staff;

public class ListViewAdapter extends BaseAdapter {

	private Activity activity;
	private List<Staff> data;
	private static LayoutInflater inflater = null;

	public ListViewAdapter(Activity a, List<Staff> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.single_item_listview, null);

		TextView name = (TextView) vi.findViewById(R.id.name_tv);
		TextView address = (TextView) vi.findViewById(R.id.address_tv);

		Staff item = new Staff();
		item = data.get(position);

		// Setting all values in listview
		name.setText(item.getName());
		address.setText(item.getAddress());
		return vi;
	}
}
