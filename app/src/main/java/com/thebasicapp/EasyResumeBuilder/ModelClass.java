package com.thebasicapp.EasyResumeBuilder;

public class ModelClass {

    Upload mUpload;
    Prof mProf;
    Proj mProj;
    Refren mRefren;
    Exper mExper;
    Educate mEducate;
    Contact mContact;
    Other mOther;


    public ModelClass() {
    }

    public ModelClass(Upload mUpload, Prof mProf, Proj mProj, Refren mRefren, Exper mExper, Educate mEducate, Contact mContact, Other mOther) {
        this.mUpload = mUpload;
        this.mProf = mProf;
        this.mProj = mProj;
        this.mRefren = mRefren;
        this.mExper = mExper;
        this.mEducate = mEducate;
        this.mContact = mContact;
        this.mOther = mOther;
    }




    public Upload getmUpload() {
        return mUpload;
    }

    public void setmUpload(Upload mUpload) {
        this.mUpload = mUpload;
    }

    public Prof getmProf() {
        return mProf;
    }

    public void setmProf(Prof mProf) {
        this.mProf = mProf;
    }

    public Proj getmProj() {
        return mProj;
    }

    public void setmProj(Proj mProj) {
        this.mProj = mProj;
    }

    public Refren getmRefren() {
        return mRefren;
    }

    public void setmRefren(Refren mRefren) {
        this.mRefren = mRefren;
    }

    public Exper getmExper() {
        return mExper;
    }

    public void setmExper(Exper mExper) {
        this.mExper = mExper;
    }

    public Educate getmEducate() {
        return mEducate;
    }

    public void setmEducate(Educate mEducate) {
        this.mEducate = mEducate;
    }

    public Contact getmContact() {
        return mContact;
    }

    public void setmContact(Contact mContact) {
        this.mContact = mContact;
    }

    public Other getmOther() {
        return mOther;
    }

    public void setmOther(Other mOther) {
        this.mOther = mOther;
    }









}
