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
    private static final String postTable = "post_Table";  // Post Table
    private static final String cricketTable = "cricket_Table";  // Cricket Table
    private static final String footballTable = "football_Table";  // Football Table
    private static final String volleyballTable = "volleyball_Table";  // Volleyball Table


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
                "member_image BLOB "+
                ")";
        db.execSQL(PendingMemberTable);   // Create Pending Member Table

        String PaymentTable = "CREATE TABLE "+paymentTable+"("+
                "payment_id TEXT PRIMARY KEY ,"+
                "payment_month TEXT ,"+
                "payment_date TEXT ,"+
                "payment_amount REAL,"+
                "admin_id TEXT,"+
                "CONSTRAINT payment_admin_FK FOREIGN KEY ('admin_id') REFERENCES admin_Table('admin_id'))";
        db.execSQL(PaymentTable);   // Create Payment Table

        String PostTable = "CREATE TABLE "+postTable+"("+
                "post_id TEXT PRIMARY KEY ,"+
                "post_date TEXT ,"+
                "post_subject TEXT ,"+
                "post_message TEXT,"+
                "post_image BLOB,"+
                "admin_id TEXT,"+
                "CONSTRAINT post_admin_FK FOREIGN KEY ('admin_id') REFERENCES admin_Table('admin_id'))";
        db.execSQL(PostTable);   // Create Post Table

        String CricketTable = "CREATE TABLE "+cricketTable+"("+
                "team_id TEXT PRIMARY KEY ,"+
                "team_name TEXT ,"+
                "captain TEXT ,"+
                "player2 TEXT,"+
                "player3 TEXT,"+
                "player4 TEXT,"+
                "player5 TEXT,"+
                "player6 TEXT,"+
                "player7 TEXT,"+
                "player8 TEXT,"+
                "player9 TEXT,"+
                "player10 TEXT,"+
                "player11 TEXT,"+
                "player12 TEXT,"+
                "player13 TEXT,"+
                "player14 TEXT,"+
                "player15 TEXT,"+
                "admin_id TEXT,"+
                "CONSTRAINT cricket_admin_FK FOREIGN KEY ('admin_id') REFERENCES admin_Table('admin_id'))";
        db.execSQL(CricketTable);   // Create Cricket Table

        String FootballTable = "CREATE TABLE "+footballTable+"("+
                "team_id TEXT PRIMARY KEY ,"+
                "team_name TEXT ,"+
                "captain TEXT ,"+
                "player2 TEXT,"+
                "player3 TEXT,"+
                "player4 TEXT,"+
                "player5 TEXT,"+
                "player6 TEXT,"+
                "player7 TEXT,"+
                "player8 TEXT,"+
                "player9 TEXT,"+
                "player10 TEXT,"+
                "player11 TEXT,"+
                "player12 TEXT,"+
                "player13 TEXT,"+
                "player14 TEXT,"+
                "player15 TEXT,"+
                "admin_id TEXT,"+
                "CONSTRAINT football_admin_FK FOREIGN KEY ('admin_id') REFERENCES admin_Table('admin_id'))";
        db.execSQL(FootballTable);   // Create Football Table

        String VolleyballTable = "CREATE TABLE "+volleyballTable+"("+
                "team_id TEXT PRIMARY KEY ,"+
                "team_name TEXT ,"+
                "captain TEXT ,"+
                "player2 TEXT,"+
                "player3 TEXT,"+
                "player4 TEXT,"+
                "player5 TEXT,"+
                "player6 TEXT,"+
                "player7 TEXT,"+
                "player8 TEXT,"+
                "player9 TEXT,"+
                "player10 TEXT,"+
                "player11 TEXT,"+
                "player12 TEXT,"+
                "admin_id TEXT,"+
                "CONSTRAINT volleyball_admin_FK FOREIGN KEY ('admin_id') REFERENCES admin_Table('admin_id'))";
        db.execSQL(VolleyballTable);   // Create Volleyball Table

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+memberTable);
        db.execSQL("DROP TABLE IF EXISTS "+authenticationTable);
        db.execSQL("DROP TABLE IF EXISTS "+pendingMemberTable);
        db.execSQL("DROP TABLE IF EXISTS "+adminTable);
        db.execSQL("DROP TABLE IF EXISTS "+paymentTable);
        db.execSQL("DROP TABLE IF EXISTS "+postTable);
        db.execSQL("DROP TABLE IF EXISTS "+cricketTable);
        db.execSQL("DROP TABLE IF EXISTS "+footballTable);
        db.execSQL("DROP TABLE IF EXISTS "+volleyballTable);
        onCreate(db);
    }

    public Cursor getId(String query)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }



    public boolean insertMember(String member_id, String member_name, String member_address, String member_dob, String member_phone,
                              String member_nic, String member_sport, String member_gender, String member_status, byte[] member_image,
                              String admin_id,String authentication_id,String username,String password,String user_role)
    {
        SQLiteDatabase dbHandlerMember = this.getWritableDatabase();
        // Member Table
        ContentValues contentValuesMember = new ContentValues();
        contentValuesMember.put("member_id",member_id);
        contentValuesMember.put("member_name",member_name);
        contentValuesMember.put("member_address",member_address);
        contentValuesMember.put("member_dob",member_dob);
        contentValuesMember.put("member_phone",member_phone);
        contentValuesMember.put("member_nic",member_nic);
        contentValuesMember.put("member_sport",member_sport);
        contentValuesMember.put("member_gender",member_gender);
        contentValuesMember.put("member_status",member_status);
        contentValuesMember.put("member_image",member_image);
        contentValuesMember.put("admin_id",admin_id);
        contentValuesMember.put("authentication_id",authentication_id);

        SQLiteDatabase dbHandlerAuthentication = this.getWritableDatabase();
        // Authentication Table
        ContentValues contentValuesAuthentication = new ContentValues();
        contentValuesAuthentication.put("authentication_id",authentication_id);
        contentValuesAuthentication.put("username",username);
        contentValuesAuthentication.put("password",password);
        contentValuesAuthentication.put("user_role",user_role);

        long resultMember = dbHandlerMember.insert(memberTable,null,contentValuesMember);
        long resultAuthentication = dbHandlerAuthentication.insert(authenticationTable,null,contentValuesAuthentication);

        dbHandlerMember.close();
        dbHandlerAuthentication.close();

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

    // Update Member
    public boolean editMember(String member_id, String member_name, String member_address, String member_dob, String member_phone,
                              String member_nic, String member_sport, String member_gender, byte[] member_image,
                              String username,String password,String authentication_id) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValuesMember = new ContentValues();
        contentValuesMember.put("member_id",member_id);
        contentValuesMember.put("member_name",member_name);
        contentValuesMember.put("member_address",member_address);
        contentValuesMember.put("member_dob",member_dob);
        contentValuesMember.put("member_phone",member_phone);
        contentValuesMember.put("member_nic",member_nic);
        contentValuesMember.put("member_sport",member_sport);
        contentValuesMember.put("member_gender",member_gender);
        contentValuesMember.put("member_image",member_image);

        long resultMember = db.update(memberTable, contentValuesMember, "member_id = ?", new String[]{member_id});

        ContentValues contentValuesAuthentication = new ContentValues();
        contentValuesAuthentication.put("username",username);
        contentValuesAuthentication.put("password",password);

        long resultAuthentication = db.update(authenticationTable, contentValuesAuthentication, "authentication_id = ?", new String[]{authentication_id});

        db.close();

        if (resultMember == -1 || resultAuthentication == -1)
        {
            Log.d("Message", "Error While adding");
            return false;
        } else {
            Log.d("Message", "Successfully Added");
            return true;
        }
    }

    public Cursor getEmailExist()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select username from authentication_Table";
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }

    public Cursor getNicExist()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select member_nic from member_Table";
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }

    public Cursor getMember(String query){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public boolean insertPendingMember(String member_id, String member_name, String member_address, String member_dob, String member_phone,
                                String member_nic, String member_sport, String member_gender, byte[] member_image,
                                String email_id,String password)
    {
        SQLiteDatabase dbHandlerMember = this.getWritableDatabase();
        // Member Table
        ContentValues contentValuesMember = new ContentValues();
        contentValuesMember.put("member_id",member_id);
        contentValuesMember.put("member_name",member_name);
        contentValuesMember.put("member_address",member_address);
        contentValuesMember.put("member_dob",member_dob);
        contentValuesMember.put("member_phone",member_phone);
        contentValuesMember.put("member_nic",member_nic);
        contentValuesMember.put("member_sport",member_sport);
        contentValuesMember.put("member_gender",member_gender);
        contentValuesMember.put("member_image",member_image);
        contentValuesMember.put("member_email",email_id);
        contentValuesMember.put("member_password",password);

        long resultMember = dbHandlerMember.insert(pendingMemberTable,null,contentValuesMember);

        dbHandlerMember.close();

        if(resultMember == -1)
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

    // Update Status
    public boolean updateMemberStatus(String member_id,String member_status){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValuesMember = new ContentValues();

        contentValuesMember.put("member_status",member_status);

        long resultMember = db.update(memberTable, contentValuesMember, "member_id = ?", new String[]{member_id});

        db.close();

        if (resultMember == -1)
        {
            Log.d("Message", "Error While adding");
            return false;
        } else {
            Log.d("Message", "Successfully Added");
            return true;
        }
    }

    // Delete pending Member
    public boolean deletePendingMember(String member_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(pendingMemberTable, "member_id='" + member_id+"'", null) > 0;

    }

    // Insert Into Payment Table
    public boolean insertPayemnt(String payment_id, String payment_month, String payment_date,Double payment_amount, String admin_id)
    {
        SQLiteDatabase dbHandlerPayment = this.getWritableDatabase();
        // Member Table
        ContentValues contentValuesPayment = new ContentValues();
        contentValuesPayment.put("payment_id",payment_id);
        contentValuesPayment.put("payment_month",payment_month);
        contentValuesPayment.put("payment_date",payment_date);
        contentValuesPayment.put("payment_amount",payment_amount);
        contentValuesPayment.put("admin_id",admin_id);

        long resultPayment = dbHandlerPayment.insert(paymentTable,null,contentValuesPayment);

        dbHandlerPayment.close();

        if(resultPayment == -1)
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


    // Insert Into Payment Table
    public boolean insertPost(String post_id, String post_date,String post_subject,String post_message,byte[] post_image, String admin_id)
    {
        SQLiteDatabase dbHandlerPost = this.getWritableDatabase();
        // Member Table
        ContentValues contentValuesPost = new ContentValues();
        contentValuesPost.put("post_id",post_id);
        contentValuesPost.put("post_date",post_date);
        contentValuesPost.put("post_subject",post_subject);
        contentValuesPost.put("post_message",post_message);
        contentValuesPost.put("post_image",post_image);
        contentValuesPost.put("admin_id",admin_id);

        long resultPost = dbHandlerPost.insert(postTable,null,contentValuesPost);

        dbHandlerPost.close();

        if(resultPost == -1)
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

    // Delete pending Member
    public boolean deletePost(String post_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(postTable, "post_id='" + post_id+"'", null) > 0;

    }

    // Insert into Cricket Table
    public boolean insertCricketTeam(String team_id, String team_name, String captain, String player2, String player3, String player4, String player5,
                                     String player6, String player7, String player8, String player9, String player10, String player11, String player12,
                                     String player13, String player14, String admin_id, String addmin001)
    {
        SQLiteDatabase dbHandlerCricket = this.getWritableDatabase();
        // Cricket Table
        ContentValues contentValuesCricket = new ContentValues();
        contentValuesCricket.put("team_id",team_id);
        contentValuesCricket.put("team_name",team_name);
        contentValuesCricket.put("captain",captain);
        contentValuesCricket.put("player2",player2);
        contentValuesCricket.put("player3",player3);
        contentValuesCricket.put("player4",player4);
        contentValuesCricket.put("player5",player5);
        contentValuesCricket.put("player6",player6);
        contentValuesCricket.put("player7",player7);
        contentValuesCricket.put("player8",player8);
        contentValuesCricket.put("player9",player9);
        contentValuesCricket.put("player10",player10);
        contentValuesCricket.put("player11",player11);
        contentValuesCricket.put("player12",player12);
        contentValuesCricket.put("player13",player13);
        contentValuesCricket.put("player14",player14);
        contentValuesCricket.put("admin_id",admin_id);

        long resultCricket = dbHandlerCricket.insert(cricketTable,null,contentValuesCricket);

        dbHandlerCricket.close();

        if(resultCricket == -1)
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

    public Cursor getMemberID(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT member_name,member_sport FROM member_Table WHERE member_id='"+id+"'";
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }

    // Insert into Football Table
    public boolean insertFootballTeam(String team_id, String team_name, String captain, String player2, String player3, String player4, String player5,
                                      String player6, String player7, String player8, String player9, String player10, String player11, String player12,
                                      String player13, String player14, String admin_id, String admin001)
    {
        SQLiteDatabase dbHandlerFootball = this.getWritableDatabase();
        // Cricket Table
        ContentValues contentValuesFootabll = new ContentValues();
        contentValuesFootabll.put("team_id",team_id);
        contentValuesFootabll.put("team_name",team_name);
        contentValuesFootabll.put("captain",captain);
        contentValuesFootabll.put("player2",player2);
        contentValuesFootabll.put("player3",player3);
        contentValuesFootabll.put("player4",player4);
        contentValuesFootabll.put("player5",player5);
        contentValuesFootabll.put("player6",player6);
        contentValuesFootabll.put("player7",player7);
        contentValuesFootabll.put("player8",player8);
        contentValuesFootabll.put("player9",player9);
        contentValuesFootabll.put("player10",player10);
        contentValuesFootabll.put("player11",player11);
        contentValuesFootabll.put("player12",player12);
        contentValuesFootabll.put("player13",player13);
        contentValuesFootabll.put("player14",player14);
        contentValuesFootabll.put("admin_id",admin_id);

        long resultCricket = dbHandlerFootball.insert(footballTable,null,contentValuesFootabll);

        dbHandlerFootball.close();

        if(resultCricket == -1)
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


    // Insert into Volleyball Table
    public boolean insertVolleyballTeam(String team_id,String team_name,String captain,String player2,String player3,String player4,String player5,
                                      String player6,String player7,String player8,String player9,String player10,String player11,String player12,
                                        String admin_id)
    {
        SQLiteDatabase dbHandlerFootball = this.getWritableDatabase();
        // Cricket Table
        ContentValues contentValuesFootabll = new ContentValues();
        contentValuesFootabll.put("team_id",team_id);
        contentValuesFootabll.put("team_name",team_name);
        contentValuesFootabll.put("captain",captain);
        contentValuesFootabll.put("player2",player2);
        contentValuesFootabll.put("player3",player3);
        contentValuesFootabll.put("player4",player4);
        contentValuesFootabll.put("player5",player5);
        contentValuesFootabll.put("player6",player6);
        contentValuesFootabll.put("player7",player7);
        contentValuesFootabll.put("player8",player8);
        contentValuesFootabll.put("player9",player9);
        contentValuesFootabll.put("player10",player10);
        contentValuesFootabll.put("player11",player11);
        contentValuesFootabll.put("player12",player12);
        contentValuesFootabll.put("admin_id",admin_id);

        long resultCricket = dbHandlerFootball.insert(footballTable,null,contentValuesFootabll);

        dbHandlerFootball.close();

        if(resultCricket == -1)
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
