package com.example.andy.snowflake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import com.example.andy.bean.Snow;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Andy on 2016/8/15.
 */
public class SnowSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private final SurfaceHolder surfaceHolder;
    private float screenWidth;
    private float screenHeight;
    private int Size_Count = 10;
    Bitmap[] bitmap_snows = new Bitmap[5];
    ArrayList<Snow> snow_list_s = new ArrayList<>();
    ArrayList<Snow> snow_list_m = new ArrayList<>();
    ArrayList<Snow> snow_list_l = new ArrayList<>();
    ArrayList<Snow> snow_list_xl = new ArrayList<>();
    ArrayList<Snow> snow_list_xxl = new ArrayList<>();
    private static Random random = new Random();
    private boolean isRunning = true;
    private final Paint paint;


    public SnowSurfaceView(Context context) {
        super(context);
        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        //设置顶层绘制的surfaceView透明
        surfaceHolder.setFormat(PixelFormat.RGBA_8888);
        initViewSize(context);
        initSnowImage();
        addRandomSnow();
        paint = new Paint();
        Thread thread = new Thread(this);
        thread.start();
    }

    private void addRandomSnow() {
        for(int x = 0; x<Size_Count; x++){
            snow_list_s.add(new Snow(bitmap_snows[0], random.nextFloat()*screenWidth, random.nextFloat()*screenHeight, 8f, 1-random.nextFloat()*2));
            snow_list_m.add(new Snow(bitmap_snows[1], random.nextFloat()*screenWidth, random.nextFloat()*screenHeight, 6f, 1-random.nextFloat()*2));
            snow_list_l.add(new Snow(bitmap_snows[2], random.nextFloat()*screenWidth, random.nextFloat()*screenHeight, 4f, 1-random.nextFloat()*2));
            snow_list_xl.add(new Snow(bitmap_snows[3], random.nextFloat() * screenWidth, random.nextFloat() * screenHeight, 3f, 1 - random.nextFloat() * 2));
            snow_list_xxl.add(new Snow(bitmap_snows[4], random.nextFloat()*screenWidth, random.nextFloat()*screenHeight, 2f, 1-random.nextFloat()*2));


        }
    }

    private void initSnowImage() {
        bitmap_snows[0] = BitmapFactory.decodeResource(getResources(), R.drawable.personnal_centestar);
        bitmap_snows[1] = BitmapFactory.decodeResource(getResources(), R.drawable.personnal_centestar);
        bitmap_snows[2] = BitmapFactory.decodeResource(getResources(), R.drawable.personnal_centestar);
        bitmap_snows[3] = BitmapFactory.decodeResource(getResources(), R.drawable.personnal_centestar);
        bitmap_snows[4] = BitmapFactory.decodeResource(getResources(), R.drawable.personnal_centestar);
    }

    private void initViewSize(Context context) {
        screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        screenHeight = context.getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public void run() {
        isRunning =true;
        while(isRunning){
            Canvas lockCanvas = null;
            try{
                lockCanvas = surfaceHolder.lockCanvas();//获取加锁画布
                if(lockCanvas != null){
                    drawSnows(lockCanvas);
                    Snow snow = null;
                    for(int x=0; x<Size_Count; x++){
                        snow = snow_list_s.get(x);
                        dropSnow(snow);
                        snow = snow_list_m.get(x);
                        dropSnow(snow);
                        snow = snow_list_l.get(x);
                        dropSnow(snow);
                        snow = snow_list_xl.get(x);
                        dropSnow(snow);
                        snow = snow_list_xxl.get(x);
                        dropSnow(snow);
                    }
                }

                Thread.sleep(20);

            }catch (Exception e){

            }finally{
                if(lockCanvas != null){
                    surfaceHolder.unlockCanvasAndPost(lockCanvas);//解锁画布
                }
            }
        }
    }

    private void dropSnow(Snow snow) {
        if(snow.x > screenWidth || snow.x<0-snow.bitmap.getWidth() || snow.y>screenHeight){
            snow.x = random.nextFloat()*screenWidth;
            snow.y = 0;
        }
        snow.x += snow.offset;
        snow.y += snow.speed;
    }

    private void drawSnows(Canvas lockCanvas) {

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
//        RectF rectF = new RectF(0,0,screenWidth,screenHeight);
        Snow snow = null;
        //清空画布
        lockCanvas.drawColor(Color.BLACK);

        for(int x =0; x<Size_Count; x++){
            snow = snow_list_s.get(x);
            lockCanvas.drawBitmap(snow.bitmap, snow.x, snow.y, paint);
            snow = snow_list_m.get(x);
            lockCanvas.drawBitmap(snow.bitmap, snow.x, snow.y, paint);
            snow = snow_list_l.get(x);
            lockCanvas.drawBitmap(snow.bitmap, snow.x, snow.y, paint);
            snow = snow_list_xl.get(x);
            lockCanvas.drawBitmap(snow.bitmap, snow.x, snow.y, paint);
            snow = snow_list_xxl.get(x);
            lockCanvas.drawBitmap(snow.bitmap, snow.x, snow.y, paint);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            isRunning = false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
