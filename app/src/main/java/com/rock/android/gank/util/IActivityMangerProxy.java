package com.rock.android.gank.util;

import android.content.Intent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class IActivityMangerProxy implements InvocationHandler {
    private Object mActivityManager;

    public IActivityMangerProxy(Object mActivityManager) {
        this.mActivityManager = mActivityManager;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if("startActivity".equals(method.getName())){
            Intent intent = null;
            int index = 0;
            for (int i = 0; i < args.length; i++) {
                if(args[i] instanceof Intent){
                    index = i;
                }
            }

            intent = (Intent) args[index];

            if(intent.getBooleanExtra(HookUtil.HOOK_ME,false)){
                Intent subIntent = new Intent();
                String packageName = "com.rock.android.gank";

                subIntent.setClassName(packageName,packageName+".ui.AboutActivity");
                subIntent.putExtra(HookUtil.PROXY_PARAM,intent);
                args[index] = subIntent;

                System.out.println("IActivityMangerProxy"+"hook success!");
            }

        }
        return method.invoke(mActivityManager,args);
    }
}
