package com.thebasicapp.EasyResumeBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdActivity;
import com.thebasicapp.EasyResumeBuilder.R;

import Helper.Constants;
import Helper.UIHelper;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends BaseActivity implements TimerInterface {
    DatabaseHandler db = new DatabaseHandler(this);

    String idstr;
    int Check = 0;
    TextView Person_name;
    private AdView adView;
    int chek = 2;
    String profileid_string, resume_name_sended;
    boolean checkdataforprofile = false;
    AsyncTask<Void, Void, Void> resumeTask;
    String nameCheck = "";
    private Context context;
    public static InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.slider);

        context = this;
    /*	mInterstitialAd = new InterstitialAd(this);

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));*/

        mInterstitialAd=new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.

                AdRequest adRequestfullScreen = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .addTestDevice("78AA8147493ED07DCD7ED83361C09655")
                        .build();
                mInterstitialAd.loadAd(adRequestfullScreen);
            }

        });

        AdRequest adRequestfullScreen = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("78AA8147493ED07DCD7ED83361C09655")
                .build();

        mInterstitialAd.loadAd(adRequestfullScreen);


        // Load ads into Interstitial Ads
//        mInterstitialAd.loadAd(adRequestfullScreen);

//        mInterstitialAd.setAdListener(new AdListener() {
//            public void onAdLoaded() {
//                showInterstitial();
//            }
//        });
        int val = UIHelper.getSaveData(Constants.Counter, context);
        if (val % 3 == 0) {
            /*mInterstitialAd.loadAd(adRequestfullScreen);
              mInterstitialAd.setAdListener(new AdListener() {
                  public void onAdLoaded() {
                      showInterstitial();
                  }
              });*/

        }
        val++;
        UIHelper.saveData(val, Constants.Counter, context);
        adView = (AdView) findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(getString(R.string.testdevice)).build();
        adView.setAdListener(new ToastAdListener(MainActivity.this, adView));
        adView.loadAd(adRequest);

        Person_name = (TextView) findViewById(R.id.textviewpersonname);
        Button personal_info, education, project, experience, refrences, otherdetails, uploadimage, generateresume;

        final List<Prof> prof = db.getAllProf();
        for (Prof pf : prof) {
            String log = "Id: " + pf.getPROFID() + " ,Name: "
                    + pf.getProfilename();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idstr = getIntent().getExtras().getString("ID");
            Profid = Integer.parseInt(idstr);
            profileid_string = String.valueOf(Profid);
            Log.d("id sended is", "" + Profid);
            Prof pr = db.getProf(Profid);
            profilename = pr.getProfilename();
            Person_name.setText(profilename);

            // id = id+1;
        }

        uploadimage = (Button) findViewById(R.id.Uploadimage);

        uploadimage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent i = new Intent(MainActivity.this, Uploadimage.class);
                String profileid = String.valueOf(Profid);
                i.putExtra("ID", profileid);
                String check = String.valueOf(chek);
                i.putExtra("Check", check);
                startActivity(i);
            }
        });

        personal_info = (Button) findViewById(R.id.personal_info);
        personal_info.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(MainActivity.this, Personal_info.class);
                String profileid = String.valueOf(Profid);
                String check = String.valueOf(chek);
                i.putExtra("Check", check);
                i.putExtra("ID", profileid);
                startActivity(i);

            }
        });

        project = (Button) findViewById(R.id.project);
        project.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                boolean checkprojects_for_profile = false;
                final List<Proj> proj = db.getAllProj();
                if (proj.size() != 0) {
                    for (Proj ed : proj) {
                        String prid = ed.getProfid();
                        if (prid.equals(profileid_string)) {
                            checkprojects_for_profile = true;
                            break;
                        }

                    }
                }

                Intent i;

                if (checkprojects_for_profile) {
                    i = new Intent(MainActivity.this, AddProject.class);
                    String profileid = String.valueOf(Profid);
                    String check = String.valueOf(chek);
                    i.putExtra("Check", check);
                    i.putExtra("ID", profileid);
                } else {
                    i = new Intent(MainActivity.this, AddProject.class);
                    String profileid = String.valueOf(Profid);
                    String check = String.valueOf(chek);
                    i.putExtra("Check", check);
                    i.putExtra("ID", profileid);
                }
                startActivity(i);
            }
        });

        education = (Button) findViewById(R.id.education);
        education.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                boolean checkeducations_for_profile = false;
                final List<Educate> educate = db.getAllEducate();
                if (educate.size() != 0) {
                    for (Educate ed : educate) {
                        String prid = ed.getProfid();
                        if (prid.equals(profileid_string)) {
                            checkeducations_for_profile = true;
                            break;
                        }

                    }
                }
                Intent i;

                if (checkeducations_for_profile) {
                    String profileid = String.valueOf(Profid);
                    i = new Intent(MainActivity.this, AddEducation.class);
                    String check = String.valueOf(chek);
                    i.putExtra("Check", check);
                    i.putExtra("ID", profileid);
                } else {
                    String profileid = String.valueOf(Profid);
                    i = new Intent(MainActivity.this, AddEducation.class);
                    String check = String.valueOf(chek);
                    i.putExtra("Check", check);
                    i.putExtra("ID", profileid);
                }
                startActivity(i);
            }
        });

        experience = (Button) findViewById(R.id.experience);
        experience.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final List<Exper> exper = db.getAllExper();
                Intent i;

                boolean checkexper_for_profile = false;
                final List<Exper> exp = db.getAllExper();
                if (exp.size() != 0) {
                    for (Exper ex : exp) {
                        String prid = ex.getProfid();
                        if (prid.equals(profileid_string)) {
                            checkexper_for_profile = true;
                            break;
                        }

                    }
                }
                if (checkexper_for_profile) {
                    i = new Intent(MainActivity.this, AddExperience.class);
                    String profileid = String.valueOf(Profid);
                    String check = String.valueOf(chek);
                    i.putExtra("Check", check);
                    i.putExtra("ID", profileid);
                } else {
                    i = new Intent(MainActivity.this, AddExperience.class);
                    String profileid = String.valueOf(Profid);
                    String check = String.valueOf(chek);
                    i.putExtra("Check", check);
                    i.putExtra("ID", profileid);
                }
                startActivity(i);
            }
        });

        refrences = (Button) findViewById(R.id.references);
        refrences.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final List<Refren> refren = db.getAllRefren();
                Intent i;

                boolean checkrefree_for_profile = false;
                if (refren.size() != 0) {
                    for (Refren ref : refren) {
                        String prid = ref.getProfid();
                        if (prid.equals(profileid_string)) {
                            checkrefree_for_profile = true;
                            break;
                        }

                    }
                }

                if (checkrefree_for_profile) {
                    i = new Intent(MainActivity.this, AddReference.class);
                    String profileid = String.valueOf(Profid);
                    String check = String.valueOf(chek);
                    i.putExtra("Check", check);
                    i.putExtra("ID", profileid);
                } else {
                    i = new Intent(MainActivity.this, AddReference.class);
                    String profileid = String.valueOf(Profid);
                    String check = String.valueOf(chek);
                    i.putExtra("Check", check);
                    i.putExtra("ID", profileid);
                }
                startActivity(i);
            }
        });

        otherdetails = (Button) findViewById(R.id.otherdetails);
        otherdetails.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(MainActivity.this, Otherdetails.class);
                String profileid = String.valueOf(Profid);
                String check = String.valueOf(chek);
                i.putExtra("Check", check);
                i.putExtra("ID", profileid);
                startActivity(i);
            }
        });

        generateresume = (Button) findViewById(R.id.generateresume);
        generateresume.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                // TODO Auto-generated method stub
                boolean check = Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED);
                boolean isPresent = Environment.getExternalStorageDirectory()
                        .exists();
                checkdataforprofile = false;
                String profileid = String.valueOf(Profid);
                CheckDatainProfile(profileid);
                Prof pr = db.getProf(Profid);
                String Profilename = pr.getProfilename();
                String filetocreate = Profilename + ".pdf";

                File folder = new File(Environment
                        .getExternalStorageDirectory() + "/Resumes");
                boolean success = true;
                if (!folder.exists()) {
                    success = folder.mkdir();
                }

                File file = new File(folder.getAbsolutePath(), filetocreate);
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        // Toast.makeText(getApplicationContext(), e.toString(),
                        // Toast.LENGTH_SHORT).show();
                    }
                }
                File filecheck = new File(file.getAbsolutePath());
                if (filecheck.exists()) {

                    if (nameCheck.equalsIgnoreCase("")) {
                        /*Toast.makeText(
                                getApplicationContext(),
								"Cannot generate resume without personal information",
								Toast.LENGTH_SHORT).show();*/
                        showAlert(getString(R.string.genate_resume));
                    } else {

                        if (check) {
                            if (checkdataforprofile) {
                                Time today = new Time(Time.getCurrentTimezone());
                                today.setToNow();
                                String day = today.monthDay + ""; // Day of the
                                // month
                                // (1-31)
                                /*
                                 * String month = today.month + ""; int mon =
								 * Integer.parseInt(month); mon = mon + 1;
								 * String year = today.year + ""; // Year String
								 * time = today.format("%k%M%S");
								 */

                                resume_name_sended = Profilename;

								/*
                                 * GenerateResume gresume = new
								 * GenerateResume(); gresume.ctx =
								 * getApplicationContext();
								 * gresume.setId(prof_id_string,
								 * resume_name_sended); gresume.main(null);
								 */
                                final ProgressDialog pd = new ProgressDialog(
                                        MainActivity.this);
                                resumeTask = new AsyncTask<Void, Void, Void>() {

                                    @Override
                                    protected void onPreExecute() {
                                        // TODO Auto-generated method stub
                                        super.onPreExecute();
                                        pd.setMessage(getResources().getString(
                                                R.string.generating));
                                        pd.setCanceledOnTouchOutside(false);
                                        pd.show();
                                    }

                                    @Override
                                    protected Void doInBackground(
                                            Void... params) {
                                        // TODO Auto-generated method stub
                                        GenerateResume gresume = new GenerateResume();
                                        gresume.ctx = getApplicationContext();
                                        String prof_id_string = Integer
                                                .toString(Profid);
                                        gresume.setId(prof_id_string,
                                                resume_name_sended,getString(R.string.still_working));
                                        gresume.main(null);
                                        return null;
                                    }

                                    @Override
                                    protected void onPostExecute(Void result) {
                                        // TODO Auto-generated method stub
                                        super.onPostExecute(result);
                                        if (pd.isShowing()) {
                                            pd.dismiss();
                                        }
                                        Toast.makeText(
                                                getApplicationContext(),
                                                getResources()
                                                        .getString(
                                                                R.string.resume_generated),
                                                Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(
                                                MainActivity.this,
                                                Generate.class);
                                        String profileid = String
                                                .valueOf(Profid);
                                        String check = String.valueOf(chek);
                                        i.putExtra("Check", check);
                                        i.putExtra("ID", profileid);
                                        startActivity(i);
                                    }
                                };
                                resumeTask.execute();

                            } else {
                                showAlert(getString(R.string.nodata));

                            }
                        } else {
                            showAlert(getString(R.string.no_sdcard));

                        }
                    }
                } else {

                    showAlert(getString(R.string.error_file));

                }

            }

        });

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), AddProfile.class);
        i.putExtra("toshow", "1");
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        if (resumeTask != null
                && resumeTask.getStatus() == AsyncTask.Status.RUNNING) {
            resumeTask.cancel(true);
        }
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
//        // Start the timer, executing first event immediately.
//        Message newMsg = mHandler.obtainMessage(MSG_DO_IT);
//        mHandler.sendMessage(newMsg);
        setHeading(getString(R.string.app_name2), getResources().getDrawable((R.drawable.icon_bar)));
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idstr = getIntent().getExtras().getString("ID");
            Profid = Integer.parseInt(idstr);
            profileid_string = String.valueOf(Profid);
            Log.d("id sended is", "" + Profid);
            Prof pr = db.getProf(Profid);
            String profilename = pr.getProfilename();
            Person_name.setText(profilename);

            // id = id+1;
        }

        timerThread = new TimerThread();
        timerThread.setTimerInterface(this);

    }

    private void CheckDatainProfile(String prof_id_string) {
        // TODO Auto-generated method stub
        final List<Contact> contacts = db.getAllContacts();
        for (Contact ct : contacts) {
            String prid = ct.getProfid();
            nameCheck = ct.getName();
            if (prid.equals(prof_id_string)) {
                checkdataforprofile = true;
                break;
            }
        }

        final List<Educate> educate = db.getAllEducate();
        for (Educate ed : educate) {

            String prid = ed.getProfid();
            if (prid.equals(prof_id_string)) {
                checkdataforprofile = true;
                break;
            }
        }

        final List<Exper> exper = db.getAllExper();
        for (Exper ex : exper) {
            String prid = ex.getProfid();
            if (prid.equals(prof_id_string)) {
                checkdataforprofile = true;
                break;
            }
        }

        final List<Proj> proj = db.getAllProj();
        for (Proj pr : proj) {
            String prid = pr.getProfid();
            if (prid.equals(prof_id_string)) {
                checkdataforprofile = true;
                break;

            }
        }

        final List<Refren> refren = db.getAllRefren();
        for (Refren ref : refren) {
            String prid = ref.getProfid();
            if (prid.equals(prof_id_string)) {
                checkdataforprofile = true;
                break;

            }
        }

        final List<Other> other = db.getAllOthers();
        for (Other ot : other) {
            String prid = ot.getProfid();
            if (prid.equals(prof_id_string)) {
                checkdataforprofile = true;
                break;
            }
        }

        final List<Upload> upload = db.getAllUpload();
        for (Upload up : upload) {
            String prid = up.getProfid();
            if (prid.equals(prof_id_string)) {
                checkdataforprofile = true;
                break;
            }

        }

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
        //sendAnalyticsScreenTime(screenTime, MainActivity.class.getSimpleName(), profilename);

    }
}
