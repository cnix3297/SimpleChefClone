package com.example.simplechef;

import android.database.Cursor;
import android.media.Image;

public class User {
    private String username;
    private String password;
    private String joinDate;
    private String  picture;
    private boolean changed = false;

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    private String birthDate;

    User(){

    }//to create an account from a database query.

    User(String username, String password){
        this.username = username;
        this.password = password;
    } //to create an account from a login screen.

    public String getUsername(){return username;}
    public void   setUsername(String username){
        this.username = username;
        changed = true;
    }

    public String getJoinDateate() {
        return joinDate;
    }

    private void formatDate(){

    } //format as needed. default format is yyyy-mm-dd


    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
        changed = true;
    }

    public String getPassword() {
            return password;
        }

    public void setPassword(String password) {
        this.password = password;
        changed = true;
    }



   /* public void checkChanged(DatabaseHelper myDBHelper){
        if(changed)
            myDBHelper.updateUser(this); //calls a query to update the user;
        changed = false;
    }
*/



}
