package com.example.vinay.dealmash;

/**
 * Created by Vinay on 31-01-2017.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LoginDataBaseAdapter
{
    static final String DATABASE_NAME = "login.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table "+"LOGIN"+
            "( " +"ID"+" integer primary key autoincrement,"+ "USEREMAIL text, USERNAME  text,PASSWORD text); ";
    // Variable to hold the database instance
    public  SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelper dbHelper;
    public  LoginDataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public  LoginDataBaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public void insertEntry(String useremail,String userName,String password)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("USEREMAIL",useremail);
        newValues.put("USERNAME", userName);
        newValues.put("PASSWORD",password);

        // Insert the row into your table
        db.insert("LOGIN", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }
    public int deleteEntry(String Useremail)
    {
        //String id=String.valueOf(ID);
        String where="USEREMAIL=?";
        int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{Useremail}) ;
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }
    public String getPassword(String useremail)
    {
        Cursor cursor=db.query("LOGIN", null, " USEREMAIL=?", new String[]{useremail}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }
    public String getUsername(String useremail)
    {
        Cursor cursor=db.query("LOGIN", null, " USEREMAIL=?", new String[]{useremail}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "";
        }
        cursor.moveToFirst();
        String username= cursor.getString(cursor.getColumnIndex("USERNAME"));
        cursor.close();
        return username;
    }
    public void  updateEntry(String useremail,String password)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("USEREMAIL", useremail);
        updatedValues.put("PASSWORD",password);

        String where="USEREMAIL = ?";
        db.update("LOGIN",updatedValues, where, new String[]{useremail});
    }
}