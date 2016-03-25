package com.rock.android.gank.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rock.android.gank.Model.GankDateData;
import com.rock.android.gank.Model.Module;
import com.rock.android.gank.R;
import com.rock.android.gank.network.NetWorkManager;
import com.rock.android.gank.ui.adapter.GankContentAdapter;
import com.rock.android.gank.ui.base.ToolbarActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

public class GankContentActivity extends ToolbarActivity {

    public static final String KEY_DATE = "key_date";
    private RecyclerView gankContentRcv;
    private GankContentAdapter mAdapter;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_gank_content;
    }

    @Override
    public boolean canBack() {
        return true;
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
        mAdapter = new GankContentAdapter(this);

        gankContentRcv.setAdapter(mAdapter);
        mAdapter.setListener(new GankContentAdapter.OnContentTvClickListener() {
            @Override
            public void onContentTvClick(View v, int position) {
                Module module = mAdapter.getList().get(position);
                Intent intent = new Intent(GankContentActivity.this, WebViewActivity.class);
                intent.putExtra("url",module.url);
                startActivity(intent);
            }
        });
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
                getDataAll(gankDateData.results.Android);
                getDataAll(gankDateData.results.iOS);
                getDataAll(gankDateData.results.benefits);
                getDataAll(gankDateData.results.otherResource);
                getDataAll(gankDateData.results.videos);
                getDataAll(gankDateData.results.recmmends);

            }
        }, date);
    }

    private void getDataAll(List<Module> modules){
        if(modules != null && modules.size() > 0){
            mAdapter.addAll((ArrayList<Module>) modules);
        }

    }

}
