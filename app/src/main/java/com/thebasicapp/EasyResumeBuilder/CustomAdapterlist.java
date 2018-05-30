package com.thebasicapp.EasyResumeBuilder;

import java.util.ArrayList;
import java.util.Calendar;

import com.thebasicapp.EasyResumeBuilder.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapterlist extends ArrayAdapter<Prof> {
	private ArrayList<Prof> entries;
	private Activity activity;

	public CustomAdapterlist(Activity a, int textViewResourceId,
			ArrayList<Prof> invertedfetch) {
		super(a, textViewResourceId, invertedfetch);
		this.entries = invertedfetch;
		this.activity = a;
	}

	public static class ViewHolder {
		public TextView item1;
		public TextView item2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.listitems, null);
			holder = new ViewHolder();
			holder.item1 = (TextView) v.findViewById(R.id.list_content);
			v.setTag(holder);
		} else
			holder = (ViewHolder) v.getTag();

		final Prof custom = entries.get(position);
		if (custom != null) {
			holder.item1.setText(custom.getProfilename());

		}
		return v;
	}

}
