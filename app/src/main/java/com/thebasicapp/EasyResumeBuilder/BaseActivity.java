package com.thebasicapp.EasyResumeBuilder;

import Helper.UIHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

public class BaseActivity extends Activity implements View.OnClickListener {

    protected TextView txtHeading;
    protected ImageView /*txtFAQ,*/ imgClear;
    protected ImageView imgSource, imgSettings;
    private Context context;
    private Activity activity;
    protected TimerThread timerThread;

    protected String profilename;
    protected int Profid;
    //Initilize gphotoprint Analytics
    protected FirebaseAnalytics mFirebaseAnalytics;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }


    protected void setHeading(String title, Drawable drawable) {
        context = this;
        txtHeading = (TextView) findViewById(R.id.textviewpersonal);
        imgSource = (ImageView) findViewById(R.id.imageviewpersonal);
        imgSettings = (ImageView) findViewById(R.id.imgSettings);
        //txtFAQ = (ImageView) findViewById(R.id.tvFAQ);
        imgClear = (ImageView) findViewById(R.id.imgClear);
        imgClear.setVisibility(View.GONE);
        //txtFAQ.setVisibility(View.VISIBLE);
        imgSettings.setVisibility(View.VISIBLE);
      /*  txtFAQ.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (UIHelper.isInternetAvailable(context)) {
                    Intent i =new Intent(BaseActivity.this,FAQActivty.class);
                    i.putExtra("ID", Profid);
                    startActivity(i);
//                    startActivity(new Intent(context, FAQActivty.class));
                } else {
                    UIHelper.showAlert(context, getResources().getString(R.string.internet_connection));
                }
            }
        });*/
        imgSettings.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i =new Intent(BaseActivity.this,SettingsActivity.class);
                i.putExtra("ID", Profid);
                startActivity(i);
//                startActivity(new Intent(context, SettingsActivity.class));
            }
        });


        txtHeading.setText(title);
        imgSource.setImageDrawable(drawable);
    }

    protected void setHeading(String title, Drawable drawable, Activity activity) {
        context = this;
        this.activity = activity;
        txtHeading = (TextView) findViewById(R.id.textviewpersonal);
        imgSource = (ImageView) findViewById(R.id.imageviewpersonal);
        imgSettings = (ImageView) findViewById(R.id.imgSettings);
       // txtFAQ = (ImageView) findViewById(R.id.tvFAQ);
        //txtFAQ.setVisibility(View.VISIBLE);
        imgSettings.setVisibility(View.VISIBLE);
        imgClear = (ImageView) findViewById(R.id.imgClear);
        imgClear.setVisibility(View.VISIBLE);
        imgClear.setOnClickListener(this);
      /*  txtFAQ.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (UIHelper.isInternetAvailable(context)) {
                    startActivity(new Intent(context, FAQActivty.class));
                } else {
                    UIHelper.showAlert(context, getResources().getString(R.string.internet_connection));
                }
            }
        });*/
        imgSettings.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(new Intent(context, SettingsActivity.class));
            }
        });
        txtHeading.setText(title);
        imgSource.setImageDrawable(drawable);
    }


    protected void fromForm(String title, Drawable drawable, Activity activity) {
        context = this;
        this.activity = activity;
        txtHeading = (TextView) findViewById(R.id.textviewpersonal);
        imgSource = (ImageView) findViewById(R.id.imageviewpersonal);
        imgSettings = (ImageView) findViewById(R.id.imgSettings);
       // txtFAQ = (ImageView) findViewById(R.id.tvFAQ);
       // txtFAQ.setVisibility(View.VISIBLE);
        imgSettings.setVisibility(View.GONE);
        imgClear = (ImageView) findViewById(R.id.imgClear);
        imgClear.setVisibility(View.VISIBLE);
        imgClear.setOnClickListener(this);
       /* txtFAQ.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (UIHelper.isInternetAvailable(context)) {
                    Intent i =new Intent(BaseActivity.this,FAQActivty.class);
                    i.putExtra("ID", Profid);
                    startActivity(i);
                } else {
                    UIHelper.showAlert(context, getResources().getString(R.string.internet_connection));
                }
            }
        });*/
        imgSettings.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(new Intent(context, SettingsActivity.class)); Intent i =new Intent(BaseActivity.this,SettingsActivity.class);
                i.putExtra("ID", Profid);
                startActivity(i);
            }
        });
        txtHeading.setText(title);
        imgSource.setImageDrawable(drawable);
    }

    protected void fromsettinsgs(String title, Drawable drawable) {
        context = this;
        txtHeading = (TextView) findViewById(R.id.textviewpersonal);
        imgSource = (ImageView) findViewById(R.id.imageviewpersonal);
        imgSettings = (ImageView) findViewById(R.id.imgSettings);
        imgSettings.setVisibility(View.VISIBLE);
        //txtFAQ = (ImageView) findViewById(R.id.tvFAQ);
      //  txtFAQ.setVisibility(View.VISIBLE);
        imgClear = (ImageView) findViewById(R.id.imgClear);
        imgClear.setVisibility(View.GONE);
        /*txtFAQ.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (UIHelper.isInternetAvailable(context)) {
                    Intent i =new Intent(BaseActivity.this,FAQActivty.class);
                    i.putExtra("ID", Profid);
                    startActivity(i);
                } else {
                    UIHelper.showAlert(context, getResources().getString(R.string.internet_connection));
                }
            }
        });*/
        txtHeading.setText(title);
        imgSource.setImageDrawable(drawable);
    }

    protected void fromList(String title, Drawable drawable) {
        context = this;
        txtHeading = (TextView) findViewById(R.id.textviewpersonal);
        imgSource = (ImageView) findViewById(R.id.imageviewpersonal);
        imgSettings = (ImageView) findViewById(R.id.imgSettings);
        imgSettings.setVisibility(View.GONE);
       // txtFAQ = (ImageView) findViewById(R.id.tvFAQ);
       // txtFAQ.setVisibility(View.VISIBLE);
        imgClear = (ImageView) findViewById(R.id.imgClear);
        imgClear.setVisibility(View.GONE);
      /*  txtFAQ.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (UIHelper.isInternetAvailable(context)) {
                    Intent i =new Intent(BaseActivity.this,FAQActivty.class);
                    i.putExtra("ID", Profid);
                    startActivity(i);
                } else {
                    UIHelper.showAlert(context, getResources().getString(R.string.internet_connection));
                }
            }
        });*/
        txtHeading.setText(title);
        imgSource.setImageDrawable(drawable);
    }

    protected void fromfaq(String title, Drawable drawable) {
        context = this;
        txtHeading = (TextView) findViewById(R.id.textviewpersonal);
        imgSource = (ImageView) findViewById(R.id.imageviewpersonal);
        imgSettings = (ImageView) findViewById(R.id.imgSettings);
        imgSettings.setVisibility(View.GONE);
        //txtFAQ = (ImageView) findViewById(R.id.tvFAQ);
        //txtFAQ.setVisibility(View.GONE);
        txtHeading.setText(title);
        imgClear = (ImageView) findViewById(R.id.imgClear);
        imgClear.setVisibility(View.GONE);
        imgSource.setImageDrawable(drawable);
        imgSettings.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i =new Intent(BaseActivity.this,SettingsActivity.class);
                i.putExtra("ID", Profid);
                startActivity(i);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgClear:
                validateFields();
                break;
        }
    }

    private void validateFields() {
        if (activity != null) {
            if (activity instanceof Personal_info) {
                Personal_info myActivity = (Personal_info) activity;
                myActivity.cleardata();
            } else if (activity instanceof Project) {
                Project myActivity = (Project) activity;
                myActivity.clearData();
            } else if (activity instanceof Education) {
                Education myActivity = (Education) activity;
                myActivity.clearData();
            } else if (activity instanceof Experience) {
                Experience myActivity = (Experience) activity;
                myActivity.clearData();
            } else if (activity instanceof Refrence) {
                Refrence myActivity = (Refrence) activity;
                myActivity.clearData();
            } else if (activity instanceof Otherdetails) {
                Otherdetails myActivity = (Otherdetails) activity;
                myActivity.clearData();
            } else if (activity instanceof Uploadimage) {
                Uploadimage myActivity = (Uploadimage) activity;
                myActivity.clearData();
            }

        }
    }

    protected void showAlert(String message) {
        if(this.context==null)
            context=this.getBaseContext();
        UIHelper.showAlert(context, message);
    }

    public void showCancelDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("");

        // set dialog message
        alertDialogBuilder
                .setMessage(getString(R.string.clear_info))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        if (activity != null) {
                            if (activity instanceof Personal_info) {
                                Personal_info myActivity = (Personal_info) activity;
                                myActivity.deleterecord();
                                activity.finish();
                            } else if (activity instanceof Project) {
                                Project myActivity = (Project) activity;
                                myActivity.deleterecord();
                                activity.finish();
                            } else if (activity instanceof Education) {
                                Education myActivity = (Education) activity;
                                myActivity.deleterecord();
                                activity.finish();
                            } else if (activity instanceof Experience) {
                                Experience myActivity = (Experience) activity;
                                myActivity.deleterecord();
                                activity.finish();
                            } else if (activity instanceof Refrence) {
                                Refrence myActivity = (Refrence) activity;
                                myActivity.deleterecord();
                                activity.finish();
                            } else if (activity instanceof Otherdetails) {
                                Otherdetails myActivity = (Otherdetails) activity;
                                myActivity.deleterecord();
                                activity.finish();
                            } else if (activity instanceof Uploadimage) {
                                Uploadimage myActivity = (Uploadimage) activity;
                                myActivity.clearData();
                            }

                        }
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
//						activity.onBackPressed();
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    protected void sendAnalyticsData(String paramData,String profilename) {
        Bundle bundle = new Bundle();
        bundle.putString(paramData + " 1", profilename);
        bundle.putString(paramData, paramData);
        mFirebaseAnalytics.logEvent(paramData, bundle);
    }

    protected void sendAnalyticsScreenTime(int time,String screenName,String profilename) {
        Bundle bundle = new Bundle();
        bundle.putString(screenName, String.valueOf(time) + "Sec");
        bundle.putString(screenName, profilename);
        bundle.putString(screenName, screenName);
        mFirebaseAnalytics.logEvent(screenName + "- STime", bundle);

    }
}
