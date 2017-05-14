package com.ds.surroundings.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;

import com.ds.surroundings.R;
import com.ds.surroundings.place.Place;
import com.ds.surroundings.place.PlaceButton;

public final class LayoutUtil {

    private LayoutUtil(){
        throw new IllegalStateException("Class is not designed for instantiation");
    }

    public static void setUpLayout(Context context, RelativeLayout layout, DisplayMetrics metrics){
        PlaceButton first = new PlaceButton(context, new Place());
        PlaceButton second = new PlaceButton(context, new Place());
        first.setBackgroundResource(R.drawable.button_shape);
        second.setBackgroundResource(R.drawable.button_shape);

        second.setX(metrics.widthPixels);
        second.setY(metrics.heightPixels);

        first.getBackground().setAlpha(0);
        second.getBackground().setAlpha(0);
        layout.addView(first, 1);
        layout.addView(second, 2);
    }
}
