package com.thebasicapp.EasyResumeBuilder;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import Helper.Constants;
import Helper.UIHelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class SettingsActivity extends BaseActivity implements TimerInterface{

    private AdView adView;
    private RadioGroup rdDateFormat;
    private Context context;
    String idstr;
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings);
        adView = (AdView) findViewById(R.id.adView);
        rdDateFormat = (RadioGroup) findViewById(R.id.rgdateformat);
        AdRequest adRequest = new AdRequest.Builder()

                .build();
        adView.setAdListener(new ToastAdListener(SettingsActivity.this, adView));
        adView.loadAd(adRequest);
        context = this;
        selectCheckedRadiobtn();

//        Bundle extras = getIntent().getExtras();
        Intent intent = getIntent();
        if (intent != null) {
            Profid = getIntent().getIntExtra("ID",0);
//            Profid = Integer.parseInt(idstr);
            Log.d("id sended is", "" + Profid);
            Prof pr = db.getProf(Profid);
            profilename = pr.getProfilename();
            // id = id+1;
        }

        rdDateFormat.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId == R.id.dd_mmm_yyyy) {
                    UIHelper.saveData(0, Constants.DATE_FORMAT, context);
                } else if (checkedId == R.id.dd_mm_yyyy) {
                    UIHelper.saveData(1, Constants.DATE_FORMAT, context);
                } else if (checkedId == R.id.mm_dd_yyyy) {
                    UIHelper.saveData(2, Constants.DATE_FORMAT, context);
                } else if (checkedId == R.id.yyyy_mm_dd) {
                    UIHelper.saveData(3, Constants.DATE_FORMAT, context);
                }
            }
        });
    }


    @Override
    protected void onDestroy() {

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
        sendAnalyticsData("Settings", profilename);
        adView.resume();
        fromsettinsgs(getString(R.string.action_settings), getResources().getDrawable((R.drawable.ic_settings)));
        timerThread = new TimerThread();
        timerThread.setTimerInterface(this);
    }

    private void selectCheckedRadiobtn() {
        int val = UIHelper.getSaveData(Constants.DATE_FORMAT, context);
        for (int i = 0; i < rdDateFormat.getChildCount(); i++) {
            RadioButton rButton = (RadioButton) rdDateFormat.getChildAt(i);

            if (i == val) {
                rButton.setChecked(true);
                return;
            }

        }
    }

    int screenTime = 0;

    @Override
    public void onStop(int count) {
        screenTime = count;
        Log.v("SettingsActivity", screenTime + "");
    }

    @Override
    protected void onStop() {
        super.onStop();
        timerThread.stopThread();
        //sendAnalyticsScreenTime(screenTime, SettingsActivity.class.getSimpleName(), profilename);

    }
}
