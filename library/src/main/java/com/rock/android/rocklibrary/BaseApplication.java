package com.rock.android.rocklibrary;

import android.app.Application;
import android.util.DisplayMetrics;

/**
 * Created by rock on 15/10/14.
 */
public class BaseApplication extends Application{

    public static BaseApplication instance;
    public static int mWidth;
    public static int mHeight;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        DisplayMetrics metrics = getApplicationContext().getResources()
                .getDisplayMetrics();
        mWidth = metrics.widthPixels;
        mHeight = metrics.heightPixels;
    }
}
