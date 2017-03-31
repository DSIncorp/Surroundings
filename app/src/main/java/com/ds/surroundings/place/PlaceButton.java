package com.ds.surroundings.place;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class PlaceButton extends Button {

    private Place place;

    public PlaceButton(Context context, Place place) {
        super(context);
        this.place = place;
    }

    public PlaceButton(Context context) {
        super(context);
    }

    public PlaceButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Place getPlace() {
        return place;
    }
}
