package in.jadroid.chatroom.utils;

/**
 * Created by JayPatel on 12/13/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SpHelper {

    private final SharedPreferences mSharedPreferences;
    public final static String PREFIX = "Chat-Zone";
    private static SpHelper mInstance;

    public static synchronized SpHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SpHelper(context);
        }
        return mInstance;
    }

    public SpHelper(Context mContext) {
        mSharedPreferences = mContext.getSharedPreferences(PREFIX, Context.MODE_PRIVATE);
    }

    //Boolean
    public void saveBooleanValue(String key, boolean value) {
        Editor edit = mSharedPreferences.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public boolean readBooleanValue(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    public void deleteValue(String key) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    //Integer
    public void saveIntValue(String key, int value) {
        Editor edit = mSharedPreferences.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public int readIntValue(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    //String
    public void saveStringValue(String key, String value) {
        Editor edit = mSharedPreferences.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public String readStringValue(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    //Long
    public void saveLongValue(String key, long value) {
        Editor edit = mSharedPreferences.edit();
        edit.putLong(key, value);
        edit.commit();
    }

    public Long readLongValue(String key, long defaultValue) {
        return mSharedPreferences.getLong(key, defaultValue);
    }

    //Float
    public void saveFloatValue(String key, Float value) {
        Editor edit = mSharedPreferences.edit();
        edit.putFloat(key, value);
        edit.commit();
    }

    public Float readFloatValue(String key, Float defaultValue) {
        return mSharedPreferences.getFloat(key, defaultValue);
    }

}