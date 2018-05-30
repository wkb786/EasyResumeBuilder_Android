package com.thebasicapp.EasyResumeBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

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
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Experience extends BaseActivity implements TimerInterface{
    EditText company_et, position_et, period_et, location_et, salary_et,
            responsibility_et;
    DatabaseHandler db = new DatabaseHandler(this);
    String company, position, period, location = "", salary = "", responsibility;
    Button save, clear;
    static final int DATE_DIALOG_ID = 0, DATE_DIALOG_ID1 = 1;
    int mYear, mMonth, mDay, id, mYearstart, mMonthstart, mDaystart;
    String idstr;
    int Check = 0;
    private CharSequence title;
    String firsttext;
    private AdView adView;
    String prof_id_string;
    Date parse, today;
    Context context;
    Date startDate;
    private Date date;
    private Date date1;
    private String displayDate = "";
    Date convertedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.experience);
        context = this;
        company_et = (EditText) findViewById(R.id.CompanyEdt);
        position_et = (EditText) findViewById(R.id.PositionEdt);
        period_et = (EditText) findViewById(R.id.PeriodEdt);
        location_et = (EditText) findViewById(R.id.LocationEdt);
        salary_et = (EditText) findViewById(R.id.SalaryEdt);
        responsibility_et = (EditText) findViewById(R.id.Job_ResponsibilityEdt);

        adView = (AdView) findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(getString(R.string.testdevice)).build();
        adView.setAdListener(new ToastAdListener(Experience.this, adView));
        adView.loadAd(adRequest);

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
        //sendAnalyticsData("Add Experience",profilename);
        period_et.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                showDialog(DATE_DIALOG_ID);

                return false;
            }
        });

        period_et.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {
                    showDialog(DATE_DIALOG_ID);
                }
            }
        });

        Button clear = (Button) findViewById(R.id.Clear);
        clear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

            }
        });

        final List<Exper> exper = db.getAllExper();
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
            company = db.getExper(id).getCompany();
            position = db.getExper(id).getPosition();
            period = db.getExper(id).getPeriod();
            location = db.getExper(id).getLocation();
            salary = db.getExper(id).getSalary();
            responsibility = db.getExper(id).getResponsibility();
            prof_id_string = db.getExper(id).getProfid();
            startDate = db.getExper(id).getStartDate();
            company_et.setText(company);
            position_et.setText(position);

            location_et.setText(location);
            salary_et.setText(salary);


            String[] part = period.split("/");
            String part01 = part[0]; // 004
            String part02 = part[1]; // 004
            if (part01.equalsIgnoreCase("Type1")) {
                String[] parts = part02.split(" ");
                String part1 = parts[0]; // 004

                SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATETYPE1);
                date = new Date();
                try {
                    date = dateFormat.parse(part1);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(context));
                    displayDate = format.format(calendar.getTime());
                } catch (java.text.ParseException e1) {
                    e1.printStackTrace();
                }
                String part2 = parts[2];

                if (android.text.TextUtils.isDigitsOnly(part2.substring(part2.length()-2, part2.length()-1))) {
                    try {
                        SimpleDateFormat dateFormat2 = new SimpleDateFormat(Constants.DATETYPE1);
                        date1 = new Date();
                        date1 = dateFormat.parse(part2);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date1);
                        SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(context));
                        displayDate = displayDate + " To " + format.format(calendar.getTime());
                    } catch (java.text.ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    displayDate = displayDate + " " + getString(R.string.still_working);
                }
                period_et.setText(displayDate);
                System.out.println(date);
            } else if (part01.equalsIgnoreCase("Type2")) {
                String[] parts = part02.split(" ");
                String part1 = parts[0]; // 004
                SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATETYPE2);
                date = new Date();
                try {
                    date = dateFormat.parse(part1);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(context));
                    displayDate = format.format(calendar.getTime());
                } catch (java.text.ParseException e1) {
                    e1.printStackTrace();
                }
                String part2 = (parts[2]).trim();

                if (android.text.TextUtils.isDigitsOnly(part2.substring(part2.length()-2, part2.length()-1))) {
                    try {
                        SimpleDateFormat dateFormat2 = new SimpleDateFormat(Constants.DATETYPE2);
                        date1 = new Date();
                        date1 = dateFormat.parse(part2);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date1);
                        SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(context));
                        displayDate = displayDate + " To " + format.format(calendar.getTime());
                    } catch (java.text.ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    displayDate = displayDate + " " + getString(R.string.still_working);
                }
                period_et.setText(displayDate);
                System.out.println(date);
            } else if (part01.equalsIgnoreCase("Type3")) {
                String[] parts = part02.split(" ");
                String part1 = parts[0]; // 004
                SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATETYPE3);
                date = new Date();
                try {
                    date = dateFormat.parse(part1);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(context));
                    displayDate = format.format(calendar.getTime());
                } catch (java.text.ParseException e1) {
                    e1.printStackTrace();
                }
                String part2 = parts[2];
                if (android.text.TextUtils.isDigitsOnly(part2.substring(part2.length()-2, part2.length()-1))) {
                    try {
                        SimpleDateFormat dateFormat2 = new SimpleDateFormat(Constants.DATETYPE3);
                        date1 = new Date();
                        date1 = dateFormat.parse(part2);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date1);
                        SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(context));
                        displayDate = displayDate + " To " + format.format(calendar.getTime());
                    } catch (java.text.ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    displayDate = displayDate + " " + getString(R.string.still_working);
                }
                period_et.setText(displayDate);
                System.out.println(date);
            } else if (part01.equalsIgnoreCase("Type4")) {
                String[] parts = part02.split(" ");
                String part1 = parts[0]; // 004
                SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATETYPE4);
                date = new Date();
                try {
                    date = dateFormat.parse(part1);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(context));
                    displayDate = format.format(calendar.getTime());
                } catch (java.text.ParseException e1) {
                    e1.printStackTrace();
                }
                String part2 = parts[2];
                if (android.text.TextUtils.isDigitsOnly(part2.substring(part2.length()-2, part2.length()-1))) {
                    try {
                        SimpleDateFormat dateFormat2 = new SimpleDateFormat(Constants.DATETYPE4);
                        date1 = new Date();
                        date1 = dateFormat.parse(part2);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date1);
                        SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(context));
                        displayDate = displayDate + " To " + format.format(calendar.getTime());
                    } catch (java.text.ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    displayDate = displayDate + " " + getString(R.string.still_working);
                }
                period_et.setText(displayDate);
                System.out.println(date);
            }
            period_et.setText(displayDate);
            responsibility_et.setText(unescape(db.getExper(id)
                    .getResponsibility().replaceAll(" , ", "\n")));

        } else {

        }

        save = (Button) findViewById(R.id.Save);

    }

    private String unescape(String description) {
        return description.replaceAll("\\\\n", "\\\n");
    }


    private void showEndDurationDialog() {
        new AlertDialog.Builder(context)
                .setTitle("")
                .setMessage("Please Select Duration")
                .setPositiveButton(R.string.end_date, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        showDialog(DATE_DIALOG_ID1);
                    }
                })
                .setNegativeButton(R.string.still_working, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        period_et.setText(fromdate + " " + getString(R.string.still_working));
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert).setCancelable(false)
                .show();
    }

    protected void clearData() {
        company_et.setText("");
        position_et.setText("");
        period_et.setText("");
        location_et.setText("");
        salary_et.setText("");
        responsibility_et.setText("");
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                String getdate = period_et.getText().toString(); // getting string
                // of date need
                // to split
                // String to
                // dd-mm-yy
                if (!getdate.equals("")) {
//				comment by me because of crash
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    DatePickerDialog startDatePicker = new DatePickerDialog(this, mDateSetListener,
                            calendar.get(Calendar.YEAR),
                            calendar.get(calendar.MONTH)
                            , calendar.get(calendar.DAY_OF_MONTH));
                    startDatePicker.setTitle(getResources().getString(R.string.start_Date));
                    return startDatePicker;
                } else {
                    DatePickerDialog startDatePicker = new DatePickerDialog(this,
                            mDateSetListener, mYear, mMonth, mDay);
                    startDatePicker.setTitle(getResources().getString(
                            R.string.start_Date));
                    return startDatePicker;
                }
            case DATE_DIALOG_ID1:
                DatePickerDialog enddate = new DatePickerDialog(this,
                        mDateSetListener1, mYear, mMonth, mDay);
                enddate.setTitle(getResources().getString(R.string.End_Date));
                return enddate;
        }
        return null;
    }

    // updates the date in the TextView

    private void updateDisplay() {
        StringBuilder period = new StringBuilder();
        period.append(mDaystart).append("-").append(mMonthstart).append("-")
                .append(mYearstart).append("ZZ");
        String test = period.toString();
        StringTokenizer token = new StringTokenizer(test, "ZZ");
        firsttext = token.nextToken();
        // period_et.setText(one);
        showEndDurationDialog();
//		showDialog(DATE_DIALOG_ID1);
    }

    private void updateDisplay1() {

        StringBuilder period = new StringBuilder();
        period.append(firsttext).append(" To ").append(mDay).append("-")
                .append(mMonth).append("-").append(mYear).append("ZZ");
        String test = period.toString();
        StringTokenizer token = new StringTokenizer(test, "ZZ");
        String one = token.nextToken();
//		period_et.setText(one);
        period_et.setText(fromdate + " To " + todate);
        Constants.EXP_DATE = fromdate + " To " + todate;
    }

    // the callback received when the user "sets" the date in the dialog
    String fromdate = "", todate = "";
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            if (view.isShown()) {
                mYearstart = year;
                mMonthstart = monthOfYear;
                mDaystart = dayOfMonth;
//                mMonthstart++;
                Time now = new Time();
                now.setToNow();

                int month = now.month;
                int dd = now.monthDay;
                int yy = now.year;
                month++;
                String nowdate = "" + month + "/" + dd + "/" + yy + "";

                String datepicked = "" + mMonthstart + "/" + mDaystart + "/"
                        + mYearstart + "";
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

                Calendar calendar = Calendar.getInstance();
                calendar.set(mYearstart, mMonthstart, mDaystart);

                SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(context));
                fromdate = format.format(calendar.getTime());
                System.out.println(fromdate);
                startDate = calendar.getTime();

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
                System.out.println("Date picked is" + parse);
                System.out.println("Today Date is" + today);

                if (today.compareTo(parse) > 0) {
                    updateDisplay();
                } else if (today.compareTo(parse) < 0) {
                    showAlert(getResources().getString(R.string.highstart));
                } else if (today.compareTo(parse) == 0) {
                    showAlert(getResources().getString(R.string.equalstart));
                }

            }
        }

    };

    private DatePickerDialog.OnDateSetListener mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {

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

            String datepicked = "" + mMonth + "/" + mDay + "/" + mYear + "";
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

            Calendar calendar = Calendar.getInstance();
            calendar.set(mYear, monthOfYear, mDay);
            SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(context));
            todate = format.format(calendar.getTime());
            System.out.println(todate);

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
            System.out.println("Date picked is" + parse);
            System.out.println("Today Date is" + today);

            if (today.compareTo(parse) > 0) {

                String datestrt = "" + mMonthstart + "/" + mDaystart + "/"
                        + mYearstart + "";
                String dateend = "" + mMonth + "/" + mDay + "/" + mYear + "";
                Date startd = new Date(), endd = new Date();
                try {
                    startd = sdf.parse(datestrt);
                    endd = sdf.parse(dateend);
                    System.out.println(parse);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                } catch (java.text.ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                if (endd.compareTo(startd) > 0) {
                    updateDisplay1();
                } else if (endd.compareTo(startd) < 0) {
                    Toast.makeText(
                            getApplicationContext(),
                            getResources().getString(
                                    R.string.startgreaterthanend),
                            Toast.LENGTH_SHORT).show();
                } else if (endd.compareTo(startd) == 0) {
                    showAlert(getResources().getString(R.string.startendequal));
                }

            } else if (today.compareTo(parse) < 0) {
                showAlert(getResources().getString(R.string.highend));
            } else if (today.compareTo(parse) == 0) {
                updateDisplay1();
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
        fromForm(getString(R.string.experiencedetails),
                getResources().getDrawable((R.drawable.work_ic_w)), Experience.this);

        timerThread = new TimerThread();
        timerThread.setTimerInterface(this);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        if (company_et.getText().toString().equalsIgnoreCase("") &&
                position_et.getText().toString().equalsIgnoreCase("") &&
                period_et.getText().toString().equalsIgnoreCase("") &&
                location_et.getText().toString().equalsIgnoreCase("") &&
                salary_et.getText().toString().equalsIgnoreCase("") &&
                responsibility_et.getText().toString().equalsIgnoreCase("")) {
            if (Check == 1) {
                showCancelDialog();
            } else {
                Constants.EXP_DATE = "";
                super.onBackPressed();
            }
        } else {
            save();
        }
    }

    public void deleterecord() {
        Exper exp = db.getExper(id);
        db.deleteAllExper(exp);
    }

    private void save() {
        if (Check == 1) {
            company = company_et.getText().toString();
            position = position_et.getText().toString();
            period = period_et.getText().toString();
            location = location_et.getText().toString();
            salary = salary_et.getText().toString();
            responsibility = responsibility_et.getText().toString()
                    .replaceAll("\\n", " , ");
            if (!checkMandatoryFields()) {

            } else {
                if (company.equals("")) {
                    showAlert(getString(R.string.enter_company_name));
                } else if (position.equals("")) {
                    showAlert(getString(R.string.enter_your_position));
                } else if (period.equals("")) {
                    showAlert(getString(R.string.enter_service_period_in_company));
                } else if (responsibility.equals("")) {
                    showAlert(getString(R.string.enter_you_job_responsibility));
                } else {
                    /*if (salary.equalsIgnoreCase("") || location.equalsIgnoreCase("")) {
                        cancel();
                    } else {*/
                    if (responsibility.endsWith(",")) {
                        responsibility = responsibility.substring(0, responsibility.length() - 1);
                    }
                    System.out.println("Responsiblity" + responsibility + "");
                    db.updateExper(new Exper(id, company, position, UIHelper.addTypetoString(context, period),
                            location, salary, responsibility,
                            prof_id_string, startDate));
                    showAlert(getString(R.string.info_updated));
                    Constants.EXP_DATE = "";
                    super.onBackPressed();
//                    }
                }
            }
        } else {
            company = company_et.getText().toString();
            position = position_et.getText().toString();
            period = period_et.getText().toString();
            location = location_et.getText().toString();
            salary = salary_et.getText().toString();
            responsibility = responsibility_et.getText().toString()
                    .replaceAll("\\n", " , ");
            if (!checkMandatoryFields()) {

            } else {
                if (company.equals("")) {
                    showAlert(getString(R.string.enter_company_name));
                } else if (position.equals("")) {
                    showAlert(getString(R.string.enter_your_position));
                } else if (period.equals("")) {
                    showAlert(getString(R.string.enter_service_period_in_company));
                } else if (responsibility.equals("")) {
                    showAlert(getString(R.string.enter_you_job_responsibility));
                } else {
                  /*  if (salary.equalsIgnoreCase("") || location.equalsIgnoreCase("")) {
                        cancel();
                    } else {*/
//                    System.out.println("last char = " + responsibility.charAt(responsibility.length() - 1));
                    if (responsibility.endsWith(",")) {
                        responsibility = responsibility.substring(0, responsibility.length() - 1);
                    }
                    db.addExperience(new Exper(company, position, UIHelper.addTypetoString(context, period),
                            location, salary, responsibility,
                            prof_id_string, startDate));
                    showAlert(getString(R.string.info_inserted));
                    Constants.EXP_DATE = "";
                    super.onBackPressed();
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
                            if (company.equals("")) {
                                showAlert(getString(R.string.enter_company_name));
                            } else if (position.equals("")) {
                                showAlert(getString(R.string.enter_your_position));
                            } else if (period.equals("")) {
                                showAlert(getString(R.string.enter_service_period_in_company));
                            } else if (responsibility.equals("")) {
                                showAlert(getString(R.string.enter_you_job_responsibility));
                            } else {
                                if (responsibility.endsWith(",")) {
                                    responsibility = responsibility.substring(0, responsibility.length() - 1);
                                }
                                System.out.println("Responsiblity" + responsibility + "");
                                db.updateExper(new Exper(id, company, position, UIHelper.addTypetoString(context, period),
                                        location, salary, responsibility,
                                        prof_id_string, startDate));
                                showAlert(getString(R.string.info_updated));
                                Constants.EXP_DATE = "";
                                finish();
//                    }
                            }
                           /* db.updateExper(new Exper(id, company, position, period,
                                    location, salary, responsibility,
                                    prof_id_string, startDate));
                            showAlert(getString(R.string.info_updated));*/
                        } else {
                            if (company.equals("")) {
                                showAlert(getString(R.string.enter_company_name));
                            } else if (position.equals("")) {
                                showAlert(getString(R.string.enter_your_position));
                            } else if (period.equals("")) {
                                showAlert(getString(R.string.enter_service_period_in_company));
                            } else if (responsibility.equals("")) {
                                showAlert(getString(R.string.enter_you_job_responsibility));
                            } else {
                                if (responsibility.endsWith(",")) {
                                    responsibility = responsibility.substring(0, responsibility.length() - 1);
                                }
                                System.out.println("Responsiblity" + responsibility + "");
                                db.addExperience(new Exper(company, position, UIHelper.addTypetoString(context, period),
                                        location, salary, responsibility,
                                        prof_id_string, startDate));
                                showAlert(getString(R.string.info_inserted));
                                Constants.EXP_DATE = "";
                                finish();
//                    }
                            }
                            /*db.addExperience(new Exper(company, position, period,
                                    location, salary, responsibility,
                                    prof_id_string, startDate));
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
                        Constants.EXP_DATE = "";
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
        if (company_et.getText().toString().equalsIgnoreCase("") ||
                position_et.getText().toString().equalsIgnoreCase("") ||
                period_et.getText().toString().equalsIgnoreCase("") ||
                responsibility_et.getText().toString().equalsIgnoreCase("")) {
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
        Log.v("Experience", screenTime + "");
    }

    @Override
    protected void onStop() {
        super.onStop();
        timerThread.stopThread();
        //sendAnalyticsScreenTime(screenTime, Experience.class.getSimpleName(), profilename);

    }
}