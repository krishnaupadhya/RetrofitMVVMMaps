package logistics.turvo.com.turvologistics.utils;

import android.support.v4.app.Fragment;
/**
 * Created by Krishna Upadhya
 */
import logistics.turvo.com.turvologistics.view.fragment.MapMarkerFragment;

public class FragmentUtils {
    public static String getFragmentTag(int type) {
        switch (type) {
            case FragmentConstants.MAP_MARKER_FRAGMENT:
                return MapMarkerFragment.class.getName();

            default:
                return Fragment.class.getName();
        }
    }
}
