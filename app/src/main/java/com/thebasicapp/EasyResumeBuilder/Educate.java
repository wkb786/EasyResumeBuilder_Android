package com.thebasicapp.EasyResumeBuilder;

import java.util.Date;

public class Educate {
    
   //private variables
   int _id;
   String _degree;
   String _uni,_school,_result,_passingyear,_profid;
   private Date startDate;
    
   // Empty constructor
   public Educate(){
        
   }
   // constructor
   public Educate(int id, String degree, String uni, String school, String result
   		, String passingyear,String Profid,Date StartDate){
       this._id = id;
       this._degree = degree;
       this._uni = uni;
       this._school = school;
       this._result = result;
       this._passingyear = passingyear;   
       this._profid = Profid;
       this.startDate=StartDate;
   }
   
   
    
   public Date getStartDate() {
	return startDate;
}
public void setStartDate(Date startDate) {
	this.startDate = startDate;
}
// constructor
   public Educate(String degree, String uni, String school, String result
	   		, String passingyear,String Profid,Date StartDate){
	   this._degree = degree;
       this._uni = uni;
       this._school = school;
       this._result = result;
       this._passingyear = passingyear; 
       this._profid = Profid;
       this.startDate=StartDate;
   }
   // getting ID
   public int getEDID(){
       return this._id;
   }
    
   // setting id
   public void setEDID(int id){
       this._id = id;
   }
    
   // getting name
   public String getDegree(){
       return this._degree;
   }
    
   // setting name= 
   public void setDegree(String degree){
       this._degree = degree;
   }
    
   // getting phone number
   public String getUni(){
       return this._uni;
   }
    
   // setting phone number
   public void setUni(String uni){
       this._uni = uni;
   }
   
   public String getSchool(){
       return this._school;
   }
    
   // setting phone number
   public void setSchool(String school){
       this._school = school;
   }
   
   public String getResult(){
       return this._result;
   }
    
   // setting phone number
   public void setResult(String result){
       this._result = result;
   }
   
   public String getPassingyear(){
       return this._passingyear;
   }
    
   // setting phone number
   public void setPassingyear(String passingyear){
       this._passingyear = passingyear;
   }
   
   public String getProfid(){
       return this._profid;
   }
    
   // setting phone number
   public void setProfid(String profid){
       this._profid = profid;
   }
   
}
