package be.artoria.belfortapp.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dietn on 15.09.14.
 */
public class MuseumDbHelper extends SQLiteOpenHelper{
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MuseumContract.MuseumEntry.TABLE_NAME + " (" +
                    MuseumContract.MuseumEntry.COLUMN_NAME_FLOOR + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    MuseumContract.MuseumEntry.COLUMN_NAME_IMAGES + TEXT_TYPE + COMMA_SEP +

                    MuseumContract.MuseumEntry.COLUMN_NAME_NL_NAME + TEXT_TYPE + COMMA_SEP +
                    MuseumContract.MuseumEntry.COLUMN_NAME_NL_DESC + TEXT_TYPE + COMMA_SEP +

                    MuseumContract.MuseumEntry.COLUMN_NAME_EN_NAME + TEXT_TYPE + COMMA_SEP +
                    MuseumContract.MuseumEntry.COLUMN_NAME_EN_DESC + TEXT_TYPE + COMMA_SEP +

                    MuseumContract.MuseumEntry.COLUMN_NAME_FR_NAME + TEXT_TYPE + COMMA_SEP +
                    MuseumContract.MuseumEntry.COLUMN_NAME_FR_DESC + TEXT_TYPE + COMMA_SEP +

                    MuseumContract.MuseumEntry.COLUMN_NAME_IT_NAME + TEXT_TYPE + COMMA_SEP +
                    MuseumContract.MuseumEntry.COLUMN_NAME_IT_DESC + TEXT_TYPE + COMMA_SEP +

                    MuseumContract.MuseumEntry.COLUMN_NAME_ES_NAME + TEXT_TYPE + COMMA_SEP +
                    MuseumContract.MuseumEntry.COLUMN_NAME_ES_DESC + TEXT_TYPE + COMMA_SEP +

                    MuseumContract.MuseumEntry.COLUMN_NAME_RU_NAME + TEXT_TYPE + COMMA_SEP +
                    MuseumContract.MuseumEntry.COLUMN_NAME_RU_DESC + TEXT_TYPE + COMMA_SEP +

                    MuseumContract.MuseumEntry.COLUMN_NAME_DE_NAME + TEXT_TYPE + COMMA_SEP +
                    MuseumContract.MuseumEntry.COLUMN_NAME_DE_DESC + TEXT_TYPE +
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MuseumContract.MuseumEntry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Museum.db";

    public MuseumDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
