package com.example.hunterz;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Blob;


public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;  // database version
    private static final String DB_NAME = "Hunterz_DB";  // database name

    private static final String memberTable = "member_Table";  // Member Table
    private static final String adminTable = "admin_Table";  // Admin Table
    private static final String authenticationTable = "authentication_Table";  // Authentication Table
    private static final String pendingMemberTable = "pendingMember_Table";  // Pending Member Table
    private static final String paymentTable = "payment_Table";  // Payment Table


    public DatabaseHandler(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String MemberTable = "CREATE TABLE "+memberTable+"("+
                "member_id TEXT PRIMARY KEY ,"+
                "member_name TEXT ,"+
                "member_address TEXT ,"+
                "member_dob TEXT ,"+
                "member_phone TEXT ,"+
                "member_nic TEXT ,"+
                "member_sport TEXT ,"+
                "member_gender TEXT ,"+
                "member_status TEXT ,"+
                "member_image BLOB ,"+
                "admin_id TEXT ,"+
                "authentication_id TEXT,"+
                "CONSTRAINT member_admin_FK FOREIGN KEY ('admin_id') REFERENCES admin_Table('admin_id'),"+
                "CONSTRAINT member_authentication_FK FOREIGN KEY ('authentication_id') REFERENCES authentication_Table('authentication_id'))";
        db.execSQL(MemberTable);   // Create Member Table

        String AdminTable = "CREATE TABLE "+adminTable+"("+  // admin Table Column
                "admin_id TEXT PRIMARY KEY ,"+
                "admin_name TEXT ,"+
                "admin_nic TEXT ,"+
                "admin_phone TEXT ,"+
                "authentication_id TEXT,"+
                "CONSTRAINT admin_authentication_FK FOREIGN KEY ('authentication_id') REFERENCES authentication_Table('authentication_id'))";
        db.execSQL(AdminTable);   // Create Admin Table

        String AuthenticationTable = "CREATE TABLE "+authenticationTable+"("+  // admin Table Column
                "authentication_id TEXT PRIMARY KEY ,"+
                "username TEXT ,"+
                "password TEXT ,"+
                "user_role TEXT,"+
                "CONSTRAINT admin_authentication_FK FOREIGN KEY ('authentication_id') REFERENCES authentication_Table('authentication_id'))";
        db.execSQL(AuthenticationTable);   // Create Authentication Table

        String PendingMemberTable = "CREATE TABLE "+pendingMemberTable+"("+
                "member_id TEXT PRIMARY KEY ,"+
                "member_name TEXT ,"+
                "member_address TEXT ,"+
                "member_dob TEXT ,"+
                "member_phone TEXT ,"+
                "member_nic TEXT ,"+
                "member_sport TEXT ,"+
                "member_gender TEXT ,"+
                "member_password TEXT ,"+
                "member_email TEXT ,"+
                "member_image BLOB ,"+
                "authentication_id TEXT,"+
                "CONSTRAINT member_authentication_FK FOREIGN KEY ('authentication_id') REFERENCES authentication_Table('authentication_id'))";
        db.execSQL(PendingMemberTable);   // Create Pending Member Table

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+memberTable);
        db.execSQL("DROP TABLE IF EXISTS "+authenticationTable);
        db.execSQL("DROP TABLE IF EXISTS "+pendingMemberTable);
        db.execSQL("DROP TABLE IF EXISTS "+adminTable);
        onCreate(db);
    }

    public Cursor getId(String query)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }

    public boolean insertMember(String member_id, String member_name, String member_address, String member_dob, String member_phone,
                              String member_nic, String member_sport, String member_gender, String member_status, Blob member_image,
                              String admin_id,String authentication_id,String username,String password,String user_role)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        // Member Table
        ContentValues contentValuesMember = new ContentValues();
        contentValuesMember.put("member_id",member_id);
        contentValuesMember.put("member_name",member_name);
        contentValuesMember.put("member_address",member_address);
        contentValuesMember.put("member_dob",member_dob);
        contentValuesMember.put("member_phone",member_phone);
        contentValuesMember.put("member_nic",member_nic);
        contentValuesMember.put("member_sport",member_sport);
        contentValuesMember.put("member_genser",member_gender);
        contentValuesMember.put("member_status",member_status);
        contentValuesMember.put("member_image",member_image.toString());
        contentValuesMember.put("admin_id",admin_id);
        contentValuesMember.put("authentication_id",authentication_id);

        // Authentication Table
        ContentValues contentValuesAuthentication = new ContentValues();
        contentValuesAuthentication.put("authentication_id",authentication_id);
        contentValuesAuthentication.put("username",username);
        contentValuesAuthentication.put("password",password);
        contentValuesAuthentication.put("user_role",user_role);

        long resultMember = db.insert(memberTable,null,contentValuesMember);
        long resultAuthentication = db.insert(memberTable,null,contentValuesAuthentication);

        db.close();

        if(resultMember == -1 || resultAuthentication == -1)
        {
            Log.d("Message","Error While adding");
            return false;
        }
        else
        {
            Log.d("Message","Successfully Added");
            return true;
        }
    }





}
