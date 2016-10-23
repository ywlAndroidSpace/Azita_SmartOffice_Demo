package com.azita.iot.ioclient.model;

import android.content.Context;

import com.azita.iot.ioclient.BuildConfig;
import com.azita.iot.ioclient.helper.MQTTOptions_;

import java.util.UUID;

/**
 * Created by Lewis.Ling on 04/28/16 AD.
 */
public class ViewModel {

    public static class MqttConfig {
        public String host;
        public String port;
        public String topic;
        public String clientId;
        final public String versionCode;

        public MqttConfig(Context context) {
            this.clientId = "CMMC-" + UUID.randomUUID().toString().split("-")[0];
            this.versionCode = "versionCode: " +BuildConfig.VERSION_CODE;
            host =  MQTTOptions_.getInstance_(context).host;
            port =  String.valueOf(MQTTOptions_.getInstance_(context).port);
            topic = MQTTOptions_.getInstance_(context).topic;
            clientId = MQTTOptions_.getInstance_(context).clientId;
        }
    }
}
