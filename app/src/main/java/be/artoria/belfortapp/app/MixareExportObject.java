package be.artoria.belfortapp.app;

import java.util.List;

/**
 * Created by Laurens on 01/07/2014.
 * Used to export json in the Mixare format
 */
public class MixareExportObject {

    List<POI> pois;
    private String status = "OK";
    private String num_results;
    private String results;

    public class POI {
        private String id;
        private String lat;
        private String lng;
        private String elevation = "0";
        private String title;
        private String distance;
        private String has_detail_page = "1";
        private String webpage;

    }

}
