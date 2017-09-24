

package logistics.turvo.com.turvologistics.data;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationsFactory {

  private final static String BASE_URL = "https://gist.githubusercontent.com/anonymous/82fd600a118c294dccb988d65fd1fe34/raw/e622759d866be8c225c180d491e2a84c270b3662/";
  public final static String RANDOM_USER_URL = "https://gist.githubusercontent.com/anonymous/82fd600a118c294dccb988d65fd1fe34/raw/e622759d866be8c225c180d491e2a84c270b3662/locations.json";

  public static LocationsService create() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
    return retrofit.create(LocationsService.class);
  }
}
