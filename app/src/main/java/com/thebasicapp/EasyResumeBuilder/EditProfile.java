package com.thebasicapp.EasyResumeBuilder;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.thebasicapp.EasyResumeBuilder.R;

public class EditProfile extends Activity implements TimerInterface {
	
	int profile_edit_id;
	EditText profile_edit;
	Button update;
	DatabaseHandler db;
	//Initilize gphotoprint Analytics
	//protected FirebaseAnalytics mFirebaseAnalytics;

	protected TimerThread timerThread;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.edit_profile);
		Bundle extrasProf = getIntent().getExtras();
		// Obtain the FirebaseAnalytics instance.
		//mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
		Bundle bundle = new Bundle();
		bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Edit Profile");
		//mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
		if (extrasProf != null) {
			profile_edit_id = getIntent().getExtras().getInt("profile_id");
			
		}
		
		db = new DatabaseHandler(this);
		profile_edit = (EditText)findViewById(R.id.edit_prof_edt);
		update = (Button)findViewById(R.id.update);
		
		Prof pr = db.getProf(profile_edit_id);
		
		String name = pr.getProfilename();
		
		profile_edit.setText(name);
		
		update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				

				// TODO Auto-generated method stub
				String profilname;
				profilname = profile_edit.getText().toString();
				if (profilname.equals("")) {
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.name_is_empty),
							Toast.LENGTH_SHORT).show();
				} else {
					if (isAlphaNumeric(profilname)) {
						//db.addProf(new Prof(profilname));
						
						db.updateProf(new Prof(profile_edit_id, profilname));
						Toast.makeText(
								getApplicationContext(),
								getResources()
										.getString(R.string.prof_updated),
								Toast.LENGTH_SHORT).show();
						EditProfile.this.finish();
						
					} else {

						Toast.makeText(
								getApplicationContext(),
								getResources().getString(
										R.string.valid_profile_name),
								Toast.LENGTH_SHORT).show();
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

	int screenTime = 0;

	@Override
	public void onStop(int count) {
		screenTime = count;
		Log.v("EditProfile", screenTime + "");
	}

	@Override
	protected void onStop() {
		super.onStop();
		timerThread.stopThread();
		//sendAnalyticsScreenTime(screenTime, EditProfile.class.getSimpleName(), "");
	}

	@Override
	protected void onResume() {
		super.onResume();
		timerThread = new TimerThread();
		timerThread.setTimerInterface(this);
	}
}
