/**
 * Created by Krishna Upadhya
 */
package logistics.turvo.com.turvologistics.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import logistics.turvo.com.turvologistics.TurvoApplication;
import logistics.turvo.com.turvologistics.R;
import logistics.turvo.com.turvologistics.data.LocationsFactory;
import logistics.turvo.com.turvologistics.data.LocationsResponse;
import logistics.turvo.com.turvologistics.data.LocationsService;
import logistics.turvo.com.turvologistics.model.Locations;

public class LocationsViewModel extends Observable {

    public ObservableInt locationsProgress;
    public ObservableInt locationsRecycler;
    public ObservableInt locationsLabel;
    public ObservableField<String> messageLabel;

    private List<Locations> locationsList;
    private Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public LocationsViewModel(@NonNull Context context) {

        this.context = context;
        this.locationsList = new ArrayList<>();
        locationsProgress = new ObservableInt(View.GONE);
        locationsRecycler = new ObservableInt(View.GONE);
        locationsLabel = new ObservableInt(View.VISIBLE);
        messageLabel = new ObservableField<>(context.getString(R.string.default_loading_location));
    }

    public void loadLocations() {
        initializeViews();
        fetchLocationsList();
    }

    //It is "public" to show an example of test
    public void initializeViews() {
        locationsLabel.set(View.GONE);
        locationsRecycler.set(View.GONE);
        locationsProgress.set(View.VISIBLE);
    }

    private void fetchLocationsList() {

        TurvoApplication turvoApplication = TurvoApplication.create(context);
        LocationsService locationsService = turvoApplication.getLocationsService();

        Disposable disposable = locationsService.fetchLocations(LocationsFactory.RANDOM_USER_URL)
                .subscribeOn(turvoApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LocationsResponse>() {
                    @Override
                    public void accept(LocationsResponse locationsResponse) throws Exception {
                        changeLocationsDataSet(locationsResponse.getLocationsList());
                        locationsProgress.set(View.GONE);
                        locationsLabel.set(View.GONE);
                        locationsRecycler.set(View.VISIBLE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        messageLabel.set(context.getString(R.string.error_loading_locations));
                        locationsProgress.set(View.GONE);
                        locationsLabel.set(View.VISIBLE);
                        locationsRecycler.set(View.GONE);
                    }
                });

        compositeDisposable.add(disposable);
    }

    private void changeLocationsDataSet(List<Locations> locations) {
        if (locationsList == null)
            locationsList = new ArrayList<>();
        else
            locationsList.clear();
        locationsList.addAll(locations);
        setChanged();
        notifyObservers();
    }

    public List<Locations> getLocationsList() {
        return locationsList;
    }

    private void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        compositeDisposable = null;
        context = null;
    }
}
