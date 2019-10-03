package com.project.admin.mobibuy;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    private final String INTRO = "intro";
    private final String FIRSTNAME = "firstname";
    private final String BALANCE = "balance";
    private SharedPreferences app_prefs;
    private Context context;

    public PreferenceHelper(Context context) {
        app_prefs = context.getSharedPreferences("shared",
                Context.MODE_PRIVATE);
        this.context = context;
    }

    public void putIsLogin(boolean loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(INTRO, loginorout);
        edit.commit();
    }
    public boolean getIsLogin() {
        return app_prefs.getBoolean(INTRO, false);
    }

    public void putFirstName(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(FIRSTNAME, loginorout);
        edit.commit();
    }
    public String getFirstName() {
        return app_prefs.getString(FIRSTNAME, "");
    }

    public void putBalance(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(BALANCE, loginorout);
        edit.commit();
    }
    public String getBalance() {
        return app_prefs.getString(BALANCE, "");
    }

}