package com.rock.android.rocklibrary.Utils.File;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceUtil {

    private static final String PREFS_NAME = "Preference";
    private static Context mContext;

    private SharedPreferences sharedPreference;

    private static SharedPreferenceUtil mPreferenceUtil;

    public static SharedPreferenceUtil getInstance(Context context) {
        mContext = context;
        if (mPreferenceUtil == null) {
            mPreferenceUtil = new SharedPreferenceUtil();
        }

        return mPreferenceUtil;
    }

    private SharedPreferences getPreferences() {
        if (sharedPreference == null)
            sharedPreference = mContext.getSharedPreferences(PREFS_NAME,
                    Context.MODE_PRIVATE);
        return sharedPreference;
    }

    public String get(String key, String defaultValue) {
        return getPreferences().getString(key, defaultValue);
    }

    public boolean get(String key, boolean defaultValue) {
        return getPreferences().getBoolean(key, defaultValue);
    }

    public long get(String key, long defaultValue) {
        return getPreferences().getLong(key, defaultValue);
    }

    public int get(String key, int defaultValue) {
        return getPreferences().getInt(key, defaultValue);
    }

    public void put(String key, String value) {
        Editor edit = getPreferences().edit();
        edit.putString(key, value);
        edit.apply();
    }

    public void put(String key, boolean value) {
        Editor edit = getPreferences().edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    public void remove(String key) {
        Editor edit = getPreferences().edit();
        edit.remove(key);
        edit.apply();
    }

    /**
     * @param key
     * @param value
     */
    public void put(String key, long value) {
        Editor edit = getPreferences().edit();
        edit.putLong(key, value);
        edit.apply();
    }

}
