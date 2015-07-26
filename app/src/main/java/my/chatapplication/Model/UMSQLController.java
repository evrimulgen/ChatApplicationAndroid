package my.chatapplication.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import my.chatapplication.DataHolder.User;

/**
 * Created by nasser on 26/07/15.
 */
public class UMSQLController {
    UMSQLite db;
    Context context ;

    public UMSQLController(Context context) {
        this.context = context;
        db = new UMSQLite(context);
    }

    public void insertUser(User user){
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put(db.TABLE_EMAIL , user.getEmail());
        value.put(db.TABLE_NAME , user.getName());
        value.put(db.TABLE_TELEPONE , user.getTelephone());
        value.put(db.TABLE_PASSWORD , user.getPassword());

        sqLiteDatabase.insert(db.TABLE_USER_NAME , null , value);
        sqLiteDatabase.close();
    }

    public User getUser(){
        User user = null;
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(db.TABLE_USER_NAME , db.selection , null , null , null , null , null);
        if(cursor.moveToFirst()){
            do{
                user = new User();
                user.setEmail(cursor.getString(cursor.getColumnIndex(db.TABLE_EMAIL)));
                user.setName(cursor.getString(cursor.getColumnIndex(db.TABLE_NAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(db.TABLE_PASSWORD)));
                user.setTelephone(cursor.getString(cursor.getColumnIndex(db.TABLE_TELEPONE)));
            }while (cursor.moveToNext());
        }
        return user;
    }

    public void eraseUser() {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+ db.TABLE_USER_NAME);
    }
}
