package com.yan.campusbbs.module.campusbbs.ui.selfcenter.chat;

import com.yan.campusbbs.config.CacheConfig;
import com.yan.campusbbs.module.campusbbs.action.ActonCloseChatActivity;
import com.yan.campusbbs.utils.ACache;
import com.yan.campusbbs.utils.RegExpUtils;

/**
 * Created by yan on 2017/2/15.
 */

public class NotifyChatActivity extends ChatActivity {

    @Override
    protected void init() {
        super.init();
        rxBus.post(new ActonCloseChatActivity());
    }

    protected void initIntentData() {
        identifier = ACache.get(getBaseContext()).getAsString(CacheConfig.INTENT_CHAT_DATA);

        if (!identifier.startsWith("86-")) {
            if (RegExpUtils.isPhoneLegal(identifier)) {
                identifier = "86-" + identifier;
            }
        }
    }
}
