package com.thebasicapp.EasyResumeBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import Helper.Constants;
import Helper.UIHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ParseException;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.thebasicapp.EasyResumeBuilder.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Education extends BaseActivity implements TimerInterface {

    EditText degree_et, uni_et, result_et;
    TextView passingyear_et;
    static final int DATE_DIALOG_ID = 0;
    int mYear, mMonth, mDay, id;
    Button save, clear;
    DatabaseHandler db = new DatabaseHandler(this);
    String degree, uni, result = "", passingyear;
    String idstr;
    int Check = 0;
    private AdView adView;
    static String prof_id_string = "";
    Date parse, today;
    private Context context;
    Date startDate;
    private Date date;
    Date convertedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.education);
        context = this;
        adView = (AdView) findViewById(R.id.adView);


        AdRequest adRequest = new AdRequest.Builder()
               // .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
               // .addTestDevice(getString(R.string.testdevice))
                .build();
        adView.setAdListener(new ToastAdListener(Education.this, adView));
        adView.loadAd(adRequest);


        degree_et = (EditText) findViewById(R.id.DegreeEdt);
        uni_et = (EditText) findViewById(R.id.UniversityEdt);
        // school_et = (EditText) findViewById(R.id.SchoolEdt);
        result_et = (EditText) findViewById(R.id.ResultEdt);
        passingyear_et = (TextView) findViewById(R.id.Passing_YearEdt);

        // db.deleteEducate();
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
        // /////////////////////// for calender //////////////////
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

//        passingyear_et.setOnTouchListener(new OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
////                showDialog(DATE_DIALOG_ID);
//                showPopup();
//                return false;
//            }
//        });

        passingyear_et.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup();
            }
        });
