package logistics.turvo.com.turvologistics.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import logistics.turvo.com.turvologistics.R;
import logistics.turvo.com.turvologistics.databinding.HomeActivityBinding;
import logistics.turvo.com.turvologistics.utils.Constants;
import logistics.turvo.com.turvologistics.utils.FragmentConstants;
import logistics.turvo.com.turvologistics.utils.FragmentUtils;
import logistics.turvo.com.turvologistics.utils.PermissionUtil;
import logistics.turvo.com.turvologistics.utils.Utility;
import logistics.turvo.com.turvologistics.view.adapters.LocationsAdapter;
import logistics.turvo.com.turvologistics.view.fragment.MapMarkerFragment;
import logistics.turvo.com.turvologistics.viewmodel.LocationsViewModel;

public class HomeActivity extends AppCompatActivity implements Observer {

    private LocationsViewModel locationsViewModel;
    private HomeActivityBinding homeActivityBinding;
    private LocationsAdapter adapter;
    RecyclerView mRecyclerView;
    private MenuItem gridItem, listItem, refreshItem;
    private FrameLayout mFrameLyt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        initData();
    }

    private void initData() {
        setSupportActionBar(homeActivityBinding.toolbar);
        setupListLocationView(homeActivityBinding.listLocations);
        setupObserver(locationsViewModel);
        checkLocationPermission();
        loadData();
    }

    /**
     *  check if location access permission is provided and ask for location access if not provided
     */
    private void checkLocationPermission() {
        PermissionUtil.checkLocationPermission(this);
    }

    private void loadData() {
        if (Utility.isNetworkAvailable(this)) {
            if (locationsViewModel != null)
                locationsViewModel.loadLocations();
        } else {
            Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
        }
    }

    private void initDataBinding() {
        homeActivityBinding = DataBindingUtil.setContentView(this, R.layout.home_activity);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_locations);
        locationsViewModel = new LocationsViewModel(this);
        homeActivityBinding.setMainViewModel(locationsViewModel);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mFrameLyt = (FrameLayout) findViewById(R.id.frame_layout);
    }

    private void setupListLocationView(RecyclerView listPeople) {
        adapter = new LocationsAdapter();
        listPeople.setAdapter(adapter);
        listPeople.setLayoutManager(new LinearLayoutManager(this));
        listPeople.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        listItem = menu.findItem(R.id.menu_list);
        gridItem = menu.findItem(R.id.menu_grid);
        refreshItem = menu.findItem(R.id.menu_refresh);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_grid) {
            setGridView();
            return true;
        } else if (item.getItemId() == R.id.menu_list) {
            setListFormatView();
            return true;
        } else if (item.getItemId() == R.id.menu_refresh) {
            loadData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setListFormatView() {
        Constants.RECYCLER_VIEW_COLUMN_COUNT = 1;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setLocationsList(locationsViewModel.getLocationsList());
        setGridIconVisibility(true);
        setListIconVisibility(false);
    }

    private void setGridView() {
        Constants.RECYCLER_VIEW_COLUMN_COUNT = 2;
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, Constants.RECYCLER_VIEW_COLUMN_COUNT));
        adapter.setLocationsList(locationsViewModel.getLocationsList());
        setGridIconVisibility(false);
        setListIconVisibility(true);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setListVisibility();
                    return true;
                case R.id.navigation_places:
                    selectedFragment = MapMarkerFragment.newInstance();
                    setMapVisibility();
                    break;

            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment, FragmentUtils.getFragmentTag(FragmentConstants.MAP_MARKER_FRAGMENT));
            transaction.commit();
            return true;
        }

    };

    private void setMapVisibility() {
        mRecyclerView.setVisibility(View.GONE);
        mFrameLyt.setVisibility(View.VISIBLE);
        setRefreshIconVisibility(false);
        setListIconVisibility(false);
        setGridIconVisibility(false);
    }

    private void setListIconVisibility(boolean isVisible) {
        if (listItem != null)
            listItem.setVisible(isVisible);
    }

    private void setGridIconVisibility(boolean isVisible) {
        if (gridItem != null)
            gridItem.setVisible(isVisible);
    }

    private void setRefreshIconVisibility(boolean isVisible) {
        if (refreshItem != null)
            refreshItem.setVisible(isVisible);
    }

    private void setListVisibility() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mFrameLyt.setVisibility(View.GONE);
        setRefreshIconVisibility(true);
        if (Constants.RECYCLER_VIEW_COLUMN_COUNT == 2) {
            setGridIconVisibility(false);
            setListIconVisibility(true);
        } else {
            setGridIconVisibility(true);
            setListIconVisibility(false);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof LocationsViewModel) {
            LocationsAdapter locationsAdapter = (LocationsAdapter) homeActivityBinding.listLocations.getAdapter();
            LocationsViewModel locationsViewModel = (LocationsViewModel) observable;
            locationsAdapter.setLocationsList(locationsViewModel.getLocationsList());
        }
    }

    public void setupObserver(Observable observable) {
        observable.addObserver(this);
    }
}
