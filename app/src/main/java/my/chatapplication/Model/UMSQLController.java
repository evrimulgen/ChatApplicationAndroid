package my.chatapplication.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import my.chatapplication.DataHolder.Chat;
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

        value.put(db.USER_EMAIL, user.getEmail());
        value.put(db.USER_NAME, user.getName());
        value.put(db.USER_TELEPHONE, user.getTelephone());
        value.put(db.USER_PASSWORD, user.getPassword());

        sqLiteDatabase.insert(db.USER_TABLE_NAME, null , value);
        sqLiteDatabase.close();
    }

    public User getUser(){
        User user = null;
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(db.USER_TABLE_NAME, db.selection_user, null , null , null , null , null);
        if(cursor.moveToFirst()){
            do{
                user = new User();
                user.setEmail(cursor.getString(cursor.getColumnIndex(db.USER_EMAIL)));
                user.setName(cursor.getString(cursor.getColumnIndex(db.USER_NAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(db.USER_PASSWORD)));
                user.setTelephone(cursor.getString(cursor.getColumnIndex(db.USER_TELEPHONE)));
            }while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return user;
    }

    public void eraseUser() {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+ db.USER_TABLE_NAME);
        sqLiteDatabase.close();
    }

    public void insertChats(List<Chat> chats){
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        for(Chat chat : chats){
            ContentValues contentValues = new ContentValues();
            contentValues.put(db.CHAT_MESSAGE , chat.getMessage());
            sqLiteDatabase.insert(db.CHAT_TABLE_NAME , null , contentValues);
        }
        sqLiteDatabase.close();
    }

    public List<Chat> getChats(){
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        List<Chat> chats = new ArrayList<Chat>();
        Cursor cursor = sqLiteDatabase.query(db.CHAT_TABLE_NAME, db.selection_chat, null , null , null , null , null);
        if(cursor.moveToFirst()){
            do{
                Chat chat = new Chat();
                chat.setMessage(cursor.getString(cursor.getColumnIndex(db.CHAT_MESSAGE)));
                chats.add(chat);
            }while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return chats;
    }

    public void eraseChat(){
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from " + db.CHAT_TABLE_NAME);
        sqLiteDatabase.close();
    }
}