//        passingyear_et.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                // TODO Auto-generated method stub
//                if (hasFocus) {
////                    showDialog(DATE_DIALOG_ID);
////                    showPopup();
//                }
//            }
//        });
        // /////////////////////////////////////////////////////////////
        Button clear = (Button) findViewById(R.id.Clear);
        clear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

            }
        });
        // //////////////////////////// at loading time //////////////////////
        final List<Educate> educate = db.getAllEducate();
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
            degree = db.getEducate(id).getDegree();
            uni = db.getEducate(id).getUni();
            result = db.getEducate(id).getResult();
            passingyear = db.getEducate(id).getPassingyear();
            prof_id_string = db.getEducate(id).getProfid();
            startDate = db.getEducate(id).getStartDate();
            try {
                if (passingyear.contains("/")) {
                    String[] parts = passingyear.split("/");
                    String part1 = parts[0]; // 004
                    String part2 = parts[1]; // 004
                    if (!part2.equalsIgnoreCase(getString(R.string.still_studing))) {
                        if (part1.equalsIgnoreCase("Type1")) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATETYPE1);
                            convertedDate = new Date();
                            try {
                                convertedDate = dateFormat.parse(part2);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(convertedDate);
                                SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(context));
                                passingyear_et.setText(format.format(calendar.getTime()));
                            } catch (ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            System.out.println(convertedDate);
                        } else if (part1.equalsIgnoreCase("Type2")) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATETYPE2);
                            convertedDate = new Date();
                            try {
                                convertedDate = dateFormat.parse(part2);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(convertedDate);
                                SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(context));
                                passingyear_et.setText(format.format(calendar.getTime()));
                            } catch (ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            System.out.println(convertedDate);
                        } else if (part1.equalsIgnoreCase("Type3")) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATETYPE3);
                            convertedDate = new Date();
                            try {
                                convertedDate = dateFormat.parse(part2);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(convertedDate);
                                SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(context));
                                passingyear_et.setText(format.format(calendar.getTime()));
                            } catch (ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            System.out.println(convertedDate);
                        } else if (part1.equalsIgnoreCase("Type4")) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATETYPE4);
                            convertedDate = new Date();
                            try {
                                convertedDate = dateFormat.parse(part2);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(convertedDate);
                                SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(context));
                                passingyear_et.setText(format.format(calendar.getTime()));
                            } catch (ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            System.out.println(convertedDate);
                        }
                    } else {
                        passingyear_et.setText(getString(R.string.still_studing));
                    }
                }
            } catch (java.text.ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            degree_et.setText(degree);
            uni_et.setText(uni);
            result_et.setText(result);
        }

        // ////////////////////////////////////////////////////////////////////
        save = (Button) findViewById(R.id.Save);
        save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                String getdate = passingyear_et.getText().toString(); // getting
                if (convertedDate != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(convertedDate);

                    return new DatePickerDialog(this, mDateSetListener, calendar.get(Calendar.YEAR),
                            calendar.get(calendar.MONTH)
                            , calendar.get(calendar.DAY_OF_MONTH));
                } else {
                    return new DatePickerDialog(this, mDateSetListener, mYear,
                            mMonth, mDay);
                }

        }
        return null;
    }

    private void updateDisplay() {
        passingyear_et.setText(toDisplaydate);
    }


    protected void clearData() {
        degree_et.setText("");
        uni_et.setText("");
        // school_et.setText("");
        result_et.setText("");
        passingyear_et.setText("");
    }

    String toDisplaydate = "";
    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            mMonth++;

            Time now = new Time();
            now.setToNow();

            int month = now.month;
            int dd = now.monthDay;
            int yy = now.year;
            month++;
            Log.d("Month day an dyear are", "" + month + " " + dd + " " + yy
                    + "");
            String nowdate = "" + month + "/" + dd + "/" + yy + "";
            TimeZone timeZone = TimeZone.getTimeZone("UTC");
            Calendar calendar = Calendar.getInstance(timeZone);
            calendar.set(mYear, monthOfYear, mDay);
            startDate = calendar.getTime();

            SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(context));
            toDisplaydate = format.format(calendar.getTime());
            System.out.println(toDisplaydate);
            String datepicked = "" + mMonth + "/" + mDay + "/" + mYear + "";
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            try {
                parse = sdf.parse(datepicked);
                today = sdf.parse(nowdate);
                System.out.println(parse);
            } catch (ParseException ex) {
                ex.printStackTrace();
            } catch (java.text.ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if (today.compareTo(parse) > 0) {
                updateDisplay();
            } else if (today.compareTo(parse) < 0) {
                showAlert(getResources().getString(R.string.highpassing));
            } else if (today.compareTo(parse) == 0) {
                updateDisplay();
            }
        }
    };

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

        fromForm(getString(R.string.educationdetails), getResources().getDrawable((R.drawable.edu_ic_w)), Education.this);

        timerThread = new TimerThread();
        timerThread.setTimerInterface(this);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

        if (degree_et.getText().toString().equalsIgnoreCase("") &&
                uni_et.getText().toString().equalsIgnoreCase("") &&
                result_et.getText().toString().equalsIgnoreCase("") &&
                passingyear_et.getText().toString().equalsIgnoreCase("")) {
            if (Check == 1) {
                showCancelDialog();
            } else {
                super.onBackPressed();
            }
        } else {
            savedata();
        }
    }

    public void deleterecord() {
        Educate ed = db.getEducate(id);
        db.deleteAllEducate(ed);
    }

    private void savedata() {

        if (Check == 1) {
            degree = degree_et.getText().toString();
            uni = uni_et.getText().toString();
            // school = school_et.getText().toString();
            result = result_et.getText().toString();
            passingyear = passingyear_et.getText().toString();
            if (!checkmandatoryfields()) {

            } else {
                if (degree.equals("")) {
                    showAlert(getString(R.string.enter_degree_name));
                } else if (uni.equals("")) {
                    showAlert(getString(R.string.enter_universty_name));
                } else if (passingyear.equals("")) {
                    showAlert(getString(R.string.enter_passing_year));
                } else {
               /* if(result.equals("")) {
                    cancel();
                }else {*/
                    db.updateEducate(new Educate(id, degree, uni, "",
                            result, UIHelper.addTypetoString(context, passingyear), prof_id_string, startDate));

                    showAlert(getString(R.string.info_updated));
                    super.onBackPressed();
                }
//                }
            }
        } else {
            degree = degree_et.getText().toString();
            uni = uni_et.getText().toString();
            // school = school_et.getText().toString();
            result = result_et.getText().toString();
            passingyear = passingyear_et.getText().toString();
            if (!checkmandatoryfields()) {

            } else {
                if (degree.equals("")) {
                    showAlert(getString(R.string.enter_degree_name));
                } else if (uni.equals("")) {
                    showAlert(getString(R.string.enter_universty_name));
                } else if (passingyear.equals("")) {
                    showAlert(getString(R.string.enter_passing_year));
                } else {
               /* if(result.equals("")){
                    cancel();
                }else {*/
                    db.addEducation(new Educate(degree, uni, "", result,
                            UIHelper.addTypetoString(context, passingyear), prof_id_string, startDate));
                    showAlert(getString(R.string.info_inserted));
                    super.onBackPressed();
//                }
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
                            if (degree.equals("")) {
                                showAlert(getString(R.string.enter_degree_name));
                            } else if (uni.equals("")) {
                                showAlert(getString(R.string.enter_universty_name));
                            } else if (passingyear.equals("")) {
                                showAlert(getString(R.string.enter_passing_year));
                            } else {
                              /*  if(result.equals("")) {
                                    cancel();
                                }else {*/
                                db.updateEducate(new Educate(id, degree, uni, "",
                                        result, UIHelper.addTypetoString(context, passingyear), prof_id_string, startDate));
                                showAlert(getString(R.string.info_updated));
                                finish();
//                                    super.onBackPressed();
//                                }
                            }
                            /*db.updateEducate(new Educate(id, degree, uni, "",
                                    result, passingyear, prof_id_string, startDate));
                            showAlert(getString(R.string.info_updated));*/
                        } else {
                            if (degree.equals("")) {
                                showAlert(getString(R.string.enter_degree_name));
                            } else if (uni.equals("")) {
                                showAlert(getString(R.string.enter_universty_name));
                            } else if (passingyear.equals("")) {
                                showAlert(getString(R.string.enter_passing_year));
                            } else {
                               /* if(result.equals("")){
                                    cancel();
                                }else {*/
                                db.addEducation(new Educate(degree, uni, "", result,
                                        UIHelper.addTypetoString(context, passingyear), prof_id_string, startDate));
                                showAlert(getString(R.string.info_inserted));
                                finish();
//                                }
                            }
                         /*   db.addEducation(new Educate(degree, uni, "", result,
                                    passingyear, prof_id_string, startDate));
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

    private boolean checkmandatoryfields() {
        if (degree_et.getText().toString().equalsIgnoreCase("") ||
                uni_et.getText().toString().equalsIgnoreCase("") ||
                passingyear_et.getText().toString().equalsIgnoreCase("")) {
            cancel();
            return false;
        } else {
            return true;
        }
    }


    public void showPopup() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("");

        // set dialog message
        alertDialogBuilder
                .setMessage(getString(R.string.select_uption))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.completion_date), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        showDialog(DATE_DIALOG_ID);

                    }
                })
                .setNegativeButton(getString(R.string.still_studing), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
//						activity.onBackPressed();
                        passingyear_et.setText(getString(R.string.still_studing));
                        Time now = new Time();
                        now.setToNow();

                        int month = now.month;
                        int dd = now.monthDay;
                        int yy = now.year;
                        TimeZone timeZone = TimeZone.getTimeZone("UTC");
                        Calendar calendar = Calendar.getInstance(timeZone);
                        calendar.set(yy, month, dd);
                        startDate = calendar.getTime();
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    int screenTime = 0;

    @Override
    public void onStop(int count) {
        screenTime = count;
        Log.v("Education", screenTime + "");
    }

    @Override
    protected void onStop() {
        super.onStop();
        timerThread.stopThread();

    }
}
