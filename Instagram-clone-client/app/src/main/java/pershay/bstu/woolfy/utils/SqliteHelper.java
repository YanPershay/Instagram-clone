package pershay.bstu.woolfy.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "User.db";
    public static final String TABLENAME = "User";
    public static final String COL_1 = "USER_ID";
    public static final String COL_2 = "USERNAME";

    public SqliteHelper(Context context){
        super(context, DBNAME, null ,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table IF NOT EXISTS " + TABLENAME + " (USER_ID TEXT, USERNAME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(db);
    }

    public boolean insertData(String userId, String username){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, userId);
        contentValues.put(COL_2, username);
        long result = db.insert(TABLENAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public void deleteData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLENAME);
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLENAME, null);
        return res;
    }
}
