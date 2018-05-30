package com.thebasicapp.EasyResumeBuilder;

import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.thebasicapp.EasyResumeBuilder.R;
import com.thebasicapp.EasyResumeBuilder.DateTimePicker.DateTimePicker;

public class Project extends BaseActivity implements DateTimePicker.DateWatcher, TimerInterface {

    EditText prtitle_et, prduration_et, role_et, tsize_et, expertise_et;
    static final int DATE_DIALOG_ID = 0;
    int mYear, mMonth, mDay, id;
    Button save, clear;
    private AdView adView;
    DatabaseHandler db = new DatabaseHandler(this);
    String prtitle, prduration, role, tsize, expertise;
    String idstr, prof_id_string;
    Context ctx = Project.this;
    int Check = 0;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.project);
        adView = (AdView) findViewById(R.id.adView);
        context = this;
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(getString(R.string.testdevice)).build();
        adView.setAdListener(new ToastAdListener(Project.this, adView));
        adView.loadAd(adRequest);

        prtitle_et = (EditText) findViewById(R.id.project_titleEdt);
        prduration_et = (EditText) findViewById(R.id.proj_durationEdt);
        role_et = (EditText) findViewById(R.id.RoleEdt);
        tsize_et = (EditText) findViewById(R.id.Team_sizeEdt);
        expertise_et = (EditText) findViewById(R.id.ExpertiseEdt);

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

        final List<Proj> proj = db.getAllProj();
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
            prtitle = db.getProj(id).getPrTitle();
            prduration = db.getProj(id).getPrDuration();
            role = db.getProj(id).getRole();
            tsize = db.getProj(id).getTsize();
            expertise = db.getProj(id).getExpertise();
            prof_id_string = db.getProj(id).getProfid();

            // expertise.replaceAll("," , "\\n");
            // expertise = unescape(expertise);

            prtitle_et.setText(prtitle);
            prduration_et.setText(prduration);
            role_et.setText(role);
            tsize_et.setText(tsize);
            expertise_et.setText(unescape(db.getProj(id).getExpertise()
                    .replaceAll(" , ", "\n")));

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
        prtitle_et.setText("");
        prduration_et.setText("");
        role_et.setText("");
        tsize_et.setText("");
        expertise_et.setText("");
    }

    private String unescape(String description) {
        return description.replaceAll("\\\\n", "\\\n");
    }

    public void button_click(View view) {
        // Create the dialog
        final Dialog mDateTimeDialog = new Dialog(this);
        // Inflate the root layout
        final RelativeLayout mDateTimeDialogView = (RelativeLayout) getLayoutInflater()
                .inflate(R.layout.date_time_dialog, null);
        // Grab widget instance
        final DateTimePicker mDateTimePicker = (DateTimePicker) mDateTimeDialogView
                .findViewById(R.id.DateTimePicker);
        mDateTimePicker.setDateChangedListener(this);

        // Update demo edittext when the "OK" button is clicked
        ((Button) mDateTimeDialogView.findViewById(R.id.SetDateTime))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        mDateTimePicker.clearFocus();
                        // TODO Auto-generated method stub
                        String result_string = "";
                        String year, month, days;
                        int y, m, d;
                        year = String.valueOf(mDateTimePicker.getYear());
                        y = Integer.parseInt(year);
                        month = String.valueOf(mDateTimePicker.getMonth());
                        m = Integer.parseInt(month);
                        days = String.valueOf(mDateTimePicker.getDay());
                        d = Integer.parseInt(days);

                        if (!year.equals("0")) {
                            if (y > 1) {
                                result_string = result_string
                                        + year
                                        + " "
                                        + getResources().getString(
                                        R.string.years);
                            } else {
                                result_string = result_string
                                        + year
                                        + " "
                                        + getResources().getString(
                                        R.string.year);
                            }

                        }
                        if (!month.equals("0")) {
                            if (result_string.equals("")) {
                                if (m > 1) {
                                    result_string = result_string
                                            + month
                                            + " "
                                            + getResources().getString(
                                            R.string.months);
                                } else {

                                    result_string = result_string
                                            + month
                                            + " "
                                            + getResources().getString(
                                            R.string.month);
                                }
                            } else {
                                if (m > 1) {
                                    result_string = result_string
                                            + ", "
                                            + month
                                            + "  "
                                            + getResources().getString(
                                            R.string.months);
                                } else {

                                    result_string = result_string
                                            + ", "
                                            + month
                                            + "  "
                                            + getResources().getString(
                                            R.string.month);
                                }
                            }
                        }
                        prduration_et.setText(result_string);
                        mDateTimeDialog.dismiss();
                    }
                });

        // Cancel the dialog when the "Cancel" button is clicked
        ((Button) mDateTimeDialogView.findViewById(R.id.CancelDialog))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        mDateTimeDialog.cancel();
                    }
                });

        // Reset Date and Time pickers when the "Reset" button is clicked

        ((Button) mDateTimeDialogView.findViewById(R.id.ResetDateTime))
                .setOnClickListener(new OnClickListener() {

                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        mDateTimePicker.reset();
                    }
                });

        // Setup TimePicker
        // No title on the dialog window
        mDateTimeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Set the dialog content view
        mDateTimeDialog.setContentView(mDateTimeDialogView);
        // Display the dialog
        mDateTimeDialog.show();
    }

    @Override
    public void onDateChanged(Calendar c) {
        // TODO Auto-generated method stub

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
        fromForm(getString(R.string.projectdetails), getResources().getDrawable((R.drawable.project_ic_w)), Project.this);
        timerThread = new TimerThread();
        timerThread.setTimerInterface(this);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        if (prtitle_et.getText().toString().equalsIgnoreCase("") &&
                prduration_et.getText().toString().equalsIgnoreCase("") &&
                role_et.getText().toString().equalsIgnoreCase("") &&
                tsize_et.getText().toString().equalsIgnoreCase("") &&
                expertise_et.getText().toString().equalsIgnoreCase("")) {
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
        Proj prj = db.getProj(id);
        db.deleteAllProj(prj);
    }

    private void save() {
        // TODO Auto-generated method stub

        if (Check == 1) {
            prtitle = prtitle_et.getText().toString();
            prduration = prduration_et.getText().toString();
            role = role_et.getText().toString();
            tsize = tsize_et.getText().toString();
            expertise = expertise_et.getText().toString()
                    .replaceAll("\\n", " , ");
            if (!checkMandatoryFields()) {

            } else {
                if (prtitle.equals("")) {
                    showAlert(getString(R.string.enter_project_name));
                } else if (prduration.equals("")) {
                    showAlert(getString(R.string.enter_project_duration));
                } else if (role.equals("")) {
                    showAlert(getString(R.string.enter_your_role));
                } else if (expertise.equals("")) {
                    showAlert(getString(R.string.enter_your_expertise));
                } else {
                   /* if (tsize.equalsIgnoreCase("")) {
                        cancel();
                    } else {*/
                        db.updateProj(new Proj(id, prtitle, prduration, role,
                                tsize, expertise, prof_id_string));
                        showAlert(getString(R.string.info_updated));
                        Project.this.finish();
//                    }
                }
            }
        } else {
            prtitle = prtitle_et.getText().toString();
            prduration = prduration_et.getText().toString();
            role = role_et.getText().toString();
            tsize = tsize_et.getText().toString();
            expertise = expertise_et.getText().toString()
                    .replaceAll("\\n", " , ");
            if (!checkMandatoryFields()) {

            } else {
                if (prtitle.equals("")) {
                    showAlert(getString(R.string.enter_project_name));
                } else if (prduration.equals("")) {
                    showAlert(getString(R.string.enter_project_duration));
                } else if (role.equals("")) {
                    showAlert(getString(R.string.enter_your_role));
//			} 
                } else if (expertise.equals("")) {
                    showAlert(getString(R.string.enter_your_expertise));
                } else {
                    /*if (tsize.equalsIgnoreCase("")) {
                        cancel();
                    } else {*/
                        db.addProject(new Proj(prtitle, prduration, role,
                                tsize, expertise, prof_id_string));
                        showAlert(getString(R.string.info_inserted));
                        Project.this.finish();
//                    }
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
                        if (Check == 1) {
                            if (prtitle.equals("")) {
                                showAlert(getString(R.string.enter_project_name));
                            } else if (prduration.equals("")) {
                                showAlert(getString(R.string.enter_project_duration));
                            } else if (role.equals("")) {
                                showAlert(getString(R.string.enter_your_role));
                            } else if (expertise.equals("")) {
                                showAlert(getString(R.string.enter_your_expertise));
                            } else {
                              /*  if (tsize.equalsIgnoreCase("")) {
                                    cancel();
                                } else {*/
                                db.updateProj(new Proj(id, prtitle, prduration, role,
                                        tsize, expertise, prof_id_string));
                                showAlert(getString(R.string.info_updated));
                                Project.this.finish();
//                                }
                            }
                            /*db.updateProj(new Proj(id, prtitle, prduration, role,
                                    tsize, expertise, prof_id_string));
                            showAlert(getString(R.string.info_updated));*/
                        } else {
                            if (prtitle.equals("")) {
                                showAlert(getString(R.string.enter_project_name));
                            } else if (prduration.equals("")) {
                                showAlert(getString(R.string.enter_project_duration));
                            } else if (role.equals("")) {
                                showAlert(getString(R.string.enter_your_role));
//			}
                            } else if (expertise.equals("")) {
                                showAlert(getString(R.string.enter_your_expertise));
                            } else {
                               /* if (tsize.equalsIgnoreCase("")) {
                                    cancel();
                                } else {*/
                                db.addProject(new Proj(prtitle, prduration, role,
                                        tsize, expertise, prof_id_string));
                                showAlert(getString(R.string.info_inserted));
                                Project.this.finish();
//                                }
                            }
                            /*db.addProject(new Proj(prtitle, prduration, role,
                                    tsize, expertise, prof_id_string));
                            showAlert(getString(R.string.info_inserted));*/
                        }
//                        finish();

                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (Check == 1) {
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

    private boolean checkMandatoryFields() {
        if (prtitle_et.getText().toString().equalsIgnoreCase("") ||
                prduration_et.getText().toString().equalsIgnoreCase("") ||
                role_et.getText().toString().equalsIgnoreCase("") ||
                expertise_et.getText().toString().equalsIgnoreCase("")) {
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
        Log.v("Generate", screenTime + "");
    }

    @Override
    protected void onStop() {
        super.onStop();
        timerThread.stopThread();
        //sendAnalyticsScreenTime(screenTime, Project.class.getSimpleName(), profilename);

    }
}
