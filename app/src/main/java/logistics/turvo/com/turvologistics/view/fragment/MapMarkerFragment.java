package logistics.turvo.com.turvologistics.view.fragment;

/**
 * Created by Krishna Upadhya
 */

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import logistics.turvo.com.turvologistics.R;
import logistics.turvo.com.turvologistics.model.LocationData;
import logistics.turvo.com.turvologistics.model.Locations;
import logistics.turvo.com.turvologistics.utils.Constants;
import logistics.turvo.com.turvologistics.utils.PermissionUtil;
import logistics.turvo.com.turvologistics.utils.Utility;
import logistics.turvo.com.turvologistics.view.adapters.MapInfoWindowsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapMarkerFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap map;
    ArrayList<Locations> markersArray = new ArrayList<>();
    private SupportMapFragment mapFragment;

    public MapMarkerFragment() {
        // Required empty public constructor
    }

    public static MapMarkerFragment newInstance() {
        // Required empty public constructor
        return new MapMarkerFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_marker, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadMaps();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(Constants.INTENT_EXTRA_LOCATION_DATA)) {
            Locations location = (Locations) getArguments().getSerializable(Constants.INTENT_EXTRA_LOCATION_DATA);
            if (markersArray != null)
                markersArray = new ArrayList<>();
            markersArray.add(location);
        } else {
            LocationData data = Utility.getLocationFromAssets(getActivity().getApplicationContext());
            if (data != null)
                markersArray = data.getLocationsList();
        }
    }

    /*
    load map fragment and attach to frame layout
     */
    private void loadMaps() {
        if (getActivity() == null || isDetached()) return;
        if (markersArray != null && markersArray.size() > 0) {
            FragmentManager fm = getChildFragmentManager();
            mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
            if (mapFragment == null) {
                mapFragment = SupportMapFragment.newInstance();
                fm.beginTransaction().replace(R.id.map, mapFragment).commit();
            }
            mapFragment.getMapAsync(this);
        } else {

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (PermissionUtil.checkLocationPermission(getActivity()))
            setUpMap();
    }

    /*
    set up map markers for the locations
     */
    private void setUpMap() {
        for (int i = 0; i < markersArray.size(); i++) {
            Locations location = markersArray.get(i);
            if (location != null) {
                createMarker(location);
            } else {
                continue;
            }
        }
        if (getArguments() != null && getArguments().containsKey(Constants.INTENT_EXTRA_LOCATION_DATA))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(markersArray.get(0).getLatitude(), markersArray.get(0).getLongitude()), 14f));
        else
            map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(markersArray.get(0).getLatitude(), markersArray.get(0).getLongitude())));
    }

    /*
     create and add markers to the map
     */
    protected void createMarker(Locations location) {
        if (location == null || getActivity() == null || isDetached()) return;

        map.addMarker(new MarkerOptions()
                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                .anchor(0.5f, 0.5f)
                .title(location.getTitle())
                .snippet(location.getSnippet()));
        if (getArguments() != null && getArguments().containsKey(Constants.INTENT_EXTRA_LOCATION_DATA))
            map.setInfoWindowAdapter(new MapInfoWindowsAdapter(getActivity().getLayoutInflater(), location, getActivity()));
    }

    /*
    recenter the map for the provided location
     */
    public void focusLocation() {
        if (getActivity() == null || isDetached() || map == null) return;
        if (markersArray != null && markersArray.size() > 0)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(markersArray.get(0).getLatitude(), markersArray.get(0).getLongitude()), 14f));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PermissionUtil.MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        setUpMap();

                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }
}
