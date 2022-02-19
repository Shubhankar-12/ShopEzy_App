package com.homofabers.shopezy.helpers;

import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.widget.ImageView;

// this class is used for adding filter to images and it's background

public class imageFilters {

    public static void  setFilter(ImageView v)
    {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        v.setColorFilter(cf);
    }

    public static void  setBackgroundFilter(ImageView v)
    {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        v.getBackground().setColorFilter(cf);
    }

    public static void  removeFilter(ImageView v)
    {
        v.setColorFilter(null);
    }

    public static void  removeBackgroundFilter(ImageView v)
    {
        v.getBackground().setColorFilter(null);
    }
}
