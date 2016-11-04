package com.azita.iot.ioclient.helper;

import android.content.Context;

import com.azita.iot.ioclient.Constants;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.UUID;

/**
 *
 * The Wetware Innovations Software License, Version 2.2
 *
 * Copyright (c) 2010-2016 The Wetware Innovations. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * 3. The end-user documentation included with the redistribution, if any, must include the following acknowlegement: "This product includes software developed by the Wetware Innovations (http://www.wetwareinno.com/)." Alternately, this acknowlegement may appear in the software itself, if and wherever such third-party acknowlegements normally appear.
 * 4. The names "The Azita Project", "Commons", and "Wetware Innovations" must not be used to endorse or promote products derived from this software without prior written permission. For written permission, please contact cs@wetwareinno.com.
 * 5. Products derived from this software may not be called "Azita" nor may "Azita" appear in their names without prior written permission of the Wetware Innovations.
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE WETWARE INNOVATIONS OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE
 *
 * Azita IoT Demo - PWC Smart Office
 * Created by Lewis.Ling on 04/28/16 AD.
 * Modified by Lewis.Ling on 10/21/16 AD.
 */
@EBean(scope = EBean.Scope.Singleton)
public class MQTTOptions {

    public String clientId;
    public String password;
    public String username;
    public int port;
    public String host;
    public String topic;

    @RootContext
    public Context mContext;


    public MQTTOptions() {
    }

    public MQTTOptions reloadConfig() {

        clientId = AppHelper.getString(mContext, Constants.MQTT_CLIENT_ID, "777777ffff5f");
        username = AppHelper.getString(mContext, Constants.MQTT_USERNAME, "pwc03");
        password = AppHelper.getString(mContext, Constants.MQTT_PASSWORD, "PwcIsPassw0rd");
        port = Integer.parseInt(AppHelper.getString(mContext, Constants.MQTT_PORT, "6883"));
        host = AppHelper.getString(mContext, Constants.MQTT_HOST, "api.azitainno.com");
        topic = AppHelper.getString(mContext, Constants.MQTT_TOPIC, "node/2110/777777ffff40/test_in");
        return this;
    }
}