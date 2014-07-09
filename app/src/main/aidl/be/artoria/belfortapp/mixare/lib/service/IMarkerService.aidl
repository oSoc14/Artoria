package be.artoria.belfortapp.mixare.lib.service;

import be.artoria.belfortapp.mixare.lib.render.MixVector;
import android.location.Location;
import android.graphics.Bitmap;
import be.artoria.belfortapp.mixare.lib.render.Camera;
import be.artoria.belfortapp.mixare.lib.gui.PaintScreen;
import be.artoria.belfortapp.mixare.lib.gui.Label;
import be.artoria.belfortapp.mixare.lib.gui.TextObj;
import be.artoria.belfortapp.mixare.lib.marker.draw.ParcelableProperty;
import be.artoria.belfortapp.mixare.lib.marker.draw.PrimitiveProperty;
import be.artoria.belfortapp.mixare.lib.marker.draw.DrawCommand;
import be.artoria.belfortapp.mixare.lib.marker.draw.ClickHandler;
/**
 * Android Interface Definition Language for contact between services in different threads,
 * In this case: The IMarkerService connects the be.artoria.belfortapp.mixare core with the markers of the plugins.
 */
interface IMarkerService {
    /** Request the process ID of this service. */
    int getPid();
    
    //--other marker interface methods--//
    String buildMarker(int id, String title, double latitude, double longitude, double altitude, String URL, int type, int color);
        
    void removeMarker(String markerName);
    
    String getPluginName();
    
    String getTitle(String markerName);

	String getURL(String markerName);

	double getLatitude(String markerName);

	double getLongitude(String markerName);

	double getAltitude(String markerName);

	MixVector getLocationVector(String markerName);

	void update(String markerName, in Location curGPSFix);

	void calcPaint(String markerName, in Camera viewCam, float addX, float addY);

	DrawCommand[] remoteDraw(String markerName);

	double getDistance(String markerName);

	void setDistance(String markerName, double distance);

	String getID(String markerName);

	void setID(String markerName, String iD);

	boolean isActive(String markerName);

	void setActive(String markerName, boolean active);

	int getColour(String markerName);

	int getMaxObjects(String markerName);

	ClickHandler fClick(String markerName);

	boolean isVisible(String markerName);

	void setExtrasParc(String markerName, String name, in ParcelableProperty parcelableProperty);
	
	void setExtrasPrim(String markerName, String name, in PrimitiveProperty primitiveProperty);
	
	MixVector getCMarker(String markerName);

	MixVector getSignMarker(String markerName);

	boolean getUnderline(String markerName);
	
	void setTxtLab(String markerName, in Label txtLab);
	
	Label getTxtLab(String markerName);
}