package logistics.turvo.com.turvologistics.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import logistics.turvo.com.turvologistics.R;


public class CustomProgressBar extends RelativeLayout {
    public CustomProgressBar(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_progress_bar, this);


    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

}
