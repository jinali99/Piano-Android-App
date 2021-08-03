package com.uniconwebdesign.mia.util;

import android.content.Context;

public class MyUtil {
    public static boolean checkLoggedIn(Context context) {
        boolean flag;
        if (context.getSharedPreferences("MyFile", 0).getString(JsonFields.TAG_USER_ID, "").equals("")) {
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }
}
