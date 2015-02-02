package be.artoria.belfortapp.app;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;


import java.io.File;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by dietn on 02/02/15.
 */
public class ImageCache {

    //used to fix #102
    public static final String FILE_PREFIX = "/Android/data/be.artoria.belfortApp/";

    public ImageCache(){
    }
    public void cachePoiImages(){
        try {
            DataManager.poidao.open();
            List<POI> pois = DataManager.poidao.getAllPOIs();
            for (int i = 0; i < pois.size(); i++) {
                //download and save image
                POI poi = pois.get(i);
                new DownloadDataTask().execute(poi.image_link);
            }
            DataManager.poidao.close();
        }catch(SQLException ex){
            System.out.println("failed to open db");
        }
    }

    private static class DownloadDataTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... imgUrl) {
            File img = new File(FILE_PREFIX);
            if(!img.exists()){
                img.mkdir();
            }
            DownloadManager mgr = (DownloadManager) PrefUtils.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUrl = Uri.parse(imgUrl[0]);
            DownloadManager.Request request = new DownloadManager.Request(downloadUrl);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false).setTitle("belfort")
                    .setDescription("downloading")
                    .setDestinationInExternalPublicDir(FILE_PREFIX,getFileNameFromUrl(imgUrl[0]));
            mgr.enqueue(request);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    public static String getFileNameFromUrl(String url){
        String regex = "http(s)?:\\/\\/";
        url = url.replaceAll(regex,"");
        String[] splitted = url.split("/");
        return splitted[splitted.length-1];
    }

    public static String getFilePathFromUrl(String url){
        return FILE_PREFIX + getFileNameFromUrl(url);
    }
}
