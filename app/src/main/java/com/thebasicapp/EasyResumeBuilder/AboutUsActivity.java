package com.thebasicapp.EasyResumeBuilder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.thebasicapp.EasyResumeBuilder.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class AboutUsActivity extends Activity implements
		OnClickListener, TimerInterface {

	WebView tvAboutUs;
	String noInterneText = "";
	private AdView mAdView;
	protected TimerThread timerThread;
	//protected FirebaseAnalytics mFirebaseAnalytics;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// TODO Auto-generated method stub
		// AboutUsActivity.this.setTitle(getString(R.string.text_about_us));
		setContentView(R.layout.about_us_frag);
		//mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


		//ActionBar actionBar = getSupportActionBar();
		//actionBar.setHomeButtonEnabled(true);
		//actionBar.setDisplayHomeAsUpEnabled(true);

		mAdView = (AdView) findViewById(R.id.adView);

		AdRequest adRequest = new AdRequest.Builder().
				addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				.addTestDevice(getString(R.string.testdevice)).build();
		mAdView.setAdListener(new ToastAdListener(AboutUsActivity.this, mAdView));
		mAdView.loadAd(adRequest);
		noInterneText = getString(R.string.text_no_internet);

		Point size = new Point();
		this.getWindowManager().getDefaultDisplay().getSize(size);

		tvAboutUs = (WebView) findViewById(R.id.tvAboutUs);
		String text = getString(R.string.about_us);
		String youtContentStr = String
				.valueOf(Html
						.fromHtml("<![CDATA[<body style=\"text-align:justify;color:#0000; \">"
								+ text + "</body>]]>"));

		tvAboutUs.loadData(youtContentStr, "text/html", "utf-8");
		// tvAboutUs.setTextSize(percent * size.x*
		// getResources().getDisplayMetrics().density);
		findViewById(R.id.btnFacebook).setOnClickListener(this);
		findViewById(R.id.btnTwitter).setOnClickListener(this);

		findViewById(R.id.btnFriend).setOnClickListener(this);
		findViewById(R.id.btnContactus).setOnClickListener(this);
		findViewById(R.id.btnMore).setOnClickListener(this);
		findViewById(R.id.btnWriteViwe).setOnClickListener(this);
		findViewById(R.id.btnWeb).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnFacebook:
			if (isNetworkAvailable()) {
				Intent facebook_intent = new Intent(
						Intent.ACTION_VIEW,
						Uri.parse(getString(R.string.facebook_page_link)));
				startActivity(facebook_intent);
			} else {
				Toast.makeText(AboutUsActivity.this, noInterneText,
						Toast.LENGTH_LONG).show();
			}

			break;
		case R.id.btnTwitter:
			if (isNetworkAvailable()) {
				Intent twitter_intent = new Intent(
						Intent.ACTION_VIEW,
						Uri.parse(getString(R.string.tiwiter_page_link)));
				startActivity(twitter_intent);
			} else {
				Toast.makeText(AboutUsActivity.this, noInterneText,
						Toast.LENGTH_LONG).show();
			}
			break;

		case R.id.btnFriend:
			if (isNetworkAvailable()) {
				String bodyText = getString(R.string.text_body);
				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				sharingIntent
						.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						bodyText);
				startActivity(Intent.createChooser(sharingIntent,
						getResources().getString(R.string.share_using)));
			} else {
				Toast.makeText(AboutUsActivity.this, noInterneText,
						Toast.LENGTH_LONG).show();
			}
			break;

		case R.id.btnContactus:
			if (isNetworkAvailable()) {
				Intent emailIntent = new Intent(
						Intent.ACTION_SENDTO,
						Uri.fromParts("mailto", 
								getString(R.string.send_email_id), null));
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
				startActivity(Intent
						.createChooser(emailIntent, getString(R.string.send_email_)));
			}
			break;

		case R.id.btnMore:
			try {
				startActivity(new Intent(Intent.ACTION_VIEW,
						Uri.parse(getString(R.string.allappslink))));
			} catch (android.content.ActivityNotFoundException anfe) {
				startActivity(new Intent(
						Intent.ACTION_VIEW,
						Uri.parse(getString(R.string.write_reviwe))));
			}

			break;

		case R.id.btnWriteViwe:
			String appPackageName = this.getPackageName();
			try {
				startActivity(new Intent(Intent.ACTION_VIEW,
						Uri.parse(getString(R.string.allappslink) + appPackageName)));
			} catch (android.content.ActivityNotFoundException anfe) {
				startActivity(new Intent(
						Intent.ACTION_VIEW,
						Uri.parse(getString(R.string.allappsweblink)
								+ appPackageName)));
			}
			break;

		case R.id.btnWeb:
			if (isNetworkAvailable()) {
				Intent twitter_intent = new Intent(Intent.ACTION_VIEW,
						Uri.parse(getString(R.string.web_link)));
				startActivity(twitter_intent);
			} else {
				Toast.makeText(AboutUsActivity.this, noInterneText,
						Toast.LENGTH_LONG).show();
			}
			break;
		}
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		//overridePendingTransition(R.anim.slide_in_from_left,
			//	R.anim.slide_out_to_right);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
		}
		return true;
	}

	@Override
	protected void onPause() {
		mAdView.pause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mAdView.resume();
		timerThread = new TimerThread();
		timerThread.setTimerInterface(this);
	}

	@Override
	protected void onDestroy() {
		mAdView.destroy();
		super.onDestroy();
	}

	int screenTime = 0;

	@Override
	public void onStop(int count) {
		screenTime = count;
		Log.v("MainActivty", screenTime + "");
	}

	@Override
	protected void onStop() {
		super.onStop();
		timerThread.stopThread();

	}
}
