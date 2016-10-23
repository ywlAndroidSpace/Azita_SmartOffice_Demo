package com.azita.iot.ioclient.helper;

import android.content.Context;
import android.util.Log;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.util.UUID;
import com.azita.iot.ioclient.Constants;
import com.azita.iot.ioclient.R;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.SocketFactory;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import static android.R.attr.x;
import static com.azita.iot.ioclient.Constants.MQTT_PORT;

/**
 * Created by Lewis.Ling on 04/28/16 AD.
 */
@EBean(scope = EBean.Scope.Singleton)
public class MQTTHelper implements MqttCallback {

    final private MqttConnectOptions mConnOpts;

    private final MqttDefaultFilePersistence dataStore;
    public static final String TAG = "AzitaMQTT";
    @RootContext
    Context context;

    private MqttClient mClient;
    private SSLContext sslContext;
    private String clientId;
    private String password;
    private String username;
    private SocketFactory socketFactory;
    private int port;
    private String host;
    private int mQos = 0;
    private String mTopic;


    private CertificateFactory certificateFactory;
    private InputStream caInput;
    private X509Certificate untrustedCACertificate;
    private String keyStoreType;
    private KeyStore keyStore;
    private String trustManagerFactoryAlgorithm;
    private TrustManagerFactory trustManagerFactory;


    public MQTTHelper() {
        String tmpDir = System.getProperty("java.io.tmpdir");
        dataStore = new MqttDefaultFilePersistence(tmpDir);
        mConnOpts = new MqttConnectOptions();
        mConnOpts.setCleanSession(true);
        mConnOpts.setKeepAliveInterval(10);
        clientId = Constants.MQTT_CLIENT_ID;
    }

    @Override
    public void connectionLost(Throwable throwable) {
        BusProvider.getInstance().postQueue(new MqttEvent(MqttEvent.MQTT_CONNECTION_LOST));
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        MqttEvent event = new MqttEvent();
        event.type = MqttEvent.MQTT_MESSAGE_ARRIVED;
        event.mqttMessage = mqttMessage;
        BusProvider.getInstance().postQueue(event);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        BusProvider.getInstance().postQueue(new MqttEvent(MqttEvent.MQTT_DELIVER_COMPLETED));
    }

    @Background
    public void subscribe(String topic, int qos) {
        try {
            mClient.subscribe(topic, qos);
            Log.d(TAG, "subscribe topic = " + topic);
            BusProvider.getInstance().postQueue(new MqttEvent(MqttEvent.MQTT_SUBSCRIBED));
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void _publish(String topic, String message, int qos, boolean retain) throws MqttException {
        BusProvider.getInstance().postQueue(new MqttEvent(MqttEvent.MQTT_PUBLISHING));
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(qos);
        mqttMessage.setRetained(retain);
        mqttMessage.setPayload(message.getBytes());
        mClient.publish(topic, mqttMessage);
    }

    @Background
    public void publish(String msg, boolean retain) {
        try {
            Log.d(TAG, "publish: " + this.mTopic + "," + msg);
            _publish(this.mTopic, msg, mQos, retain);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    @Background
    public void connect() {
        BusProvider.getInstance().postQueue(new MqttEvent(MqttEvent.MQTT_CONNECTING));
        if (mClient.isConnected()) {
            BusProvider.getInstance().postQueue(new MqttEvent(MqttEvent.MQTT_CONNECTED));
        } else {
            try {
                Log.d(TAG, "connecting..");
                mClient.connect(mConnOpts);
                Log.d(TAG, "connected.!");
                BusProvider.getInstance().postQueue(new MqttEvent(MqttEvent.MQTT_CONNECTED));
            } catch (MqttException e) {
                MqttEvent event = new MqttEvent(MqttEvent.MQTT_CONNECT_FAIL);
                try {
                    event.reason = e.getCause().getMessage();
                }
                catch (Exception ex) {
                    event.reason = "unknown reason";
                }
                BusProvider.getInstance().postQueue(event);
                e.printStackTrace();
            }
        }
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
        Log.d(TAG, "setClientId: " + clientId);
        createConnection();
    }

    public void setAuth(String user, String pass, SocketFactory socketFactory) {
        if (user.isEmpty() || pass.isEmpty()) {
            return;
        }

        this.username = user;
        this.password = pass;
        this.socketFactory = socketFactory;
        Log.d(TAG, "setAuth: " + user + "/" + pass);

        mConnOpts.setUserName(this.username);
        mConnOpts.setPassword(this.password.toCharArray());
        mConnOpts.setSocketFactory(this.socketFactory);
        createConnection();
    }

    private void createConnection() {
        String host = "ssl://" + this.host + ":" + this.port;
        Log.d(TAG, "clientId " + this.clientId + " " + host);
        Log.d(TAG, "prepareConnection: " + host);
        try {
            mClient = new MqttClient(host, this.clientId, dataStore);
            mClient.setCallback(this);
        } catch (MqttException e) {
            Log.d(TAG, "createConnection Exception: " + e);
            e.printStackTrace();
        }
    }

    public void setHostPort(String host, int port) {
        this.host = host;
        this.port = port;
        Log.d(TAG, "setHostPort: " + host + "/" + port);
        createConnection();
    }

    public void setTopic(String topic) {
        Log.d(TAG, "setTopic: " + topic);
        this.mTopic = topic;
    }

    public static class MqttEvent {
        public static final int MQTT_CONNECTED = 0x00;
        public static final int MQTT_MESSAGE_ARRIVED = 0x01;
        public static final int MQTT_CONNECTION_LOST = 0x02;
        public static final int MQTT_DELIVER_COMPLETED = 0x03;
        public static final int MQTT_ERROR = 0x04;
        public static final int MQTT_CONNECT_FAIL = 0x05;
        public static final int MQTT_CONNECTING = 0x06;
        public static final int MQTT_PUBLISHING = 0x07;
        public static final int MQTT_SUBSCRIBED = 0x08;
        public int type;
        public String reason;

        public MqttMessage mqttMessage;

        public MqttEvent(int type) {
            this.type = type;
        }

        public MqttEvent() {

        }
    }
}
