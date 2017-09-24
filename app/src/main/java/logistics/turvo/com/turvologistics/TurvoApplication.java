
package logistics.turvo.com.turvologistics;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import logistics.turvo.com.turvologistics.data.LocationsFactory;
import logistics.turvo.com.turvologistics.data.LocationsService;

public class TurvoApplication extends Application {

  private LocationsService locationsService;
  private Scheduler scheduler;

  @Override
  public void onCreate() {
    super.onCreate();
    Fabric.with(this, new Crashlytics());
  }

  private static TurvoApplication get(Context context) {
    return (TurvoApplication) context.getApplicationContext();
  }

  public static TurvoApplication create(Context context) {
    return TurvoApplication.get(context);
  }

  public LocationsService getLocationsService() {
    if (locationsService == null) {
      locationsService = LocationsFactory.create();
    }

    return locationsService;
  }


  public Scheduler subscribeScheduler() {
    if (scheduler == null) {
      scheduler = Schedulers.io();
    }

    return scheduler;
  }

  public void setLocationsService(LocationsService locationsService) {
    this.locationsService = locationsService;
  }

  public void setScheduler(Scheduler scheduler) {
    this.scheduler = scheduler;
  }
}
