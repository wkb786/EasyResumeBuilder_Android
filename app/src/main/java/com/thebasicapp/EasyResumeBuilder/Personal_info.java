package com.thebasicapp.EasyResumeBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;

import Helper.Constants;
import Helper.UIHelper;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;


import android.widget.Toast;

public class Personal_info extends BaseActivity implements OnItemSelectedListener ,TimerInterface {
   // String[] genderarr = {"Male", "Female",};

    EditText DOB_ET, name_ET, address_ET, languages_ET, contact_ET, Email_ET;
    static final int DATE_DIALOG_ID = 0;
    Button save, clear, savelanguage;
    // AutoCompleteTextView Gender_ET;
    Date parse, today;

    private int mYear;
    private int mMonth;
    public static boolean personal_entered = false;
    private AdView adView;
    private int mDay, id;
    DatabaseHandler db = new DatabaseHandler(this);
    public static String name, gender = "", dob, address, languages, contact, email;
    String languagesadded = "", prof_id_string;
    boolean checkforprofinfo_personal = false;
    private Spinner genderspinner;
    //private String[] state = {"Female", "Male", "Other"};
    String []gendersNew = new String[3] ;

    private String[] state = new String[3];

    Context context;
    private Date date;
    String idstr;
    Date convertedDate;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.personal);
        adView = (AdView) findViewById(R.id.adView);
        gendersNew=   getResources().getStringArray(R.array.country_arrays);
        state[0] = gendersNew[0];
        state[1] = gendersNew[1];
        state[2] =gendersNew[2];
        context = this;
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice(getString(R.string.testdevice))
                .build();
        adView.setAdListener(new ToastAdListener(Personal_info.this, adView));
        adView.loadAd(adRequest);

        DOB_ET = (EditText) findViewById(R.id.DOBEdt);
        name_ET = (EditText) findViewById(R.id.NameEdt);
        address_ET = (EditText) findViewById(R.id.AddressEdt);
        languages_ET = (EditText) findViewById(R.id.LanguagesEdt);
        contact_ET = (EditText) findViewById(R.id.CellEdt);
        Email_ET = (EditText) findViewById(R.id.EmailEdt);
        genderspinner = (Spinner) findViewById(R.id.Genderspinner);
        save = (Button) findViewById(R.id.Save);
        // savelanguage = (Button) findViewById(R.id.SaveLanguagebtn);
        // db.deleteContact();
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                R.layout.custom_spinner_item, state);
        adapter_state.setDropDownViewResource(R.layout.dropdown_spinner);
        genderspinner.setAdapter(adapter_state);
        genderspinner.setOnItemSelectedListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idstr = getIntent().getExtras().getString("ID");
            Profid = Integer.parseInt(idstr);
            prof_id_string = String.valueOf(Profid);
            Log.d("id sended is", "" + Profid);
            Prof pr = db.getProf(Profid);
            String profilename = pr.getProfilename();
            Prof profile = db.getProf(Profid);
            String profilename2 = profile.getProfilename();
            name_ET.setText(profilename2);
