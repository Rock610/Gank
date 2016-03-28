package com.rock.android.gank.network;

import com.rock.android.gank.Model.GankDateData;
import com.rock.android.gank.Model.ModuleResult;
import com.rock.android.gank.network.RetrofitServices.GankDateService;
import com.rock.android.rocklibrary.DataManager.NetWork.BaseNetWorkManager;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by rock on 16/3/22.
 */
public class NetWorkManager extends BaseNetWorkManager {

    private NetWorkManager() {
        super();
        service = retrofit.create(GankDateService.class);

    }

    public GankDateService service;

    private static class SingletonHolder{
        private static final NetWorkManager INSTANCE = new NetWorkManager();
    }

    //获取单例
    public static NetWorkManager getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public Subscription getGankDateData(Subscriber<GankDateData> subscriber,String date){
        return service.getGankDateData(date)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription getHistory(Subscriber<List<String>> subscriber){
        return service.getHistory()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription getDataByType(Subscriber<ModuleResult> subscriber, String type, int size, int page){
        return service.getGankDataByType(type,size,page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
