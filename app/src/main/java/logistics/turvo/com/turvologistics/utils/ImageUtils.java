package logistics.turvo.com.turvologistics.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import logistics.turvo.com.turvologistics.R;

/**
 * Created by Krishna Upadhya
 */
public class ImageUtils {
    public static void setImageThumbnailWithResize(Context context, String url, ImageView myImageView) {
        Glide
                .with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.placeholder_16_9)
                .override(200, 115)
                .crossFade()
                .error(R.drawable.placeholder_16_9)
                .into(myImageView);
    }

    public static void setImageThumbnail(Context context, String url, ImageView myImageView) {
        Glide
                .with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.placeholder_16_9)
                .crossFade()
                .error(R.drawable.placeholder_16_9)
                .into(myImageView);
    }

}
