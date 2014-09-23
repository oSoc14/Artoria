package be.artoria.belfortapp.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laurens on 22/09/2014.
 */
public class Floor {
    public List<FloorExhibit> exhibits = new ArrayList<FloorExhibit>(5);
    public int floor = 0;

    public Floor(){}

    public Floor(int floor){
        this.floor = floor;
    }
}
