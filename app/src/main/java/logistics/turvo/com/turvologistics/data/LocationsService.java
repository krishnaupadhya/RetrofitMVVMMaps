

package logistics.turvo.com.turvologistics.data;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface LocationsService {

  @GET Observable<LocationsResponse> fetchLocations(@Url String url);

}
