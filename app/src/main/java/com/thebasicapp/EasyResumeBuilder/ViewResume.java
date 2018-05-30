package com.thebasicapp.EasyResumeBuilder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.webviewtopdf.PdfView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Helper.Constants;
import Helper.UIHelper;


public class ViewResume extends Activity {
    Context c;
    WebView webView;
    String template = "";
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
    DatabaseHandler db;
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
    Intent in;
    String proId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_resume);
        c = ViewResume.this;
        db = new DatabaseHandler(c);
        in = getIntent();
        proId = in.getStringExtra("profID");
        modelClass = GenerateResume.modelClass;
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
        exper_list = db.getAllExperbyId(proId);
        educate_list = db.getAllEducatebyID(proId);
        pro_list = db.getAllProjbyId(proId);
        refer_list = db.getAllRefrenbyId(proId);
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
//            refname = refer_list.get(i).getRefname();
//            refemail = refer_list.get(i).getRefemail();
//            Refren r = new Refren(0, refname, null, null,
//                    refemail, null);
//            referencesList.add(r);
            String sName, sEmail;
            if (Constants.TEMPLATE_NO == 3) {
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
//            projTitle = pro_list.get(i).getPrTitle();
//            projDduration = pro_list.get(i).getPrDuration();
//            projRole = pro_list.get(i).getRole();
//            projTeamsize = pro_list.get(i).getTsize();
//            projExpertise = pro_list.get(i).getExpertise();
//            Proj p = new Proj(0, projTitle, projDduration, projRole, projTeamsize
//                    , projExpertise, null);
//            projectsList.add(p);

            if (Constants.TEMPLATE_NO == 3) {
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

            if (Constants.TEMPLATE_NO == 3) {
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

            if (Constants.TEMPLATE_NO == 3) {
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
        String base64 = "";
        if (modelClass.getmUpload() != null) {
            String photoPath = modelClass.getmUpload().getImagepath();

            if (!photoPath.equals("")) {
                Bitmap photoBitmap = downSampleBitmap(photoPath);
                base64 = BitmapToBase64(photoBitmap);
            }
        }
        if (Constants.TEMPLATE_NO == 3) {
            template = "<HTML>\n" +
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
        } else {
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
        }

        webView = (WebView) findViewById(R.id.webView);
        webView.loadData(template, "text/html", null);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setBuiltInZoomControls(true);
        ModelClass m = GenerateResume.modelClass;
        Button open = (Button) findViewById(R.id.open_pdf);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS + "/PDFTest/");
                final String fileName = "Test.pdf";

                final ProgressDialog progressDialog = new ProgressDialog(c);
                progressDialog.setMessage("Please wait");
                progressDialog.show();
                PdfView.createWebPrintJob(ViewResume.this, webView, path, fileName, new PdfView.Callback() {

                    @Override
                    public void success(String path) {
                        progressDialog.dismiss();
                        PdfView.openPdfFile(ViewResume.this, getString(R.string.app_name), "Do you want to open the pdf file?" + fileName, path);

                    }

                    @Override
                    public void failure() {
                        progressDialog.dismiss();

                    }
                });
            }

        });


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
}
