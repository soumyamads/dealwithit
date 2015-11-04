package com.snyxius.apps.dealwithit.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by AMAN on 09-09-2015.
 */
public class CustomPasswordEditText extends EditText {

    public CustomPasswordEditText(Context context) {
        super(context);
        init();
    }

    public CustomPasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public CustomPasswordEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setTypeface(Typeface.DEFAULT);
    }
}
