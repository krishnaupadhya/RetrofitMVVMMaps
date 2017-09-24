package logistics.turvo.com.turvologistics.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.TextView;

import java.util.Hashtable;

/**
 * Created by Krishna upadhya
 */
public class FontManager
{
    private static final Hashtable<String, Typeface> cache = new Hashtable<>();

    public static void setFont(TextView view, String fontName)
    {
        if(!TextUtils.isEmpty(fontName)) {
            view.setTypeface(getTypeface(view.getContext(), fontName));
            view.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2.0f,  view.getContext().getResources().getDisplayMetrics()), 1.0f);
        }
    }

    private static Typeface getTypeface(Context c, String name)
    {
        synchronized (cache)
        {
            if (!cache.containsKey(name))
            {
                Typeface typeface = Typeface.createFromAsset(c.getAssets(), "fonts/" + name);
                cache.put(name, typeface);
            }
            return cache.get(name);
        }
    }
}