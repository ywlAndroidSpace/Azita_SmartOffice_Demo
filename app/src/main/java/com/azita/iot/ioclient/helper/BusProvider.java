package com.azita.iot.ioclient.helper;

/**
 * Created by Lewis.Ling on 04/28/16 AD.
 */
public class BusProvider {
    private static final AppBus BUS = new AppBus();

    public static AppBus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }
}