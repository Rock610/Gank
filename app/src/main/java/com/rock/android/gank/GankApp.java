package com.rock.android.gank;

import android.content.Context;

import com.litesuits.orm.LiteOrm;
import com.rock.android.rocklibrary.BaseApplication;

/**
 * Created by rock on 16/3/24.
 */
public class GankApp extends BaseApplication{

    public static final boolean DEBUG = true;

    public static GankApp instance;
    public static final String DB_NAME = "rock_gank.db";
    public static LiteOrm GankDB;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

//        try {
//            HookUtil.hookAMS();
//            HookUtil.hookHandler();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        GankDB = LiteOrm.newSingleInstance(this,DB_NAME);
        GankDB.setDebugged(DEBUG);
    }
}
