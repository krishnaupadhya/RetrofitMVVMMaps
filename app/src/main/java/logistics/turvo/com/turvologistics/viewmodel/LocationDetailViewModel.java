package logistics.turvo.com.turvologistics.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import logistics.turvo.com.turvologistics.model.Locations;
/**
 * Created by Krishna Upadhya
 */

public class LocationDetailViewModel extends BaseObservable{

    private Locations locations;

    public LocationDetailViewModel(Locations locations ) {
        this.locations = locations;
    }

    public String getTitle() {
        return locations.title;
    }

    public String getSnippet() {
        return locations.snippet;
    }

    public String getImage_url() {
        return locations.image_url;
    }

    @BindingAdapter("imageUrl") public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    public void setLocations(Locations locations) {
        this.locations = locations;
        notifyChange();
    }
}
