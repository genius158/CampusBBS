package com.yan.campusbbs.base;

import com.yan.campusbbs.config.SharedPreferenceConfig;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.ChangeSkin;
import com.yan.campusbbs.util.SPUtils;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/2/8.
 */

public abstract class BaseSkinFragment extends BaseFragment implements ChangeSkin  {

    protected void skinInit() {
        changeSkin(new ActionChangeSkin(
                SPUtils.getInt(getContext()
                        , MODE_PRIVATE, SharedPreferenceConfig.SHARED_PREFERENCE
                        , SharedPreferenceConfig.SKIN_INDEX, 0)
        ));
    }
}
