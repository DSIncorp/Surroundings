package com.ds.surroundings.place;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

public class PlaceButton extends AppCompatButton {

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
