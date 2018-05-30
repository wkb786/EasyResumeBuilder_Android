package com.thebasicapp.EasyResumeBuilder;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.thebasicapp.EasyResumeBuilder.R;

public class Refrence extends BaseActivity implements TimerInterface {

    EditText refname_et, refdetail_et, refcontact_et, refemail_et;
    static final int DATE_DIALOG_ID = 0;
    int mYear, mMonth, mDay, id;
    Button save, clear;
    DatabaseHandler db = new DatabaseHandler(this);
    String refname, refdetail, refcontact, refemail;
    String idstr, prof_id_string;
    private AdView adView;
    int Check = 0;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.refrence);
        adView = (AdView) findViewById(R.id.adView);
        
        context = this;
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(getString(R.string.testdevice)).build();
        adView.setAdListener(new ToastAdListener(Refrence.this, adView));
        adView.loadAd(adRequest);

        refname_et = (EditText) findViewById(R.id.NameEdt);
        refdetail_et = (EditText) findViewById(R.id.Refrence_DetailsEdt);
        refcontact_et = (EditText) findViewById(R.id.CellEdt);
        refemail_et = (EditText) findViewById(R.id.EmailEdt);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        Bundle extrasProf = getIntent().getExtras();
        if (extrasProf != null) {
            String chk = getIntent().getExtras().getString("Check");
            Check = Integer.parseInt(chk);
            if (Check != 2) {

            } else {
                String idstr = getIntent().getExtras().getString("ID");
                Profid = Integer.parseInt(idstr);
                prof_id_string = String.valueOf(Profid);
                Log.d("id sended is", "" + Profid);
                Prof pr = db.getProf(Profid);
                profilename = pr.getProfilename();
            }
        }
        final List<Refren> refren = db.getAllRefren();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String chk = getIntent().getExtras().getString("Check");
            Check = Integer.parseInt(chk);
            if (Check != 1) {

            } else {
                idstr = getIntent().getExtras().getString("ID");
                id = Integer.parseInt(idstr);
            }
        }

        if (Check == 1) {
            refname = db.getRefren(id).getRefname();
            refdetail = db.getRefren(id).getRefdetail();
            refcontact = db.getRefren(id).getRefcontact();
            refemail = db.getRefren(id).getRefemail();
            prof_id_string = db.getRefren(id).getProfid();

            refname_et.setText(refname);
            refdetail_et.setText(refdetail);
            refcontact_et.setText(refcontact);
            refemail_et.setText(refemail);
        } else {

        }

        Button clear = (Button) findViewById(R.id.Clear);
        clear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

            }
        });

        save = (Button) findViewById(R.id.Save);
        save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });

    }

    protected void clearData() {
        refname_et.setText("");
        refdetail_et.setText("");
        refcontact_et.setText("");
        refemail_et.setText("");
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
        fromForm(getString(R.string.rdetails), getResources().getDrawable((R.drawable.refren_ic_w)), Refrence.this);
        timerThread = new TimerThread();
        timerThread.setTimerInterface(this);
    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        if (refname_et.getText().toString().equalsIgnoreCase("") &&
                refdetail_et.getText().toString().equalsIgnoreCase("") &&
                refcontact_et.getText().toString().equalsIgnoreCase("") &&
                refemail_et.getText().toString().equalsIgnoreCase("")) {
            if (Check == 1) {
                showCancelDialog();
            } else {
                super.onBackPressed();
            }
        } else {
            save();
        }
    }

    public void deleterecord() {
        Refren ref = db.getRefren(id);
        db.deleteAllRefren(ref);
    }

    private void save() {

        // TODO Auto-generated method stub

        if (Check == 1) {
            refcontact = "";
            refname = refname_et.getText().toString();
            refdetail = refdetail_et.getText().toString();
            refcontact = refcontact_et.getText().toString();
            refemail = refemail_et.getText().toString();
            boolean check = emailValidator(refemail);
            if (!checkMadatoryFields()) {

            } else {
                if (refname.equals("")) {
                    showAlert(getString(R.string.enter_refree_name));
                } else if (refdetail.equals("")) {
                    showAlert(getString(R.string.enter_refree_details));
                } else if (refemail.equals("") && refcontact.equals("")) {
                    showAlert(getString(R.string.enter_refree_email));
                } else {
                    if (refemail.equals("")) {
//                        cancel();
                        db.updateRefren(new Refren(id, refname, refdetail,
                                refcontact, refemail, prof_id_string));
                        showAlert(getString(R.string.info_updated));
                        finish();
                    } else if (refcontact.equals("")) {
                        if (check) {
//                            cancel();
                            db.updateRefren(new Refren(id, refname, refdetail,
                                    refcontact, refemail, prof_id_string));
                            showAlert(getString(R.string.info_updated));
                            finish();
                        } else {
                            showAlert(getString(R.string.email_not_valid));
                        }
                    } else {
                        if (check) {
                            db.updateRefren(new Refren(id, refname, refdetail,
                                    refcontact, refemail, prof_id_string));
                            showAlert(getString(R.string.info_updated));
                            finish();
                        } else {
                            showAlert(getString(R.string.email_not_valid));
                        }
                    }
                }
            }
        } else {
            refcontact = "";
            refname = refname_et.getText().toString();
            refdetail = refdetail_et.getText().toString();
            refcontact = refcontact_et.getText().toString();
            refemail = refemail_et.getText().toString();
            boolean check = emailValidator(refemail);
            if(!checkMadatoryFields()){

            }else {
                if (refname.equals("")) {
                    showAlert(getString(R.string.enter_refree_details));
                } else if (refdetail.equals("")) {
                    showAlert(getString(R.string.enter_refree_details));
                } else if (refemail.equals("") && refcontact.equals("")) {
                    showAlert(getString(R.string.enter_refree_email));
                } else {
                    if (refemail.equals("")) {
//                        cancel();
                        db.addRefrence(new Refren(refname, refdetail,
                                refcontact, refemail, prof_id_string));
                        showAlert(getString(R.string.info_updated));
                        finish();
                    } else if (refcontact.equals("")) {
                        if (check) {
//                            cancel();
                            db.addRefrence(new Refren(refname, refdetail,
                                    refcontact, refemail, prof_id_string));
                            showAlert(getString(R.string.info_updated));
                            finish();
                        } else {
                            showAlert(getString(R.string.email_not_valid));
                        }
                    } else {
                        if (check) {
                            db.addRefrence(new Refren(refname, refdetail,
                                    refcontact, refemail, prof_id_string));
                            showAlert(getString(R.string.info_updated));
                            finish();
                        } else {
                            showAlert(getString(R.string.email_not_valid));
                        }
                    }
                }
            }
        }
    }

    public void cancel() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("");

        // set dialog message
        alertDialogBuilder
                .setMessage(getString(R.string.save_information))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        boolean check = emailValidator(refemail);
                        if (Check == 1) {
                            if (refname.equals("")) {
                                showAlert(getString(R.string.enter_refree_name));
                            } else if (refdetail.equals("")) {
                                showAlert(getString(R.string.enter_refree_details));
                            } else if (refemail.equals("") && refcontact.equals("")) {
                                showAlert(getString(R.string.enter_refree_email));
                            } else {
                                if (refemail.equals("")) {
//                                    cancel();
                                    db.updateRefren(new Refren(id, refname, refdetail,
                                            refcontact, refemail, prof_id_string));
                                    showAlert(getString(R.string.info_updated));
                                    finish();
                                } else if (refcontact.equals("")) {
                                    if (check) {
//                                        cancel();
                                        db.updateRefren(new Refren(id, refname, refdetail,
                                                refcontact, refemail, prof_id_string));
                                        showAlert(getString(R.string.info_updated));
                                        finish();
                                    } else {
                                        showAlert(getString(R.string.email_not_valid));
                                    }
                                } else {
                                    if (check) {
                                        db.updateRefren(new Refren(id, refname, refdetail,
                                                refcontact, refemail, prof_id_string));
                                        showAlert(getString(R.string.info_updated));
                                        finish();
                                    } else {
                                        showAlert(getString(R.string.email_not_valid));
                                    }
                                }
                            }
                        } else {
                            if (refname.equals("")) {
                                showAlert(getString(R.string.enter_refree_details));
                            } else if (refdetail.equals("")) {
                                showAlert(getString(R.string.enter_refree_details));
                            } else if (refemail.equals("") && refcontact.equals("")) {
                                showAlert(getString(R.string.enter_refree_email));
                            } else {
                                if (refemail.equals("")) {
//                                    cancel();
                                    db.addRefrence(new Refren(refname, refdetail,
                                            refcontact, refemail, prof_id_string));
                                    showAlert(getString(R.string.info_updated));
                                    finish();
                                } else if (refcontact.equals("")) {
                                    if (check) {
                                        db.addRefrence(new Refren(refname, refdetail,
                                                refcontact, refemail, prof_id_string));
                                        showAlert(getString(R.string.info_updated));
                                        finish();
                                    } else {
                                        showAlert(getString(R.string.email_not_valid));
                                    }
                                } else {
                                    if (check) {
                                        db.addRefrence(new Refren(refname, refdetail,
                                                refcontact, refemail, prof_id_string));
                                        showAlert(getString(R.string.info_updated));
                                        finish();
                                    } else {
                                        showAlert(getString(R.string.email_not_valid));
                                    }
                                }
                            }
                        }

                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(Check==1){
                            deleterecord();
                        }
                        dialog.cancel();
                        finish();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private boolean checkMadatoryFields() {
        if (refname_et.getText().toString().equalsIgnoreCase("") ||
                refdetail_et.getText().toString().equalsIgnoreCase("") ||
                refemail_et.getText().toString().equalsIgnoreCase("")&&
                refcontact_et.getText().toString().equalsIgnoreCase("")) {
            cancel();
            return false;
        } else {
            return true;
        }
    }

    int screenTime = 0;

    @Override
    public void onStop(int count) {
        screenTime = count;
        Log.v("Refrence", screenTime + "");
    }

    @Override
    protected void onStop() {
        super.onStop();
        timerThread.stopThread();
        //sendAnalyticsScreenTime(screenTime, Refrence.class.getSimpleName(), profilename);

    }
}
