package com.thebasicapp.EasyResumeBuilder;

public class Refren {

	// private variables
	int _id;
	String _refname;
	String _refdetail, _refcontact, _refemail, _profid;

	// Empty constructor
	public Refren() {

	}

	// constructor
	public Refren(int id, String refname, String refdetail, String refcontact,
			String refemail, String Profid) {
		this._id = id;
		this._refname = refname;
		this._refdetail = refdetail;
		this._refcontact = refcontact;
		this._refemail = refemail;
		this._profid = Profid;
	}

	// constructor
	public Refren(String refname, String refdetail, String refcontact,
			String refemail, String Profid) {
		this._refname = refname;
		this._refdetail = refdetail;
		this._refcontact = refcontact;
		this._refemail = refemail;
		this._profid = Profid;
	}

	// getting ID
	public int getREFID() {
		return this._id;
	}

	// setting id
	public void setREFID(int id) {
		this._id = id;
	}

	// getting name
	public String getRefname() {
		return this._refname;
	}

	// setting name=
	public void setRefname(String refname) {
		this._refname = refname;
	}

	// getting phone number
	public String getRefdetail() {
		return this._refdetail;
	}

	// setting phone number
	public void setRefdetail(String refdetail) {
		this._refdetail = refdetail;
	}

	public String getRefcontact() {
		return this._refcontact;
	}

	// setting phone number
	public void setRefcontact(String refcontact) {
		this._refcontact = refcontact;
	}

	public String getRefemail() {
		return this._refemail;
	}

	// setting phone number
	public void setRefemail(String refemail) {
		this._refemail = refemail;
	}

	public String getProfid() {
		return this._profid;
	}

	// setting phone number
	public void setProfid(String profid) {
		this._profid = profid;
	}
}
