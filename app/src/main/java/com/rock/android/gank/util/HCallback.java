package com.rock.android.gank.util;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

public class HCallback implements android.os.Handler.Callback {

    public static final int LAUNCH_ACTIVITY = 100;
    Handler mHandler;
    public HCallback(Handler handler) {
        mHandler = handler;
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == LAUNCH_ACTIVITY) {
            Object r = msg.obj;
            try {
                //得到消息中的Intent(启动SubActivity的Intent)
                Intent intent = (Intent) FieldUtil.getField(r.getClass(), r, "intent");
                //得到此前保存起来的Intent(启动TargetActivity的Intent)
                Intent target = intent.getParcelableExtra(HookUtil.PROXY_PARAM);
                //将启动SubActivity的Intent替换为启动TargetActivity的Intent
                intent.setComponent(target.getComponent());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mHandler.handleMessage(msg);
        return true;
    }
}
