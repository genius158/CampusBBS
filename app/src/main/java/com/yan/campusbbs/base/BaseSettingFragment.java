package com.yan.campusbbs.base;

import com.yan.campusbbs.config.SharedPreferenceConfig;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.rxbusaction.ActionImageControl;
import com.yan.campusbbs.util.setting.SystemSetting;
import com.yan.campusbbs.util.SPUtils;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/2/8.
 */

public abstract class BaseSettingFragment extends BaseFragment implements SystemSetting {

    private void settingInit() {
        skinInit();
        imgShowControlInit();
    }

    @Override
    public void activityCreated() {
        super.activityCreated();
        settingInit();
    }

    private void skinInit() {
        changeSkin(new ActionChangeSkin(
                SPUtils.getInt(getContext()
                        , MODE_PRIVATE
                        , SharedPreferenceConfig.SHARED_PREFERENCE
                        , SharedPreferenceConfig.SKIN_INDEX
                        , 0)
        ));
    }

    private void imgShowControlInit() {
        imageShow(new ActionImageControl(
                SPUtils.getBoolean(getContext()
                        , MODE_PRIVATE
                        , SharedPreferenceConfig.SHARED_PREFERENCE
                        , SharedPreferenceConfig.CAN_IMAGE_SHOW
                        , true)
        ));
    }

    @Override
    public void imageShow(ActionImageControl actionImageControl) {

    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {

    }
}
