package com.thebasicapp.EasyResumeBuilder;

public class Upload {

	// private variables
	int _id;
	String _imagepath, _profid;

	// Empty constructor
	public Upload() {

	}

	// constructor
	public Upload(int id, String imagepath, String Profid) {
		this._id = id;
		this._imagepath = imagepath;
		this._profid = Profid;
	}

	// constructor
	public Upload(String imagepath, String Profid) {
		this._imagepath = imagepath;
		this._profid = Profid;
		// getting ID
	}

	public int getUPID() {
		return this._id;
	}

	// setting id
	public void setUPID(int id) {
		this._id = id;
	}

	// getting name
	public String getImagepath() {
		return this._imagepath;
	}

	// setting name=
	public void setImagepath(String imagepath) {
		this._imagepath = imagepath;
	}

	public String getProfid() {
		return this._profid;
	}

	// setting phone number
	public void setProfid(String profid) {
		this._profid = profid;
	}

}
