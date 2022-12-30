package com.thebasicapp.EasyResumeBuilder;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;

public class FAQActivty extends BaseActivity implements TimerInterface{

    private AdView adView;
    private WebView webview;
    String idstr;
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_faq);
        adView = (AdView) findViewById(R.id.adView);
        webview = (WebView) findViewById(R.id.webView1);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("https://www.technokeet.com/android-easy-resume-builder-faq/");

        Intent intent = getIntent();
        if (intent != null) {
            Profid = getIntent().getIntExtra("ID",0);
            Log.d("id sended is", "" + Profid);
          //  Prof pr = db.getProf(Profid);
          //  profilename = pr.getProfilename();
            // id = id+1;
        }

        //sendAnalyticsData("FAQ", profilename);

        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
               // .addTestDevice(getString(R.string.testdevice))
                .build();
        adView.setAdListener(new ToastAdListener(FAQActivty.this, adView));
        adView.loadAd(adRequest);
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
        adView.resume();
        fromfaq(getString(R.string.faq), getResources().getDrawable((R.drawable.ic_faq)));
        timerThread = new TimerThread();
        timerThread.setTimerInterface(this);
    }

    int screenTime = 0;

    @Override
    public void onStop(int count) {
        screenTime = count;
        Log.v("FAQActivty", screenTime + "");
    }

    @Override
    protected void onStop() {
        super.onStop();
        timerThread.stopThread();
        //sendAnalyticsScreenTime(screenTime, FAQActivty.class.getSimpleName(), profilename);

    }

}
