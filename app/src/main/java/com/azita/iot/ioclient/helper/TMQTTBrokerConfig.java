/*
*                                  The MIT License (MIT)
*
* Copyright (c) 2014 - Manuel Domínguez Dorado <ingeniero@ManoloDominguez.com>
*
* Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
* associated documentation files (the "Software"), to deal in the Software without restriction,
* including without limitation the rights to use, copy, modify, merge, publish, distribute,
* sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
*     The above copyright notice and this permission notice shall be included in all copies or
*     substantial portions of the Software.
*
*     THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
*     BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
*     NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
*     DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
*     OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.azita.iot.ioclient.helper;

import com.azita.iot.ioclient.R;
import com.azita.iot.ioclient.Constants;

/**
 * This class stores the configuration of a remote MQTT broker that listen TLS an has a self signed
 * server certificate or uses its own CA to sign it.
 *
 * Created by Lewis.Ling on 04/28/16 AD.
 *
 */
public class TMQTTBrokerConfig {

    private static final int MQTT_BROKER_CONFIG_TLSPORT = Integer.parseInt(Constants.MQTT_PORT);
    private static final String MQTT_BROKER_CONFIG_BROKER_ADDRESS = Constants.MQTT_HOST;
    private static final String MQTT_BROKER_CONFIG_PROTOCOL = Constants.MQTT_PROTOCOL;
    private static final int MQTT_BROKER_CONFIG_CA_CERT_RESID = R.raw.azitainno_ca;

    private int tlsPort;
    private String address;
    private String protocol;
    private int brokerCACertificateFileResourceID;

    public TMQTTBrokerConfig() {
        this.tlsPort = this.MQTT_BROKER_CONFIG_TLSPORT;
        this.address = this.MQTT_BROKER_CONFIG_BROKER_ADDRESS;
        this.protocol = this.MQTT_BROKER_CONFIG_PROTOCOL;
        this.brokerCACertificateFileResourceID = this.MQTT_BROKER_CONFIG_CA_CERT_RESID;
    }

    public int getTlsPort() {
        return tlsPort;
    }

    public void setTlsPort(int tlsPort) {
        this.tlsPort = tlsPort;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getBrokerCACertificateFileResourceID() {
        return this.brokerCACertificateFileResourceID;
    }

    public void setBrokerCACertificateFileResourceID(int brokerCACertificateFileResourceID) {
        this.brokerCACertificateFileResourceID = brokerCACertificateFileResourceID;
    }

    public String getMQTTBrokerURL() {
        return "ssl://" + this.address + ":" + Integer.toString(this.tlsPort);
    }
}
