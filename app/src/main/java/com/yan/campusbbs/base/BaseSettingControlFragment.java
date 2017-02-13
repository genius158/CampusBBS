package com.yan.campusbbs.base;

import com.yan.campusbbs.config.SharedPreferenceConfig;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.module.setting.SettingControl;
import com.yan.campusbbs.util.SPUtils;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by yan on 2017/2/8.
 */

public abstract class BaseSettingControlFragment extends BaseFragment implements SettingControl {

    private void settingInit() {
        skinInit();
    }

    @Override
    public void activityCreated() {
        super.activityCreated();
        settingInit();
    }

    private void skinInit() {
        changeSkin(new ActionChangeSkin(
                sPUtils().getInt(MODE_PRIVATE
                        , SharedPreferenceConfig.SHARED_PREFERENCE
                        , SharedPreferenceConfig.SKIN_INDEX
                        , 0)
        ));
    }


    protected abstract SPUtils sPUtils();

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {

    }
}
