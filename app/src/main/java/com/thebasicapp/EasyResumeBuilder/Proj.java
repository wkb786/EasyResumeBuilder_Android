package com.thebasicapp.EasyResumeBuilder;

public class Proj {
    
   //private variables
   int _id;
   String _projtitle;
   String _projduration,_role,_teamsize,_expertise,_profid;
    
   // Empty constructor
   public Proj(){
        
   }
   // constructor
   public Proj(int id, String projtitle, String projduration, String role, String teamsize
   		, String expertise,String Profid){
       this._id = id;
       this._projtitle = projtitle;
       this._projduration = projduration;
       this._role = role;
       this._teamsize = teamsize;
       this._expertise = expertise;   
       this._profid = Profid;    
   }
    
   // constructor
   public Proj(String projtitle, String projduration, String role, String teamsize
	   		, String expertise,String Profid){
	   this._projtitle = projtitle;
       this._projduration = projduration;
       this._role = role;
       this._teamsize = teamsize;
       this._expertise = expertise;
       this._profid = Profid;
   }
   // getting ID
   public int getPRID(){
       return this._id;
   }
    
   // setting id
   public void setPRID(int id){
       this._id = id;
   }
    
   // getting name
   public String getPrTitle(){
       return this._projtitle;
   }
    
   // setting name= 
   public void setPrTitle(String projtitle){
       this._projtitle = projtitle;
   }
    
   // getting phone number
   public String getPrDuration(){
       return this._projduration;
   }
    
   // setting phone number
   public void setPrDuration(String projduration){
       this._projduration = projduration;
   }
   
   public String getRole(){
       return this._role;
   }
    
   // setting phone number
   public void setRole(String role){
       this._role = role;
   }
   
   public String getTsize(){
       return this._teamsize;
   }
    
   // setting phone number
   public void setTsize(String teamsize){
       this._teamsize = teamsize;
   }
   
   public String getExpertise(){
       return this._expertise;
   }
    
   // setting phone number
   public void setExpertise(String expertise){
       this._expertise = expertise;
   }
   
   public String getProfid(){
       return this._profid;
   }
    
   // setting phone number
   public void setProfid(String profid){
       this._profid = profid;
   }
   
}
