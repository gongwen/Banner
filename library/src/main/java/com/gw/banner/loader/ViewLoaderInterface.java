package com.gw.banner.loader;

import android.content.Context;
import android.view.View;

import java.io.Serializable;

/**
 * Created by GongWen on 17/11/9.
 */

public interface ViewLoaderInterface<T, V extends View> extends Serializable {
    void fillData(Context context, T data, V view);

    V createView(Context context);
}
