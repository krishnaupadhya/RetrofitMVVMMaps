/*
 * Copyright © 2016, National Media Group Ltd. All rights reserved.
 * Written under contract by Robosoft Technologies Pvt. Ltd.
 */

/**
 * Created by Krishna Upadhya on 19/10/2016.
 */

package logistics.turvo.com.turvologistics.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import logistics.turvo.com.turvologistics.R;
import logistics.turvo.com.turvologistics.utils.Constants;
import logistics.turvo.com.turvologistics.utils.FontManager;

/**
 * Created by Krishna Upadhya
 */
public class LatoTextView extends AppCompatTextView {

    public LatoTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        FontManager.setFont(this, Constants.TYPE_LATO_REGULAR);
    }

    public LatoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttribute(context, attrs);

    }

    public LatoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttribute(context, attrs);
    }

    private void setAttribute(Context context, AttributeSet attrs) {
        int font;
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.LatoTextView);
        try {
            font = ta.getInteger(R.styleable.LatoTextView_latoFontType, 0);
        } finally {
            ta.recycle();
        }
        if (font != 0) {
            switch (font) {

                case 1:
                    FontManager.setFont(this, Constants.TYPE_LATO_REGULAR);
                    break;

                case 2:
                    FontManager.setFont(this, Constants.TYPE_LATO_LIGHT);
                    break;

                case 3:
                    FontManager.setFont(this, Constants.TYPE_LATO_SEMI_BOLD);
                    break;
            }
        }
    }
}
