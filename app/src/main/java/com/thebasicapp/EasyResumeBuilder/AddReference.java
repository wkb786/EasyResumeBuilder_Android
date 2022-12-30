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

public class AddReference extends BaseActivity implements TimerInterface{

	DatabaseHandler db = new DatabaseHandler(this);
	Button add;
	private AdView adView;
	String profileid_string;
	final ArrayList<Prof> your_array_list = new ArrayList<Prof>();
	CustomAdapterlist arrayAdapter;
	ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.addrefrence);

		adView = (AdView) findViewById(R.id.adView);


		AdRequest adRequest = new AdRequest.Builder()

				.build();
		adView.setAdListener(new ToastAdListener(AddReference.this, adView));
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


		lv = (ListView) findViewById(R.id.listView1);
		final List<Refren> refren = db.getAllRefren();

		for (Refren ref : refren) {

			String prid = ref.getProfid();
			if (prid.equals(profileid_string)) {
				String refrennces = ref.getRefname();
				if(refrennces.length() > 45){
					refrennces=refrennces.subSequence(0, 30).toString();
					refrennces = refrennces+"...";
				}
				your_array_list.add(new Prof(ref.getREFID(), refrennces));
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
				Intent i = new Intent(AddReference.this, Refrence.class);
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
				Intent i = new Intent(AddReference.this, Refrence.class);
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
						AddReference.this);

				alertDialogBuilder.setTitle(getResources().getString(
						R.string.do_you_want_delete));
				alertDialogBuilder
						.setMessage(
								getResources()
										.getString(R.string.yes_to_delete_ref))
						.setCancelable(false)
						.setPositiveButton(
								getResources().getString(R.string.yes),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										int pos = your_array_list.get(position)
												.getPROFID();
										Refren ref = db.getRefren(pos);
										db.deleteAllRefren(ref);
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
		sendAnalyticsData("AddRef",profilename);
		adView.resume();
//		setHeading(getString(R.string.refrencedetails), getResources().getDrawable((R.drawable.refren_ic_w)));
		fromList(getString(R.string.rdetails), getResources().getDrawable((R.drawable.refren_ic_w)));
		your_array_list.clear();
		final List<Refren> refren = db.getAllRefren();

		for (Refren ref : refren) {

			String prid = ref.getProfid();
			if (prid.equals(profileid_string)) {
				String refrennces = ref.getRefname();
				if(refrennces.length() > 45){
					refrennces=refrennces.subSequence(0, 44).toString();
					refrennces = refrennces+"...";
				}
				your_array_list.add(new Prof(ref.getREFID(), refrennces));
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
		Log.v("AddReference", screenTime + "");
	}

	@Override
	protected void onStop() {
		super.onStop();
		timerThread.stopThread();
		//sendAnalyticsScreenTime(screenTime, AddReference.class.getSimpleName(), profilename);

	}
}
