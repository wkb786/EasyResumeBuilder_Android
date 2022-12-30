package com.thebasicapp.EasyResumeBuilder;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


import com.google.firebase.analytics.FirebaseAnalytics;
import com.thebasicapp.EasyResumeBuilder.R;

import Helper.UIHelper;

public class Profile extends Activity implements TimerInterface {
	EditText profname;
	Button saveprof;
	String profilename;
	int id;
	private boolean exist = false;
	private Context context;

	private AdView adView;
	DatabaseHandler db = new DatabaseHandler(this);
	//Initilize gphotoprint Analytics
	protected FirebaseAnalytics mFirebaseAnalytics;
	protected TimerThread timerThread;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.profile);
		context=this;
		adView = (AdView) findViewById(R.id.adView);

		// Obtain the FirebaseAnalytics instance.
		mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
		Bundle bundle = new Bundle();
		bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Profile");
		mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);




		AdRequest adRequest = new AdRequest.Builder()

		.build();
		adView.setAdListener(new ToastAdListener(Profile.this, adView));
		adView.loadAd(adRequest);

		profname = (EditText) findViewById(R.id.profilename);
		saveprof = (Button) findViewById(R.id.saveprofile);

		final List<Prof> prof = db.getAllProf();
		if (!prof.equals(null)) {
			for (Prof pf : prof) {
				profilename = pf.getProfilename();
				id = pf.getPROFID();
			}
			// profname.setText(profilename);

		} else {

		}

		saveprof.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String profilname;
				profilname = profname.getText().toString();

				if (profilname.equals("")) {
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.name_is_empty),
							Toast.LENGTH_SHORT).show();
				} else {
					if (isAlphaNumeric(profilname)) {
						ArrayList<Prof> profile_list = AddProfile.your_array_list;
						for (int i = 0; i < profile_list.size(); i++) {
							String same_name = profile_list.get(i)
									.getProfilename();
							if (profilname.equalsIgnoreCase(same_name)) {
								Toast.makeText(getApplicationContext(),
										"Profile Already Exist",
										Toast.LENGTH_SHORT).show();
								exist = true;

							}
							else{
								exist=false;
							}
						}
						if (!exist) {
							db.addProf(new Prof(profilname));
							Toast.makeText(
									getApplicationContext(),
									getResources().getString(
											R.string.profile_added),
									Toast.LENGTH_SHORT).show();
							final List<Prof> prof = db.getAllProf();
							String profiname;
							int idtosend = 0;
							for (Prof pf : prof) {
								profiname = pf.getProfilename();
								idtosend = pf.getPROFID();
							}

							// int idtosend = Integer.parseInt(position);
							String pos = String.valueOf(idtosend);

							Intent i = new Intent(Profile.this,
									MainActivity.class);
							i.putExtra("ID", pos);
							startActivity(i);
							Profile.this.finish();
						}

					} else {

//						Toast.makeText(
//								getApplicationContext(),
//								getResources().getString(
//										R.string.valid_profile_name),
//								Toast.LENGTH_SHORT).show();
						UIHelper.showAlert(context, getResources().getString(
								R.string.valid_profile_name));
						
					}
				}

			}
		});

	}

	public boolean isAlphaNumeric(String s) {
		String pattern = "^[a-zA-Z0-9 ]*$";
		if (s.matches(pattern)) {
			return true;
		}
		return false;
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
		adView.resume();
		timerThread = new TimerThread();
		timerThread.setTimerInterface(this);

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i=new Intent(getApplicationContext(),AddProfile.class);
		i.putExtra("toshow","1");
		startActivity(i);
	}

	int screenTime = 0;

	@Override
	public void onStop(int count) {
		screenTime = count;
		Log.v("Profile", screenTime + "");
	}

	@Override
	protected void onStop() {
		super.onStop();
		timerThread.stopThread();
		//sendAnalyticsScreenTime(screenTime, Profile.class.getSimpleName(), profilename);

	}



}
