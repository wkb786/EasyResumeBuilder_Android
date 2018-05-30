package com.thebasicapp.EasyResumeBuilder;

import java.util.Date;

public class Exper implements Comparable<Exper> {

	// private variables
	int _id;
	String _company;
	private Date startDate;
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	String _position, _period, _location, _salary, _responsibility, _profid,_startDate;

	// Empty constructor
	public Exper() {

	}

	// constructor
	public Exper(int id, String company, String position, String period,
			String location, String salary, String responsibility, String Profid,Date startDate) {
		this._id = id;
		this._company = company;
		this._position = position;
		this._period = period;
		this._location = location;
		this._salary = salary;
		this._responsibility = responsibility;
		this._profid = Profid;
		this.startDate=startDate;
	}

	public String get_startDate() {
		return _startDate;
	}

	public void set_startDate(String _startDate) {
		this._startDate = _startDate;
	}

	// constructor
	public Exper(String company, String position, String period,
			String location, String salary, String responsibility, String Profid,Date StartDate) {
		this._company = company;
		this._position = position;
		this._period = period;
		this._location = location;
		this._salary = salary;
		this._responsibility = responsibility;
		this._profid = Profid;
		this.startDate=StartDate;
	}

	// getting ID
	public int getEXID() {
		return this._id;
	}

	// setting id
	public void setEXID(int id) {
		this._id = id;
	}

	// getting name
	public String getCompany() {
		return this._company;
	}

	// setting name=
	public void setCompany(String company) {
		this._company = company;
	}

	// getting phone number
	public String getPosition() {
		return this._position;
	}

	// setting phone number
	public void setPosition(String position) {
		this._position = position;
	}

	public String getPeriod() {
		return this._period;
	}

	// setting phone number
	public void setPeriod(String period) {
		this._period = period;
	}

	public String getLocation() {
		return this._location;
	}

	// setting phone number
	public void setLocation(String location) {
		this._location = location;
	}

	public String getSalary() {
		return this._salary;
	}

	// setting phone number
	public void setSalary(String salary) {
		this._salary = salary;
	}

	public String getResponsibility() {
		return this._responsibility;
	}

	// setting phone number
	public void setResponsibility(String responsibility) {
		this._responsibility = responsibility;
	}

	public String getProfid() {
		return this._profid;
	}

	// setting phone number
	public void setProfid(String profid) {
		this._profid = profid;
	}

	@Override
	public int compareTo(Exper o) {
		// TODO Auto-generated method stub
		return getStartDate().compareTo(o.getStartDate());
	}

}
