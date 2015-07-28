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

    // Table User
    public static final String USER_TABLE_NAME = "user";

    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_TELEPHONE = "telephone";
    public static final String USER_NAME = "name";
    // *****************************************************

    // Table Chat
    public static final String CHAT_TABLE_NAME = "chat";
    public static final String CHAT_ID = "id";
    public static final String CHAT_MESSAGE = "message";
    // ****************************************************

    public static final String[] selection_user = {
            USER_EMAIL,
            USER_PASSWORD,
            USER_NAME,
            USER_TELEPHONE
    };

    public static final String[] selection_chat = {
            CHAT_ID,
            CHAT_MESSAGE
    };

    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_USER =
            "CREATE TABLE "  + USER_TABLE_NAME + "(" +
                    USER_EMAIL + " TEXT PRIMARY KEY ," +
                    USER_PASSWORD + " TEXT NOT NULL ," +
                    USER_TELEPHONE + " TEXT NOT NULL , " +
                    USER_NAME + " TEXT NOT NULL"
            + ")";

    private static final String CREATE_TABLE_CHAT =
            "CREATE TABLE " + CHAT_TABLE_NAME + "("+
                    CHAT_ID + " INTEGER PRIMARY KEY , " +
                    CHAT_MESSAGE + " TEXT NOT NULL)";

    public UMSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_CHAT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CHAT_TABLE_NAME);
        // create new tables
        onCreate(db);
    }

}
