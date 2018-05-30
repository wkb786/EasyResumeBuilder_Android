package com.thebasicapp.EasyResumeBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.thebasicapp.EasyResumeBuilder.R;

public class AddExperience extends BaseActivity implements TimerInterface {
	DatabaseHandler db = new DatabaseHandler(this);
	Button add;
	private AdView adView;
	String profileid_string = "";
	CustomAdapterlist arrayAdapter;
	ListView lv;
	final ArrayList<Prof> your_array_list = new ArrayList<Prof>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.addexperience);

		lv = (ListView) findViewById(R.id.listView1);

		adView = (AdView) findViewById(R.id.adView);


		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				.addTestDevice(getString(R.string.testdevice)).build();
		adView.setAdListener(new ToastAdListener(AddExperience.this, adView));
		adView.loadAd(adRequest);

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

		sendAnalyticsData("Add Experience",profilename);
		final List<Exper> exper = db.getAllExperbydate();

		for (Exper ex : exper) {

			String prid = ex.getProfid();
			if (prid.equals(profileid_string)) {
				String comp = ex.getCompany();
				if(comp.length() > 45){
					comp=comp.substring(0, 44);
					comp = comp+"...";
				}
				your_array_list.add(new Prof(ex.getEXID(), comp));
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
				Intent i = new Intent(AddExperience.this, Experience.class);
				i.putExtra("ID", posid);
				i.putExtra("Check", check);
				startActivity(i);
			}

		});

		add = (Button) findViewById(R.id.Add);
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(AddExperience.this, Experience.class);
				int chek = 2;
				String check = String.valueOf(chek);
				i.putExtra("Check", check);
				i.putExtra("ID", profileid_string);
				startActivity(i);
			}
		});

		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent,
					final View view, final int position, long id) {
				// Toast.makeText(getApplicationContext(),"item clicked is "+
				// position + " ", Toast.LENGTH_SHORT).show();
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						AddExperience.this);

				// set title
				alertDialogBuilder.setTitle(getResources().getString(
						R.string.do_you_want_delete));

				// set dialog message
				alertDialogBuilder
						.setMessage(
								getResources()
										.getString(R.string.yes_to_delete_exp))
						.setCancelable(false)
						.setPositiveButton(
								getResources().getString(R.string.yes),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										int pos = your_array_list.get(position)
												.getPROFID();
										Exper exp = db.getExper(pos);
										db.deleteAllExper(exp);
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

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();

				return true;
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
		sendAnalyticsData("AddExp",profilename);
		adView.resume();
		timerThread = new TimerThread();
		timerThread.setTimerInterface(this);
//		setHeading(getString(R.string.experiencedetails), getResources().getDrawable((R.drawable.work_ic_w)));
//		fromForm(getString(R.string.experiencedetails),
//				getResources().getDrawable((R.drawable.work_ic_w)),AddExperience.this);
		fromList(getString(R.string.experiencedetails), getResources().getDrawable((R.drawable.work_ic_w)));
		your_array_list.clear();
		final List<Exper> exper = db.getAllExper();
		if(!exper.isEmpty()) {
			Collections.sort(exper, new CustomComparator());

			final List<Exper> expbydate = db.getAllExperbydate();

			for (Exper ex : exper) {

				String prid = ex.getProfid();
				if (prid.equals(profileid_string)) {
					String comp = ex.getCompany();
					if (comp.length() > 45) {
						comp = comp.substring(0, 44);
						comp = comp + "...";
					}
					your_array_list.add(new Prof(ex.getEXID(), comp));
				}

			}
		}
		/*Collections.sort(exper);
		for (Exper ex : exper) {
			Log.v("Company", ex.getCompany());
			Log.v("time", ex.getStartDate()+"");

		}*/
		arrayAdapter = new CustomAdapterlist(this, R.layout.listitems,
				your_array_list);

		lv.setAdapter(arrayAdapter);
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
