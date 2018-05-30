package com.thebasicapp.EasyResumeBuilder;

import java.util.List;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class Splash extends Activity {

    DatabaseHandler db = new DatabaseHandler(this);
    Context context;

//    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        context = this;
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

}
