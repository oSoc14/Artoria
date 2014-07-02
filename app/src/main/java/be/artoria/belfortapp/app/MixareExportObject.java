package be.artoria.belfortapp.app;

import java.util.List;

/**
 * Created by Laurens on 01/07/2014.
 * Used to export json in the Mixare format using the gson library
 */
public class MixareExportObject {

    public List<POI> pois;
    public String status = "OK";
    public String num_results;
    public String results;

    public class POI {
        public String id;
        public String lat;
        public String lng;
        public String elevation = "0";
        public String title;
        public String distance;
        public String has_detail_page = "1";
        public String webpage;

        /* Extra information not needed by mixare but used by use */
        public String imageUrl;
        public String description;

    }

}
