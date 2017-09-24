package logistics.turvo.com.turvologistics.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Krishna Upadhya
 */

public class LocationData extends BaseDataModel {
    @SerializedName("items")
    private ArrayList<Locations> locationsList;


    public ArrayList<Locations> getLocationsList() {
        return locationsList;
    }

    public void setLocationsList(ArrayList<Locations> locationsList) {
        this.locationsList = locationsList;
    }
}
