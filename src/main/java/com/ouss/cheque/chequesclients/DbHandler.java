package com.ouss.cheque.chequesclients;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static java.lang.Integer.parseInt;

public class DbHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "ipsas";
    private static final String TABLE_Users = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";
    private static final String KEY_EMAIL = "email";

    private static final String TABLE_cheques = "cheques";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_DATE = "date";
    private static final String KEY_STATE = "state";
    private static final String KEY_UserEmail = "useremail";
    public DbHandler(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE_clt = "CREATE TABLE " + TABLE_Users + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_FNAME + " TEXT,"
                + KEY_LNAME+ " TEXT,"
                + KEY_EMAIL + " TEXT"+ ")";
        String CREATE_TABLE_cheque = "CREATE TABLE " + TABLE_cheques + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_AMOUNT + " TEXT,"
                + KEY_DATE+ " TEXT,"
                + KEY_STATE + " TEXT,"
                + KEY_UserEmail+ " TEXT" +
                ")";
        db.execSQL(CREATE_TABLE_clt);
        db.execSQL(CREATE_TABLE_cheque);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_cheques);
        // Create tables again
        onCreate(db);
    }
    // **** CRUD (Create, Read, Update, Delete) Operations ***** //

    // Adding new User Details
    void insertUserDetails(String fName, String lName, String email){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_FNAME, fName);
        cValues.put(KEY_LNAME, lName);
        cValues.put(KEY_EMAIL, email);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Users,null, cValues);
        db.close();
    }
    // Adding new Cheque
    void insertUserCheque(String amount, String date, String state,String email){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_AMOUNT, amount);
        cValues.put(KEY_DATE, date);
        cValues.put(KEY_STATE, state);
        cValues.put(KEY_UserEmail, email);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_cheques,null, cValues);
        db.close();
    }

    // Get User Details
    public ArrayList<HashMap<String, String>> GetUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_Users;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("id",cursor.getString(cursor.getColumnIndex(KEY_ID)));
            user.put("fname",cursor.getString(cursor.getColumnIndex(KEY_FNAME)));
            user.put("lname",cursor.getString(cursor.getColumnIndex(KEY_LNAME)));
            user.put("email",cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
            userList.add(user);
        }
        return  userList;
    }

    // Get Cheque
    public ArrayList<ChequeEntity> GetCheques(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<ChequeEntity> userList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_cheques;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            ChequeEntity user = new ChequeEntity(
                    cursor.getString(cursor.getColumnIndex(KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(KEY_AMOUNT)),
                    cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                    cursor.getString(cursor.getColumnIndex(KEY_STATE)),
                    cursor.getString(cursor.getColumnIndex(KEY_UserEmail)));
            userList.add(user);
        }
        return  userList;
    }

    // Get User Details based on userid
    public ChequeEntity GetUserByUserId(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_Users;
        Cursor cursor = db.query(TABLE_Users, new String[]{KEY_FNAME, KEY_LNAME, KEY_EMAIL}, KEY_ID+ "=?",new String[]{String.valueOf(userid)},null, null, null, null);
        if (cursor.moveToNext()){
            ChequeEntity user = new ChequeEntity(
                    cursor.getString(cursor.getColumnIndex(KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(KEY_AMOUNT)),
                    cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                    cursor.getString(cursor.getColumnIndex(KEY_STATE)),
                    cursor.getString(cursor.getColumnIndex(KEY_UserEmail)));
            return user;
        }
        return null;
    }

    // Get User Details based on userid
    public ChequeEntity GetChequeById(int chequeid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery("SELECT * from "+ TABLE_cheques+ " WHERE id='"+chequeid+"'",null);
        cursor.moveToFirst();
            ChequeEntity cheque= new ChequeEntity(cursor.getString(cursor.getColumnIndex(KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(KEY_AMOUNT)),
                    cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                    cursor.getString(cursor.getColumnIndex(KEY_STATE)),
                    cursor.getString(cursor.getColumnIndex(KEY_UserEmail)));
        cursor.close();
        return  cheque;
    }

    // Update User Details
    public int UpdateCheque(String id,String state){
        SQLiteDatabase db = this.getWritableDatabase();
        String st= state.toLowerCase().contains("no")? "Soldé": "Non soldé";
        ContentValues cVals = new ContentValues();
        cVals.put(KEY_STATE, st);
        int count = db.update(TABLE_cheques, cVals, KEY_ID+" = ?",new String[]{String.valueOf(id)});
        return  count;
    }

}

