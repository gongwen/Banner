package com.gw.banner.sample;

import android.widget.Toast;

/**
 * Created by GongWen on 17/12/4.
 */

public class ToastUtil {
    private ToastUtil() {
    }

    public static void toastShort(CharSequence text) {
        Toast.makeText(MainApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(CharSequence text) {
        Toast.makeText(MainApplication.getInstance(), text, Toast.LENGTH_LONG).show();
    }
}