//            name_ET.setText(profilename);
        }

        final List<Contact> contacts = db.getAllContacts();
        for (Contact ct : contacts) {

            String prid = ct.getProfid();
            if (prid.equals(prof_id_string)) {
                checkforprofinfo_personal = true;
                name = ct.getName();
                dob = ct.getDOB();
                gender = ct.getGender();
                address = ct.getAddress();
                languages = ct.getLanguage();
                contact = ct.getContact();
                email = ct.getEmail();
                id = ct.getID();

//                name_ET.setText(name);

                if (gender.equals(state[0])) {
                    genderspinner.setSelection(0);
                } else if (gender.equals(state[1])) {
                    genderspinner.setSelection(1);
                } else {
                    genderspinner.setSelection(2);
                }
                // Gender_ET.setText(gender);
//				DOB_ET.setText(UIHelper.par(dob, context));
                //04-Dec-1992  //04-12-1992    12-4-1992   1992-04-12
                if (!dob.equals("")) {
                    try {
//					date=new Date();

                        if (dob.contains("/")) {
                            String[] parts = dob.split("/");
                            String part1 = parts[0]; // 004
                            if (parts.length > 1) {
                                String part2 = parts[1]; // 004
                                if (part1.equalsIgnoreCase("Type1")) {
                                    SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATETYPE1);
                                    convertedDate = new Date();
                                    try {
                                        convertedDate = dateFormat.parse(part2);
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.setTime(convertedDate);
                                        SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(context));
                                        DOB_ET.setText(format.format(calendar.getTime()));
                                    } catch (ParseException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    } catch (java.text.ParseException e) {
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
                                        DOB_ET.setText(format.format(calendar.getTime()));
                                    } catch (ParseException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    } catch (java.text.ParseException e) {
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
                                        DOB_ET.setText(format.format(calendar.getTime()));
                                    } catch (ParseException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    } catch (java.text.ParseException e) {
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
                                        DOB_ET.setText(format.format(calendar.getTime()));
                                    } catch (ParseException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    } catch (java.text.ParseException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println(convertedDate);
                                }
                            }
                        } else {
                            date = UIHelper.parseDate(dob);
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(date);
                            SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(context));
                            DOB_ET.setText(format.format(calendar.getTime()));
                            Log.v("Date", UIHelper.parseDate(dob) + "");
                        }
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                }
                address_ET.setText(address);
                languages_ET.setText(languages);
                contact_ET.setText(contact);
                Email_ET.setText(email);
                break;
            }

        }

        Button clear = (Button) findViewById(R.id.Clear);
        clear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

            }
        });

        DOB_ET.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (DOB_ET.getRight() - DOB_ET.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        DOB_ET.setText("");
                        return true;
                    } else {
                        showDialog(DATE_DIALOG_ID);
                        return false;
                    }
                }
                return false;
            }
        });

        save.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

