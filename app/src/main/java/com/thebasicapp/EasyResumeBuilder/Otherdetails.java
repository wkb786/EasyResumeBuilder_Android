package com.thebasicapp.EasyResumeBuilder;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.thebasicapp.EasyResumeBuilder.R;

public class Otherdetails extends BaseActivity implements TimerInterface{

	EditText Dlicience_ET, Passno_ET;
	static final int DATE_DIALOG_ID = 0;
	Button save, clear, savelanguage;
	private AdView adView;

	private int mYear;
	private int mMonth;
	private int mDay, id;
	DatabaseHandler db = new DatabaseHandler(this);
	String dlicience, passno;
	String languagesadded = "", prof_id_string;
	boolean checkforprofid_other = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.otherdetails);

		adView = (AdView) findViewById(R.id.adView);

		AdRequest adRequest = new AdRequest.Builder()
				//.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				//.addTestDevice(getString(R.string.testdevice))
				.build();
		adView.setAdListener(new ToastAdListener(Otherdetails.this, adView));
		adView.loadAd(adRequest);
		
		Bundle extrasProf = getIntent().getExtras();
		if (extrasProf != null) {
			String idstr = getIntent().getExtras().getString("ID");
			Profid = Integer.parseInt(idstr);
			prof_id_string = String.valueOf(Profid);
			Log.d("id sended is", "" + Profid);
			Prof pr = db.getProf(Profid);
			profilename = pr.getProfilename();
		}



		Dlicience_ET = (EditText) findViewById(R.id.Driving_LicenceEdt);
		Passno_ET = (EditText) findViewById(R.id.Passport_NoEdt);
		save = (Button) findViewById(R.id.Save);

		final List<Other> other = db.getAllOthers();
		if (other.size() != 0) {
			for (Other ot : other) {
				String prid = ot.getProfid();
				if (prid.equals(prof_id_string)) {
					checkforprofid_other = true;
					dlicience = ot.getDlicience();
					passno = ot.getPassno();
					id = ot.getOTID();
					Dlicience_ET.setText(dlicience);
					Passno_ET.setText(passno);
					break;
				}
			}
		}
		Button clear = (Button) findViewById(R.id.Clear);
		clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});

		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});

	}
	
	protected void clearData(){
		Dlicience_ET.setText("");
		Passno_ET.setText("");
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
		sendAnalyticsData("OtherInfo",profilename);
		adView.resume();
//		setHeading(getString(R.string.otherdetails), getResources().getDrawable((R.drawable.others_ic_w)),Otherdetails.this);
		fromForm(getString(R.string.otherdetails), getResources().getDrawable((R.drawable.others_ic_w)), Otherdetails.this);
		timerThread = new TimerThread();
		timerThread.setTimerInterface(this);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(Dlicience_ET.getText().toString().equalsIgnoreCase("")&&
				Passno_ET.getText().toString().equalsIgnoreCase("")){
			if(checkforprofid_other){
				showCancelDialog();
			}else{
			super.onBackPressed();
			}
		}else{
			save();
		}
	}
	
	public void deleterecord(){
		Other exp = db.getOther(id);
		db.deleteAllOther(exp);
	}
	
	private void save(){

		// TODO Auto-generated method stub
		String dlicience="", passno="";

		if (checkforprofid_other) {
			dlicience = Dlicience_ET.getText().toString();
			passno = Passno_ET.getText().toString();
			
			/*if (dlicience.equals("")) {
				Toast.makeText(
						getApplicationContext(),
						getResources().getString(
								R.string.enter_driving_licence_no),
						Toast.LENGTH_SHORT).show();
			} else if (passno.equals("")) {
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.passport_no),
						Toast.LENGTH_SHORT).show();
			} */
			if(dlicience.equals("") && passno.equals("")){
			
				/*Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.other_msg),
						Toast.LENGTH_SHORT).show();*/
				showAlert(getString(R.string.other_msg));
				
			}else {
				db.updateOther(new Other(id, dlicience, passno,
						prof_id_string));

				/*Toast.makeText(
						getApplicationContext(),
						getResources().getString(R.string.info_updated),
						Toast.LENGTH_SHORT).show();*/
				showAlert(getString(R.string.info_updated));
				Otherdetails.this.finish();
			}
		} else {
			dlicience = Dlicience_ET.getText().toString();
			passno = Passno_ET.getText().toString();
			/*if (dlicience.equals("")) {
				Toast.makeText(
						getApplicationContext(),
						getResources().getString(
								R.string.enter_driving_licence_no),
						Toast.LENGTH_SHORT).show();
			} else if (passno.equals("")) {
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.passport_no),
						Toast.LENGTH_SHORT).show();
			} */
			if(dlicience.equals("") && passno.equals("")){
				
				/*Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.other_msg),
						Toast.LENGTH_SHORT).show();*/
				showAlert(getString(R.string.other_msg));
				
			} else {
				db.addOther(new Other(dlicience, passno, prof_id_string));
				/*Toast.makeText(
						getApplicationContext(),
						getResources()
								.getString(R.string.info_inserted),
						Toast.LENGTH_SHORT).show();*/
				showAlert(getString(R.string.info_updated));
				Otherdetails.this.finish();
			}
		}
	}

	int screenTime = 0;

	@Override
	public void onStop(int count) {
		screenTime = count;
		Log.v("Otherdetails", screenTime + "");
	}

	@Override
	protected void onStop() {
		super.onStop();
		timerThread.stopThread();
		//sendAnalyticsScreenTime(screenTime, Otherdetails.class.getSimpleName(), profilename);

	}
}
