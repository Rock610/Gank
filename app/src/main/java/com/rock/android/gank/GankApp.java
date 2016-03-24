package com.rock.android.gank;

import com.rock.android.rocklibrary.BaseApplication;

/**
 * Created by rock on 16/3/24.
 */
public class GankApp extends BaseApplication{


    public static GankApp instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
