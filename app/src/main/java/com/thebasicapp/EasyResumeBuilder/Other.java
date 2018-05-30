package com.thebasicapp.EasyResumeBuilder;

public class Other {

	// private variables
	int _id;
	String _drivinglicience;
	String _passportno, _profid;

	// Empty constructor
	public Other() {

	}

	// constructor
	public Other(int id, String drivinglicience, String passportno,
			String Profid) {
		this._id = id;
		this._drivinglicience = drivinglicience;
		this._passportno = passportno;
		this._profid = Profid;

	}

	// constructor
	public Other(String drivinglicience, String passportno, String Profid) {
		this._drivinglicience = drivinglicience;
		this._passportno = passportno;
		this._profid = Profid;
	}

	// getting ID
	public int getOTID() {
		return this._id;
	}

	// setting id
	public void setOTID(int id) {
		this._id = id;
	}

	// getting name
	public String getDlicience() {
		return this._drivinglicience;
	}

	// setting name=
	public void setDlicience(String drivinglicience) {
		this._drivinglicience = drivinglicience;
	}

	// getting phone number
	public String getPassno() {
		return this._passportno;
	}

	// setting phone number
	public void setPassno(String passportno) {
		this._passportno = passportno;
	}

	public String getProfid() {
		return this._profid;
	}

	// setting phone number
	public void setProfid(String profid) {
		this._profid = profid;
	}
}
