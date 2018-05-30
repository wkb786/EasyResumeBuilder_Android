package com.thebasicapp.EasyResumeBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ParseException;
import android.util.Log;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DrawInterface;
import com.itextpdf.text.pdf.draw.LineSeparator;

import Helper.Constants;
import Helper.UIHelper;

public class GenerateResume {
    public static Context ctx;
    static String name;
    static String gender;
    static String dob;
    static String address;
    static String languages;
    static String contacts;
    static String email;
    static Document document;
    public static ModelClass modelClass;

    public static String _id;
    public static String resume_name;
    public static String stillW;

    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);

    private static Font heading = new Font(Font.FontFamily.UNDEFINED, 20,
            Font.BOLD | Font.NORMAL);

    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font small = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL);

    private static Font smallbold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

    public void setId(String id, String resumname, String stillW) {
        this._id = id;
        this.resume_name = resumname;
        this.stillW = stillW;


    }

    public static void main(String[] args) {

        try {
            // boolean isPresent =
            // Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED
            // );
            // if(isPresent){
            File folder = new File(
                    android.os.Environment.getExternalStorageDirectory()

                            + "/Resumes");
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdir();
            }
            String resume = resume_name + ".pdf";
            File file = new File(folder.getAbsolutePath(), resume);
            if (!file.exists()) {
                file.createNewFile();
            }
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            // }
            modelClass = new ModelClass();
            document.open();
            addMetaData(document);
            addTitlePage(document);
            // addContent(document);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addMetaData(Document document) {
        document.addTitle("My Resume PDF");
        document.addSubject("Resume Generated");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("thebasicapp");
        document.addCreator("Technokeet");
    }

    static Date convertedDate;

    private static void addTitlePage(Document document)

            throws DocumentException {

        setBackground(document, ctx);
        DatabaseHandler db = new DatabaseHandler(ctx);
        final List<Contact> contact = db.getAllContacts();
        if (contact.size() != 0) {
            for (Contact cn : contact) {
                String prid = cn.getProfid();
                if (prid.equals(_id)) {
                    name = cn.getName();
                    dob = cn.getDOB();
                   /* if(!dob.toString().equalsIgnoreCase("")) {
                        dob = UIHelper.getConvertedDate(dob, ctx);
                    }*/
                    if (dob.contains("/")) {
                        String[] parts = dob.split("/");
                        Log.v("parts length", parts.length + "");
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
                                    SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(ctx));
                                    dob = format.format(calendar.getTime());
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
                                    SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(ctx));
                                    dob = format.format(calendar.getTime());
                                } catch (ParseException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (java.text.ParseException e) {
                                    e.printStackTrace();
                                }
                            } else if (part1.equalsIgnoreCase("Type3")) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATETYPE3);
                                convertedDate = new Date();
                                try {
                                    convertedDate = dateFormat.parse(part2);
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTime(convertedDate);
                                    SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(ctx));
                                    dob = format.format(calendar.getTime());
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
                                    SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(ctx));
                                    dob = format.format(calendar.getTime());
                                } catch (ParseException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (java.text.ParseException e) {
                                    e.printStackTrace();
                                }
                                System.out.println(convertedDate);
                            }
                        } else {
                            dob = "";
                        }
                    }
                    gender = cn.getGender();
                    address = cn.getAddress();
                    languages = cn.getLanguage();
                    contacts = cn.getContact();
                    email = cn.getEmail();

                    Contact c = new Contact(Integer.parseInt(prid), name, gender, dob, address
                            , languages, contacts, email, prid);
                    modelClass.setmContact(c);

                }
            }

        } else {

        }
        Chunk sigUnderline = new Chunk("");
        sigUnderline.setUnderline(0.1f, -2f);
        BaseColor colorGrey = BaseColor.GRAY;
        Chunk linebreak = new Chunk((DrawInterface) new LineSeparator(4f, 100f,
                colorGrey, Element.ALIGN_CENTER, -1));
        // doc.Add(linebreak);

        /*
         * Paragraph Header = new Paragraph("Document Header",
         * FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLDITALIC));
         * document.add(Header);
         */

        if (contact.size() != 0) {
            Paragraph preface1 = new Paragraph();
            preface1.add(new Paragraph("" + name, catFont));
            // preface1.setAlignment(Element.ALIGN_CENTER);
            preface1.setAlignment(Element.HEADER);
            // preface1.add(sigUnderline);

            Paragraph preface = new Paragraph();
            // We add one empty line
            // addEmptyLine(preface, 1);
            // Lets write a big header
            preface.add(new Paragraph(ctx.getResources().getString(
                    R.string.gender)
                    + "             : " + gender, small));
            preface.add(new Paragraph(ctx.getResources().getString(
                    R.string.contactno)
                    + "      : " + contacts, small));
            preface.add(new Paragraph(ctx.getResources().getString(
                    R.string.email)
                    + "               : " + email, small));
            if (!dob.equalsIgnoreCase("")) {
                preface.add(new Paragraph(ctx.getResources()
                        .getString(R.string.dob) + "                : " + dob,
                        small));
            }

            preface.add(new Paragraph(ctx.getResources().getString(
                    R.string.langs)
                    + "       : " + languages, small));
            preface.add(new Paragraph(ctx.getResources().getString(
                    R.string.address)
                    + "           : " + address, small));

            document.add(preface1);
            document.add(preface);
        } else {
//			Paragraph preface2 = new Paragraph();
//			preface2.add(new Paragraph(ctx.getResources().getString(
//					R.string.no_personal_info), heading));
//			addEmptyLine(preface2, 5);
//			document.add(preface2);
            // document.newPage();
        }

        document.add(linebreak);

        final List<Upload> upload = db.getAllUpload();
        if (upload.size() != 0) {

            for (Upload up : upload) {
                String prid = up.getProfid();
                if (prid.equals(_id)) {
                    String imageUrl = up.getImagepath();
                    Image image = null;

                    try {
                        image = image.getInstance(imageUrl);
                    } catch (MalformedURLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    image.setAbsolutePosition(450f, 700f);
                    image.scaleAbsolute(100f, 100f);

                    Upload u = new Upload(imageUrl,prid);
                    modelClass.setmUpload(u);
                    document.add(image);
                }
            }

        }

        Date date;
        Date date1;
        String displayDate = "";

        final List<Educate> educate = db.getAllEducatebyID(_id);
        Collections.sort(educate, new CustomComparatorEdu());
//		final List<Educate> educate = db.getAllEducationByDate();
        if (educate.size() != 0) {

            Paragraph subPara = new Paragraph();
            String mystring = ctx.getString(R.string.educationdetails);
            subPara.add(new Paragraph("" + mystring + ":", catFont));

            // document.add(linebreak);
            addEmptyLine(subPara, 1);
            // Section subCatPart = catPart.addSection(subPara);
            createTable(subPara);
            addEmptyLine(subPara, 1);

            document.add(subPara);
        } else {

//			Paragraph preface2 = new Paragraph();
//			String mystring = ctx.getString(R.string.educationdetails);
//			preface2.add(new Paragraph(ctx.getResources()
//					.getString(R.string.no) + " " + mystring + ":", heading));
//			addEmptyLine(preface2, 4);
//			document.add(preface2);
        }
        document.add(linebreak);

        final List<Exper> exper = db.getAllExperbyId(_id);
        Collections.sort(exper, new CustomComparator());
//		final List<Exper> exper = db.getAllExperbydate();
        String company, position, period, location, salary, jobresponsibility;
        if (exper.size() != 0) {
            Paragraph preface2 = new Paragraph();
            String mystring = ctx.getString(R.string.experiencedetails);
            preface2.add(new Paragraph("" + mystring + ":", catFont));
            createTableex(preface2);
            addEmptyLine(preface2, 1);
            document.add(preface2);
            int align = 1;
            for (Exper ex : exper) {
                String prid = ex.getProfid();
                if (prid.equals(_id)) {
                    company = ex.getCompany();
                    position = ex.getPosition();
                    period = ex.getPeriod();
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
                            SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(ctx));
                            displayDate = format.format(calendar.getTime());
                        } catch (java.text.ParseException e1) {
                            e1.printStackTrace();
                        }
                        String part2 = parts[2];
                        if (android.text.TextUtils.isDigitsOnly(part2.substring(part2.length() - 2, part2.length() - 1))) {
                            try {
                                SimpleDateFormat dateFormat2 = new SimpleDateFormat(Constants.DATETYPE1);
                                date1 = new Date();
                                date1 = dateFormat.parse(part2);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date1);
                                SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(ctx));
                                displayDate = displayDate + " To " + format.format(calendar.getTime());
                            } catch (java.text.ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        } else {
                            displayDate = displayDate + " " + stillW;
                        }
                        period = displayDate;
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
                            SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(ctx));
                            displayDate = format.format(calendar.getTime());
                        } catch (java.text.ParseException e1) {
                            e1.printStackTrace();
                        }
                        String part2 = parts[2];
                        if (android.text.TextUtils.isDigitsOnly(part2.substring(part2.length() - 2, part2.length() - 1))) {
                            try {
                                SimpleDateFormat dateFormat2 = new SimpleDateFormat(Constants.DATETYPE2);
                                date1 = new Date();
                                date1 = dateFormat.parse(part2);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date1);
                                SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(ctx));
                                displayDate = displayDate + " To " + format.format(calendar.getTime());
                            } catch (java.text.ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        } else {
                            displayDate = displayDate + " " + stillW;
                        }
                        period = displayDate;
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
                            SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(ctx));
                            displayDate = format.format(calendar.getTime());
                        } catch (java.text.ParseException e1) {
                            e1.printStackTrace();
                        }
                        String part2 = parts[2];
                        if (android.text.TextUtils.isDigitsOnly(part2.substring(part2.length() - 2, part2.length() - 1))) {
                            try {
                                SimpleDateFormat dateFormat2 = new SimpleDateFormat(Constants.DATETYPE3);
                                date1 = new Date();
                                date1 = dateFormat.parse(part2);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date1);
                                SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(ctx));
                                displayDate = displayDate + " To " + format.format(calendar.getTime());
                            } catch (java.text.ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        } else {
                            displayDate = displayDate + " " + stillW;
                        }
                        period = displayDate;
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
                            SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(ctx));
                            displayDate = format.format(calendar.getTime());
                        } catch (java.text.ParseException e1) {
                            e1.printStackTrace();
                        }
                        String part2 = parts[2];
                        if (android.text.TextUtils.isDigitsOnly(part2.substring(part2.length() - 2, part2.length() - 1))) {
                            try {
                                SimpleDateFormat dateFormat2 = new SimpleDateFormat(Constants.DATETYPE4);
                                date1 = new Date();
                                date1 = dateFormat.parse(part2);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date1);
                                SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(ctx));
                                displayDate = displayDate + " To " + format.format(calendar.getTime());
                            } catch (java.text.ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        } else {
                            displayDate = displayDate + " " + stillW;
                        }
                        period = displayDate;
                    }
                  /*  String[] parts = period.toString().split(" To ");
                    String part1 = parts[0]; // 004
                    try {
                        date = UIHelper.parseDate(part1);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(ctx));
                        displayDate = format.format(calendar.getTime());
                    } catch (java.text.ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Log.v("position1", parts[0]);
                    Log.v("position2", parts[1]);
                    String part2 = parts[1];
                    if (!part2.startsWith("Still")) {
                        try {
                            date1 = UIHelper.parseDate(part2);
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(date1);
                            SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(ctx));
                            displayDate = displayDate + " To " + format.format(calendar.getTime());
                        } catch (java.text.ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else {
                        displayDate = displayDate + " To " + "Still Working";
                    }*/
                    location = ex.getLocation();
                    salary = ex.getSalary();
                    jobresponsibility = ex.getResponsibility();

                    Paragraph prefaceex = new Paragraph();
                    prefaceex.add(new Paragraph(ctx.getResources().getString(
                            R.string.company)
                            + "              : " + company, small));
                    prefaceex
                            .add(new Paragraph(ctx.getResources().getString(
                                    R.string.position)
                                    + "                      : " + position,
                                    small));
                    prefaceex.add(new Paragraph(ctx.getResources().getString(
                            R.string.period)
                            + "                     : " + displayDate, small));
                    if (!location.equalsIgnoreCase("")) {
                        prefaceex.add(new Paragraph(ctx.getResources().getString(
                                R.string.location)
                                + "                     : " + location,
                                small));
                    }
                    if (!salary.equals("") && !salary.equals(null)) {
                        prefaceex.add(new Paragraph(ctx.getResources().getString(
                                R.string.salary)
                                + "                          : " + salary,
                                small));
                    }
                    prefaceex.add(new Paragraph(ctx.getResources().getString(
                            R.string.jobresponsibility)
                            + "      : " + jobresponsibility, small));

                    addEmptyLine(prefaceex, 1);
                    if (align % 2 == 0) {
                        prefaceex.setAlignment(Element.ALIGN_RIGHT);
                    }
                    align++;
                    document.add(prefaceex);
                }
            }

        } else {
//			Paragraph preface2 = new Paragraph();
//			String mystring = ctx.getString(R.string.experiencedetails);
//			preface2.add(new Paragraph(ctx.getResources()
//					.getString(R.string.no) + " " + mystring + ":", heading));
//			addEmptyLine(preface2, 4);
//			document.add(preface2);
        }

        document.add(linebreak);

        final List<Proj> proj = db.getAllProjbyId(_id);
        String title, duration, role, teamsize, expertise;
        if (proj.size() != 0) {
            Paragraph preface2 = new Paragraph();
            String mystring = ctx.getString(R.string.projectdetails);
            preface2.add(new Paragraph("" + mystring + ":", catFont));
            addEmptyLine(preface2, 1);
            document.add(preface2);
            for (Proj pr : proj) {
                String prid = pr.getProfid();
                if (prid.equals(_id)) {
                    title = pr.getPrTitle();
                    duration = pr.getPrDuration();
                    role = pr.getRole();
                    teamsize = pr.getTsize();
                    expertise = pr.getExpertise();
                    expertise = expertise.substring(0, 1).toUpperCase() + expertise.substring(1);

                    Paragraph prefaceex = new Paragraph();
                    prefaceex.add(new Paragraph(ctx.getResources().getString(
                            R.string.ptitle)
                            + "             : " + title, small));
                    prefaceex.add(new Paragraph(ctx.getResources().getString(
                            R.string.pduration)
                            + "       : " + duration, small));
                    prefaceex
                            .add(new Paragraph(ctx.getResources().getString(
                                    R.string.role)
                                    + "                          : " + role,
                                    small));
                    if (!teamsize.equalsIgnoreCase("")) {
                        prefaceex.add(new Paragraph(ctx.getResources().getString(
                                R.string.tsize)
                                + "                 : " + teamsize, small));
                    }
                    prefaceex.add(new Paragraph(ctx.getResources().getString(
                            R.string.expertise)
                            + "                   : " + expertise, small));

                    addEmptyLine(prefaceex, 1);
                    document.add(prefaceex);
                }
            }

        } else {
//			Paragraph preface2 = new Paragraph();
//			String mystring = ctx.getString(R.string.projectdetails);
//			preface2.add(new Paragraph(ctx.getResources()
//					.getString(R.string.no) + " " + mystring + ":", heading));
//			addEmptyLine(preface2, 4);
//			document.add(preface2);
        }

        document.add(linebreak);

        final List<Other> other = db.getAllOthersbyId(_id);
        String dlicence, passportno;
        if (other.size() != 0) {
            Paragraph preface2 = new Paragraph();
            String mystring = ctx.getString(R.string.otherdetails);
            preface2.add(new Paragraph("" + mystring + ":", catFont));
            createTableex(preface2);
            addEmptyLine(preface2, 1);
            document.add(preface2);
            for (Other ot : other) {
                String prid = ot.getProfid();
                if (prid.equals(_id)) {
                    dlicence = ot.getDlicience();
                    passportno = ot.getPassno();

                    Paragraph prefaceex = new Paragraph();
                    if (!dlicence.equals("")) {
                        prefaceex.add(new Paragraph(ctx.getResources().getString(
                                R.string.DLicence)
                                + "      : " + dlicence, small));
                    }
                    if (!passportno.equals("")) {
                        prefaceex.add(new Paragraph(ctx.getResources().getString(
                                R.string.PassportNo)
                                + "            : " + passportno, small));
                    }
                    addEmptyLine(prefaceex, 1);
                    document.add(prefaceex);
                }
            }

        } else {

        }

        document.add(linebreak);
        final List<Refren> refren = db.getAllRefrenbyId(_id);
        String rname, rdetail, rcontactno, remail;
        if (refren.size() != 0) {
            Paragraph preface2 = new Paragraph();
            String mystring = ctx.getString(R.string.refrencedetails);
            preface2.add(new Paragraph("" + mystring + ":", catFont));
            addEmptyLine(preface2, 1);
            document.add(preface2);
            for (Refren ref : refren) {
                String prid = ref.getProfid();
                if (prid.equals(_id)) {
                    rname = ref.getRefname();
                    rdetail = ref.getRefdetail();
                    rcontactno = ref.getRefcontact();
                    remail = ref.getRefemail();

                    Paragraph prefaceex = new Paragraph();
                    prefaceex.add(new Paragraph(ctx.getResources().getString(
                            R.string.refree_name)
                            + "             : " + rname, small));
                    prefaceex.add(new Paragraph(ctx.getResources().getString(
                            R.string.refrencedetails)
                            + "           : " + rdetail, small));
                    if (!rcontactno.equals("")) {
                        prefaceex.add(new Paragraph(ctx.getResources().getString(
                                R.string.refree_contact)
                                + "     : " + rcontactno, small));
                    }
                    if (!remail.equals("")) {
                        prefaceex.add(new Paragraph(ctx.getResources().getString(
                                R.string.refreeemail)
                                + "             : " + remail, small));
                    }
                    prefaceex.setAlignment(Element.ALIGN_LEFT);
                    addEmptyLine(prefaceex, 1);
                    document.add(prefaceex);
                }
            }

        } else {
//			Paragraph preface2 = new Paragraph();
//			String mystring = ctx.getString(R.string.refrencefurneshed);
//			preface2.add(new Paragraph("" + mystring + "", subFont));
//			addEmptyLine(preface2, 4);
//			document.add(preface2);
        }

    }

    private static void addContent(Document document) throws DocumentException {
        Anchor anchor = new Anchor("First Chapter", catFont);
        anchor.setName("First Chapter");

        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);

        Paragraph subPara = new Paragraph("Subcategory 1", subFont);
        Section subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Hello"));

        subPara = new Paragraph("Subcategory 2", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Paragraph 1"));
        subCatPart.add(new Paragraph("Paragraph 2"));
        subCatPart.add(new Paragraph("Paragraph 3"));

        // add a list
        // createList(subCatPart);
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 5);
        subCatPart.add(paragraph);

        // add a table
        // createTable(subCatPart);

        // now add all this to the document
        document.add(catPart);

        // Next section
        anchor = new Anchor("Second Chapter", catFont);
        anchor.setName("Second Chapter");

        // Second parameter is the number of the chapter
        catPart = new Chapter(new Paragraph(anchor), 1);

        subPara = new Paragraph("Subcategory", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("This is a very important message"));

        // now add all this to the document
        document.add(catPart);

    }

    private static void createTable(Paragraph para) throws BadElementException {
        DatabaseHandler db = new DatabaseHandler(ctx);
        final List<Educate> educate = db.getAllEducatebyID(_id);
        boolean toShow = false;
        if (educate.size() != 0) {
            int i = 1;
            for (Educate ed : educate) {
                if (!ed.getResult().equalsIgnoreCase("")) {
                    toShow = true;
                }
            }
        }
        PdfPTable table;
        if (toShow) {
            table = new PdfPTable(5);
        } else {
            table = new PdfPTable(4);
        }
        table.setTotalWidth(PageSize.A4.getWidth() - 70);
        table.setLockedWidth(true);
        try {
            if (toShow) {
                table.setWidths(new int[]{2, 5, 5, 3, 3});
            } else {
                table.setWidths(new int[]{2, 5, 5, 3});
            }
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /*
         * t.setBorderColor(BaseColor.GRAY); t.setPadding(4); t.setSpacing(4);
         * t.setBorderWidth(1);
         */


        PdfPCell c1 = new PdfPCell(new Phrase(ctx.getResources().getString(R.string.serial_no)));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setPadding(5);
        c1.setBorderColor(BaseColor.GRAY);
        c1.setBorderWidth(1);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(ctx.getResources().getString(
                R.string.degree)));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidth(1);
        c1.setBorderColor(BaseColor.GRAY);
        c1.setPadding(5);
        table.addCell(c1);

        c1 = new PdfPCell(
                new Phrase(ctx.getResources().getString(R.string.uni)));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidth(1);
        c1.setBorderColor(BaseColor.GRAY);
        c1.setPadding(5);
        // c1.setFixedHeight(45f);

        table.addCell(c1);
		
		/*c1 = new PdfPCell(
				new Phrase(ctx.getResources().getString(R.string.sch)));
		c1.setBorderWidth(2);
		c1.setBorderColor(BaseColor.GRAY);
		c1.setPadding(1);
		// c1.setFixedHeight(45f);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);*/
        if (toShow) {
            c1 = new PdfPCell(new Phrase(ctx.getResources().getString(
                    R.string.result)));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorderWidth(1);
            c1.setBorderColor(BaseColor.GRAY);
            c1.setPadding(5);
            table.addCell(c1);
        }

        c1 = new PdfPCell(new Phrase(ctx.getResources().getString(
                R.string.PassingYear)));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidth(1);
        c1.setBorderColor(BaseColor.GRAY);
        c1.setPadding(5);
        table.addCell(c1);

        table.setHeaderRows(1);

        String degree = null, uni = null, result = null, passingyear = null;
        int edid = 0;

//		final List<Educate> educate = db.getAllEducatebyID(_id);
//		boolean toShow=false;
        if (educate.size() != 0) {
            int i = 1;
            for (Educate ed : educate) {
                String prid = ed.getProfid();
                if (prid.equals(_id)) {
                    edid = ed.getEDID();
                    degree = ed.getDegree();
                    uni = ed.getUni();
                    result = ed.getResult();
                    passingyear = ed.getPassingyear();
//                    passingyear= UIHelper.getConvertedDate(passingyear,ctx);
                    //school = ed.getSchool();


                    if (passingyear.contains("/")) {
                        String[] parts = passingyear.split("/");
                        String part1 = parts[0]; // 004
                        String part2 = parts[1]; // 004
                        if (!part2.equalsIgnoreCase(ctx.getString(R.string.still_studing))) {
                            if (part1.equalsIgnoreCase("Type1")) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATETYPE1);
                                convertedDate = new Date();
                                try {
                                    convertedDate = dateFormat.parse(part2);
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTime(convertedDate);
                                    SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(ctx));
                                    passingyear = format.format(calendar.getTime());
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
                                    SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(ctx));
                                    passingyear = format.format(calendar.getTime());
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
                                    SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(ctx));
                                    passingyear = format.format(calendar.getTime());
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
                                    SimpleDateFormat format = new SimpleDateFormat(UIHelper.getDateFormat(ctx));
                                    passingyear = format.format(calendar.getTime());
                                } catch (ParseException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (java.text.ParseException e) {
                                    e.printStackTrace();
                                }
                                System.out.println(convertedDate);
                            }
                        } else {
                            passingyear = ctx.getString(R.string.still_studing);
                        }
                    }


                    c1 = new PdfPCell(new Phrase("" + i + ""));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);
