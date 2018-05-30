package com.thebasicapp.EasyResumeBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Helper.UIHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 22;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";
    private static final String TABLE_EDUCATION = "education";
    private static final String TABLE_EXPERIENCE = "experience";
    private static final String TABLE_PROJECT = "project";
    private static final String TABLE_REFRENCE = "refrence";
    private static final String TABLE_OTHER = "others";
    private static final String TABLE_PROFILE = "profile";
    private static final String TABLE_IMAGE = "image";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    public static final String KEY_Name = "name";
    public static final String KEY_Gender = "gender";
    public static final String KEY_DOB = "dob";
    public static final String KEY_Address = "address";
    public static final String KEY_Languages = "languages";
    public static final String KEY_Contact = "contact";
    public static final String KEY_Email = "email";
    public static final String KEY_Profid = "Profid";

    // /////////////// education ///////////////////////

    private static final String EDU_ID = "id";
    public static final String EDU_Degree = "degree";
    public static final String EDU_Uni = "uni";
    public static final String EDU_School = "school";
    public static final String EDU_Result = "result";
    public static final String EDU_PassingYear = "passingyear";
    public static final String EDU_Profid = "Profid";
    public static final String EDU_PassingDate = "Profid";
    public static final String EDU_StartDate = "startDate";

    // ////////////////////////////////////////////////

    // /////////////// experience ///////////////////////

    private static final String EXP_ID = "id";
    public static final String EXP_Company = "company";
    public static final String EXP_Position = "position";
    public static final String EXP_Period = "period";
    public static final String EXP_Location = "location";
    public static final String EXP_Salary = "salary";
    public static final String EXP_Responsibility = "responsibility";
    public static final String EXP_StartDate = "startdate";
    public static final String EXP_Profid = "Profid";


    // ////////////////////////////////////////////////

    // /////////////// project ///////////////////////

    private static final String PRO_ID = "id";
    public static final String PRO_Title = "title";
    public static final String PRO_Duration = "duration";
    public static final String PRO_Role = "role";
    public static final String PRO_Tsize = "tsize";
    public static final String PRO_Expertise = "expertise";
    public static final String PRO_Profid = "Profid";

    // ////////////////////////////////////////////////

    // /////////////// refrence ///////////////////////

    private static final String REF_ID = "id";
    public static final String REF_Refname = "refname";
    public static final String REF_Refdetail = "refdetail";
    public static final String REF_Refcontact = "refcontact";
    public static final String REF_Refmail = "refemail";
    public static final String REF_Profid = "Profid";

    // ////////////////////////////////////////////////

    // ////////////////////// others /////////////////

    private static final String OTH_ID = "id";
    public static final String OTH_Dlicience = "dlicience";
    public static final String OTH_Passno = "passno";
    public static final String OTH_Profid = "Profid";

    // ///////////////////////////////////////////////

    // /////////////////////////////////////////////////////

    private static final String IMG_ID = "id";
    public static final String IMG_Imagepath = "imagepath";
    public static final String IMG_Profid = "Profid";

    // /////////////////////////////////////////////////////

    // /////////////////////////////////////////////////////

    private static final String PROF_ID = "id";
    public static final String PROF_name = "profilename";

    // /////////////////////////////////////////////////////

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_Name + " TEXT,"
                + KEY_Gender + " TEXT," + KEY_DOB + " TEXT," + KEY_Address
                + " TEXT," + KEY_Languages + " TEXT," + KEY_Contact + " TEXT,"
                + KEY_Email + " TEXT," + KEY_Profid + " TEXT" + ")";

        String CREATE_EDUCATION_TABLE = "CREATE TABLE " + TABLE_EDUCATION + "("
                + EDU_ID + " INTEGER PRIMARY KEY," + EDU_Degree + " TEXT,"
                + EDU_Uni + " TEXT," + EDU_School + " TEXT," + EDU_Result
                + " TEXT," + EDU_PassingYear + " TEXT," + EDU_StartDate + " TEXT," + EDU_Profid + " TEXT"
                + ")";

        String CREATE_EXPERIENCE_TABLE = "CREATE TABLE " + TABLE_EXPERIENCE
                + "(" + EXP_ID + " INTEGER PRIMARY KEY," + EXP_Company
                + " TEXT," + EXP_Position + " TEXT," + EXP_Period + " TEXT,"
                + EXP_Location + " TEXT," + EXP_Salary + " TEXT,"
                + EXP_Responsibility + " TEXT," + EXP_StartDate + " TEXT," + EXP_Profid + " TEXT" + ")";

        String CREATE_PROJECT_TABLE = "CREATE TABLE " + TABLE_PROJECT + "("
                + PRO_ID + " INTEGER PRIMARY KEY," + PRO_Title + " TEXT,"
                + PRO_Duration + " TEXT," + PRO_Role + " TEXT," + PRO_Tsize
                + " TEXT," + PRO_Expertise + " TEXT," + PRO_Profid + " TEXT"
                + ")";

        String CREATE_REFRENCE_TABLE = "CREATE TABLE " + TABLE_REFRENCE + "("
                + REF_ID + " INTEGER PRIMARY KEY," + REF_Refname + " TEXT,"
                + REF_Refdetail + " TEXT," + REF_Refcontact + " TEXT,"
                + REF_Refmail + " TEXT," + REF_Profid + " TEXT" + ")";

        String CREATE_OTHER_TABLE = "CREATE TABLE " + TABLE_OTHER + "("
                + OTH_ID + " INTEGER PRIMARY KEY," + OTH_Dlicience + " TEXT,"
                + OTH_Passno + " TEXT," + OTH_Profid + " TEXT" + ")";

        String CREATE_IMAGE_TABLE = "CREATE TABLE " + TABLE_IMAGE + "("
                + IMG_ID + " INTEGER PRIMARY KEY," + IMG_Imagepath + " TEXT,"
                + IMG_Profid + " TEXT" + ")";

        String CREATE_PROFILE_TABLE = "CREATE TABLE " + TABLE_PROFILE + "("
                + PROF_ID + " INTEGER PRIMARY KEY," + PROF_name + " TEXT" + ")";

        db.execSQL(CREATE_EDUCATION_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_EXPERIENCE_TABLE);
        db.execSQL(CREATE_PROJECT_TABLE);
        db.execSQL(CREATE_REFRENCE_TABLE);
        db.execSQL(CREATE_OTHER_TABLE);
        db.execSQL(CREATE_IMAGE_TABLE);
        db.execSQL(CREATE_PROFILE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EDUCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPERIENCE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REFRENCE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OTHER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE);
        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_Name, contact.getName()); // Contact Name
        values.put(KEY_Gender, contact.getGender());
        values.put(KEY_DOB, contact.getDOB()); // Contact Phone
        values.put(KEY_Address, contact.getAddress());
        values.put(KEY_Languages, contact.getLanguage());
        values.put(KEY_Contact, contact.getContact());
        values.put(KEY_Email, contact.getEmail());
        values.put(KEY_Profid, contact.getProfid());

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    void addEducation(Educate educate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // values.put(EDU_ID, educate.getEDID()); // Contact Name
        values.put(EDU_Degree, educate.getDegree());
        values.put(EDU_Uni, educate.getUni()); // Contact Phone
        values.put(EDU_School, educate.getSchool());
        values.put(EDU_Result, educate.getResult());
        values.put(EDU_PassingYear, educate.getPassingyear());
        values.put(EDU_StartDate, UIHelper.persistDate(educate.getStartDate()));
        values.put(EDU_Profid, educate.getProfid());

        // Inserting Row
        db.insert(TABLE_EDUCATION, null, values);
        db.close(); // Closing database connection
    }

    void addExperience(Exper exper) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // values.put(EDU_ID, educate.getEDID()); // Contact Name
        values.put(EXP_Company, exper.getCompany());
        values.put(EXP_Position, exper.getPosition()); // Contact Phone
        values.put(EXP_Period, exper.getPeriod());
        values.put(EXP_Location, exper.getLocation());
        values.put(EXP_Salary, exper.getSalary());
        values.put(EXP_Responsibility, exper.getResponsibility());
        values.put(EXP_StartDate, UIHelper.persistDate(exper.getStartDate()));
        values.put(EXP_Profid, exper.getProfid());

        // Inserting Row
        db.insert(TABLE_EXPERIENCE, null, values);
        db.close(); // Closing database connection
    }

    void addProject(Proj proj) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // values.put(EDU_ID, educate.getEDID()); // Contact Name
        values.put(PRO_Title, proj.getPrTitle());
        values.put(PRO_Duration, proj.getPrDuration()); // Contact Phone
        values.put(PRO_Role, proj.getRole());
        values.put(PRO_Tsize, proj.getTsize());
        values.put(PRO_Expertise, proj.getExpertise());
        values.put(PRO_Profid, proj.getProfid());

        // Inserting Row
        db.insert(TABLE_PROJECT, null, values);
        db.close(); // Closing database connection
    }

    void addRefrence(Refren refren) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // values.put(EDU_ID, educate.getEDID()); // Contact Name
        values.put(REF_Refname, refren.getRefname());
        values.put(REF_Refdetail, refren.getRefdetail()); // Contact Phone
        values.put(REF_Refcontact, refren.getRefcontact());
        values.put(REF_Refmail, refren.getRefemail());
        values.put(REF_Profid, refren.getProfid());

        // Inserting Row
        db.insert(TABLE_REFRENCE, null, values);
        db.close(); // Closing database connection
    }

    void addOther(Other other) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OTH_Dlicience, other.getDlicience()); // Contact Name
        values.put(OTH_Passno, other.getPassno());
        values.put(OTH_Profid, other.getProfid());

        // Inserting Row
        db.insert(TABLE_OTHER, null, values);
        db.close(); // Closing database connection
    }

    void addUpload(Upload upload) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(IMG_Imagepath, upload.getImagepath());
        values.put(IMG_Profid, upload.getProfid());

        // Inserting Row
        db.insert(TABLE_IMAGE, null, values);
        db.close(); // Closing database connection
    }

    void addProf(Prof prof) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PROF_name, prof.getProfilename());

        // Inserting Row
        db.insert(TABLE_PROFILE, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID,
                        KEY_Name, KEY_Gender, KEY_DOB, KEY_Address, KEY_Languages,
                        KEY_Contact, KEY_Email, KEY_Profid}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6),
                cursor.getString(7), cursor.getString(8));
        // return contact
        db.close();
        return contact;
    }

    Upload getUpload(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_IMAGE, new String[]{IMG_ID,
                        IMG_Imagepath, IMG_Profid}, IMG_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Upload upload = new Upload(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        db.close();
        return upload;
    }

    Prof getProf(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PROFILE, new String[]{PROF_ID,
                        PROF_name}, PROF_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Prof prof = new Prof(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        // return contact
        db.close();
        return prof;
    }

    Educate getEducate(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EDUCATION, new String[]{EDU_ID,
                        EDU_Degree, EDU_Uni, EDU_School, EDU_Result, EDU_PassingYear,
                        EDU_Profid, EDU_StartDate}, EDU_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Educate educate = new Educate(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6), UIHelper.loadDate(cursor, 7));
        // return contact
        db.close();
        return educate;
    }

    Exper getExper(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EXPERIENCE, new String[]{EXP_ID,
                        EXP_Company, EXP_Position, EXP_Period, EXP_Location,
                        EXP_Salary, EXP_Responsibility, EXP_Profid, EXP_StartDate}, EXP_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

		/*Exper exper = new Exper(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3),
				cursor.getString(4), cursor.getString(5), cursor.getString(6),
				cursor.getString(7));*/

        Exper exper = new Exper(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6),
                cursor.getString(7), UIHelper.loadDate(cursor, 8));
        // return contact
        db.close();
        return exper;
    }

    Proj getProj(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PROJECT, new String[]{PRO_ID,
                        PRO_Title, PRO_Duration, PRO_Role, PRO_Tsize, PRO_Expertise,
                        PRO_Profid}, PRO_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Proj proj = new Proj(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6));
        // return contact
        db.close();
        return proj;
    }

    Refren getRefren(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_REFRENCE, new String[]{REF_ID,
                        REF_Refname, REF_Refdetail, REF_Refcontact, REF_Refmail,
                        REF_Profid}, REF_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Refren refren = new Refren(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5));
        // return contact
        db.close();
        return refren;
    }

    Other getOther(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_OTHER, new String[]{OTH_ID,
                        OTH_Dlicience, OTH_Passno, OTH_Profid}, OTH_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Other other = new Other(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        // return contact
        db.close();
        return other;
    }

    // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setGender(cursor.getString(2));
                contact.setDOB(cursor.getString(3));
                contact.setAddress(cursor.getString(4));
                contact.setLanguage(cursor.getString(5));
                contact.setContact(cursor.getString(6));
                contact.setEmail(cursor.getString(7));
                contact.setProfid(cursor.getString(8));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        db.close();
        return contactList;
    }

    public List<Upload> getAllUpload() {
        List<Upload> uploadList = new ArrayList<Upload>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_IMAGE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Upload upload = new Upload();
                upload.setUPID(Integer.parseInt(cursor.getString(0)));
                upload.setImagepath(cursor.getString(1));
                upload.setProfid(cursor.getString(2));
                // Adding contact to list
                uploadList.add(upload);
            } while (cursor.moveToNext());
        }

        // return contact list
        db.close();
        return uploadList;
    }


    public List<Prof> getAllProf() {
        List<Prof> profList = new ArrayList<Prof>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PROFILE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Prof prof = new Prof();
                prof.setPROFID(Integer.parseInt(cursor.getString(0)));
                prof.setProfilename(cursor.getString(1));
                // Adding contact to list
                profList.add(prof);
            } while (cursor.moveToNext());
        }

        // return contact list
        db.close();
        return profList;
    }

    public List<Educate> getAllEducate() {
        List<Educate> educateList = new ArrayList<Educate>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EDUCATION;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if ((cursor != null) && (cursor.moveToFirst())) {
            do {
                Educate educate = new Educate();
                educate.setEDID(Integer.parseInt(cursor.getString(0)));
                educate.setDegree(cursor.getString(1));
                educate.setUni(cursor.getString(2));
                educate.setSchool(cursor.getString(3));
                educate.setResult(cursor.getString(4));
                educate.setPassingyear(cursor.getString(5));
                educate.setStartDate(UIHelper.loadDate(cursor, 6));
                educate.setProfid(cursor.getString(7));

                // Adding contact to list
                educateList.add(educate);
            } while (cursor.moveToNext());
        }

        // return contact list
        db.close();
        Collections.sort(educateList, new CustomComparatorEdu());
        return educateList;
    }

    public List<Educate> getAllEducatebyID(String id) {
        List<Educate> educateList = new ArrayList<Educate>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EDUCATION + " WHERE" + " " + EDU_Profid + " = " + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if ((cursor != null) && (cursor.moveToFirst())) {
            do {
                Educate educate = new Educate();
                educate.setEDID(Integer.parseInt(cursor.getString(0)));
                educate.setDegree(cursor.getString(1));
                educate.setUni(cursor.getString(2));
                educate.setSchool(cursor.getString(3));
                educate.setResult(cursor.getString(4));
                educate.setPassingyear(cursor.getString(5));
                educate.setStartDate(UIHelper.loadDate(cursor, 6));
                educate.setProfid(cursor.getString(7));
                GenerateResume.modelClass.setmEducate(educate);
                // Adding contact to list
                educateList.add(educate);
            } while (cursor.moveToNext());
        }

        // return contact list
        db.close();
        Collections.sort(educateList, new CustomComparatorEdu());
        return educateList;
    }


    //Sort date by order wise
    public List<Educate> getAllEducationByDate() {
        List<Educate> educateList = new ArrayList<Educate>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EDUCATION + " ORDER BY " + EXP_StartDate + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if ((cursor != null) && (cursor.moveToFirst())) {
            do {
                Educate educate = new Educate();
                educate.setEDID(Integer.parseInt(cursor.getString(0)));
                educate.setDegree(cursor.getString(1));
                educate.setUni(cursor.getString(2));
                educate.setSchool(cursor.getString(3));
                educate.setResult(cursor.getString(4));
                educate.setPassingyear(cursor.getString(5));
                educate.setStartDate(UIHelper.loadDate(cursor, 6));
                educate.setProfid(cursor.getString(7));

                // Adding contact to list
                educateList.add(educate);
            } while (cursor.moveToNext());
        }

        // return contact list
        db.close();
        return educateList;
    }

    public List<Exper> getAllExper() {
        List<Exper> experList = new ArrayList<Exper>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EXPERIENCE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if ((cursor != null) && (cursor.moveToFirst())) {
            do {
                Exper exper = new Exper();
                exper.setEXID(Integer.parseInt(cursor.getString(0)));
                exper.setCompany(cursor.getString(1));
                exper.setPosition(cursor.getString(2));
                exper.setPeriod(cursor.getString(3));
                exper.setLocation(cursor.getString(4));
                exper.setSalary(cursor.getString(5));
                exper.setResponsibility(cursor.getString(6));
                exper.setStartDate(UIHelper.loadDate(cursor, 7));
                exper.setProfid(cursor.getString(8));

                // Adding contact to list
                experList.add(exper);
            } while (cursor.moveToNext());
        }

        // return contact list
        db.close();
        return experList;
    }

    public List<Exper> getAllExperbyId(String id) {
        List<Exper> experList = new ArrayList<Exper>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EXPERIENCE + " WHERE" + " " + EXP_Profid + " = " + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if ((cursor != null) && (cursor.moveToFirst())) {
            do {
                Exper exper = new Exper();
                exper.setEXID(Integer.parseInt(cursor.getString(0)));
                exper.setCompany(cursor.getString(1));
                exper.setPosition(cursor.getString(2));
                exper.setPeriod(cursor.getString(3));
                exper.setLocation(cursor.getString(4));
                exper.setSalary(cursor.getString(5));
                exper.setResponsibility(cursor.getString(6));
                exper.setStartDate(UIHelper.loadDate(cursor, 7));
                exper.setProfid(cursor.getString(8));

                GenerateResume.modelClass.setmExper(exper);
                // Adding contact to list
                experList.add(exper);
            } while (cursor.moveToNext());
        }

        // return contact list
        db.close();
        return experList;
    }

    public List<Exper> getAllExperbydate() {
        List<Exper> experList = new ArrayList<Exper>();
        String selectQuery = "SELECT  * FROM " + TABLE_EXPERIENCE + " ORDER BY " + EXP_StartDate + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if ((cursor != null) && (cursor.moveToFirst())) {
            do {
                Exper exper = new Exper();
                exper.setEXID(Integer.parseInt(cursor.getString(0)));
                exper.setCompany(cursor.getString(1));
                exper.setPosition(cursor.getString(2));
                exper.setPeriod(cursor.getString(3));
                exper.setLocation(cursor.getString(4));
                exper.setSalary(cursor.getString(5));
                exper.setResponsibility(cursor.getString(6));
                exper.setStartDate(UIHelper.loadDate(cursor, 7));
                exper.setProfid(cursor.getString(8));

                // Adding contact to list
                experList.add(exper);

                //Log.v("Name",cursor.getString(1)+"");
                System.out.println(cursor.getString(1));
                System.out.println(cursor.getString(7));
                //Log.v("StartDate",cursor.getString(7)+"");
            } while (cursor.moveToNext());
        }

        // return contact list
        db.close();
        return experList;
    }

    public List<Proj> getAllProj() {
        List<Proj> projList = new ArrayList<Proj>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PROJECT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if ((cursor != null) && (cursor.moveToFirst())) {
            do {
                Proj proj = new Proj();
                proj.setPRID(Integer.parseInt(cursor.getString(0)));
                proj.setPrTitle(cursor.getString(1));
                proj.setPrDuration(cursor.getString(2));
                proj.setRole(cursor.getString(3));
                proj.setTsize(cursor.getString(4));
                proj.setExpertise(cursor.getString(5));
                proj.setProfid(cursor.getString(6));

                // Adding contact to list
                projList.add(proj);
            } while (cursor.moveToNext());
        }

        // return contact list
        db.close();
        return projList;
    }

    public List<Proj> getAllProjbyId(String PROFID) {
        List<Proj> projList = new ArrayList<Proj>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PROJECT + " WHERE" + " " + PRO_Profid + " = " + PROFID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if ((cursor != null) && (cursor.moveToFirst())) {
            do {
                Proj proj = new Proj();
                proj.setPRID(Integer.parseInt(cursor.getString(0)));
                proj.setPrTitle(cursor.getString(1));
                proj.setPrDuration(cursor.getString(2));
                proj.setRole(cursor.getString(3));
                proj.setTsize(cursor.getString(4));
                proj.setExpertise(cursor.getString(5));
                proj.setProfid(cursor.getString(6));
                if (GenerateResume.modelClass == null) {
                    GenerateResume.modelClass = new ModelClass();
                }
                GenerateResume.modelClass.setmProj(proj);
                // Adding contact to list
                projList.add(proj);
            } while (cursor.moveToNext());
        }

        // return contact list
        db.close();
        return projList;
    }

    public List<Refren> getAllRefren() {
        List<Refren> refrenList = new ArrayList<Refren>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_REFRENCE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if ((cursor != null) && (cursor.moveToFirst())) {
            do {
                Refren refren = new Refren();
                refren.setREFID(Integer.parseInt(cursor.getString(0)));
                refren.setRefname(cursor.getString(1));
                refren.setRefdetail(cursor.getString(2));
                refren.setRefcontact(cursor.getString(3));
                refren.setRefemail(cursor.getString(4));
                refren.setProfid(cursor.getString(5));

                // Adding contact to list
                refrenList.add(refren);
            } while (cursor.moveToNext());
        }

        // return contact list
        db.close();
        return refrenList;
    }

    public List<Refren> getAllRefrenbyId(String id) {
        List<Refren> refrenList = new ArrayList<Refren>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_REFRENCE + " WHERE" + " " + KEY_Profid + " = " + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if ((cursor != null) && (cursor.moveToFirst())) {
            do {
                Refren refren = new Refren();
                refren.setREFID(Integer.parseInt(cursor.getString(0)));
                refren.setRefname(cursor.getString(1));
                refren.setRefdetail(cursor.getString(2));
                refren.setRefcontact(cursor.getString(3));
                refren.setRefemail(cursor.getString(4));
                refren.setProfid(cursor.getString(5));
                GenerateResume.modelClass.setmRefren(refren);
                // Adding contact to list
                refrenList.add(refren);
            } while (cursor.moveToNext());
        }

        // return contact list
        db.close();
        return refrenList;
    }

    public List<Other> getAllOthers() {
        List<Other> otherList = new ArrayList<Other>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_OTHER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if ((cursor != null) && (cursor.moveToFirst())) {
            do {
                Other other = new Other();
                other.setOTID(Integer.parseInt(cursor.getString(0)));
                other.setDlicience(cursor.getString(1));
                other.setPassno(cursor.getString(2));
                other.setProfid(cursor.getString(3));
                // Adding contact to list
                otherList.add(other);
            } while (cursor.moveToNext());
        }

        // return contact list
        db.close();
        return otherList;
    }

    public List<Other> getAllOthersbyId(String id) {
        List<Other> otherList = new ArrayList<Other>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_OTHER + " WHERE" + " " + OTH_Profid + " = " + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if ((cursor != null) && (cursor.moveToFirst())) {
            do {
                Other other = new Other();
                other.setOTID(Integer.parseInt(cursor.getString(0)));
                other.setDlicience(cursor.getString(1));
                other.setPassno(cursor.getString(2));
                other.setProfid(cursor.getString(3));

                GenerateResume.modelClass.setmOther(other);
                // Adding contact to list
                otherList.add(other);
            } while (cursor.moveToNext());
        }

        // return contact list
        db.close();
        return otherList;
    }

    // Updating single contact
    public void updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_Name, contact.getName());
        values.put(KEY_Gender, contact.getGender());
        values.put(KEY_DOB, contact.getDOB());
        values.put(KEY_Address, contact.getAddress());
        values.put(KEY_Languages, contact.getLanguage());
        values.put(KEY_Contact, contact.getContact());
        values.put(KEY_Email, contact.getEmail());
        values.put(KEY_Profid, contact.getProfid());

        // updating row
        db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getID())});
        db.close();
    }

    public void updateEducate(Educate educate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // values.put(EDU_ID, educate.getEDID());
        values.put(EDU_Degree, educate.getDegree());
        values.put(EDU_Uni, educate.getUni());
        values.put(EDU_School, educate.getSchool());
        values.put(EDU_Result, educate.getResult());
        values.put(EDU_PassingYear, educate.getPassingyear());
        values.put(EDU_StartDate, UIHelper.persistDate(educate.getStartDate()));
        values.put(EDU_Profid, educate.getProfid());

        // updating row
        db.update(TABLE_EDUCATION, values, EDU_ID + " = ?",
                new String[]{String.valueOf(educate.getEDID())});
        db.close();
    }

    public void updateUpload(Upload upload) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // values.put(EDU_ID, educate.getEDID());
        values.put(IMG_Imagepath, upload.getImagepath());
        values.put(IMG_Profid, upload.getProfid());

        // updating row
        db.update(TABLE_IMAGE, values, IMG_ID + " = ?",
                new String[]{String.valueOf(upload.getUPID())});
        db.close();
    }

    public void updateProf(Prof prof) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // values.put(EDU_ID, educate.getEDID());
        values.put(PROF_name, prof.getProfilename());

        // updating row
        db.update(TABLE_PROFILE, values, PROF_ID + " = ?",
                new String[]{String.valueOf(prof.getPROFID())});
        db.close();
    }

    public void updateExper(Exper exper) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // values.put(EDU_ID, educate.getEDID());
        values.put(EXP_Company, exper.getCompany());
        values.put(EXP_Position, exper.getPosition());
        values.put(EXP_Period, exper.getPeriod());
        values.put(EXP_Location, exper.getLocation());
        values.put(EXP_Salary, exper.getSalary());
        values.put(EXP_Responsibility, exper.getResponsibility());
        values.put(EXP_Profid, exper.getProfid());
        values.put(EXP_StartDate, UIHelper.persistDate(exper.getStartDate()));
        // updating row
        db.update(TABLE_EXPERIENCE, values, EXP_ID + " = ?",
                new String[]{String.valueOf(exper.getEXID())});
        db.close();
    }

    public void updateProj(Proj proj) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // values.put(EDU_ID, educate.getEDID());
        values.put(PRO_Title, proj.getPrTitle());
        values.put(PRO_Duration, proj.getPrDuration());
        values.put(PRO_Role, proj.getRole());
        values.put(PRO_Tsize, proj.getTsize());
        values.put(PRO_Expertise, proj.getExpertise());
        values.put(PRO_Profid, proj.getProfid());

        // updating row
        db.update(TABLE_PROJECT, values, PRO_ID + " = ?",
                new String[]{String.valueOf(proj.getPRID())});
        db.close();
    }

    public void updateRefren(Refren refren) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // values.put(EDU_ID, educate.getEDID());
        values.put(REF_Refname, refren.getRefname());
        values.put(REF_Refdetail, refren.getRefdetail());
        values.put(REF_Refcontact, refren.getRefcontact());
        values.put(REF_Refmail, refren.getRefemail());
        values.put(REF_Profid, refren.getProfid());

        // updating row
        db.update(TABLE_REFRENCE, values, REF_ID + " = ?",
                new String[]{String.valueOf(refren.getREFID())});
        db.close();
    }

    public void updateOther(Other other) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(OTH_Dlicience, other.getDlicience());
        values.put(OTH_Passno, other.getPassno());
        values.put(OTH_Profid, other.getProfid());

        // updating row
        db.update(TABLE_OTHER, values, OTH_ID + " = ?",
                new String[]{String.valueOf(other.getOTID())});
        db.close();
    }

    // Deleting single contact
    public void deleteContact() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, null, null);
        db.close();
    }

    public void deleteUpload() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_IMAGE, null, null);
        db.close();
    }

    public void deleteProf() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROFILE, null, null);
        db.close();
    }

    public void deleteEducate() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EDUCATION, null, null);
        db.close();
    }

    public void deleteExper() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXPERIENCE, null, null);
        db.close();
    }

    public void deleteProj() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROJECT, null, null);
        db.close();
    }

    public void deleteRefren() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REFRENCE, null, null);
        db.close();
    }

    public void deleteOther() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_OTHER, null, null);
        db.close();
    }

    public void deleteAllContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getID())});
        db.close();
    }

    public void deleteAllUpload(Upload upload) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_IMAGE, IMG_ID + " = ?",
                new String[]{String.valueOf(upload.getUPID())});
        db.close();
    }

    public void deleteAllProf(Prof prof) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROFILE, PROF_ID + " = ?",
                new String[]{String.valueOf(prof.getPROFID())});
        db.close();
    }

    public void deleteAllEducate(Educate educate) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EDUCATION, EDU_ID + " = ?",
                new String[]{String.valueOf(educate.getEDID())});
        db.close();
    }

    public void deleteAllExper(Exper exper) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXPERIENCE, EXP_ID + " = ?",
                new String[]{String.valueOf(exper.getEXID())});
        db.close();
    }

    public void deleteAllProj(Proj proj) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROJECT, PRO_ID + " = ?",
                new String[]{String.valueOf(proj.getPRID())});
        db.close();
    }

    public void deleteAllRefren(Refren refren) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REFRENCE, REF_ID + " = ?",
                new String[]{String.valueOf(refren.getREFID())});
        db.close();
    }

    public void deleteAllOther(Other other) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_OTHER, OTH_ID + " = ?",
                new String[]{String.valueOf(other.getOTID())});
        db.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close();
        // return count
        return cursor.getCount();
    }

    public int getUploadCount() {
        String countQuery = "SELECT  * FROM " + TABLE_IMAGE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close();
        // return count
        return cursor.getCount();
    }

    public int getProfCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PROFILE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close();
        // return count
        return cursor.getCount();
    }

    public int getEducateCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EDUCATION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close();
        // return count
        return cursor.getCount();
    }

    public int getExperCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EXPERIENCE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close();
        // return count
        return cursor.getCount();
    }

    public int getProjCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PROJECT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close();
        // return count
        return cursor.getCount();
    }

    public int getRefrenCount() {
        String countQuery = "SELECT  * FROM " + TABLE_REFRENCE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close();
        // return count
        return cursor.getCount();
    }

    public int getOthersCount() {
        String countQuery = "SELECT  * FROM " + TABLE_OTHER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close();
        // return count
        return cursor.getCount();
    }

}