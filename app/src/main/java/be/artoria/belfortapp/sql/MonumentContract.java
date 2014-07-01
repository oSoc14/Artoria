package be.artoria.belfortapp.sql;

import android.provider.BaseColumns;

/**
 * Created by Laurens on 01/07/2014.
 */
public final class MonumentContract {

        // To prevent someone from accidentally instantiating the contract class,
        // give it an empty constructor.
        public MonumentContract() {}

        /* Inner class that defines the table contents */
        public static abstract class MonumentEntry implements BaseColumns {
            public static final String TABLE_NAME = "monuments";
            public static final String COLUMN_NAME_ENTRY_ID = "monumentid";
            public static final String COLUMN_NAME_NAME = "name";
            public static final String COLUMN_NAME_LAT = "lat";
            public static final String COLUMN_NAME_LON = "lon";
            public static final String COLUMN_NAME_DESCRIPTION = "description";

        }
    }
