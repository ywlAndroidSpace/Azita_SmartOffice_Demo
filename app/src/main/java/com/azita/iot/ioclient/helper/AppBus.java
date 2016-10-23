package com.azita.iot.ioclient.helper;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * Created by Lewis.Ling on 04/28/16 AD.
 */
public class AppBus extends Bus{
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public void postQueue(final Object obj) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                BusProvider.getInstance().post(obj);
            }
        });
    }

}