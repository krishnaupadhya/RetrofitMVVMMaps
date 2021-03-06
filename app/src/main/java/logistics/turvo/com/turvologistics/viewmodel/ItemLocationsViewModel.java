package logistics.turvo.com.turvologistics.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import logistics.turvo.com.turvologistics.model.Locations;
import logistics.turvo.com.turvologistics.utils.ImageUtils;
import logistics.turvo.com.turvologistics.view.LocationDetailActivity;

/**
 * Created by Krishna Upadhya
 */
public class ItemLocationsViewModel extends BaseObservable {

    private Locations locations;
    private Context context;

    public ItemLocationsViewModel(Locations locations, Context context) {
        this.locations = locations;
        this.context = context;
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

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        // Glide.with(imageView.getContext()).load(url).into(imageView);
        ImageUtils.setImageThumbnail(imageView.getContext(), url, imageView);
    }

    public void onItemClick(View view) {
        context.startActivity(LocationDetailActivity.launchDetail(view.getContext(), locations));
    }

    public void setLocations(Locations locations) {
        this.locations = locations;
        notifyChange();
    }
}
