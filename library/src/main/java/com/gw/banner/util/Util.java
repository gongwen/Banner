package com.gw.banner.util;

import android.util.Log;

/**
 * Created by GongWen on 17/11/9.
 */

public class Util {
    /**
     * @param count(adapter.getRealCount())
     * @param position
     * @return
     */
    public static int toRealPosition(int count, int position) {
        int realPosition = (position - 1) % count;
        if (realPosition < 0)
            realPosition += count;
        return realPosition;
    }
}
