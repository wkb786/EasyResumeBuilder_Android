package com.thebasicapp.EasyResumeBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.thebasicapp.EasyResumeBuilder.R;

import Helper.Constants;
import Helper.UIHelper;

public class AddProfile extends BaseActivity implements TimerInterface{
    DatabaseHandler db = new DatabaseHandler(this);
    Button add;
    private AdView adView;
    int idofprofiletodelete;
    String profilename, prof_id_string;
    public static ArrayList<Prof> your_array_list = new ArrayList<Prof>();
    CustomAdapterlist arrayAdapter;
    ListView lv;
    AlertDialog.Builder builder;
    private Context context;
    protected TimerThread timerThread;
    //Initilize gphotoprint Analytics
    protected FirebaseAnalytics mFirebaseAnalytics;
//    InterstitialAd mInterstitialAd;

//    AdRequest adRequestfullScreen = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//            .addTestDevice("78AA8147493ED07DCD7ED83361C09655")
//            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        context = this;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.addprofile);
        builder = new AlertDialog.Builder(this);
        adView = (AdView) findViewById(R.id.adView);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

      /*  mInterstitialAd = new InterstitialAd(this);
        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));
        mInterstitialAd.loadAd(adRequestfullScreen);
//        showInterstitial();
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }

        });*/

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(getString(R.string.testdevice)).build();
        adView.setAdListener(new ToastAdListener(AddProfile.this, adView));
        adView.loadAd(adRequest);

        add = (Button) findViewById(R.id.addprofile);
        lv = (ListView) findViewById(R.id.listView1);
        final List<Prof> prof = db.getAllProf();
        for (Prof pf : prof) {
            your_array_list.add(new Prof(pf.getPROFID(), pf.getProfilename()));
        }

