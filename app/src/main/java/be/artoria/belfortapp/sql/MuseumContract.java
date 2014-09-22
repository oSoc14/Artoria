package be.artoria.belfortapp.sql;

import android.provider.BaseColumns;

/**
 * Created by dietn on 15.09.14.
 */
public class MuseumContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public MuseumContract() {}

    /* Inner class that defines the table contents */
    public static abstract class MuseumEntry implements BaseColumns {
        public static final String TABLE_NAME = "floor";
        public static final String COLUMN_NAME_FLOOR = "floor";
        public static final String COLUMN_NAME_IMAGE = "image";

        public static final String COLUMN_NAME_NL_NAME = "NL_name";
        public static final String COLUMN_NAME_NL_DESC = "NL_desc";

        public static final String COLUMN_NAME_EN_NAME = "EN_name";
        public static final String COLUMN_NAME_EN_DESC = "EN_desc";

        public static final String COLUMN_NAME_FR_NAME = "FR_name";
        public static final String COLUMN_NAME_FR_DESC = "FR_desc";

        public static final String COLUMN_NAME_IT_NAME = "IT_name";
        public static final String COLUMN_NAME_IT_DESC = "IT_desc";

        public static final String COLUMN_NAME_ES_NAME = "ES_name";
        public static final String COLUMN_NAME_ES_DESC = "ES_desc";

        public static final String COLUMN_NAME_RU_NAME = "RU_name";
        public static final String COLUMN_NAME_RU_DESC = "RU_desc";

        public static final String COLUMN_NAME_DE_NAME = "DE_name";
        public static final String COLUMN_NAME_DE_DESC = "DE_desc";

    }
}
