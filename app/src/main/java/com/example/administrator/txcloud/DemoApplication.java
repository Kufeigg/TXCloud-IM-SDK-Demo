package com.example.administrator.txcloud;

import android.app.Application;

import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.config.CustomFaceConfig;
import com.tencent.qcloud.tim.uikit.config.GeneralConfig;
import com.tencent.qcloud.tim.uikit.config.TUIKitConfigs;

public class DemoApplication extends Application {

    private static DemoApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();

        // 配置 Config，请按需配置
        TUIKitConfigs configs = TUIKit.getConfigs();
        configs.setSdkConfig(new TIMSdkConfig(1400258864));
        configs.setCustomFaceConfig(new CustomFaceConfig());
        configs.setGeneralConfig(new GeneralConfig());
        instance = this;
        TUIKit.init(this, 1400258864, configs);
    }

    public static DemoApplication instance() {
        return instance;
    }
}