package com.gw.banner.loader;

import android.content.Context;
import android.view.View;

import java.io.Serializable;

/**
 * Created by GongWen on 17/11/9.
 */

public interface ViewLoaderInterface<T, V extends View> extends Serializable {
    void fillData(Context mContext, V mView, T mData, int mPosition);

    V createView(Context mContext);
}
