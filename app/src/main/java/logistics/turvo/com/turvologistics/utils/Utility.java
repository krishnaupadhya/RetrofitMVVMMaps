package logistics.turvo.com.turvologistics.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import logistics.turvo.com.turvologistics.model.LocationData;


/**
 * Created by Krishna Upadhya
 */

public class Utility {

    public static int getScreenWidth(Context context) {
        try {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            return metrics.widthPixels;
        } catch (Exception e) {
            e.printStackTrace();
            return 800;
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager ConnectMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (ConnectMgr == null)
            return false;
        NetworkInfo NetInfo = ConnectMgr.getActiveNetworkInfo();
        if (NetInfo == null)
            return false;

        return NetInfo.isConnected();
    }

    public static LocationData getLocationFromAssets(Context context) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(Constants.LOCATION_ASSETS);
            Reader reader = new InputStreamReader(inputStream);
            Gson gson = new Gson();
            LocationData locationData = gson.fromJson(reader, LocationData.class);
            return locationData;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                    inputStream = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