        arrayAdapter = new CustomAdapterlist(this, R.layout.listitems,
                your_array_list);

        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                int chek = 1;
                String profilename = your_array_list.get(position)
                        .getProfilename();
                int idtosend = your_array_list.get(position).getPROFID();
                String pos = String.valueOf(idtosend);
                Intent i = new Intent(AddProfile.this, MainActivity.class);
                i.putExtra("ID", pos);
                startActivity(i);
                finish();
            }

        });

        lv.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent,
                                           final View view, final int position, long id) {
                // Toast.makeText(getApplicationContext(),"item clicked is "+
                // position + " ", Toast.LENGTH_SHORT).show();

                profilename = your_array_list.get(position).getProfilename();

                idofprofiletodelete = your_array_list.get(position).getPROFID();
                prof_id_string = String.valueOf(idofprofiletodelete);

                final CharSequence[] items = {
                        getResources().getString(R.string.delete),
                        getResources().getString(R.string.edit),
                        getResources().getString(R.string.cancel)};

                builder.setTitle(getResources().getString(
                        R.string.make_selection));
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        // mDoneButton.setText(items[item]);
                        // Toast.makeText(getApplicationContext(), ""+item+"",
                        // Toast.LENGTH_SHORT).show();
                        if (item == 0) {
                            deleteprofile(idofprofiletodelete, position,
                                    profilename);

                        } else if (item == 1) {
                            Intent edit = new Intent(AddProfile.this,
                                    EditProfile.class);
                            edit.putExtra("profile_id", idofprofiletodelete);
                            startActivity(edit);

                        } else if (item == 2) {

                            dialog.cancel();
                        }

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

                return true;
            }
        });

        add.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(AddProfile.this, Profile.class);
                startActivity(i);
                finish();

            }
        });
        if (getIntent() != null) {
            if (getIntent().getStringExtra("toshow").equalsIgnoreCase("0")) {
                int val = UIHelper.getSaveData(Constants.FeedbackCount, context);
                boolean show = (UIHelper.getbooleanData(Constants.Feedback, context));
                if (!show) {
                    if (val == 4) {
                        showAlert();
                    } else if (val == 6) {
                        showAlert();
                    } else {
                        if (!(val == 0 || val == 1 || val == 2 || val == 3)) {
                            showAlert();
                        }
                    }
                }
                val++;
                UIHelper.saveData(val, Constants.FeedbackCount, context);
            }
        }
    }

    private void showAlert() {
        final Dialog dialog1 = new Dialog(AddProfile.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.feedback_alert);
        dialog1.setCancelable(false);
        dialog1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView yes = (TextView) dialog1.findViewById(R.id.btnYes);
        TextView no = (TextView) dialog1.findViewById(R.id.btnNo);

        yes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AppStoreDialog();
                dialog1.dismiss();
            }
        });
        no.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedbackDialog();
                dialog1.dismiss();

            }
        });
        dialog1.show();
    }


    private void AppStoreDialog() {
        final Dialog dialog1 = new Dialog(AddProfile.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.feedback_alert);
        dialog1.setCancelable(false);
        dialog1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView yes = (TextView) dialog1.findViewById(R.id.btnYes);
        TextView no = (TextView) dialog1.findViewById(R.id.btnNo);
        TextView title = (TextView) dialog1.findViewById(R.id.tvText);
        yes.setText(getResources().getString(R.string.ok_sure));
        no.setText(getResources().getString(R.string.no_thanks));
        title.setText(getResources().getString(R.string.rating_appstore));
        yes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button, 
                // to taken back to our application, we need to add following flags to intent. 
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
                }
                UIHelper.saveData(Constants.Feedback, true, context);
                dialog1.dismiss();
            }
        });
        no.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.saveData(Constants.Feedback, true, context);
                dialog1.dismiss();


            }
        });
        dialog1.show();
    }

    private void FeedbackDialog() {
        final Dialog dialog1 = new Dialog(AddProfile.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.feedback_alert);
        dialog1.setCancelable(false);
        dialog1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView yes = (TextView) dialog1.findViewById(R.id.btnYes);
        TextView no = (TextView) dialog1.findViewById(R.id.btnNo);
        TextView title = (TextView) dialog1.findViewById(R.id.tvText);
        yes.setText(getResources().getString(R.string.ok_sure));
        no.setText(getResources().getString(R.string.no_thanks));
        title.setText(getResources().getString(R.string.give_feedback));
        yes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "support@technokeet.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Easy Resume Builder Android � Feedback");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                }
                UIHelper.saveData(Constants.Feedback, true, context);
                dialog1.dismiss();
            }

        });
        no.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.saveData(Constants.Feedback, true, context);
                dialog1.dismiss();


            }
        });
        dialog1.show();
    }

    public void deleteprofile(int id, final int position, String prof) {

        idofprofiletodelete = id;
        profilename = prof;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                AddProfile.this);

        // set title
        alertDialogBuilder.setTitle(getResources().getString(
                R.string.do_you_want_delete));

        // set dialog message
        alertDialogBuilder
                .setMessage(getResources().getString(R.string.yes_to_delete))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                db.deleteAllProf(new Prof(idofprofiletodelete,
                                        profilename));

                                your_array_list.remove(position);
                                arrayAdapter.notifyDataSetChanged();
                                DeleteAlldataofProfile();
                                Toast.makeText(
                                        getApplicationContext(),
                                        getResources().getString(R.string.profiledeleted),
                                        Toast.LENGTH_SHORT).show();
                                List<Prof> prof = db.getAllProf();
                                if (prof.size() != 0) {
                                } else {
                                    Intent i = new Intent(AddProfile.this,
                                            Profile.class);
                                    startActivity(i);
                                    AddProfile.this.finish();
                                }
                            }

                        })
                .setNegativeButton(getResources().getString(R.string.no),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void DeleteAlldataofProfile() {
        // TODO Auto-generated method stub
        final List<Contact> contacts = db.getAllContacts();
        for (Contact ct : contacts) {
            String prid = ct.getProfid();
            if (prid.equals(prof_id_string)) {
                int id = ct.getID();
                String name = ct.getName();
                String dob = ct.getDOB();
                String gender = ct.getGender();
                String address = ct.getAddress();
                String languages = ct.getLanguage();
                String contact = ct.getContact();
                String email = ct.getEmail();
                db.deleteAllContact(new Contact(id, name, gender, dob, address,
                        languages, contact, email, prof_id_string));
                break;
            }
        }

        final List<Educate> educate = db.getAllEducate();
        for (Educate ed : educate) {

            String prid = ed.getProfid();
            if (prid.equals(prof_id_string)) {
                int id = ed.getEDID();
                String degree = ed.getDegree();
                String uni = ed.getUni();
                String school = ed.getSchool();
                String result = ed.getResult();
                String passingyear = ed.getPassingyear();
                Date startDate = ed.getStartDate();
                db.deleteAllEducate(new Educate(id, degree, uni, school,
                        result, passingyear, prof_id_string, startDate));
            }
        }

        final List<Exper> exper = db.getAllExper();
        for (Exper ex : exper) {
            String prid = ex.getProfid();
            if (prid.equals(prof_id_string)) {
                int id = ex.getEXID();
                String company = ex.getCompany();
                String position = ex.getPosition();
                String period = ex.getPeriod();
                String location = ex.getLocation();
                String salary = ex.getSalary();
                String responsibility = ex.getResponsibility();
                Date startDate = ex.getStartDate();
                db.deleteAllExper(new Exper(id, company, position, period,
                        location, salary, responsibility, prof_id_string, startDate));
            }
        }

        final List<Proj> proj = db.getAllProj();
        for (Proj pr : proj) {
            String prid = pr.getProfid();
            if (prid.equals(prof_id_string)) {
                int id = pr.getPRID();
                String prtitle = pr.getPrTitle();
                String prduration = pr.getPrDuration();
                String role = pr.getRole();
                String tsize = pr.getTsize();
                String expertise = pr.getExpertise();
                db.deleteAllProj(new Proj(id, prtitle, prduration, role, tsize,
                        expertise, prof_id_string));

            }
        }

        final List<Refren> refren = db.getAllRefren();
        for (Refren ref : refren) {
            String prid = ref.getProfid();
            if (prid.equals(prof_id_string)) {
                int id = ref.getREFID();
                String refname = ref.getRefname();
                String refdetail = ref.getRefdetail();
                String refcontact = ref.getRefcontact();
                String refemail = ref.getRefemail();
                db.deleteAllRefren(new Refren(id, refname, refdetail,
                        refcontact, refemail, prof_id_string));

            }
        }

        final List<Other> other = db.getAllOthers();
        for (Other ot : other) {
            String prid = ot.getProfid();
            if (prid.equals(prof_id_string)) {
                int id = ot.getOTID();
                String dlicience = ot.getDlicience();
                String passno = ot.getPassno();
                db.deleteAllOther(new Other(id, dlicience, passno,
                        prof_id_string));
            }
        }

        final List<Upload> upload = db.getAllUpload();
        for (Upload up : upload) {
            String prid = up.getProfid();
            if (prid.equals(prof_id_string)) {
                int id = up.getUPID();
                String imagepath = up.getImagepath();
                db.deleteAllUpload(new Upload(id, imagepath, prof_id_string));
            }

        }

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
        sendAnalyticsData("AddProfile",profilename);
        // your_array_list = new ArrayList<Prof>();
        your_array_list.clear();
        final List<Prof> prof = db.getAllProf();
        for (Prof pf : prof) {
            your_array_list.add(new Prof(pf.getPROFID(), pf.getProfilename()));
        }

        arrayAdapter = new CustomAdapterlist(this, R.layout.listitems,
                your_array_list);

        lv.setAdapter(arrayAdapter);

        adView.resume();
        timerThread = new TimerThread();
        timerThread.setTimerInterface(this);
    }

    int screenTime = 0;

    @Override
    public void onStop(int count) {
        screenTime = count;
        Log.v("Add Education", screenTime + "");
    }

    @Override
    protected void onStop() {
        super.onStop();
        timerThread.stopThread();

    }

}