//		DOB_ET.setOnTouchListener(new OnTouchListener() {
//			public boolean onTouch(View v, MotionEvent event) {
//				showDialog(DATE_DIALOG_ID);
//				return false;
//			}
//		});

        DOB_ET.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {
                    showDialog(DATE_DIALOG_ID);
                }
            }
        });

    }


    protected void cleardata() {
        DOB_ET.setText("");
        name_ET.setText("");
        address_ET.setText("");
        languages_ET.setText("");
        contact_ET.setText("");
        Email_ET.setText("");
        genderspinner.setSelection(0);
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                if (convertedDate != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(convertedDate);

                    return new DatePickerDialog(this, mDateSetListener, calendar.get(Calendar.YEAR), calendar.get(calendar.MONTH)
                            , calendar.get(calendar.DAY_OF_MONTH));
                } else {
                    return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
                }
        }
        return null;
    }

    // updates the date in the TextView

    private void updateDisplay() {
        DOB_ET.setText(toDisplaydate);
    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    String toDisplaydate = "";

    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            if (view.isShown()) {
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
                Log.d("Month day and year are", "" + month + " " + dd + " " + yy
                        + "");
                String nowdate = "" + month + "/" + dd + "/" + yy + "";
                String date = "" + mMonth + "/" + mDay + "/" + mYear + "";

                Calendar calendar = Calendar.getInstance();
                calendar.set(mYear, monthOfYear, mDay);

                SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(context));
                toDisplaydate = format.format(calendar.getTime());
                System.out.println(toDisplaydate);
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                try {
                    parse = sdf.parse(date);
                    today = sdf.parse(nowdate);
                    System.out.println(parse);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                } catch (java.text.ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("My Date is" + parse);
                System.out.println("Today Date is" + today);

                if (today.compareTo(parse) > 0) {
                    updateDisplay();
                } else if (today.compareTo(parse) < 0) {
                    System.out.println("Today Date is Lesser than my Date");
                    showAlert(getResources().getString(R.string.highbday));
                } else if (today.compareTo(parse) == 0) {
                    System.out.println("Today Date is equal to my Date");
                    showAlert(getResources().getString(R.string.equalbday));
                }

            }
        }
    };

    @Override
    public void onDestroy() {

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
        sendAnalyticsData("PersonalInfo",profilename);
        adView.resume();
        timerThread = new TimerThread();
        timerThread.setTimerInterface(this);
        fromForm(getString(R.string.pdetails), getResources().getDrawable((R.drawable.person_ic_w)), Personal_info.this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // TODO Auto-generated method stub
        genderspinner.setSelection(position);
        String selState = (String) genderspinner.getSelectedItem();
        // showgender.setText("Your Gender:" + selState);
        gender = selState;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
//		super.onBackPressed();
        if (name_ET.getText().toString().equalsIgnoreCase("") && DOB_ET.getText().toString().equalsIgnoreCase("") &&
                address_ET.getText().toString().equalsIgnoreCase("") &&
                languages_ET.getText().toString().equalsIgnoreCase("") &&
                contact_ET.getText().toString().equalsIgnoreCase("") &&
                Email_ET.getText().toString().equalsIgnoreCase("")) {
            if (checkforprofinfo_personal) {
                showCancelDialog();
            } else {
                super.onBackPressed();
            }
        } else {
            save();
        }
    }

    public void deleterecord() {
        Contact pr = db.getContact(id);
        db.deleteAllContact(pr);
    }

    public void save() {

        // TODO Auto-generated method stub
//        String name, dob = "", address, languages, contact, email;

        if (checkforprofinfo_personal) {

            name = name_ET.getText().toString();
            dob = DOB_ET.getText().toString();
            address = address_ET.getText().toString();
            languages = languages_ET.getText().toString();
            contact = contact_ET.getText().toString();
            email = Email_ET.getText().toString();
            if (!checkmandatoryFields()) {

            } else {

                if (name.equals("")) {
                    showAlert(getString(R.string.enter_your_name));
                } else if (gender.equals("")) {
                    showAlert(getString(R.string.enter_your_gender));
                } else if (address.equals("")) {
                    showAlert(getString(R.string.enter_your_address));
                } else if (languages.equals("")) {
                    showAlert(getString(R.string.enter_languages));
                } else if (contact.equals("")) {
                    showAlert(getString(R.string.enter_cell_no));
                } else if (email.equals("")) {
                    showAlert(getString(R.string.enter_email_id));
                } else {
                    boolean check = emailValidator(email);
                    if (check) {
                      /*  if (dob.equals("")) {
                            cancel();
                        } else {*/

                        db.updateProf(new Prof(Integer.parseInt(idstr), name));
                        db.updateContact(new Contact(id, name, gender, UIHelper.addTypetoString(context, dob),
                                address, languages, contact, email,
                                prof_id_string));
                        showAlert(getString(R.string.info_updated));
                        Personal_info.this.finish();
//                        }

                    } else {
                        showAlert(getString(R.string.email_not_valid));
                    }
                }
            }
        } else {
            name = name_ET.getText().toString();
            dob = DOB_ET.getText().toString();
            address = address_ET.getText().toString();
            languages = languages_ET.getText().toString();
            contact = contact_ET.getText().toString();
            email = Email_ET.getText().toString();
            boolean check = emailValidator(email);
            if (!checkmandatoryFields()) {

            } else {
                if (name.equals("")) {
                    showAlert(getString(R.string.enter_your_name));
                } else if (gender.equals("")) {
                    showAlert(getString(R.string.enter_your_gender));
                } else if (address.equals("")) {
                    showAlert(getString(R.string.enter_your_address));
                } else if (languages.equals("")) {
                    showAlert(getString(R.string.enter_languages));
                } else if (contact.equals("")) {
                    showAlert(getString(R.string.enter_cell_no));
                } else if (email.equals("")) {
                    showAlert(getString(R.string.enter_email_id));
                } else {
                    if (check) {
                        db.updateProf(new Prof(Integer.parseInt(idstr), name));
                        db.addContact(new Contact(name, gender, UIHelper.addTypetoString(context, dob),
                                address, languages, contact, email,
                                prof_id_string));
//                        showAlert(getString(R.string.info_inserted));
                        Personal_info.this.finish();
//                        }
                    } else {
                        showAlert(getString(R.string.email_not_valid));

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
                        if (checkforprofinfo_personal) {
                            if (name.equals("")) {
                                showAlert(getString(R.string.enter_your_name));
                            } else if (gender.equals("")) {
                                showAlert(getString(R.string.enter_your_gender));
                            } else if (address.equals("")) {
                                showAlert(getString(R.string.enter_your_address));
                            } else if (languages.equals("")) {
                                showAlert(getString(R.string.enter_languages));
                            } else if (contact.equals("")) {
                                showAlert(getString(R.string.enter_cell_no));
                            } else if (email.equals("")) {
                                showAlert(getString(R.string.enter_email_id));
                            } else {
                                boolean check = emailValidator(email);
                                if (check) {
                                   /* if (dob.equals("")) {
                                        cancel();
                                    } else {*/

                                    String[] parts = UIHelper.addTypetoString(context, dob).split("/");
                                    String part1 = parts[0]; // 004
                                    String part2 = parts[1]; // 004
                                    Log.v("Part1", part1 + "");
                                    Log.v("Part2", part2 + "");
                                    db.updateProf(new Prof(Integer.parseInt(idstr), name));
                                    db.updateContact(new Contact(id, name, gender, UIHelper.addTypetoString(context, dob),
                                            address, languages, contact, email,
                                            prof_id_string));
                                    showAlert(getString(R.string.info_updated));
                                    Personal_info.this.finish();
//                                    }

                                } else {
                                    showAlert(getString(R.string.email_not_valid));
                                }
                            }
                           /* db.updateContact(new Contact(id, name, gender, dob,
                                    address, languages, contact, email,
                                    prof_id_string));
                            showAlert(getString(R.string.info_updated));*/
                        } else {
                            boolean check = emailValidator(email);
                            if (name.equals("")) {
                                showAlert(getString(R.string.enter_your_name));
                            } else if (gender.equals("")) {
                                showAlert(getString(R.string.enter_your_gender));
                            } else if (address.equals("")) {
                                showAlert(getString(R.string.enter_your_address));
                            } else if (languages.equals("")) {
                                showAlert(getString(R.string.enter_languages));
                            } else if (contact.equals("")) {
                                showAlert(getString(R.string.enter_cell_no));
                            } else if (email.equals("")) {
                                showAlert(getString(R.string.enter_email_id));
                            } else {
                                if (check) {
                                /*    if (dob.equals("")) {
                                        cancel();
                                    } else {*/


                                    db.updateProf(new Prof(Integer.parseInt(idstr), name));
                                    db.addContact(new Contact(name, gender, UIHelper.addTypetoString(context, dob),
                                            address, languages, contact, email,
                                            prof_id_string));
                                    showAlert(getString(R.string.info_inserted));
                                    Personal_info.this.finish();
//                                    }
                                } else {
                                    showAlert(getString(R.string.email_not_valid));

                                }
                            }
                            /*db.addContact(new Contact(name, gender, dob,
                                    address, languages, contact, email,
                                    prof_id_string));
                            showAlert(getString(R.string.info_inserted));*/
                        }
//                        finish();

                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (checkforprofinfo_personal) {
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

    private boolean checkmandatoryFields() {
        if (name_ET.getText().toString().equalsIgnoreCase("") ||
                address_ET.getText().toString().equalsIgnoreCase("") ||
                languages_ET.getText().toString().equalsIgnoreCase("") ||
                contact_ET.getText().toString().equalsIgnoreCase("") ||
                Email_ET.getText().toString().equalsIgnoreCase("")) {
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
        Log.v("Person info", screenTime + "");
    }


    @Override
    protected void onStop() {
        super.onStop();
        timerThread.stopThread();
        //sendAnalyticsScreenTime(screenTime, Personal_info.class.getSimpleName(), profilename);
    }

}
