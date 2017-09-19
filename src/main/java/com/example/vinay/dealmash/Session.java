package com.example.vinay.dealmash;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Vinay on 01-02-2017.
 */
public class Session {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public Session(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences("myapp",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public boolean isloggedin(){
        String usersessionname=sharedPreferences.getString("sessionname","");
        if(usersessionname.equals("")){
            return false;
        }
        return true;
    }
    public  void setloggedin(String username){
        editor.putString("sessionname",username);
        editor.commit();
    }
    public void  setloggedout(){
        editor.putString("sessionname","");
        editor.commit();
    }
    public String getusername(){
        return sharedPreferences.getString("sessionname","");
    }
    public void putPreferences(String username,String Preferences){
        editor.putString(username,Preferences);
        editor.commit();
    }
    public void putRadius(String username,String radius){
        editor.putString(username+"radius",radius);
        editor.commit();
    }
    public String getRadius(String username){
        return sharedPreferences.getString(username+"radius","");
    }
    public String getPreferences(String username){
        return sharedPreferences.getString(username,"");
    }
}
