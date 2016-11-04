package com.azita.iot.ioclient.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.azita.iot.ioclient.Constants;
import com.azita.iot.ioclient.R;
import com.azita.iot.ioclient.helper.AppHelper;
import com.azita.iot.ioclient.helper.BusProvider;
import com.azita.iot.ioclient.helper.MQTTHelper;
import com.azita.iot.ioclient.helper.MQTTHelper_;
import com.azita.iot.ioclient.helper.MQTTOptions;
import com.azita.iot.ioclient.helper.MQTTOptions_;
import com.azita.iot.ioclient.helper.TSelfSignedSSLSocketFactory;
import com.daimajia.easing.BaseEasingMethod;
import com.daimajia.easing.Glider;
import com.daimajia.easing.Skill;
import com.nineoldandroids.animation.AnimatorSet;
import com.squareup.otto.Subscribe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 *
 * The Wetware Innovations Software License, Version 2.2
 *
 * Copyright (c) 2010-2016 The Wetware Innovations. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 1. The Project (Smart Office) related development activities, deployed by PWR, with partnership Cloudity Limited.
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
 *
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = "AzitaMQTT";
    private LinearLayout all_layout;

    public int powerbar_powerbar_btn_flag       = 0;
    public int powerbar_magcharging_btn_flag    = 0;
    public int powerbar_conference_btn_flag     = 0;
    public int powerbar_display_btn_flag        = 0;
    public int Lightingbar_1_btn_flag = 0;
    public int Lightingbar_2_btn_flag = 0;
    public int Lightingbar_3_btn_flag = 0;
    public int Lightingbar_4_btn_flag = 0;
    public int Lightingdimmer_1_btn_flag = 0;
    public int Lightingdimmer_2_btn_flag = 0;
    public int Lightingdimmer_3_btn_flag = 0;
    public int Lightingdimmer_4_btn_flag = 0;
    public int _powerbar_powerbar_btn = 0;
    public int _powerbar_magcharging_btn = 0;
    public int _powerbar_conference_btn = 0;
    public int _powerbar_display_btn = 0;
    public int _Lightingbar_1_btn = 0;
    public int _Lightingbar_2_btn = 0;
    public int _Lightingbar_3_btn = 0;
    public int _Lightingbar_4_btn = 0;
    public int _Lightingdimmer_1_dummy_value = 0;
    public int _Lightingdimmer_2_dummy_value = 0;
    public int _Lightingdimmer_3_dummy_value = 0;
    public int _Lightingdimmer_4_dummy_value = 0;
    public int _Lightingdimmer_1_value = 0;
    public int _Lightingdimmer_2_value = 0;
    public int _Lightingdimmer_3_value = 0;
    public int _Lightingdimmer_4_value = 0;

    SeekBar volumeControl;
    SeekBar snapBarControl;

    @Bind({R.id.button4, R.id.button5, R.id.button6})
    List<ToggleButton> nameViews;

    @Bind(R.id.button7)
    ToggleButton button7;
    @Bind(R.id.button8)
    ToggleButton button8;
    @Bind(R.id.button9)
    ToggleButton button9;
    @Bind(R.id.button1)
    ImageButton PowerBar;

    @Bind(R.id.button2)
    ImageButton LightingBar;
    @Bind(R.id.button3)
    ImageButton LightingDimmer;

    @Bind(R.id.iv_temp)
    ImageView ivTemp;
    @Bind(R.id.iv_pm)
    ImageView ivPm;
    @Bind(R.id.iv_motion)
    ImageView ivMotion;
    @Bind(R.id.iv_cabinet)
    ImageView ivCabinet;
    @Bind(R.id.iv_door)
    ImageView ivDoor;
    @Bind(R.id.iv_window)
    ImageView ivWindow;

    @Bind(R.id.llTemp)
    LinearLayout llTemp;
    @Bind(R.id.llPm)
    LinearLayout llPm;
    @Bind(R.id.llMotion)
    LinearLayout llMotion;
    @Bind(R.id.llCabinet)
    LinearLayout llCabinet;
    @Bind(R.id.llDoor)
    LinearLayout llDoor;
    @Bind(R.id.ll_Window)
    LinearLayout llWindow;


    @Bind(R.id.button10)
    ToggleButton masterButton;
    SparseIntArray bitMask = new SparseIntArray();


    private TSelfSignedSSLSocketFactory selfSignedSSLSocketFactory;
    private int mCurrentState = 0b000000000;
    private Context mContext;
    private int dimmer_value = 0;
    final private EventHandler mEventHandler
            = new EventHandler();

    ToggleButton dimmerButton, temphumidButton, aqiButton, motionButton;

    private static final int SNAP_MIN = 0;
    private static final int SNAP_MIDDLE = 50;
    private static final int SNAP_MAX = 100;

    private static final int LOWER_HALF = (SNAP_MIN + SNAP_MIDDLE) / 2;
    private static final int UPPER_HALF = (SNAP_MIDDLE + SNAP_MAX) / 2;

    public boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llTemp:
                if (nameViews.get(0).isChecked()) {
                    nameViews.get(0).setChecked(false);
                }else{
                    nameViews.get(0).setChecked(true);
                }
                break;
            case R.id.llPm:
                if (nameViews.get(1).isChecked()) {
                    nameViews.get(1).setChecked(false);
                }else{
                    nameViews.get(1).setChecked(true);
                }
                break;
            case R.id.llMotion:
                if (nameViews.get(2).isChecked()) {
                    nameViews.get(2).setChecked(false);
                }else{
                    nameViews.get(2).setChecked(true);
                }
                break;
            case R.id.llCabinet:
                if (button7.isChecked()) {
                    button7.setChecked(false);
                }else{
                    button7.setChecked(true);
                }
                break;
            case R.id.llDoor:
                if (button8.isChecked()) {
                    button8.setChecked(false);
                }else{
                    button8.setChecked(true);
                }
                break;
            case R.id.ll_Window:
                if (button9.isChecked()) {
                    button9.setChecked(false);
                }else{
                    button9.setChecked(true);
                }
                break;
        }
    }

    public class EventHandler {
        @Subscribe
        public void onBusMessage(MQTTHelper.MqttEvent event) {

            switch (event.type) {
                case MQTTHelper.MqttEvent.MQTT_CONNECTING:
                    break;
                case MQTTHelper.MqttEvent.MQTT_CONNECTION_LOST:
                    MQTTHelper_.getInstance_(mContext).connect();
                    break;
                case MQTTHelper.MqttEvent.MQTT_CONNECTED:
                    String topic = MQTTOptions_.getInstance_(mContext).topic;
                    MQTTHelper_.getInstance_(mContext).subscribe(topic, 0);
                    break;
                case MQTTHelper.MqttEvent.MQTT_SUBSCRIBED:
                    break;
                case MQTTHelper.MqttEvent.MQTT_MESSAGE_ARRIVED:
                    String msg = event.mqttMessage.toString();
                    if (isJSONValid(msg)) {
                        try {
                            JSONObject payload = new JSONObject(msg);
                            int customerID = Integer.parseInt(payload.getString("customerID"));
                            int classID = Integer.parseInt(payload.getString("classID"));
                            int typeID = Integer.parseInt(payload.getString("typeID"));
                            int DeviceID = Integer.parseInt(payload.getString("DeviceID"));
                            if (customerID == 2110) {
                                switch (classID) {
                                    case 1031: // Temperature / Humidity
                                        if (typeID == 1001 && DeviceID == 1001) {
                                            String temperature = payload.getString("t");
                                            String humidity = payload.getString("h");
                                            float humidity_float = Float.parseFloat(humidity);
                                            int humidity_int = (int)humidity_float;
                                            humidity = String.valueOf(humidity_int);
                                            mCurrentState = (0b000000001) | mCurrentState;
                                            String dummy = "Temp: " + temperature + "C Humid:" + humidity + "%";
                                            temphumidButton.setTextOn(dummy);
                                            updateUI(mCurrentState);
                                        }
                                        break;
                                    case 1033: // Multi All-in-one Sensor
                                        if (typeID == 1001 && DeviceID == 1001) {
                                            String pm25 = payload.getString("p");
                                            String voc = payload.getString("v");
                                            mCurrentState = (0b000000010) | mCurrentState;
                                            String dummy = "PM2.5/VOC        " + pm25 + " / " + voc + " ";
                                            aqiButton.setTextOn(dummy);
                                            motionButton = (ToggleButton) findViewById(R.id.button6);
                                            updateUI(mCurrentState);
                                        }
                                        break;
                                    case 1039: // Motion Detector Sensor
                                        if (typeID == 1001 && DeviceID == 1001) {
                                            int value = Integer.parseInt(payload.getString("value"));
                                            if (value==1) {
                                                mCurrentState = (0b000000100) | mCurrentState;
                                            } else {
                                                mCurrentState = (0b111111011) & mCurrentState;
                                            }
                                            updateUI(mCurrentState);
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "unexpected JSON exception from MQTT rx ", e);
                        }
                    }
                    break;
                case MQTTHelper.MqttEvent.MQTT_DELIVER_COMPLETED:
                    char currentText = (char) ((0b0 << 9) | mCurrentState);
                    break;
                case MQTTHelper.MqttEvent.MQTT_CONNECT_FAIL:
                    break;
                case MQTTHelper.MqttEvent.MQTT_ERROR:
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        all_layout = (LinearLayout) findViewById(R.id.device_control_fragment);
        Intent intent = new Intent(mContext, ConfigurationActivity.class);
        startActivity(intent);
        ButterKnife.bind(this);

        temphumidButton = (ToggleButton) findViewById(R.id.button4);
        aqiButton = (ToggleButton) findViewById(R.id.button5);
        motionButton = (ToggleButton) findViewById(R.id.button6);

//        PowerBar.setBackgroundResource(R.drawable.powerbar_on_);
//        LightingBar.setBackgroundResource(R.drawable.lighting_on_);
//        LightingDimmer.setBackgroundResource(R.drawable.dimmer_6_);

        llTemp.setOnClickListener(this);
        llPm.setOnClickListener(this);
        llMotion.setOnClickListener(this);
        llCabinet.setOnClickListener(this);
        llDoor.setOnClickListener(this);
        llWindow.setOnClickListener(this);

        bitMask.put(R.id.button4, 1 << 3);
        bitMask.put(R.id.button5, 1 << 4);
        bitMask.put(R.id.button6, 1 << 5);
        bitMask.put(R.id.button7, 1 << 6);
        bitMask.put(R.id.button8, 1 << 7);
        bitMask.put(R.id.button9, 1 << 8);

        try {
            this.selfSignedSSLSocketFactory = new TSelfSignedSSLSocketFactory(this.getResources());
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        BusProvider.getInstance().register(mEventHandler);
    }

    private void setVolumeControlListener() {
        volumeControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progressChanged = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MainActivity.this, "seek bar progress: " + progressChanged, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void setSnapBarControl() {
        snapBarControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {

                final int duration = 750;
                int progress = seekBar.getProgress();
                if (progress >= SNAP_MIN && progress <= LOWER_HALF)
                    setProgressAnimated(seekBar, progress, SNAP_MIN, Skill.ElasticEaseOut, duration);
                if (progress > LOWER_HALF && progress <= UPPER_HALF)
                    setProgressAnimated(seekBar, progress, SNAP_MIDDLE, Skill.ElasticEaseOut, duration);
                if (progress > UPPER_HALF && progress <= SNAP_MAX) {
                    setProgressAnimated(seekBar, progress, SNAP_MAX, Skill.ElasticEaseOut, duration);
                }
            }
        });
    }

    private static void setProgressAnimated(final SeekBar seekBar, int from, int to, Skill skill, final int duration) {
        final AnimatorSet set = new AnimatorSet();
        set.playTogether(Glider.glide(skill, duration, com.nineoldandroids.animation.ValueAnimator.ofInt().ofFloat(from, to), new BaseEasingMethod.EasingListener() {
            @Override
            public void on(float t, float result, float v2, float v3, float v4) {
                seekBar.setProgress((int) result);
            }
        }));
        set.setDuration(duration);
        set.start();
    }

    private static void setProgressAnimatedJdk(final SeekBar seekBar, int from, int to) {
        ValueAnimator anim = ValueAnimator.ofInt(from, to);
        anim.setDuration(100);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animProgress = (Integer) animation.getAnimatedValue();
                seekBar.setProgress(animProgress);
            }
        });
        anim.start();
    }

    public void showDialog_powerbar(View view) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.share_bottom_dialog_powerbar, null);
        final ShareBottomPopupDialog shareBottomPopupDialog = new ShareBottomPopupDialog(this, dialogView);
        shareBottomPopupDialog.showPopup(all_layout);

        ImageButton powerbar_powerbar_btn = (ImageButton) dialogView.findViewById(R.id.powerbar_powerbar_btn);
        powerbar_powerbar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (powerbar_powerbar_btn_flag==1) {
                    Toast.makeText(MainActivity.this, "Power Bar Off", Toast.LENGTH_SHORT).show();
                    if (powerbar_powerbar_btn_flag==1 && powerbar_magcharging_btn_flag==0 && powerbar_conference_btn_flag==0 && powerbar_display_btn_flag==0) {

                    }
                    powerbar_powerbar_btn_flag = 0;
                } else {
                    Toast.makeText(MainActivity.this, "Power Bar On", Toast.LENGTH_SHORT).show();
                    powerbar_powerbar_btn_flag = 1;
                }
                _powerbar_powerbar_btn = 1;
                String dummy = "{\"customerID\":2110, \"classID\":1022, \"typeID\":1001, \"DeviceID\":1001, \"value\":\"0000" + powerbar_display_btn_flag + powerbar_conference_btn_flag + powerbar_magcharging_btn_flag + powerbar_powerbar_btn_flag + "B\"}";
                MQTTHelper_.getInstance_(mContext).publish(dummy, true);
                shareBottomPopupDialog.dismiss();
            }
        });
        if (_powerbar_powerbar_btn == 1) {
            if (powerbar_powerbar_btn_flag==1) {
                powerbar_powerbar_btn.setImageResource(R.mipmap.opt_powerbar_1);
            } else {
                powerbar_powerbar_btn.setImageResource(R.mipmap.opt_powerbar_0);
            }
        }
        ImageButton powerbar_magcharging_btn = (ImageButton) dialogView.findViewById(R.id.powerbar_magcharging_btn);
        powerbar_magcharging_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (powerbar_magcharging_btn_flag==1) {
                    Toast.makeText(MainActivity.this, "Magnetic Charger Disabled", Toast.LENGTH_SHORT).show();
                    if (powerbar_powerbar_btn_flag==0 && powerbar_magcharging_btn_flag==1 && powerbar_conference_btn_flag==0 && powerbar_display_btn_flag==0) {

                    }
                    powerbar_magcharging_btn_flag = 0;
                } else {
                    Toast.makeText(MainActivity.this, "Magnetic Charger Enabled", Toast.LENGTH_SHORT).show();
                    powerbar_magcharging_btn_flag = 1;
                }
                _powerbar_magcharging_btn = 1;
                String dummy = "{\"customerID\":2110, \"classID\":1022, \"typeID\":1001, \"DeviceID\":1001, \"value\":\"0000" + powerbar_display_btn_flag + powerbar_conference_btn_flag + powerbar_magcharging_btn_flag + powerbar_powerbar_btn_flag + "B\"}";
                MQTTHelper_.getInstance_(mContext).publish(dummy, true);
                shareBottomPopupDialog.dismiss();
            }
        });
        if (_powerbar_magcharging_btn == 1) {
            if (powerbar_magcharging_btn_flag==1) {
                powerbar_magcharging_btn.setImageResource(R.mipmap.opt_magcharging_1);
            } else {
                powerbar_magcharging_btn.setImageResource(R.mipmap.opt_magcharging_0);
            }
        }
        ImageButton powerbar_conference_btn = (ImageButton) dialogView.findViewById(R.id.powerbar_conference_btn);
        powerbar_conference_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (powerbar_conference_btn_flag==1) {
                    Toast.makeText(MainActivity.this, "Conference System Disabled", Toast.LENGTH_SHORT).show();
                    if (powerbar_powerbar_btn_flag==0 && powerbar_magcharging_btn_flag==0 && powerbar_conference_btn_flag==1 && powerbar_display_btn_flag==0) {

                    }
                    powerbar_conference_btn_flag = 0;
                } else {
                    Toast.makeText(MainActivity.this, "Conference System Enabled", Toast.LENGTH_SHORT).show();
                    powerbar_conference_btn_flag = 1;
                }
                _powerbar_conference_btn = 1;
                String dummy = "{\"customerID\":2110, \"classID\":1022, \"typeID\":1001, \"DeviceID\":1001, \"value\":\"0000" + powerbar_display_btn_flag + powerbar_conference_btn_flag + powerbar_magcharging_btn_flag + powerbar_powerbar_btn_flag + "B\"}";
                MQTTHelper_.getInstance_(mContext).publish(dummy, true);
                shareBottomPopupDialog.dismiss();
            }
        });
        if (_powerbar_conference_btn == 1) {
            if (powerbar_conference_btn_flag==1) {
                powerbar_conference_btn.setImageResource(R.mipmap.opt_conference_1);
            } else {
                powerbar_conference_btn.setImageResource(R.mipmap.opt_conference_0);
            }
        }
        ImageButton powerbar_display_btn = (ImageButton) dialogView.findViewById(R.id.powerbar_display_btn);
        powerbar_display_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (powerbar_display_btn_flag==1) {
                    Toast.makeText(MainActivity.this, "Projector Power Off", Toast.LENGTH_SHORT).show();
                    if (powerbar_powerbar_btn_flag==0 && powerbar_magcharging_btn_flag==0 && powerbar_conference_btn_flag==0 && powerbar_display_btn_flag==1) {

                    }
                    powerbar_display_btn_flag = 0;
                } else {
                    Toast.makeText(MainActivity.this, "Projector Power On", Toast.LENGTH_SHORT).show();
                    powerbar_display_btn_flag = 1;
                }
                _powerbar_display_btn = 1;
                String dummy = "{\"customerID\":2110, \"classID\":1022, \"typeID\":1001, \"DeviceID\":1001, \"value\":\"0000" + powerbar_display_btn_flag + powerbar_conference_btn_flag + powerbar_magcharging_btn_flag + powerbar_powerbar_btn_flag + "B\"}";
                MQTTHelper_.getInstance_(mContext).publish(dummy, true);
                shareBottomPopupDialog.dismiss();
            }
        });
        if (_powerbar_display_btn == 1) {
            if (powerbar_display_btn_flag==1) {
                powerbar_display_btn.setImageResource(R.mipmap.opt_display_1);
            } else {
                powerbar_display_btn.setImageResource(R.mipmap.opt_display_0);
            }
        }
        Button share_pop_cancle_btn = (Button) dialogView.findViewById(R.id.share_pop_cancle_btn);
        share_pop_cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareBottomPopupDialog.dismiss();
            }
        });
    }

    public void showDialog_lightingbar(View view) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.share_bottom_dialog_lightingbar, null);
        final ShareBottomPopupDialog shareBottomPopupDialog = new ShareBottomPopupDialog(this, dialogView);
        shareBottomPopupDialog.showPopup(all_layout);

        ImageButton lightingbar_1_btn = (ImageButton) dialogView.findViewById(R.id.lightingbar_1_btn);
        lightingbar_1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Lightingbar_1_btn_flag==1) {
                    Toast.makeText(MainActivity.this, "Light #1 Off", Toast.LENGTH_SHORT).show();
                    if (Lightingbar_1_btn_flag ==1 && Lightingbar_2_btn_flag ==0 && Lightingbar_3_btn_flag ==0 && Lightingbar_4_btn_flag ==0) {

                    }
                    Lightingbar_1_btn_flag = 0;
                } else {
                    Toast.makeText(MainActivity.this, "Light #1 On", Toast.LENGTH_SHORT).show();
                    Lightingbar_1_btn_flag = 1;
                }
                _Lightingbar_1_btn = 1;
                String dummy = "{\"customerID\":2110, \"classID\":1011, \"typeID\":1001, \"DeviceID\":1001, \"value\":\"0000" + Integer.toString(_Lightingdimmer_4_value*Lightingbar_4_btn_flag, 16) + Integer.toString(_Lightingdimmer_3_value*Lightingbar_3_btn_flag, 16) + Integer.toString(_Lightingdimmer_2_value*Lightingbar_2_btn_flag, 16) + Integer.toString(_Lightingdimmer_1_value*Lightingbar_1_btn_flag, 16) + "B\"}";
                MQTTHelper_.getInstance_(mContext).publish(dummy, true);
                shareBottomPopupDialog.dismiss();
            }
        });
        if (_Lightingbar_1_btn == 1) {
            if (Lightingbar_1_btn_flag ==1) {
                lightingbar_1_btn.setImageResource(R.mipmap.opt_lighting_1);
            } else {
                lightingbar_1_btn.setImageResource(R.mipmap.opt_lighting_0);
            }
        }
        ImageButton lightingbar_2_btn = (ImageButton) dialogView.findViewById(R.id.lightingbar_2_btn);
        lightingbar_2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Lightingbar_2_btn_flag ==1) {
                    Toast.makeText(MainActivity.this, "Light #2 Off", Toast.LENGTH_SHORT).show();
                    if (Lightingbar_1_btn_flag ==0 && Lightingbar_2_btn_flag ==1 && Lightingbar_3_btn_flag ==0 && Lightingbar_4_btn_flag ==0) {

                    }
                    Lightingbar_2_btn_flag = 0;
                } else {
                    Toast.makeText(MainActivity.this, "Light #2 On", Toast.LENGTH_SHORT).show();
                    Lightingbar_2_btn_flag = 1;
                }
                _Lightingbar_2_btn = 1;
                String dummy = "{\"customerID\":2110, \"classID\":1011, \"typeID\":1001, \"DeviceID\":1001, \"value\":\"0000" + Integer.toString(_Lightingdimmer_4_value * Lightingbar_4_btn_flag, 16) + Integer.toString(_Lightingdimmer_3_value * Lightingbar_3_btn_flag, 16) + Integer.toString(_Lightingdimmer_2_value * Lightingbar_2_btn_flag, 16) + Integer.toString(_Lightingdimmer_1_value * Lightingbar_1_btn_flag, 16) + "B\"}";
                MQTTHelper_.getInstance_(mContext).publish(dummy, true);
                shareBottomPopupDialog.dismiss();
            }
        });
        if (_Lightingbar_2_btn == 1) {
            if (Lightingbar_2_btn_flag ==1) {
                lightingbar_2_btn.setImageResource(R.mipmap.opt_lighting_1);
            } else {
                lightingbar_2_btn.setImageResource(R.mipmap.opt_lighting_0);
            }
        }
        ImageButton lightingbar_3_btn = (ImageButton) dialogView.findViewById(R.id.lightingbar_3_btn);
        lightingbar_3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Lightingbar_3_btn_flag ==1) {
                    Toast.makeText(MainActivity.this, "Light #3 Off", Toast.LENGTH_SHORT).show();
                    if (Lightingbar_1_btn_flag ==0 && Lightingbar_2_btn_flag ==0 && Lightingbar_3_btn_flag ==1 && Lightingbar_4_btn_flag ==0) {

                    }
                    Lightingbar_3_btn_flag = 0;
                } else {
                    Toast.makeText(MainActivity.this, "Light #3 On", Toast.LENGTH_SHORT).show();
                    Lightingbar_3_btn_flag = 1;
                }
                _Lightingbar_3_btn = 1;
                String dummy = "{\"customerID\":2110, \"classID\":1011, \"typeID\":1001, \"DeviceID\":1001, \"value\":\"0000" + Integer.toString(_Lightingdimmer_4_value*Lightingbar_4_btn_flag, 16) + Integer.toString(_Lightingdimmer_3_value*Lightingbar_3_btn_flag, 16) + Integer.toString(_Lightingdimmer_2_value*Lightingbar_2_btn_flag, 16) + Integer.toString(_Lightingdimmer_1_value*Lightingbar_1_btn_flag, 16) + "B\"}";
                MQTTHelper_.getInstance_(mContext).publish(dummy, true);
                shareBottomPopupDialog.dismiss();
            }
        });
        if (_Lightingbar_3_btn == 1) {
            if (Lightingbar_3_btn_flag ==1) {
                lightingbar_3_btn.setImageResource(R.mipmap.opt_lighting_1);
            } else {
                lightingbar_3_btn.setImageResource(R.mipmap.opt_lighting_0);
            }
        }
        ImageButton lightingbar_4_btn = (ImageButton) dialogView.findViewById(R.id.lightingbar_4_btn);
        lightingbar_4_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Lightingbar_4_btn_flag ==1) {
                    Toast.makeText(MainActivity.this, "Light #4 Off", Toast.LENGTH_SHORT).show();
                    if (Lightingbar_1_btn_flag ==0 && Lightingbar_2_btn_flag ==0 && Lightingbar_3_btn_flag ==0 && Lightingbar_4_btn_flag ==1) {

                    }
                    Lightingbar_4_btn_flag = 0;
                } else {
                    Toast.makeText(MainActivity.this, "Light #4 On", Toast.LENGTH_SHORT).show();
                    Lightingbar_4_btn_flag = 1;
                }
                _Lightingbar_4_btn = 1;
                String dummy = "{\"customerID\":2110, \"classID\":1011, \"typeID\":1001, \"DeviceID\":1001, \"value\":\"0000" + Integer.toString(_Lightingdimmer_4_value*Lightingbar_4_btn_flag, 16) + Integer.toString(_Lightingdimmer_3_value*Lightingbar_3_btn_flag, 16) + Integer.toString(_Lightingdimmer_2_value*Lightingbar_2_btn_flag, 16) + Integer.toString(_Lightingdimmer_1_value*Lightingbar_1_btn_flag, 16) + "B\"}";
                MQTTHelper_.getInstance_(mContext).publish(dummy, true);
                shareBottomPopupDialog.dismiss();
            }
        });
        if (_Lightingbar_4_btn == 1) {
            if (Lightingbar_4_btn_flag ==1) {
                lightingbar_4_btn.setImageResource(R.mipmap.opt_lighting_1);
            } else {
                lightingbar_4_btn.setImageResource(R.mipmap.opt_lighting_0);
            }
        }
        Button share_pop_cancle_btn = (Button) dialogView.findViewById(R.id.share_pop_cancle_btn);
        share_pop_cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareBottomPopupDialog.dismiss();
            }
        });
    }

    public void showDialog_lightingdimmer(View view) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.share_bottom_dialog_lightingdimmer, null);
        final ShareBottomPopupDialog shareBottomPopupDialog = new ShareBottomPopupDialog(this, dialogView);
        shareBottomPopupDialog.showPopup(all_layout);

        SeekBar light_dimmer_1_seekbar = (SeekBar) dialogView.findViewById(R.id.light_dimmer_1_seekbar);
        light_dimmer_1_seekbar.setProgress(_Lightingdimmer_1_value);
        light_dimmer_1_seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progressChanged = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                _Lightingdimmer_1_dummy_value = progressChanged;
                Toast.makeText(MainActivity.this, "Light bar #1 progress: " + progressChanged, Toast.LENGTH_SHORT)
                        .show();
                _Lightingdimmer_1_value = _Lightingdimmer_1_dummy_value;
                String dummy = "{\"customerID\":2110, \"classID\":1011, \"typeID\":1001, \"DeviceID\":1001, \"value\":\"0000" + Integer.toString(_Lightingdimmer_4_value*Lightingbar_4_btn_flag, 16) + Integer.toString(_Lightingdimmer_3_value*Lightingbar_3_btn_flag, 16) + Integer.toString(_Lightingdimmer_2_value*Lightingbar_2_btn_flag, 16) + Integer.toString(_Lightingdimmer_1_value*Lightingbar_1_btn_flag, 16) + "B\"}";
                MQTTHelper_.getInstance_(mContext).publish(dummy, true);
                shareBottomPopupDialog.dismiss();
            }
        });
        SeekBar light_dimmer_2_seekbar = (SeekBar) dialogView.findViewById(R.id.light_dimmer_2_seekbar);
        light_dimmer_2_seekbar.setProgress(_Lightingdimmer_2_value);
        light_dimmer_2_seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progressChanged = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                _Lightingdimmer_2_dummy_value = progressChanged;
                Toast.makeText(MainActivity.this, "Light bar #2 progress: " + progressChanged, Toast.LENGTH_SHORT)
                        .show();
                _Lightingdimmer_2_value = _Lightingdimmer_2_dummy_value;
                String dummy = "{\"customerID\":2110, \"classID\":1011, \"typeID\":1001, \"DeviceID\":1001, \"value\":\"0000" + Integer.toString(_Lightingdimmer_4_value*Lightingbar_4_btn_flag, 16) + Integer.toString(_Lightingdimmer_3_value*Lightingbar_3_btn_flag, 16) + Integer.toString(_Lightingdimmer_2_value*Lightingbar_2_btn_flag, 16) + Integer.toString(_Lightingdimmer_1_value*Lightingbar_1_btn_flag, 16) + "B\"}";
                MQTTHelper_.getInstance_(mContext).publish(dummy, true);
                shareBottomPopupDialog.dismiss();
            }
        });
        SeekBar light_dimmer_3_seekbar = (SeekBar) dialogView.findViewById(R.id.light_dimmer_3_seekbar);
        light_dimmer_3_seekbar.setProgress(_Lightingdimmer_3_value);
        light_dimmer_3_seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progressChanged = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                _Lightingdimmer_3_dummy_value = progressChanged;
                Toast.makeText(MainActivity.this, "Light bar #3 progress: " + progressChanged, Toast.LENGTH_SHORT)
                        .show();
                _Lightingdimmer_3_value = _Lightingdimmer_3_dummy_value;
                String dummy = "{\"customerID\":2110, \"classID\":1011, \"typeID\":1001, \"DeviceID\":1001, \"value\":\"0000" + Integer.toString(_Lightingdimmer_4_value*Lightingbar_4_btn_flag, 16) + Integer.toString(_Lightingdimmer_3_value*Lightingbar_3_btn_flag, 16) + Integer.toString(_Lightingdimmer_2_value*Lightingbar_2_btn_flag, 16) + Integer.toString(_Lightingdimmer_1_value*Lightingbar_1_btn_flag, 16) + "B\"}";
                MQTTHelper_.getInstance_(mContext).publish(dummy, true);
                shareBottomPopupDialog.dismiss();
            }
        });
        SeekBar light_dimmer_4_seekbar = (SeekBar) dialogView.findViewById(R.id.light_dimmer_4_seekbar);
        light_dimmer_4_seekbar.setProgress(_Lightingdimmer_4_value);
        light_dimmer_4_seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progressChanged = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                _Lightingdimmer_4_dummy_value = progressChanged;
                Toast.makeText(MainActivity.this, "Light bar #4 progress: " + progressChanged, Toast.LENGTH_SHORT)
                        .show();
                _Lightingdimmer_4_value = _Lightingdimmer_4_dummy_value;
                String dummy = "{\"customerID\":2110, \"classID\":1011, \"typeID\":1001, \"DeviceID\":1001, \"value\":\"0000" + Integer.toString(_Lightingdimmer_4_value*Lightingbar_4_btn_flag, 16) + Integer.toString(_Lightingdimmer_3_value*Lightingbar_3_btn_flag, 16) + Integer.toString(_Lightingdimmer_2_value*Lightingbar_2_btn_flag, 16) + Integer.toString(_Lightingdimmer_1_value*Lightingbar_1_btn_flag, 16) + "B\"}";
                MQTTHelper_.getInstance_(mContext).publish(dummy, true);
                shareBottomPopupDialog.dismiss();
            }
        });
    }

    public void ShowDimmerSeekBarDialog()
    {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this, R.style.SeekbarAlertDialogStyle); //getSupportActionBar().getThemedContext()); //this);
        final LayoutInflater inflater = (LayoutInflater)
                this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View Viewlayout = inflater.inflate(R.layout.activity_dimmer,
                (ViewGroup) findViewById(R.id.layout_Dimmerdialog));
        final TextView dimmertxtItem = (TextView)Viewlayout.findViewById(R.id.dimmertxtItem);

        SeekBar DimmerSeekBar = (SeekBar) Viewlayout.findViewById(R.id.DimmerSeekBar);

        DimmerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                dimmertxtItem.setText("Value of : " + progress);
            }
            public void onStartTrackingTouch(SeekBar arg0) {

            }
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        popDialog.setPositiveButton("OK",
        new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        popDialog.create();
        popDialog.show();
    }

    @OnCheckedChanged({R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9})
    public void checkChanged(CompoundButton view,boolean check) {
        int id = view.getId();
        char c = (char) ((0b0 << 9) | mCurrentState);
        if (check) {
            mCurrentState |= bitMask.get(id);
        } else {
            mCurrentState &= ~bitMask.get(id);
        }
        setImageResource(id, check);

        int checkFlag = 0;
        if (check) {
            checkFlag = 1;
        } else {
            checkFlag = 0;
        }
        if (true) {
            if (id == button7.getId()) {   // Locker
                String dummy = "{\"customerID\":2110, \"classID\":1037, \"typeID\":1001, \"DeviceID\":1001, \"value\":" + checkFlag + "}";
                MQTTHelper_.getInstance_(mContext).publish(dummy, false);
            } else if (id == button8.getId()) {      // Door
                String dummy = "{\"customerID\":2110, \"classID\":1036, \"typeID\":1001, \"DeviceID\":1001, \"value\":" + checkFlag + "}";
                MQTTHelper_.getInstance_(mContext).publish(dummy, false);
            } else if (id == button9.getId()) {      // Curtain
                String dummy = "{\"customerID\":2110, \"classID\":1027, \"typeID\":1001, \"DeviceID\":1001, \"value\":" + checkFlag + "}";
                MQTTHelper_.getInstance_(mContext).publish(dummy, true);
            }
        }


        char state = (char) (mCurrentState & 0b111111111);
        masterButton.setChecked(state != 0);
    }

    @OnClick({R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9/*, R.id.button10*/})
    public void buttonClicked(ToggleButton button) {
//        char c = (char) ((0b0 << 9) | mCurrentState);
//        int id = button.getId();
//        int checkFlag = 0;
//        if (button.isChecked()) {
//            checkFlag = 1;
//        } else {
//            checkFlag = 0;
//        }
//        if (true) {
//            if (id == 2131558524) {   // Locker
//                String dummy = "{\"customerID\":2110, \"classID\":1037, \"typeID\":1001, \"DeviceID\":1001, \"value\":" + checkFlag + "}";
//                MQTTHelper_.getInstance_(mContext).publish(dummy, false);
//            } else if (id == 2131558525) {      // Door
//                String dummy = "{\"customerID\":2110, \"classID\":1036, \"typeID\":1001, \"DeviceID\":1001, \"value\":" + checkFlag + "}";
//                MQTTHelper_.getInstance_(mContext).publish(dummy, false);
//            } else if (id == 2131558526) {      // Curtain
//                String dummy = "{\"customerID\":2110, \"classID\":1027, \"typeID\":1001, \"DeviceID\":1001, \"value\":" + checkFlag + "}";
//                MQTTHelper_.getInstance_(mContext).publish(dummy, true);
//            }
//        }
    }

    private void setImageResource(int id,boolean check){
        switch (id){
            case R.id.button4:
                if(check){
                    ivTemp.setImageResource(R.mipmap.temphumid_1);
                }else{
                    ivTemp.setImageResource(R.mipmap.temphumid_0);
                }
                break;
            case R.id.button5:
                if(check){
                    ivPm.setImageResource(R.mipmap.aoi_1);
                }else{
                    ivPm.setImageResource(R.mipmap.aoi_0);
                }
                break;
            case R.id.button6:
                if(check){
                    ivMotion.setImageResource(R.mipmap.motion_1);
                }else{
                    ivMotion.setImageResource(R.mipmap.motion_0);
                }
                break;
            case R.id.button7:
                if(check){
                    ivCabinet.setImageResource(R.mipmap.locker_1);
                }else{
                    ivCabinet.setImageResource(R.mipmap.locker_0);
                }
                break;
            case R.id.button8:
                if(check){
                    ivDoor.setImageResource(R.mipmap.door_1);
                }else{
                    ivDoor.setImageResource(R.mipmap.door_0);
                }
                break;
            case R.id.button9:
                if(check){
                    ivWindow.setImageResource(R.mipmap.curtain_1);
                }else{
                    ivWindow.setImageResource(R.mipmap.curtain_0);
                }
                break;
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MQTTOptions mConOpts = MQTTOptions_.getInstance_(mContext);
        MQTTHelper_.getInstance_(mContext).setHostPort(Constants.MQTT_HOST, Integer.parseInt(Constants.MQTT_PORT));
        MQTTHelper_.getInstance_(mContext).setTopic(Constants.MQTT_TOPIC);
        MQTTHelper_.getInstance_(mContext).setClientId(Constants.MQTT_CLIENT_ID);
        MQTTHelper_.getInstance_(mContext).setAuth(Constants.MQTT_USERNAME, Constants.MQTT_PASSWORD, this.selfSignedSSLSocketFactory.getSelfSignedSSLSocketFactory());
        MQTTHelper_.getInstance_(mContext).connect();
    }

    private void updateUI(int currentState) {
        char c = (char) ((0b0 << 3) | currentState);
        ButterKnife.apply(nameViews, AppHelper.UPDATE_TOGGLE_STATE, c);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(mEventHandler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_config) {
            Intent intent = new Intent(mContext, ConfigurationActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
