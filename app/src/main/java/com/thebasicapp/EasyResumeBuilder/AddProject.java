package com.thebasicapp.EasyResumeBuilder;

import java.util.ArrayList;
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

public class AddProject extends BaseActivity implements TimerInterface {

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
		setContentView(R.layout.addproject);



		adView = (AdView) findViewById(R.id.adView);

		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				.addTestDevice(getString(R.string.testdevice)).build();
		adView.setAdListener(new ToastAdListener(AddProject.this, adView));
		adView.loadAd(adRequest);

		Bundle extrasProf = getIntent().getExtras();
		String profilename = "";
		if (extrasProf != null) {
			String idstr = getIntent().getExtras().getString("ID");
			Profid = Integer.parseInt(idstr);
			profileid_string = String.valueOf(Profid);
			Log.d("id sended is", "" + Profid);
			Prof pr = db.getProf(Profid);
			profilename = pr.getProfilename();
		}
		lv = (ListView) findViewById(R.id.listView1);
		final List<Proj> proj = db.getAllProj();

		for (Proj pr : proj) {
			your_array_list.clear();
			String prid = pr.getProfid();
			if (prid.equals(profileid_string)) {
				
				String projectt = pr.getPrTitle();
				if(projectt.length() > 45){
					projectt = projectt.substring(0, 44);
					projectt = projectt+"...";
				}
				Log.e("Project limited", projectt);
				your_array_list.add(new Prof(pr.getPRID(), projectt));
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
				Intent i = new Intent(AddProject.this, Project.class);
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
				Intent i = new Intent(AddProject.this, Project.class);
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
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						AddProject.this);

				// set title
				alertDialogBuilder.setTitle(getResources().getString(
						R.string.do_you_want_delete));

				alertDialogBuilder
						.setMessage(
								getResources()
										.getString(R.string.yes_to_delete_proj))
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
										Proj prj = db.getProj(pos);
										db.deleteAllProj(prj);
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
		sendAnalyticsData("AddProj",profilename);
		adView.resume();
//		setHeading(getString(R.string.projectdetails), getResources().getDrawable((R.drawable.project_ic_w)));
		fromList(getString(R.string.projectdetails), getResources().getDrawable((R.drawable.project_ic_w)));
		your_array_list.clear();
		List<Proj> proj = db.getAllProj();
		Log.v("project by id",db.getAllProjbyId(profileid_string)+"");

		for (Proj pr : proj) {

			String prid = pr.getProfid();
			if (prid.equals(profileid_string)) {
				
				String projectt = pr.getPrTitle();
				if(projectt.length() > 45){
					projectt = projectt.substring(0, 44);
					projectt = projectt+"...";
				}
				your_array_list.add(new Prof(pr.getPRID(), projectt));
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
		Log.v("AddProject", screenTime + "");
	}

	@Override
	protected void onStop() {
		super.onStop();
		timerThread.stopThread();
		//sendAnalyticsScreenTime(screenTime, AddProject.class.getSimpleName(), profilename);

	}
}
