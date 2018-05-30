package com.thebasicapp.EasyResumeBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
//import com.mobfox.adapter.MobFoxExtras;
import com.thebasicapp.EasyResumeBuilder.R;

public class AddEducation extends BaseActivity implements TimerInterface  {

	DatabaseHandler db = new DatabaseHandler(this);
	Button add;
	private AdView adView;
	String profileid_string = "";
	CustomAdapterlist arrayAdapter;
	String noInterneText = "";
	ListView lv;
	final ArrayList<Prof> your_array_list = new ArrayList<Prof>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.addeducation);
		lv = (ListView) findViewById(R.id.listView1);
		final List<Educate> educate = db.getAllEducationByDate();

		adView = (AdView) findViewById(R.id.adView);


		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				.addTestDevice(getString(R.string.testdevice)).build();
		adView.setAdListener(new ToastAdListener(AddEducation.this, adView));
		adView.loadAd(adRequest);
		noInterneText = getString(R.string.text_no_internet);

		Bundle extrasProf = getIntent().getExtras();
		String profilename="";
		if (extrasProf != null) {
			String idstr = getIntent().getExtras().getString("ID");
			Profid = Integer.parseInt(idstr);
			profileid_string = String.valueOf(Profid);
			Log.d("id sended is", "" + Profid);
			Prof pr = db.getProf(Profid);
			profilename = pr.getProfilename();
		}

		for (Educate ed : educate) {

			String prid = ed.getProfid();
			if (prid.equals(profileid_string)) {
				String deg = ed.getDegree();
				if (deg.length() > 45) {
					deg = deg.substring(0, 44);
					deg = deg + "...";
				}
				your_array_list.add(new Prof(ed.getEDID(), deg));
			}

		}

		arrayAdapter = new CustomAdapterlist(this, R.layout.listitems,
				your_array_list);

		lv.setAdapter(arrayAdapter);

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				int chek = 1;
				int pos = your_array_list.get(position).getPROFID();
				String posid = String.valueOf(pos);
				String check = String.valueOf(chek);
				Intent i = new Intent(AddEducation.this, Education.class);
				i.putExtra("ID", posid);
				i.putExtra("Check", check);
				startActivity(i);
			}

		});

		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent,
					final View view, final int position, long id) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						AddEducation.this);

				alertDialogBuilder.setTitle(getResources().getString(
						R.string.do_you_want_delete));
				alertDialogBuilder
						.setMessage(
								getResources().getString(
										R.string.yes_to_delete_edu))
						.setCancelable(false)
						.setPositiveButton(
								getResources().getString(R.string.yes),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, close
										// current activity
										// AddEducation.this.finish();
										int pos = your_array_list.get(position)
												.getPROFID();
										Educate ed = db.getEducate(pos);
										db.deleteAllEducate(ed);
										your_array_list.remove(position);
										arrayAdapter.notifyDataSetChanged();
									}
								})
						.setNegativeButton(
								getResources().getString(R.string.no),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();

				return true;
			}
		});

		add = (Button) findViewById(R.id.Add);
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(AddEducation.this, Education.class);
				int chek = 2;
				String check = String.valueOf(chek);
				i.putExtra("Check", check);
				i.putExtra("ID", profileid_string);
				startActivity(i);
			}
		});
	}

	@Override
	protected void onDestroy() {

		db.close();
		adView.destroy();
		super.onDestroy();
	}

	@Override
	public void onPause() {
		adView.pause();
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		sendAnalyticsData("AddEdu",profilename);
		adView.resume();
//		setHeading(getString(R.string.educationdetails), getResources().getDrawable((R.drawable.edu_ic_w)));
		fromList(getString(R.string.educationdetails), getResources().getDrawable((R.drawable.edu_ic_w)));
		your_array_list.clear();
		List<Educate> educate = db.getAllEducate();
//		Collections.sort(educate,new CustomComparatorEdu()); 
		for (Educate ed : educate) {

			String prid = ed.getProfid();
			if (prid.equals(profileid_string)) {
				String deg = ed.getDegree();
				if (deg.length() > 45) {
					deg = deg.substring(0, 44);
					deg = deg + "...";
				}
				your_array_list.add(new Prof(ed.getEDID(), deg));
			}
		}
		arrayAdapter = new CustomAdapterlist(this, R.layout.listitems,
				your_array_list);

		lv.setAdapter(arrayAdapter);
		timerThread = new TimerThread();
		timerThread.setTimerInterface(this);
	}

	int screenTime = 0;

	@Override
	public void onStop(int count) {
		screenTime = count;
		Log.v("Add Education", screenTime + "");
	}

	@Override
	protected void onStop() {
		super.onStop();
		timerThread.stopThread();

	}
}