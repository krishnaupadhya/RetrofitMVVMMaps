
package logistics.turvo.com.turvologistics.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import logistics.turvo.com.turvologistics.model.Locations;

public class LocationsResponse {

  @SerializedName("items") private List<Locations> locationsList;

  public List<Locations> getLocationsList() {
    return locationsList;
  }

  public void setLocationsList(List<Locations> mLocationsList) {
    this.locationsList = mLocationsList;
  }
}