//                    if(toShow) {
                    c1 = new PdfPCell(new Phrase("" + degree + ""));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);
//                    }

                    c1 = new PdfPCell(new Phrase("" + uni + ""));
                    c1.setPadding(5);
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);
					
					/*c1 = new PdfPCell(new Phrase(""+school+""));
					c1.setHorizontalAlignment(Element.ALIGN_LEFT);
					c1.setPadding(5);
					table.addCell(c1);*/
                    if (toShow) {
                        c1 = new PdfPCell(new Phrase("" + result + ""));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(c1);
                    }

                    c1 = new PdfPCell(new Phrase("" + passingyear + ""));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);

                    i++;
                }
            }


        } else {
            table.addCell("");
            table.addCell("");
            table.addCell("");
            table.addCell("");
            table.addCell("");
            table.addCell("");
        }
        para.add(table);

    }

    private static void createTableex(Paragraph para)
            throws BadElementException {
        PdfPTable table = new PdfPTable(1);
        table.setTotalWidth(PageSize.A4.getWidth() - 50);
        table.setLockedWidth(true);
        /*
         * t.setBorderColor(BaseColor.GRAY); t.setPadding(4); t.setSpacing(4);
         * t.setBorderWidth(1);
         */

        PdfPCell c1 = new PdfPCell(new Phrase(ctx.getResources().getString(
                R.string.experiencedetails)));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setPadding(4);
        c1.setBorderColor(BaseColor.GRAY);
        c1.setBorderWidth(2);
        table.addCell(c1);
        table.setHeaderRows(1);
        para.add(table);

    }

    public static void setBackground(Document document, Context ctx) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(ctx.getResources(),
                R.drawable.bg);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        Image img;
        int noofpages = document.getPageNumber();

        try {
            // for(int i = 0; i<noofpages; i++){
            img = Image.getInstance(stream.toByteArray());
            img.setAbsolutePosition(0, 0);

            document.add(img);
            // }
        } catch (BadElementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

}
