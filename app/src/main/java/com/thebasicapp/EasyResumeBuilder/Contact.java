package com.thebasicapp.EasyResumeBuilder;

public class Contact {
     
    //private variables
    int _id;
    String _name;
    String _gender,_DOB,_address,_languages,_Contact,_Emial,_profid;
     
    // Empty constructor
    public Contact(){
         
    }
    // constructor
    public Contact(int id, String name, String gender, String DOB, String Address
    		, String Languages, String Contact,String Email,String Profid){
        this._id = id;
        this._name = name;
        this._gender = gender;
        this._DOB = DOB;
        this._address = Address;
        this._languages = Languages;
        this._Contact = Contact;
        this._Emial = Email;
        this._profid = Profid;
        
    }
     
    // constructor
    public Contact(String name, String gender, String DOB, String Address
    		, String Languages, String Contact,String Email,String Profid){
    	this._name = name;
        this._gender = gender;
        this._DOB = DOB;
        this._address = Address;
        this._languages = Languages;
        this._Contact = Contact;
        this._Emial = Email;
        this._profid = Profid;
    }
    // getting ID
    public int getID(){
        return this._id;
    }
     
    // setting id
    public void setID(int id){
        this._id = id;
    }
     
    // getting name
    public String getName(){
        return this._name;
    }
     
    // setting name= 
    public void setName(String name){
        this._name = name;
    }
     
    // getting phone number
    public String getGender(){
        return this._gender;
    }
     
    // setting phone number
    public void setGender(String gender){
        this._gender = gender;
    }
    
    public String getDOB(){
        return this._DOB;
    }
     
    // setting phone number
    public void setDOB(String dob){
        this._DOB = dob;
    }
    
    public String getAddress(){
        return this._address;
    }
     
    // setting phone number
    public void setAddress(String address){
        this._address = address;
    }
    
    public String getLanguage(){
        return this._languages;
    }
     
    // setting phone number
    public void setLanguage(String languages){
        this._languages = languages;
    }
    
    public String getContact(){
        return this._Contact;
    }
     
    // setting phone number
    public void setContact(String contact){
        this._Contact = contact;
    }
    
    public String getEmail(){
        return this._Emial;
    }
     
    // setting phone number
    public void setEmail(String email){
        this._Emial = email;
    }
    
    public String getProfid(){
        return this._profid;
    }
     
    // setting phone number
    public void setProfid(String profid){
        this._profid = profid;
    }
    
}
