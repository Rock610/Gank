package com.rock.android.gank.ui.contentmvvm;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.rock.android.gank.Model.GankDateData;
import com.rock.android.gank.Model.Module;
import com.rock.android.gank.network.NetWorkManager;

import rx.Subscriber;

public class ContentViewModel extends BaseObservable{

    public final ObservableField<Module> module = new ObservableField<>();

    public void requestModule(){
        NetWorkManager.getInstance().getGankDateData(new Subscriber<GankDateData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GankDateData gankDateData) {
                module.set(gankDateData.results.Android.get(0));
            }
        }, "2018/06/12");
    }
}
