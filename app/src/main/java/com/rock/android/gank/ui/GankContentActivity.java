package com.rock.android.gank.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rock.android.gank.Model.GankDateData;
import com.rock.android.gank.R;
import com.rock.android.gank.network.NetWorkManager;
import com.rock.android.gank.ui.base.ToolbarActivity;

import rx.Subscriber;

public class GankContentActivity extends ToolbarActivity {

    public static final String KEY_DATE = "key_date";
    private RecyclerView gankContentRcv;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_gank_content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        gankContentRcv = (RecyclerView) findViewById(R.id.gankContentRcv);
        gankContentRcv.setLayoutManager(new LinearLayoutManager(this));
        String date = getIntent().getStringExtra(KEY_DATE);

        requestGankDateData(date);
    }


    private void requestGankDateData(String date){
        NetWorkManager.getInstance().getGankDateData(new Subscriber<GankDateData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GankDateData gankDateData) {

            }
        }, date);
    }
}
