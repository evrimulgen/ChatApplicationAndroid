package my.chatapplication.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nasser on 26/07/15.
 */
public class UMSQLite extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ChatApplication";

    //Table Name
    public static final String TABLE_USER_NAME = "user";

    // Table Names
    public static final String TABLE_EMAIL = "email";
    public static final String TABLE_PASSWORD = "password";
    public static final String TABLE_TELEPONE = "telephone";
    public static final String TABLE_NAME = "name";

    public static final String[] selection = {
        TABLE_EMAIL ,
        TABLE_PASSWORD ,
        TABLE_NAME ,
        TABLE_TELEPONE
    };

    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_USER =
            "CREATE TABLE "  + TABLE_USER_NAME + "(" +
            TABLE_EMAIL    + " TEXT PRIMARY KEY ," +
            TABLE_PASSWORD + " TEXT NOT NULL ," +
            TABLE_TELEPONE + " TEXT NOT NULL , " +
            TABLE_NAME     + " TEXT NOT NULL"
            + ")";

    public UMSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_NAME);

        // create new tables
        onCreate(db);
    }

}
