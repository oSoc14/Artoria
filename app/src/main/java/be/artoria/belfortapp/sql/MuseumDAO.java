package be.artoria.belfortapp.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import be.artoria.belfortapp.app.Floor;


/**
 * Created by dietn on 15.09.14.
 */
public class MuseumDAO {
    // Database fields
    private SQLiteDatabase database;
    private final MuseumDbHelper dbHelper;
    private static final String[] allColumns = {
            MuseumContract.MuseumEntry.COLUMN_NAME_FLOOR,
            MuseumContract.MuseumEntry.COLUMN_NAME_IMAGES,

            MuseumContract.MuseumEntry.COLUMN_NAME_NL_NAME,
            MuseumContract.MuseumEntry.COLUMN_NAME_NL_DESC,

            MuseumContract.MuseumEntry.COLUMN_NAME_EN_NAME,
            MuseumContract.MuseumEntry.COLUMN_NAME_EN_DESC,

            MuseumContract.MuseumEntry.COLUMN_NAME_FR_NAME,
            MuseumContract.MuseumEntry.COLUMN_NAME_FR_DESC,

            MuseumContract.MuseumEntry.COLUMN_NAME_IT_NAME,
            MuseumContract.MuseumEntry.COLUMN_NAME_IT_DESC,

            MuseumContract.MuseumEntry.COLUMN_NAME_ES_NAME,
            MuseumContract.MuseumEntry.COLUMN_NAME_ES_DESC,

            MuseumContract.MuseumEntry.COLUMN_NAME_RU_NAME,
            MuseumContract.MuseumEntry.COLUMN_NAME_RU_DESC,

            MuseumContract.MuseumEntry.COLUMN_NAME_DE_NAME,
            MuseumContract.MuseumEntry.COLUMN_NAME_DE_DESC,
    };

    public MuseumDAO(Context context) {
        dbHelper = new MuseumDbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Floor saveFloor(Floor floor) {
        final ContentValues values = new ContentValues();
        values.put(MuseumContract.MuseumEntry.COLUMN_NAME_FLOOR,floor.floor);
        values.put(MuseumContract.MuseumEntry.COLUMN_NAME_IMAGES,getImagesAsCSV(floor.images));

        values.put(MuseumContract.MuseumEntry.COLUMN_NAME_NL_NAME,floor.NL_name);
        values.put(MuseumContract.MuseumEntry.COLUMN_NAME_NL_DESC,floor.NL_desc);

        values.put(MuseumContract.MuseumEntry.COLUMN_NAME_EN_NAME,floor.EN_name);
        values.put(MuseumContract.MuseumEntry.COLUMN_NAME_EN_DESC,floor.EN_desc);

        values.put(MuseumContract.MuseumEntry.COLUMN_NAME_FR_NAME,floor.FR_name);
        values.put(MuseumContract.MuseumEntry.COLUMN_NAME_FR_DESC,floor.FR_desc);

        values.put(MuseumContract.MuseumEntry.COLUMN_NAME_IT_NAME,floor.IT_name);
        values.put(MuseumContract.MuseumEntry.COLUMN_NAME_IT_DESC,floor.IT_desc);

        values.put(MuseumContract.MuseumEntry.COLUMN_NAME_ES_NAME,floor.ES_name);
        values.put(MuseumContract.MuseumEntry.COLUMN_NAME_ES_DESC,floor.ES_desc);

        values.put(MuseumContract.MuseumEntry.COLUMN_NAME_RU_NAME,floor.RU_name);
        values.put(MuseumContract.MuseumEntry.COLUMN_NAME_RU_DESC,floor.RU_desc);

        values.put(MuseumContract.MuseumEntry.COLUMN_NAME_DE_NAME,floor.DE_name);
        values.put(MuseumContract.MuseumEntry.COLUMN_NAME_DE_DESC,floor.DE_desc);

        database.insert(MuseumContract.MuseumEntry.TABLE_NAME, null,
                values);

        return floor;
    }

    private String getImagesAsCSV(String[] images){
        if(images.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < images.length; i++) {
                sb.append(images[i]);
                sb.append(",");
            }
            String toReturn = sb.toString();
            toReturn = toReturn.substring(0, toReturn.length() - 1);
            return toReturn;
        }else{
            return "";
        }
    }

    public void clearTable(){
        database.execSQL(MuseumDbHelper.SQL_DELETE_ENTRIES);
        database.execSQL(MuseumDbHelper.SQL_CREATE_ENTRIES);
    }

    public List<Floor> getAllFloors() {
        final List<Floor> floors = new ArrayList<Floor>();

        final Cursor cursor = database.query(MuseumContract.MuseumEntry.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final Floor floor = cursorToFloor(cursor);
            floors.add(floor);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return floors;
    }

    private Floor cursorToFloor(Cursor cursor) {
        if(cursor == null ) return null;
        Floor floor = new Floor();
        floor.floor = cursor.getInt(cursor.getColumnIndex(MuseumContract.MuseumEntry.COLUMN_NAME_FLOOR));
        floor.images = cursor.getString(cursor.getColumnIndex(MuseumContract.MuseumEntry.COLUMN_NAME_IMAGES)).split(",");

        floor.NL_name = cursor.getString(cursor.getColumnIndex(MuseumContract.MuseumEntry.COLUMN_NAME_NL_NAME));
        floor.NL_desc = cursor.getString(cursor.getColumnIndex(MuseumContract.MuseumEntry.COLUMN_NAME_NL_DESC));

        floor.EN_name = cursor.getString(cursor.getColumnIndex(MuseumContract.MuseumEntry.COLUMN_NAME_EN_NAME));
        floor.EN_desc = cursor.getString(cursor.getColumnIndex(MuseumContract.MuseumEntry.COLUMN_NAME_EN_DESC));

        floor.FR_name = cursor.getString(cursor.getColumnIndex(MuseumContract.MuseumEntry.COLUMN_NAME_FR_NAME));
        floor.FR_desc = cursor.getString(cursor.getColumnIndex(MuseumContract.MuseumEntry.COLUMN_NAME_FR_DESC));

        floor.IT_name = cursor.getString(cursor.getColumnIndex(MuseumContract.MuseumEntry.COLUMN_NAME_IT_NAME));
        floor.IT_desc = cursor.getString(cursor.getColumnIndex(MuseumContract.MuseumEntry.COLUMN_NAME_IT_DESC));

        floor.ES_name = cursor.getString(cursor.getColumnIndex(MuseumContract.MuseumEntry.COLUMN_NAME_ES_NAME));
        floor.ES_desc = cursor.getString(cursor.getColumnIndex(MuseumContract.MuseumEntry.COLUMN_NAME_ES_DESC));

        floor.RU_name = cursor.getString(cursor.getColumnIndex(MuseumContract.MuseumEntry.COLUMN_NAME_RU_NAME));
        floor.RU_desc = cursor.getString(cursor.getColumnIndex(MuseumContract.MuseumEntry.COLUMN_NAME_RU_DESC));

        floor.DE_name = cursor.getString(cursor.getColumnIndex(MuseumContract.MuseumEntry.COLUMN_NAME_DE_NAME));
        floor.DE_desc = cursor.getString(cursor.getColumnIndex(MuseumContract.MuseumEntry.COLUMN_NAME_DE_DESC));
        return floor;
    }
}
