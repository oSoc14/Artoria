package be.artoria.belfortapp.sql;

import android.provider.BaseColumns;

/**
 * Created by Laurens on 01/07/2014.
 */
public final class POIContract {

        // To prevent someone from accidentally instantiating the contract class,
        // give it an empty constructor.
        public POIContract() {}

        /* Inner class that defines the table contents */
        public static abstract class POIEntry implements BaseColumns {
            public static final String TABLE_NAME = "monuments";
            public static final String COLUMN_NAME_ENTRY_ID = "id";
            public static final String COLUMN_NAME_NAME = "name";
            public static final String COLUMN_NAME_NAME_FR = "nameFR";
            public static final String COLUMN_NAME_NAME_EN = "nameEN";
            public static final String COLUMN_NAME_LAT = "lat";
            public static final String COLUMN_NAME_LON = "lon";
            public static final String COLUMN_NAME_HEIGHT = "height";
            public static final String COLUMN_NAME_DESCRIPTION = "description";
            public static final String COLUMN_NAME_DESCRIPTION_EN = "descriptionEN";
            public static final String COLUMN_NAME_DESCRIPTION_FR = "descriptionFR";
            public static final String COLUMN_NAME_IMAGE_URL = "image_url";
        }
    }
