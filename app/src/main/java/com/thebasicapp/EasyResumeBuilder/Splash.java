package com.thebasicapp.EasyResumeBuilder;

import java.util.List;
import java.util.Map;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback;
import com.google.android.gms.ads.initialization.AdapterStatus;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class Splash extends Activity {

    DatabaseHandler db = new DatabaseHandler(this);
    Context context;
public static Splash Instance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        context = this;
        Instance=this;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (CheckingPermissionIsEnabledOrNot()) {
                MoveNext();

            } else {
                RequestMultiplePermission();
            }

        } else {
            MoveNext();
        }
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Map<String, AdapterStatus> statusMap = initializationStatus.getAdapterStatusMap();
                for (String adapterClass : statusMap.keySet()) {
                    AdapterStatus status = statusMap.get(adapterClass);

                }
                loadAd();
            }
        });
    }

    void MoveNext() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                final List<Prof> prof = db.getAllProf();
                Intent i;
                if (prof.size() != 0) {
                    i = new Intent(Splash.this, AddProfile.class);
                    i.putExtra("toshow", "0");
                } else {
                    i = new Intent(Splash.this, Profile.class);
                }
                startActivity(i);
                Splash.this.finish();

            }
        }, 3000);
    }

    public boolean CheckingPermissionIsEnabledOrNot() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED;
    }
    private void RequestMultiplePermission() {

        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(Splash.this, new String[]
                {
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, 7);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 7:

                if (grantResults.length > 0) {

                    boolean WriteStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (WriteStorage && ReadStorage) {

                        MoveNext();
                        Toast.makeText(Splash.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        finish();
                        Toast.makeText(Splash.this, "Permission Denied", Toast.LENGTH_LONG).show();

                    }
                } else {
                    finish();
                }

                break;
        }
    }
    //// Admob Int Start
    private AdManagerInterstitialAd interstitialAd;

    public void loadAd() {
        AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();
        AdManagerInterstitialAd.load(
                this,
                "ca-app-pub-6060832157788103/1218371270",
                adRequest,
                new AdManagerInterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull AdManagerInterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        Splash.this.interstitialAd = interstitialAd;


                        //Toast.makeText(Screen_Mirroring_Activity.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        Log.d("TAG", "The ad was dismissed.");

                                        Splash.this.interstitialAd = null;


                                        loadAd();

                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        //Log.d("TAG", "The ad failed to show.");
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        // Screen_Mirroring_Activity.this.interstitialAd = null;
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        Log.d("TAG", "The ad was shown.");
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error

                        interstitialAd = null;


                    }
                });
    }

    public void showInterstitial(Activity activity) {
        // Show the ad if it's ready. Otherwise toast and restart the game.

        if (interstitialAd != null) {
            interstitialAd.show(activity);

        } else {
            loadAd();

        }
    }
}
