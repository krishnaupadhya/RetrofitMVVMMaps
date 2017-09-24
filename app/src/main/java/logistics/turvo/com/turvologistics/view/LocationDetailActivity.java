package logistics.turvo.com.turvologistics.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import logistics.turvo.com.turvologistics.R;
import logistics.turvo.com.turvologistics.databinding.LocationDetailActivityBinding;
import logistics.turvo.com.turvologistics.model.Locations;
import logistics.turvo.com.turvologistics.utils.Constants;
import logistics.turvo.com.turvologistics.utils.FragmentConstants;
import logistics.turvo.com.turvologistics.utils.FragmentUtils;
import logistics.turvo.com.turvologistics.view.fragment.MapMarkerFragment;
import logistics.turvo.com.turvologistics.viewmodel.LocationDetailViewModel;

/**
 * Created by Krishna Upadhya
 */
public class LocationDetailActivity extends AppCompatActivity {
    private LocationDetailActivityBinding locationDetailActivityBinding;
    private Locations mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationDetailActivityBinding =
                DataBindingUtil.setContentView(this, R.layout.location_detail_activity);
        initView();
    }

    private void initView() {
        setSupportActionBar(locationDetailActivityBinding.toolbar);
        displayHomeAsUpEnabled();
        getExtrasFromIntent();
        if (mLocation != null)
            initMapFragment();
    }

    public static Intent launchDetail(Context context, Locations locations) {
        Intent intent = new Intent(context, LocationDetailActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_LOCATION_DATA, locations);
        return intent;
    }

    private void displayHomeAsUpEnabled() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void getExtrasFromIntent() {
        mLocation = (Locations) getIntent().getSerializableExtra(Constants.INTENT_EXTRA_LOCATION_DATA);
        LocationDetailViewModel locationDetailViewModel = new LocationDetailViewModel(mLocation);
        locationDetailActivityBinding.setLocationDetailViewModel(locationDetailViewModel);
        setTitle(mLocation.getTitle());
    }

    private void initMapFragment() {
        Bundle args = new Bundle();
        args.putSerializable(Constants.INTENT_EXTRA_LOCATION_DATA, mLocation);
        MapMarkerFragment mapMarkerFragment = new MapMarkerFragment();
        mapMarkerFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.map_container_layout, mapMarkerFragment, FragmentUtils.getFragmentTag(FragmentConstants.MAP_MARKER_FRAGMENT)).commit();
    }

    public void reCenterLocation() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FragmentUtils.getFragmentTag(FragmentConstants.MAP_MARKER_FRAGMENT));
        if (fragment != null) {
            MapMarkerFragment mapMarkerFragment = (MapMarkerFragment) fragment;
            if (mapMarkerFragment != null) {
                mapMarkerFragment.focusLocation();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_recenter) {
            reCenterLocation();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
