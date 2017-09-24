package logistics.turvo.com.turvologistics.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import logistics.turvo.com.turvologistics.R;
import logistics.turvo.com.turvologistics.model.Locations;
import logistics.turvo.com.turvologistics.utils.ImageUtils;

/**
 * Created by krishna
 */
public class MapInfoWindowsAdapter implements GoogleMap.InfoWindowAdapter {
    private View popup = null;
    private LayoutInflater inflater = null;
    private Locations locations;
    private Context mContext;

    public MapInfoWindowsAdapter(LayoutInflater inflater, Locations locations, Context context) {
        this.inflater = inflater;
        this.locations = locations;
        mContext = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return (null);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getInfoContents(final Marker marker) {
        if (popup == null) {
            popup = inflater.inflate(R.layout.place_pop_up, null);
        }

        TextView tv = (TextView) popup.findViewById(R.id.title);
        tv.setText(marker.getTitle());
        if (locations != null && !TextUtils.isEmpty(locations.image_url)) {
            final ImageView imageView = (ImageView) popup.findViewById(R.id.place_icon);
            ImageUtils.setImageThumbnail(mContext, locations.image_url, imageView);
        }
        return (popup);
    }
}
