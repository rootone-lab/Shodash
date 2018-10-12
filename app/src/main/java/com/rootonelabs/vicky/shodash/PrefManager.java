package com.rootonelabs.vicky.shodash;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Vicky on 12-Oct-18.
 */

public class PrefManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    //shared Pref mode;
    int PRIVATE_MODE=0;

    //Shared Pref File name
    private static final String PREF_NAME = "shodash_welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PrefManager(Context _context) {
        this._context = _context;
        this.pref = _context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        this.editor = pref.edit();
    }

    public void setIsFirstTimeLaunch(boolean isFirstTimeLaunch){
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTimeLaunch);
        editor.commit();
    }
    public boolean isFirstTimeLaunch(){
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH,true);

    }
}
