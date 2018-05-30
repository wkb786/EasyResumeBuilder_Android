package com.thebasicapp.EasyResumeBuilder;

public class Prof {
    
   //private variables
   int _id;
   String _profilename;
    
   // Empty constructor
   public Prof(){
        
   }
   // constructor
   public Prof(int id, String profilename){
       this._id = id;
       this._profilename = profilename;
   }
    
   // constructor
   public Prof(String profilename){
   	this._profilename = profilename;
   // getting ID
   }
   
   	public int getPROFID(){
       return this._id;
   }
    
   // setting id
   public void setPROFID(int id){
       this._id = id;
   }
    
   // getting name
   public String getProfilename(){
       return this._profilename;
   }
    
   // setting name= 
   public void setProfilename(String profilename){
       this._profilename = profilename;
   }
    
   
   
}
