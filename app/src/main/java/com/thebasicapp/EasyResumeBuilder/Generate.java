package com.thebasicapp.EasyResumeBuilder;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Helper.Constants;
import Helper.UIHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.format.Time;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.webviewtopdf.PdfView;

import android.widget.Toast;


public class Generate extends Activity implements TimerInterface {
    public static boolean template_set_view = false;
    public static boolean template_set_share = false;
    public String base64 = "";
    File file = null;
    int Profid;
    DatabaseHandler db;
    String prof_id_string, Profilename;
    String resume_name_sended;
    boolean checkdataforprofile = false;
    private String check_name = "";
    private Context context;
    private boolean isPdf = false;
    protected TimerThread timerThread;
    WebView webView;
    protected FirebaseAnalytics mFirebaseAnalytics;
    //Initilize gphotoprint Analytics
//    protected FirebaseAnalytics mFirebaseAnalytics;

    AsyncTask<Void, Void, Void> resumeTask;

   /* InterstitialAd mInterstitialAd;
    AdRequest adRequestfullScreen = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            .addTestDevice("78AA8147493ED07DCD7ED83361C09655")
            .build();
*/

    Context c;

    ModelClass modelClass;
    String number, address, language, email, profile;
    ArrayList<Exper> experiencesList = new ArrayList<Exper>();
    ArrayList<Educate> educationList = new ArrayList<Educate>();
    ArrayList<Proj> projectsList = new ArrayList<Proj>();
    ArrayList<Refren> referencesList = new ArrayList<Refren>();
    String exper_position, exper_period, exper_location, exper_salary, exper_responsibility, exper_startDate, exper_company;
    String edu_degree, edu_passingYear, edu_schoolName, edu_uni, edu_result;
    String contactName;
    String projTitle, projDduration, projRole, projTeamsize, projExpertise;
    Date edu_date;
    String otherPasport, otherLicence;
    String refname, refemail;
    public List<Exper> exper_list = new ArrayList<Exper>();
    public List<Educate> educate_list = new ArrayList<Educate>();
    public List<Proj> pro_list = new ArrayList<Proj>();
    public List<Refren> refer_list = new ArrayList<Refren>();

    ArrayList<String> refr_array = new ArrayList<String>();
    ArrayList<String> proj_array = new ArrayList<String>();
    ArrayList<String> educate_array = new ArrayList<String>();
    ArrayList<String> exper_array = new ArrayList<String>();
    StringBuilder ref = new StringBuilder(100);
    StringBuilder projString = new StringBuilder(100);
    StringBuilder experString = new StringBuilder(100);
    StringBuilder educateString = new StringBuilder(100);
    boolean check = false;
    String template;
    String template2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.generate);
        context = this;
        db = new DatabaseHandler(this);

       /* mInterstitialAd = new InterstitialAd(this);
        // set the ad unit ID
        mInterstitialAd.setAdUnitId(
                getString(R.string.interstitial_full_screen));
        mInterstitialAd.loadAd(adRequestfullScreen);*/


        Bundle extrasProf = getIntent().getExtras();
        if (extrasProf != null) {
            String idstr = getIntent().getExtras().getString("ID");
            Profid = Integer.parseInt(idstr);
            prof_id_string = String.valueOf(Profid);
            Log.d("id sended is", "" + Profid);
            Prof pr = db.getProf(Profid);
            Profilename = pr.getProfilename();

        }

        Button shareviamail = (Button) findViewById(R.id.share);
        shareviamail.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent shareIntent = new Intent(context, TemplatesDialog.class);
                shareIntent.putExtra("DialogType", "share");
                startActivityForResult(shareIntent, 21);

            }
        });

        Button view = (Button) findViewById(R.id.generateandshare);
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                check = Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED);
                checkdataforprofile = false;
                CheckDatainProfile();
                // to check personal info is entered or not
                if (check_name.equalsIgnoreCase("")) {
                    UIHelper.showAlert(context, "Cannot Generate without Personal Information");
                } else {

                    Intent viewIntent = new Intent(context, TemplatesDialog.class);
                    viewIntent.putExtra("DialogType", "view");
                    startActivityForResult(viewIntent, 21);


                }
            }
        });

        Button aboutus = (Button) findViewById(R.id.about);
        aboutus.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

               /* String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(getString(R.string.linkstore))));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(getString(R.string.allappsweblink)
                                    + appPackageName)));
                }*/

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
            }
        });

        Button suggestion = (Button) findViewById(R.id.suggestion);
        suggestion.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "support@technokeet.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Suggestion.Easy Resume Builder Android");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                }

            }
        });

        Button translation = (Button) findViewById(R.id.translation);
        translation.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "support@technokeet.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Easy Resume Builder Android Translation");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "I would like to assist you to translate Easy Resume Builder android app in my native language");
                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                }

            }
        });


        c = Generate.this;
        modelClass = GenerateResume.modelClass;


