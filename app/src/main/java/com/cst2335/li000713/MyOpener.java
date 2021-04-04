package com.cst2335.li000713;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;

import androidx.annotation.Nullable;

public class MyOpener extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "MESSAGEDB";
    protected final static int VERSION_NUM = 5;
    public final static String TABLE_NAME = "MESSAGE_TABLE";
    public final static String COL_ID = "_id";
    public final static String COL_TEXT = "TEXT";
    public final static String COL_TYPE = "SEND_TYPE";



   public MyOpener(Context ctx){

       super(ctx, DATABASE_NAME, null,VERSION_NUM);
   }

     @Override
    public void onCreate(SQLiteDatabase db)
     {
         db.execSQL("CREATE TABLE " + TABLE_NAME + "  (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                 + COL_TEXT + "  text,"
                 + COL_TYPE  + "  INTEGER);");
     }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {   //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }
}
