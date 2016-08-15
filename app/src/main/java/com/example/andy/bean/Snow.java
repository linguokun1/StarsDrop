package com.example.andy.bean;

import android.graphics.Bitmap;

/**
 * Created by Andy on 2016/8/15.
 */
public class Snow {
    public Bitmap bitmap;
    public float x;
    public float y;
    public float speed;
    public float offset;

    public Snow(Bitmap bitmap, float x, float y, float speed, float offset) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.offset = offset;
    }
}