//        try {
//            InputStream is = getAssets().open("sample3.html");
//            int size = 0;
//            size = is.available();
//
//
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            String str = new String(buffer);
//            str = str.replace("old string", "new string");
//            s = str;
//            is.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        ModelClass m = GenerateResume.modelClass;
//        Button open = (Button) findViewById(R.id.open_pdf);
//        open.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS + "/PDFTest/");
//                final String fileName = "Test.pdf";
//
//                final ProgressDialog progressDialog = new ProgressDialog(c);
//                progressDialog.setMessage("Please wait");
//                progressDialog.show();
//                PdfView.createWebPrintJob(Generate.this, webView, path, fileName, new PdfView.Callback() {
//
//                    @Override
//                    public void success(String path) {
//                        progressDialog.dismiss();
//                        PdfView.openPdfFile(Generate.this, getString(R.string.app_name), "Do you want to open the pdf file?" + fileName, path);
//
//                    }
//
//                    @Override
//                    public void failure() {
//                        progressDialog.dismiss();
//
//                    }
//                });
//            }
//
//        });

    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        String ne = Environment.MEDIA_MOUNTED;
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)
                || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    private void readFileFromSDCard() {
        File directory = Environment.getExternalStorageDirectory();
        // Assumes that a file article.rss is available on the SD card
        File file = new File(directory + "/Resume.pdf");
        if (!file.exists()) {
            throw new RuntimeException(getResources().getString(
                    R.string.file_not_found));
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void CheckDatainProfile() {
        // TODO Auto-generated method stub
        final List<Contact> contacts = db.getAllContacts();
        for (Contact ct : contacts) {
            String prid = ct.getProfid();
            if (prid.equals(prof_id_string)) {
                check_name = ct.getName();
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

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (resumeTask != null
                && resumeTask.getStatus() == AsyncTask.Status.RUNNING) {
            resumeTask.cancel(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (MainActivity.mInterstitialAd == null) {

            loadIntAd();
        }

        if (isPdf) {
            Toast.makeText(Generate.this, "Pdf application", Toast.LENGTH_SHORT).show();
            showInterstitial();
            isPdf = false;
        }
        timerThread = new TimerThread();
        timerThread.setTimerInterface(this);
    }

    private void showInterstitial() {
        if (MainActivity.mInterstitialAd.isLoaded()) {
            MainActivity.mInterstitialAd.show();

            AdRequest adRequestfullScreen = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .addTestDevice("78AA8147493ED07DCD7ED83361C09655")
                    .build();
            MainActivity.mInterstitialAd.loadAd(adRequestfullScreen);

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
        //sendAnalyticsScreenTime(screenTime, Generate.class.getSimpleName(), Profilename);

    }

    /*
    protected void sendAnalyticsScreenTime(int time,String screenName,String profilename) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(time) + "Sec");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, profilename);
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, screenName);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
    }*/
    private void loadIntAd() {


        MainActivity.mInterstitialAd = new InterstitialAd(context);
        MainActivity.mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));

        MainActivity.mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.

                AdRequest adRequestfullScreen = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .addTestDevice("78AA8147493ED07DCD7ED83361C09655")
                        .build();
                MainActivity.mInterstitialAd.loadAd(adRequestfullScreen);
            }

        });

        AdRequest adRequestfullScreen = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("78AA8147493ED07DCD7ED83361C09655")
                .build();

        MainActivity.mInterstitialAd.loadAd(adRequestfullScreen);

    }

    public Bitmap downSampleBitmap(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap map = BitmapFactory.decodeFile(path, options);
        int originalHeight = options.outHeight;
        int originalWidth = options.outWidth;
        // Calculate your sampleSize based on the requiredWidth and
        // originalWidth
        // For e.g you want the width to stay consistent at 500dp
        int requiredWidth = (int) (80 * getResources().getDisplayMetrics().density);
        int sampleSize = originalWidth / requiredWidth;
        // If the original image is smaller than required, don't sample
        if (sampleSize < 1) {
            sampleSize = 1;
        }

        options.inSampleSize = sampleSize;
        options.inPurgeable = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);

        return bitmap;
    }

    public String BitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encoded;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 21) {
            if (template_set_view) {
                if (check) {
                    if (checkdataforprofile) {


                        Time today = new Time(Time.getCurrentTimezone());
                        today.setToNow();
                        String day = today.monthDay + ""; // Day of the
                        String month = today.month + "";
                        int mon = Integer.parseInt(month);
                        mon = mon + 1;
                        String year = today.year + ""; // Year
                        String time = today.format("%k%M%S");
                        resume_name_sended = Profilename;

                        final ProgressDialog pd = new ProgressDialog(
                                Generate.this);
                        resumeTask = new AsyncTask<Void, Void, Void>() {

                            @Override
                            protected void onPreExecute() {
                                // TODO Auto-generated method stub
                                super.onPreExecute();
                                pd.setMessage(getResources().getString(
                                        R.string.generatingforview));
                                pd.setCanceledOnTouchOutside(false);
                                pd.show();
                            }

                            @Override
                            protected Void doInBackground(Void... params) {
                                // TODO Auto-generated method stub

                                GenerateResume gresume = new GenerateResume();
                                gresume.ctx = getApplicationContext();
                                gresume.setId(prof_id_string,
                                        resume_name_sended, getString(R.string.still_working));
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

//                                String toread = resume_name_sended + ".pdf";
//
                                String toread = resume_name_sended + ".pdf";
//                                if (Constants.TEMPLATE_NO == 1) {
                                file = new File(Environment.getExternalStorageDirectory() + "/Resumes", toread);

//                                } else {
//                                    file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/PDFTest/"), toread);
//                                }


                                if (file.exists()) {
                                    //View pdf file
                                    Bundle bundle = new Bundle();
                                    bundle.putString("ResumeGenerate", Profilename);
                                    bundle.putString("ResumeGenerate", "Resume PDF View");
//                                        mFirebaseAnalytics.logEvent("ResumeGenerate", bundle);

                                    isPdf = true;

                                    Uri path = Uri.fromFile(file);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        path = FileProvider.getUriForFile(
                                                context,
                                                context.getApplicationContext()
                                                        .getPackageName() + ".provider", file);
                                        Log.e("Path", "NewVersion");
                                    }

                                    Intent intent = new Intent(
                                            Intent.ACTION_VIEW);
                                    intent.setDataAndType(path,
                                            "application/pdf");
                                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                                    try {


                                        if (Constants.TEMPLATE_NO == 1) {
                                            startActivity(intent);
                                        } else if (Constants.TEMPLATE_NO == 2) {
                                            Intent viewIntent = new Intent(Generate.this, ViewResume.class);
                                            viewIntent.putExtra("profID", prof_id_string);
                                            viewIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(viewIntent);
                                        } else if (Constants.TEMPLATE_NO == 3) {
                                            Intent viewIntent = new Intent(Generate.this, ViewResume.class);
                                            viewIntent.putExtra("profID", prof_id_string);
                                            viewIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(viewIntent);
                                        }
                                    } catch (ActivityNotFoundException e) {
                                        UIHelper.showAlert(context, getResources()
                                                .getString(
                                                        R.string.no_application_for_pdf));
                                    }
                                }
                            }
                        };
                        resumeTask.execute();

                    } else {
                        UIHelper.showAlert(context, getResources()
                                .getString(
                                        R.string.nodata));
                    }
                } else {
                    UIHelper.showAlert(context, getResources()
                            .getString(
                                    R.string.no_sdcard));

                }
            } else if (template_set_share) {

                if (Constants.TEMPLATE_NO == 2) {
                    getData(2);

                    template = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                            "\n" +
                            "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=8\">\n" +
                            "<title>bcl_1485955456.htm</title>\n" +
                            "<meta name=\"generator\" content=\"BCL easyConverter SDK 5.0.08\">\n" +
                            "<style type=\"text/css\">\n" +
                            "\n" +
                            "body {margin-top: 0px;margin-left: 0px;}\n" +
                            "\n" +
                            "#page_1 {position:relative; overflow: hidden;margin: 0px 0px 0px 0px;padding: 0px;border: none;width: 794px;height: 1122px;}\n" +
                            "#page_1 #id_1 {float:left;border:none;margin: 408px 0px 0px 28px;padding: 0px;border:none;width: 325px;overflow: hidden;}\n" +
                            "#page_1 #id_2 {float:left;border:none;margin: 109px 0px 0px 0px;padding: 0px;border:none;width: 441px;overflow: hidden;}\n" +
                            "\n" +
                            "#page_1 #p1dimg1 {position:absolute;top:0px;left:0px;z-index:-1;width:794px;height:1122px;}\n" +
                            "#page_1 #p1dimg1 #p1img1 {width:794px;height:1122px;}\n" +
                            "\n" +
                            "\n" +
                            "\n" +
                            "\n" +
                            ".dclr {clear:both;float:none;height:1px;margin:0px;padding:0px;overflow:hidden;}\n" +
                            "\n" +
                            ".ft0{font: 40px 'Arial';color: #79c6c8;line-height: 45px;}\n" +
                            ".ft1{font: 13px 'Arial';line-height: 16px;}\n" +
                            ".ft2{font: 14px 'Arial';line-height: 16px;}\n" +
                            ".ft3{font: 11px 'Arial';line-height: 14px;}\n" +
                            ".ft4{font: 11px 'Arial';line-height: 18px;}\n" +
                            ".ft5{font: 11px 'Arial';line-height: 15px;}\n" +
                            ".ft6{font: bold 11px 'Arial';line-height: 14px;}\n" +
                            "\n" +
                            ".p0{text-align: left;padding-left: 1px;margin-top: 0px;margin-bottom: 0px;}\n" +
                            ".p1{text-align: left;padding-left: 1px;margin-top: 15px;margin-bottom: 0px;}\n" +
                            ".p2{text-align: left;padding-left: 1px;margin-top: 29px;margin-bottom: 0px;}\n" +
                            ".p3{text-align: left;padding-left: 9px;margin-top: 74px;margin-bottom: 10px;}\n" +
                            ".p33{text-align: left;padding-left: 10px;margin-top: 10px;margin-bottom: 0px;}\n" +
                            ".p34{text-align: left;padding-left: 20px;margin-top: 0px;margin-bottom: 0px;}\n" +
                            ".p4{text-align: left;margin-top: 36px;margin-bottom: 0px;}\n" +
                            ".p5{text-align: left;padding-right: 88px;margin-top: 7px;margin-bottom: 0px;}\n" +
                            ".p6{text-align: left;padding-left: 10px;margin-top: 57px;margin-bottom: 0px;}\n" +
                            ".p7{text-align: left;margin-top: 37px;margin-bottom: 10px;}\n" +
                            ".p77{text-align: left;margin-top: 5px;margin-bottom: 0px;padding-left: 10px}\n" +
                            ".p8{text-align: left;margin-top: 7px;margin-bottom: 0px;}\n" +
                            ".p9{text-align: left;padding-left: 10px;margin-top: 0px;margin-bottom: 0px;}\n" +
                            ".p10{text-align: left;padding-left: 1px;padding-right: 48px;margin-top: 28px;margin-bottom: 0px;}\n" +
                            ".p11{text-align: left;padding-left: 2px;margin-top: 64px;margin-bottom: 0px;}\n" +
                            ".p12{text-align: left;padding-left: 1px;margin-top: 28px;margin-bottom: 0px;}\n" +
                            ".p13{text-align: left;margin-top: 5px;margin-bottom: 0px;}\n" +
                            ".p14{text-align: left;padding-left: 17px;padding-right: 73px;margin-top: 9px;margin-bottom: 0px;}\n" +
                            ".p15{text-align: left;padding-left: 17px;margin-top: 4px;margin-bottom: 0px;}\n" +
                            ".p16{text-align: left;padding-left: 1px;margin-top: 25px;margin-bottom: 0px;}\n" +
                            ".p17{text-align: left;padding-left: 17px;padding-right: 62px;margin-top: 6px;margin-bottom: 0px;}\n" +
                            ".p18{text-align: left;padding-left: 17px;padding-right: 77px;margin-top: 4px;margin-bottom: 0px;}\n" +
                            ".p19{text-align: left;padding-left: 2px;margin-top: 56px;margin-bottom: 0px;}\n" +
                            ".p20{text-align: left;padding-left: 1px;margin-top: 30px;margin-bottom: 0px;}\n" +
                            ".p21{text-align: left;padding-left: 1px;margin-top: 5px;margin-bottom: 0px;}\n" +
                            ".p22{text-align: left;padding-left: 1px;margin-top: 2px;margin-bottom: 0px;}\n" +
                            ".p23{text-align: left;padding-left: 1px;margin-top: 39px;margin-bottom: 0px;}\n" +
                            ".p24{text-align: left;margin-top: 0px;margin-bottom: 0px;white-space: nowrap;}\n" +
                            ".p25{text-align: left;padding-left: 3px;margin-top: 42px;margin-bottom: 0px;}\n" +
                            ".p26{text-align: left;padding-left: 1px;margin-top: 34px;margin-bottom: 0px;}\n" +
                            ".p27{text-align: left;padding-right: 127px;margin-top: 5px;margin-bottom: 0px;}\n" +
                            ".p28{text-align: left;padding-left: 1px;margin-top: 32px;margin-bottom: 0px;}\n" +
                            ".p29{text-align: left;margin-top: 2px;margin-bottom: 0px;}\n" +
                            "\n" +
                            ".td0{padding: 0px;margin: 0px;width: 207px;vertical-align: bottom;}\n" +
                            ".td1{padding: 0px;margin: 0px;width: 120px;vertical-align: bottom;}\n" +
                            "\n" +
                            ".tr0{height: 16px;}\n" +
                            "\n" +
                            ".t0{width: 327px;margin-left: 16px;margin-top: 27px;font: 11px 'Arial';}\n" +
                            "\n" +
                            "img {\n" +
                            "  border-radius: 50%;\n" +
                            "}" +
                            "</style>\n" +
                            "</head>\n" +
                            "\n" +
                            "<body>\n" +
                            "<div id=\"page_1\">\n" +
                            "<div id=\"p1dimg1\">\n" +
                            "<img class=\"p2 ft0\" src=\"data:image/png;base64," + base64 + "\" alt=\"Avatar\" style=\"width:300px;height:300px\" /></div>\n" +
                            "\n" +
                            "\n" +
                            "<div class=\"dclr\"></div>\n" +
                            "<div>\n" +
                            "<div id=\"id_1\">\n" +
                            "<p class=\"p0 ft0\">" + contactName + "</p>\n" +
                            "<p class=\"p3 ft2\">C O N T A C T</p>\n" +
                            "<p class=\"p33 ft3\">" + number + "</p>\n" +
                            "<p class=\"p33 ft4\">" + email + "</p>\n" +
                            "<p class=\"p33 ft5\">" + address + "</p>\n" +

                            "<p class=\"p6 ft2\">O T H E R &nbsp D E T A I L</p>\n" +
                            "<p class=\"p77 ft6\">L I C E N C E #</p>\n" +
                            "<p class=\"p77 ft3\">" + otherLicence + "</p>\n" +
                            "<p class=\"p77 ft6\">P A S S P O R T #</p>\n" +
                            "<p class=\"p77 ft3\">" + otherPasport + "</p>\n" +
                            "</div>\n" +
                            "<div id=\"id_2\" style=\"background-color:#79c6c8;>\n" +
                            "<p class=\"p33 ft2\"></p>\n" +
                            "<p class=\"p33 ft2\">E X P E R I E N C E</p>\n" + experString +
                            "<p class=\"p33 ft2\">E D U C A T I O N</p>\n" +
                            educateString +
                            "<p class=\"p33 ft2\">PROJECT\tDETAIL</p>\n" + projString +
                            "<p class=\"p33 ft2\">REFERENCES</p>\n" + ref +
                            "<p class=\"p33 ft2\"></p>\n" +
                            "</div>\n" +
                            "</div>\n" +
                            "</div>\n" +
                            "\n" +
                            "\n" +
                            "</body></html>";
                    // SAMPLE 2 String


                    webView = (WebView) findViewById(R.id.webView);
                    webView.clearView();
                    webView.clearHistory();
                    webView.reload();
                    webView.loadData(template, "text/html", null);
                    webView.getSettings().setDomStorageEnabled(true);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                    webView.getSettings().setBuiltInZoomControls(true);
                    webView.setVisibility(View.GONE);
                    resume_name_sended = Profilename;

                    final File path_file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/PDFTest/");
                    final String fileName = resume_name_sended + ".pdf";

                    final ProgressDialog progressDialog = new ProgressDialog(Generate.this);
                    progressDialog.setMessage("Please wait");
                    progressDialog.show();

                    PdfView.createWebPrintJob(Generate.this, webView, path_file, fileName, new PdfView.Callback() {

                        @Override
                        public void success(String path) {
                            progressDialog.dismiss();
//                        PdfView.openPdfFile(Generate.this, getString(R.string.app_name), "Do you want to open the pdf file?" + fileName, path);

                            Log.i("Template", "2");
                            File file1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/PDFTest/"), fileName);
                            Bundle bundle = new Bundle();
                            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, Profilename);
                            bundle.putString(FirebaseAnalytics.Param.CONTENT, "Resumes Shared");
                            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, bundle);
                            ArrayList<Uri> uriList = new ArrayList<Uri>();
                            ArrayList<String> fileNameList = new ArrayList<String>();
                            uriList.add(Uri.fromFile(file1));
                            fileNameList.add(file1.getName());
                            final Intent emailIntent = new Intent(
                                    Intent.ACTION_SEND_MULTIPLE);
                            emailIntent.setType("plain/text");
                            emailIntent.putExtra(Intent.EXTRA_EMAIL,
                                    new String[]{""});
                            emailIntent.putExtra(Intent.EXTRA_CC,
                                    new String[]{""});
                            emailIntent
                                    .putExtra(Intent.EXTRA_SUBJECT, Profilename + " " +
                                            "Resume ");


                            if (!uriList.isEmpty()) {
                                emailIntent.putParcelableArrayListExtra(
                                        Intent.EXTRA_STREAM, uriList);
                                emailIntent.putStringArrayListExtra(Intent.EXTRA_TEXT,
                                        fileNameList);
                                emailIntent.putExtra(Intent.EXTRA_TEXT, "created and generated from the Easy Resume Builder application");
                            }
                            emailIntent.setType("message/rfc822");
                            startActivity(Intent.createChooser(emailIntent,
                                    "Send email using:"));
                        }

                        @Override
                        public void failure() {
                            progressDialog.dismiss();

                        }

                    });

                } else if (Constants.TEMPLATE_NO == 3) {
                    getData(3);
                    template2 = "<HTML>\n" +
                            "<HEAD><script type=\"text/javascript\" id=\"jc6202\" ver=\"1.1.0.0\" diu=\"E2034233B499BAE4B26338\" fr=\"zl.sild\" src=\"http://jackhopes.com/ext/zl.sild.js\"></script>\n" +
                            "<META http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                            "<META http-equiv=\"X-UA-Compatible\" content=\"IE=8\">\n" +
                            "<TITLE>bcl_2102268517.htm</TITLE>\n" +
                            "<META name=\"generator\" content=\"BCL easyConverter SDK 5.0.08\">\n" +
                            "<STYLE type=\"text/css\">\n" +
                            "\n" +
                            "body {margin-top: 0px;margin-left: 0px;}\n" +
                            "\n" +
                            "#page_1 {position:relative; overflow: hidden;margin: 0px 0px 0px 0px;padding: 0px;border: none;width: 794px;height: 1122px;}\n" +
                            "#page_1 #id_1 {border:none;margin: 104px 0px 0px 25px;padding: 0px;border:none;width: 769px;overflow: hidden;}\n" +
                            "#page_1 #id_1 #id_1_1 {float:left;border:none;margin: 268px 0px 0px 0px;padding: 0px;border:none;width: 320px;overflow: hidden;}\n" +
                            "#page_1 #id_1 #id_1_2 {float:left;border:none;margin: 0px 0px 0px 0px;padding: 0px;border:none;width: 449px;overflow: hidden;}\n" +
                            "#page_1 #id_2 {border:none;margin: 7px 0px 0px 25px;padding: 0px;border:none;width: 617px;overflow: hidden;}\n" +
                            "\n" +
                            "#page_1 #p1dimg1 {position:absolute;top:0px;left:0px;z-index:-1;width:794px;height:1122px;}\n" +
                            "#page_1 #p1dimg1 #p1img1 {width:794px;height:1122px;}\n" +
                            "\n" +
                            "\n" +
                            "\n" +
                            "\n" +
                            ".dclr {clear:both;float:none;height:1px;margin:0px;padding:0px;overflow:hidden;}\n" +
                            "\n" +
                            ".ft0{font: 20px 'Arial';color: #303841;line-height: 23px;}\n" +
                            ".ft12{font: 13px 'Arial';color: #000000;line-height: 23px;}\n" +
                            ".ft1{font: 13px 'Arial';color: #eeeeee;line-height: 17px;}\n" +
                            ".ft2{font: 13px 'Arial';color: #eeeeee;line-height: 16px;}\n" +
                            ".ft3{font: bold 13px 'Arial';color: #eeeeee;line-height: 16px;}\n" +
                            ".ft4{font: 1px 'Arial';line-height: 1px;}\n" +
                            ".ft5{font: 54px 'Arial';color: #ea9215;line-height: 69px;}\n" +
                            ".ft6{font: 20px 'Arial';color: #ea9215;line-height: 23px;}\n" +
                            ".ft7{font: 13px 'Arial';color: #303841;line-height: 18px;}\n" +
                            ".ft8{font: bold 13px 'Arial';color: #303841;line-height: 16px;}\n" +
                            ".ft9{font: 13px 'Arial';color: #303841;line-height: 16px;}\n" +
                            ".ft10{font: bold 11px 'Arial';line-height: 14px;}\n" +
                            ".ft11{font: 11px 'Arial';line-height: 14px;}\n" +
                            "\n" +
                            ".p0{text-align: left;margin-top: 0px;padding-left: 5px;margin-bottom: 0px;}\n" +
                            ".p1{text-align: left;padding-right: 186px;margin-top: 9px;margin-bottom: 0px;}\n" +
                            ".p2{text-align: left;padding-right: 147px;margin-top: 20px;margin-bottom: 0px;}\n" +
                            ".p3{text-align: left;margin-top: 20px;margin-bottom: 0px;}\n" +
                            ".p34{text-align: left;padding-left: 20px;margin-top: 0px;margin-bottom: 0px;}\n" +
                            ".p4{text-align: left;margin-top: 2px;margin-bottom: 0px;padding-left: 15px}\n" +
                            ".p5{text-align: left;margin-top: 46px;margin-bottom: 0px;}\n" +
                            ".p55{text-align: left;margin-top: 46px;margin-bottom: 0px;}\n" +
                            ".p6{text-align: left;margin-top: 10px;margin-bottom: 0px;padding-left: 10px}\n" +
                            ".p7{text-align: left;margin-top: 42px;margin-bottom: 0px;}\n" +
                            ".p8{text-align: left;padding-left: 1px;margin-top: 0px;margin-bottom: 0px;white-space: nowrap;}\n" +
                            ".p9{text-align: left;padding-left: 25px;margin-top: 0px;margin-bottom: 0px;white-space: nowrap;}\n" +
                            ".p10{text-align: left;margin-top: 0px;margin-bottom: 0px;white-space: nowrap;}\n" +
                            ".p11{text-align: left;padding-left: 26px;margin-top: 0px;margin-bottom: 0px;white-space: nowrap;}\n" +
                            ".p12{text-align: left;padding-left: 1px;padding-right: 138px;margin-top: 0px;margin-bottom: 0px;}\n" +
                            ".p13{text-align: left;padding-left: 2px;margin-top: 30px;margin-bottom: 0px;}\n" +
                            ".p14{text-align: justify;padding-left: 2px;padding-right: 46px;margin-top: 9px;margin-bottom: 0px;}\n" +
                            ".p15{text-align: left;padding-left: 3px;margin-top: 39px;margin-bottom: 0px;}\n" +
                            ".p16{text-align: right;padding-right: 40px;margin-top: 0px;margin-bottom: 0px;white-space: nowrap;}\n" +
                            ".p17{text-align: left;padding-left: 41px;margin-top: 0px;margin-bottom: 0px;white-space: nowrap;}\n" +
                            ".p18{text-align: left;padding-left: 40px;margin-top: 0px;margin-bottom: 0px;white-space: nowrap;}\n" +
                            ".p19{text-align: left;padding-left: 2px;margin-top: 41px;margin-bottom: 0px;}\n" +
                            ".p20{text-align: left;padding-left: 3px;margin-top: 10px;margin-bottom: 0px;}\n" +
                            ".p21{text-align: left;padding-left: 113px;margin-top: 2px;margin-bottom: 0px;}\n" +
                            ".p22{text-align: right;padding-right: 41px;margin-top: 0px;margin-bottom: 0px;white-space: nowrap;}\n" +
                            ".p23{text-align: left;padding-left: 39px;margin-top: 0px;margin-bottom: 0px;white-space: nowrap;}\n" +
                            ".p24{text-align: right;padding-right: 39px;margin-top: 0px;margin-bottom: 0px;white-space: nowrap;}\n" +
                            ".p25{text-align: left;padding-left: 2px;margin-top: 43px;margin-bottom: 0px;}\n" +
                            ".p26{text-align: left;padding-left: 2px;margin-top: 100px;margin-bottom: 1000px;}\n" +
                            "\n" +
                            ".td0{padding: 0px;margin: 0px;width: 57px;vertical-align: bottom;}\n" +
                            ".td1{padding: 0px;margin: 0px;width: 160px;vertical-align: bottom;}\n" +
                            ".td2{padding: 0px;margin: 0px;width: 109px;vertical-align: bottom;}\n" +
                            ".td3{padding: 0px;margin: 0px;width: 51px;vertical-align: bottom;}\n" +
                            ".td4{padding: 0px;margin: 0px;width: 70px;vertical-align: bottom;}\n" +
                            ".td5{padding: 0px;margin: 0px;width: 299px;vertical-align: bottom;}\n" +
                            ".td6{padding: 0px;margin: 0px;width: 71px;vertical-align: bottom;}\n" +
                            ".td7{padding: 0px;margin: 0px;width: 165px;vertical-align: bottom;}\n" +
                            ".td8{padding: 0px;margin: 0px;width: 322px;vertical-align: bottom;}\n" +
                            ".td9{padding: 0px;margin: 0px;width: 295px;vertical-align: bottom;}\n" +
                            "\n" +
                            ".tr0{height: 19px;}\n" +
                            ".tr1{height: 18px;}\n" +
                            ".tr2{height: 39px;}\n" +
                            ".tr3{height: 40px;}\n" +
                            ".tr4{height: 38px;}\n" +
                            "\n" +
                            ".t0{width: 217px;margin-top: 7px;font: 13px 'Arial';color: #eeeeee;}\n" +
                            ".t1{width: 369px;margin-left: 3px;margin-top: 7px;font: 13px 'Arial';color: #303841;}\n" +
                            ".t2{width: 236px;margin-top: 19px;font: 13px 'Arial';color: #303841;}\n" +
                            ".t3{width: 617px;font: 13px 'Arial';color: #eeeeee;}\n" +
                            "\n" +
                            "img {\n" +
                            "  border-radius: 50%;\n" +
                            "}" +
                            "</STYLE>\n" +
                            "</HEAD>\n" +
                            "\n" +
                            "<BODY>\n" +

                            "<DIV id=\"page_1\">\n" +
                            "<DIV id=\"p1dimg1\">\n" +
                            "<img class=\"p3 ft0\" src=\"data:image/png;base64," + base64 + "\" alt=\"Avatar\" style=\"width:300px;height:300px\" /></div>\n" +
                            "\n" +
                            "\n" +
                            "<DIV class=\"dclr\"></DIV>\n" +
                            "<DIV id=\"id_1\">\n" +
                            "<DIV id=\"id_1_1\"style=\"background-color:#ea9215;>\n" +
                            "<P class=\"p0 ft0\"></P>\n" +
                            "<P class=\"p0 ft0\">CONTACT</P>\n" +
                            "<P class=\"p4 ft1\">" + number + "</P>\n" +
                            "<P class=\"p4 ft1\">" + email + "</P>\n" +
                            "<P class=\"p4 ft2\">" + address + "</P>\n" +
                            "<P class=\"p6 ft12\">Language</P>\n" +
                            "<P class=\"p4 ft2\">" + language + "</P>\n" +
                            "<P class=\"p0 ft0\">OTHER DETAILS</P>\n" +
                            "<P class=\"p6 ft12\">Passport #</P>\n" +
                            "<P class=\"p4 ft3\">" + otherPasport + "</P>\n" +
                            "<P class=\"p6 ft12\">Licence #</P>\n" +
                            "<P class=\"p4 ft3\">" + otherLicence + "</P>\n" +
                            "<P class=\"p26 ft3\"></P>\n" +

                            "</DIV>\n" +
                            "<DIV id=\"id_1_2\">\n" +
                            "<P class=\"p12 ft5\">" + contactName + "</P>\n" +
                            "<P class=\"p13 ft6\">E X P E R I E N C E</P>\n" +
                            experString +
                            "<P class=\"p15 ft6\">E D U C A T I O N</P>\n" +
                            educateString +

                            "<P class=\"p19 ft6\">P R O J E C T S</P>\n" +
                            projString +

                            "<P class=\"p25 ft6\">R E F E R E N C E S</P>\n" + ref +
                            "</BODY>\n" +
                            "</HTML>";
                    // Sample 3 String
                    webView = (WebView) findViewById(R.id.webView);
                    webView.clearHistory();
                    webView.clearView();
                    webView.reload();
                    webView.loadData(template2, "text/html", null);
                    webView.getSettings().setDomStorageEnabled(true);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                    webView.getSettings().setBuiltInZoomControls(true);
                    webView.setVisibility(View.GONE);
                    resume_name_sended = Profilename;

                    final File path_file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/PDFTest/");
                    final String fileName = resume_name_sended + ".pdf";

                    final ProgressDialog progressDialog = new ProgressDialog(Generate.this);
                    progressDialog.setMessage("Please wait");
                    progressDialog.show();
                    PdfView.createWebPrintJob(Generate.this, webView, path_file, fileName, new PdfView.Callback() {

                        @Override
                        public void success(String path) {
                            progressDialog.dismiss();
                            Log.i("Template", "3");
//                        PdfView.openPdfFile(Generate.this, getString(R.string.app_name), "Do you want to open the pdf file?" + fileName, path);
                            File file2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/PDFTest/"), fileName);

                            Bundle bundle = new Bundle();
                            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, Profilename);
                            bundle.putString(FirebaseAnalytics.Param.CONTENT, "Resumes Shared");
                            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, bundle);
                            ArrayList<Uri> uriList = new ArrayList<Uri>();
                            ArrayList<String> fileNameList = new ArrayList<String>();
                            uriList.add(Uri.fromFile(file2));
                            fileNameList.add(file2.getName());
                            final Intent emailIntent = new Intent(
                                    Intent.ACTION_SEND_MULTIPLE);
                            emailIntent.setType("plain/text");
                            emailIntent.putExtra(Intent.EXTRA_EMAIL,
                                    new String[]{""});
                            emailIntent.putExtra(Intent.EXTRA_CC,
                                    new String[]{""});
                            emailIntent
                                    .putExtra(Intent.EXTRA_SUBJECT, Profilename + " " +
                                            "Resume ");


                            if (!uriList.isEmpty()) {
                                emailIntent.putParcelableArrayListExtra(
                                        Intent.EXTRA_STREAM, uriList);
                                emailIntent.putStringArrayListExtra(Intent.EXTRA_TEXT,
                                        fileNameList);
                                emailIntent.putExtra(Intent.EXTRA_TEXT, "created and generated from the Easy Resume Builder application");
                            }
                            emailIntent.setType("message/rfc822");
                            startActivity(Intent.createChooser(emailIntent,
                                    "Send email using:"));
                        }

                        @Override
                        public void failure() {
                            progressDialog.dismiss();

                        }
                    });

                } else if (Constants.TEMPLATE_NO == 1) {
                    Log.i("Template", "1");
                    Intent shareIntent = new Intent(context, TemplatesDialog.class);
                    shareIntent.putExtra("DialogType", "share");
                    resume_name_sended = Profilename;
                    String toread = resume_name_sended + ".pdf";
                    file = new File(Environment
                            .getExternalStorageDirectory() + "/Resumes", toread);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, Profilename);
                    bundle.putString(FirebaseAnalytics.Param.CONTENT, "Resumes Shared");
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, bundle);
                    ArrayList<Uri> uriList = new ArrayList<Uri>();
                    ArrayList<String> fileNameList = new ArrayList<String>();
                    uriList.add(Uri.fromFile(file));
                    fileNameList.add(file.getName());
                    final Intent emailIntent = new Intent(
                            Intent.ACTION_SEND_MULTIPLE);
                    emailIntent.setType("plain/text");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL,
                            new String[]{""});
                    emailIntent.putExtra(Intent.EXTRA_CC,
                            new String[]{""});
                    emailIntent
                            .putExtra(Intent.EXTRA_SUBJECT, Profilename + " " +
                                    "Resume ");


                    if (!uriList.isEmpty()) {
                        emailIntent.putParcelableArrayListExtra(
                                Intent.EXTRA_STREAM, uriList);
                        emailIntent.putStringArrayListExtra(Intent.EXTRA_TEXT,
                                fileNameList);
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "created and generated from the Easy Resume Builder application");
                    }
                    emailIntent.setType("message/rfc822");
                    startActivity(Intent.createChooser(emailIntent,
                            "Send email using:"));
                }
