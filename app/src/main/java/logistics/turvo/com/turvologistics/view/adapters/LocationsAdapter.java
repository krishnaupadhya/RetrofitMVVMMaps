package logistics.turvo.com.turvologistics.view.adapters;

/**
 * Created by Krishna Upadhya
 */
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import logistics.turvo.com.turvologistics.R;
import logistics.turvo.com.turvologistics.databinding.ItemLocationsBinding;
import logistics.turvo.com.turvologistics.model.Locations;
import logistics.turvo.com.turvologistics.viewmodel.ItemLocationsViewModel;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.LocationAdapterViewHolder> {

  private List<Locations> locationsList;

  public LocationsAdapter() {
    this.locationsList = Collections.emptyList();
  }

  @Override public LocationAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ItemLocationsBinding itemLocationsBinding =
        DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_locations,
            parent, false);
    return new LocationAdapterViewHolder(itemLocationsBinding);
  }

  @Override public void onBindViewHolder(LocationAdapterViewHolder holder, int position) {
    holder.bindLocations(locationsList.get(position));
  }

  @Override public int getItemCount() {
    return locationsList.size();
  }

  public void setLocationsList(List<Locations> locationsList) {
    this.locationsList = locationsList;
    notifyDataSetChanged();
  }

  public static class LocationAdapterViewHolder extends RecyclerView.ViewHolder {
    ItemLocationsBinding mItemLocationsBinding;

    public LocationAdapterViewHolder(ItemLocationsBinding itemLocationsBinding) {
      super(itemLocationsBinding.itemLocations);
      this.mItemLocationsBinding = itemLocationsBinding;
    }

    void bindLocations(Locations locations) {
      if (mItemLocationsBinding.getLocationsViewModel() == null) {
        mItemLocationsBinding.setLocationsViewModel(
            new ItemLocationsViewModel(locations, itemView.getContext()));
      } else {
        mItemLocationsBinding.getLocationsViewModel().setLocations(locations);
      }
    }
  }
}
