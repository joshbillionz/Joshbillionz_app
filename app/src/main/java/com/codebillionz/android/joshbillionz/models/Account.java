package com.codebillionz.android.joshbillionz.models;


public class Account {

    private String username;
    private Name name;
    private String email;
    private String password;
    private String uId;

    public Account(){

    }

    public Account(String FirstName, String lastName , String email, String password, String uId){
        setName(new Name(FirstName, lastName));
        this.email = email;
        setPassword(password);
        this.uId=uId;
    }


    //setters

    private void setUsername(String username) {
        this.username = username;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
    //getters


    public String getuId() {
        return uId;
    }

    public String getUsername() {
        return username;
    }

    public Name getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }



    //inner classes
    public static class Name{
       private String firstName;
       private String middleName;
       private String lastName;

        Name(){

        }
        Name(String firstName, String middleName, String lastName){
            this.firstName= firstName;
            this.lastName= lastName;
            this.middleName= middleName;

        }

        Name(String firstName, String lastName){
            this.firstName = firstName;
            this.lastName= lastName;

        }



        public String getFullNameString(){
            return new String(firstName +" "+lastName);
        }

        //getters

        public String getFirstName() {
            return firstName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public String getLastName() {
            return lastName;
        }

        //setters


        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }

}