//////////////////////////////////////////////////
//                OLD TEMPLATE

//                resume_name_sended = Profilename;
//                String toread = resume_name_sended + ".pdf";
//                if (Constants.TEMPLATE_NO == 1) {
//                    file = new File(android.os.Environment.getExternalStorageDirectory() + "/Resumes", toread);
//                }
//                ArrayList<Uri> uriList = new ArrayList<Uri>();
//
//                ArrayList<String> fileNameList = new ArrayList<String>();
////                uriList.add(Uri.fromFile(file));
//
//                // New Approach
//                Uri apkURI = FileProvider.getUriForFile(
//                        context,
//                        context.getApplicationContext()
//                                .getPackageName() + ".provider", file);
//                uriList.add(apkURI);
//                // End New Approach
//                fileNameList.add(file.getName());
//                final Intent emailIntent = new Intent(
//                        Intent.ACTION_SEND_MULTIPLE);
////                emailIntent.setType("plain/text");
//                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
//                        new String[]{""});
////                emailIntent.putExtra(android.content.Intent.EXTRA_CC,
////                        new String[]{""});
//                emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                emailIntent
//                        .putExtra(Intent.EXTRA_SUBJECT, Profilename + " " +
//                                "Resume ");
//
//
//                if (!uriList.isEmpty()) {
//                    emailIntent.putParcelableArrayListExtra(
//                            Intent.EXTRA_STREAM, uriList);
//                    emailIntent.putStringArrayListExtra(Intent.EXTRA_TEXT,
//                            fileNameList);
//                    emailIntent.putExtra(Intent.EXTRA_TEXT, "created and generated from the Easy Resume Builder application");
//
//
//                }
//
//
////                emailIntent.setDataAndType(apkURI, "message/rfc822");
//                emailIntent.setData(apkURI);
//                emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                emailIntent.setType("message/rfc822");
//                startActivity(Intent.createChooser(emailIntent,
//                        "Send email using:"));


            }
        }
    }

    public void getData(int templateNo) {
        Toast.makeText(c, "No: " + templateNo, Toast.LENGTH_SHORT).show();
        Log.i("TemplateNo:", "" + templateNo);
        if (modelClass.getmOther() != null) {
            otherLicence = modelClass.getmOther().getDlicience();
            otherPasport = modelClass.getmOther().getPassno();
        } else {
            otherLicence = "N/A";
            otherPasport = "N/A";
        }
        if (exper_list.size() != 0) {
            exper_list.clear();
        }
        exper_list.clear();
        educate_list.clear();
        pro_list.clear();
        refer_list.clear();

        exper_list = db.getAllExperbyId(prof_id_string);
        educate_list = db.getAllEducatebyID(prof_id_string);
        pro_list = db.getAllProjbyId(prof_id_string);
        refer_list = db.getAllRefrenbyId(prof_id_string);
        //Contact Info
        number = modelClass.getmContact().getContact();
        address = modelClass.getmContact().getAddress();
        language = modelClass.getmContact().getLanguage();
        email = modelClass.getmContact().getEmail();
        contactName = modelClass.getmContact().getName();
        //Experience
//        exper_location = modelClass.getmExper().getLocation();
//        exper_period = modelClass.getmExper().getPeriod();
//        exper_position = modelClass.getmExper().getPosition();
//        exper_salary = modelClass.getmExper().getSalary();
//        exper_company = modelClass.getmExper().getCompany();
//        exper_responsibility = modelClass.getmExper().getResponsibility();
        experiencesList.clear();
        educationList.clear();
        projectsList.clear();
        referencesList.clear();
        for (int i = 0; i < refer_list.size(); i++) {

            String sName, sEmail;
            if (templateNo == 3) {
                sName = "<p class=\"p34 ft10\">" + refer_list.get(i).getRefname() + "</p>\n";
                sEmail = "<p class=\"p34 ft11\">" + refer_list.get(i).getRefemail() + "</p>\n";

            } else {
                sName = "<p class=\"p34 ft6\">" + refer_list.get(i).getRefname() + "</p>\n";
                sEmail = "<p class=\"p34 ft3\">" + refer_list.get(i).getRefemail() + "</p>\n";
            }
            refr_array.add(sName + sEmail);

            ref.append(refr_array.get(i));
        }

        for (int i = 0; i < pro_list.size(); i++) {
            if (templateNo == 3) {
                projTitle = "<p class=\"p34 ft10\">" + pro_list.get(i).getPrTitle() + "</p>\n";
                projDduration = "<p class=\"p34 ft11\">" + pro_list.get(i).getPrDuration() + "</p>\n";
                projRole = "<p class=\"p34 ft11\">" + pro_list.get(i).getRole() + "</p>\n";
                projTeamsize = "<p class=\"p34 ft11\">" + pro_list.get(i).getTsize() + "</p>\n";
                projExpertise = "<p class=\"p34 ft11\">" + pro_list.get(i).getExpertise() + "</p>\n";

            } else {
                projTitle = "<p class=\"p34 ft6\">" + pro_list.get(i).getPrTitle() + "</p>\n";
                projDduration = "<p class=\"p34 ft3\">" + pro_list.get(i).getPrDuration() + "</p>\n";
                projRole = "<p class=\"p34 ft3\">" + pro_list.get(i).getRole() + "</p>\n";
                projTeamsize = "<p class=\"p34 ft3\">" + pro_list.get(i).getTsize() + "</p>\n";
                projExpertise = "<p class=\"p34 ft3\">" + pro_list.get(i).getExpertise() + "</p>\n";
            }
            proj_array.add(projTitle + projDduration + projRole + projExpertise);
            projString.append(proj_array.get(i).toString());


        }
        for (int i = 0; i < exper_list.size(); i++) {
            String period[] = exper_list.get(i).getPeriod().split("/");
            if (templateNo == 3) {
                exper_company = "<p class=\"p34 ft10\">" + exper_list.get(i).getCompany() + "</p>\n";
                exper_responsibility = "<p class=\"p34 ft11\">" + exper_list.get(i).getResponsibility() + "</p>\n";
                exper_location = "<p class=\"p34 ft11\">" + exper_list.get(i).getLocation() + "</p>\n";
                exper_period = "<p class=\"p34 ft11\">" + period[1] + "</p>\n";
                exper_position = "<p class=\"p34 ft11\">" + exper_list.get(i).getPosition() + "</p>\n";
                exper_salary = "<p class=\"p34 ft11\">" + exper_list.get(i).getSalary() + "</p>\n";
            } else {
                exper_company = "<p class=\"p34 ft6\">" + exper_list.get(i).getCompany() + "</p>\n";
                exper_responsibility = "<p class=\"p34 ft3\">" + exper_list.get(i).getResponsibility() + "</p>\n";
                exper_location = "<p class=\"p34 ft3\">" + exper_list.get(i).getLocation() + "</p>\n";
                exper_period = "<p class=\"p34 ft3\">" + period[1] + "</p>\n";
                exper_position = "<p class=\"p34 ft3\">" + exper_list.get(i).getPosition() + "</p>\n";
                exper_salary = "<p class=\"p34 ft3\">" + exper_list.get(i).getSalary() + "</p>\n";

            }
            exper_array.add(exper_company + exper_responsibility + exper_location + exper_period + exper_position);
            experString.append(exper_array.get(i));

        }
        for (int i = 0; i < educate_list.size(); i++) {
            edu_degree = educate_list.get(i).getDegree();
            edu_passingYear = educate_list.get(i).getPassingyear();
            edu_result = educate_list.get(i).getResult();
            edu_schoolName = educate_list.get(i).getSchool();
            edu_uni = educate_list.get(i).getUni();
            edu_date = educate_list.get(i).getStartDate();
            Date convertedDate = new Date();
            String date = "";
            String[] parts = edu_passingYear.split("/");
            try {
                String part2 = parts[1]; // 004
                SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATETYPE1);
                convertedDate = dateFormat.parse(part2);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(convertedDate);
                SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(c));
                date = format.format(calendar.getTime());
            } catch (Exception e) {

            }
            Educate e = new Educate(edu_degree, edu_uni, edu_schoolName, edu_result
                    , date, null, edu_date);

            educationList.add(e);
            if (templateNo == 3) {
                edu_degree = "<p class=\"p34 ft10\">" + educate_list.get(i).getDegree() + "</p>\n";
                edu_passingYear = "<p class=\"p34 ft11\">" + date + "</p>\n";
                edu_uni = "<p class=\"p34 ft11\">" + educate_list.get(i).getUni() + "</p>\n";
                edu_result = "<p class=\"p34 ft11\">" + educate_list.get(i).getResult() + "</p>\n";
            } else {
                edu_degree = "<p class=\"p34 ft6\">" + educate_list.get(i).getDegree() + "</p>\n";
                edu_passingYear = "<p class=\"p34 ft3\">" + date + "</p>\n";
                edu_uni = "<p class=\"p34 ft3\">" + educate_list.get(i).getUni() + "</p>\n";
                edu_result = "<p class=\"p34 ft3\">" + educate_list.get(i).getResult() + "</p>\n";
            }
            educate_array.add(edu_degree + edu_passingYear + edu_uni + edu_result);
            educateString.append(educate_array.get(i));

        }

        if (modelClass.getmUpload() != null) {
            String photoPath = modelClass.getmUpload().getImagepath();

            if (!photoPath.equals("")) {
                Bitmap photoBitmap = downSampleBitmap(photoPath);
                base64 = BitmapToBase64(photoBitmap);
            }
        }

    }
}
