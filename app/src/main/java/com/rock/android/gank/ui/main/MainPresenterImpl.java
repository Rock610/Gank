package com.rock.android.gank.ui.main;

import com.litesuits.orm.db.assit.QueryBuilder;
import com.rock.android.gank.GankApp;
import com.rock.android.gank.Model.Module;
import com.rock.android.gank.Model.ModuleResult;
import com.rock.android.gank.R;
import com.rock.android.gank.base.BasePresenterImpl;
import com.rock.android.gank.network.NetWorkManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;

/**
 * Created by rock on 2017/4/28.
 */

public class MainPresenterImpl<V extends MainView> extends BasePresenterImpl<V> implements MainPresenter<V> {

    @Inject
    public MainPresenterImpl() {
    }

    private Subscription mSubscription;

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        //先取数据库中的10条来加快显示效果
        queryDbToLoadData();
        requestBenefitsData();
    }

    private void queryDbToLoadData(){
        QueryBuilder qb = QueryBuilder.create(Module.class);
        qb.appendOrderDescBy("publishedAt");
        qb.limit(0,10);
//        mAdapter.addAll(GankApp.GankDB.query(qb));

        getView().refreshDataModule(GankApp.GankDB.query(qb),true);
    }

    @Override
    public void requestBenefitsData(){
        if(!getView().isHasMore()) return;

        Subscriber<ModuleResult> subscriber = new Subscriber<ModuleResult>() {
            @Override
            public void onCompleted() {
                getView().setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                getView().setRefreshing(false);
                getView().showToast(R.string.request_failed);
            }

            @Override
            public void onNext(ModuleResult module) {
                List<Module> list = module.results;
                if(list == null){
                    getView().setHasMore(false);
                    return;
                }
                if(list.size() < 10){
                    getView().setHasMore(false);
                }

                if(getView().getPage() == 1){
                    getView().clearList();
                }


                final ArrayList<Module> modules = new ArrayList<>();

                Observable.just(module).flatMap(new Func1<ModuleResult, Observable<Module>>() {
                    @Override
                    public Observable<Module> call(ModuleResult moduleResult) {

                        return Observable.from(moduleResult.results).filter(new Func1<Module, Boolean>() {
                            @Override
                            public Boolean call(Module module) {
                                return !hasSameDate(module);
                            }
                        });
                    }
                }).subscribe(new Subscriber<Module>() {
                    @Override
                    public void onCompleted() {
                        getView().refreshDataModule(modules,false);
                        GankApp.GankDB.save(getView().getCurrentDataList());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Module module) {
                        modules.add(module);
                    }
                });

            }
        };

        mSubscription = NetWorkManager.getInstance().getDataByType(subscriber,"福利",10,getView().getPage());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mSubscription.unsubscribe();
    }

    private boolean hasSameDate(Module module){
        ArrayList<Module> list = getView().getCurrentDataList();
        String dateTemp = module.fetchPublishedAtAsLocal();
        boolean hasTheDate = false;
        //逐个比较日期，排除相同的日期的照片
        for (Module module1 : list) {
            if(dateTemp.equals(module1.fetchPublishedAtAsLocal())){
                hasTheDate = true;
                break;
            }
        }

        return hasTheDate;
    }
}
