package com.mercy.idictionary.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.mercy.idictionary.models.Word;

import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils extends SQLiteOpenHelper {

    private DatabaseUtils databaseUtils = null;

    private DatabaseUtils(Context context) {
        super(context, "mydb.sqlite", null, 1);
    }

    public static DatabaseUtils getInstance(Context context){
        return new DatabaseUtils(context);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table tblWord (_id integer primary key autoincrement," +
                " _word text, _definition text)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists tblWord");
        onCreate(sqLiteDatabase);
    }

//    public boolean insertItem(FirebaseModel firebaseModel) {
//        SQLiteDatabase database = getWritableDatabase();
//        ContentValues row = new ContentValues();
//        row.put("_word", firebaseModel.getTitleFire());
//        row.put("_definition", firebaseModel.getContentsFire());
//        long newId = database.insert("tblWord", null, row);
//        return (newId != -1);
//    }


    // insert all items to local database
    public boolean insertAllItems(List<Word> firebaseModels) {
        SQLiteDatabase database = getWritableDatabase();
        long inserted = -1;
        for (Word model : firebaseModels){
            ContentValues row = new ContentValues();
            row.put("_word", model.getTitleFire());
            row.put("_definition", model.getContentsFire());
            inserted = database.insert("tblWord", null, row);
        }
        return (inserted != -1);
    }

    // get all words from local database
    public List<Word> getAllItems() {
        List<Word> words = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        String columns[] = {"_id", "_word", "_definition"};
        Cursor cursor = database.query("tblWord", columns, null,
                null, null, null, "_word");
        while (cursor.moveToNext()){
            Word firebaseModel =
                    new Word(cursor.getString(1), cursor.getString(2));
            words.add(firebaseModel);
        }
        cursor.close();
        return  words;
    }

}
