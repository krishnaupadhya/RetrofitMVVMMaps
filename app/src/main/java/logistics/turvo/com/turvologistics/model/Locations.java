package logistics.turvo.com.turvologistics.model;

/**
 * Created by Krishna Upadhya
 */

public class Locations extends BaseDataModel{
    public String title;

    public String image_url;

    public String snippet;

    public double longitude;

    public double latitude;

    public String getTitle ()
    {
        return title;
    }

    public String getImage_url ()
    {
        return image_url;
    }

    public String getSnippet()
    {
        return snippet;
    }

    public double getLongitude ()
    {
        return longitude;
    }

    public double getLatitude ()
    {
        return latitude;
    }
}
