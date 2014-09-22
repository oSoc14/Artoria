package be.artoria.belfortapp.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laurens on 22/09/2014.
 */
public class Floor {
    public List<FloorExhibit> exhibits;
    public int floor;

    public Floor(int floor){
        this.floor = floor;
        exhibits = new ArrayList<FloorExhibit>(5);
    }
}
