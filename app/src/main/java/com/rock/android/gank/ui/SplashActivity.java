package com.rock.android.gank.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.rock.android.gank.R;
import com.rock.android.gank.comm.Constants;
import com.rock.android.gank.network.NetWorkManager;
import com.rock.android.gank.util.ACache;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        NetWorkManager.getInstance().getHistory(new Subscriber<List<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<String> strings) {
                ArrayList<String> history = (ArrayList<String>) strings;
                ACache.get(SplashActivity.this).put(Constants.KEY_HISTORY, history);
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
