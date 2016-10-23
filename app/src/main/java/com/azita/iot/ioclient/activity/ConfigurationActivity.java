package com.azita.iot.ioclient.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.azita.iot.ioclient.Constants;
import com.azita.iot.ioclient.R;
import com.azita.iot.ioclient.databinding.ActivityConfigurationBinding;
import com.azita.iot.ioclient.databinding.ContentConfigurationBinding;
import com.azita.iot.ioclient.helper.AppHelper;
import com.azita.iot.ioclient.helper.MQTTOptions;
import com.azita.iot.ioclient.helper.MQTTOptions_;
import com.azita.iot.ioclient.model.ViewModel;

import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnLongClick;

/**
 * Created by Lewis.Ling on 04/28/16 AD.
 */
public class ConfigurationActivity extends BaseActivity {
    ActivityConfigurationBinding mBinding;
    ContentConfigurationBinding cc;

    @Bind(R.id.toolbar)
    Toolbar mToolBar;

    private Context mContext;

    MQTTOptions mConnOpts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_configuration);

        mContext = this;
        ButterKnife.bind(this);
        mConnOpts = MQTTOptions_.getInstance_(mContext).reloadConfig();


        mBinding.setConfig(new ViewModel.MqttConfig(mContext));
        setSupportActionBar(mToolBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_config, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            /*if (validateForm()) {
                finish();
            } else {

            }*/
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
