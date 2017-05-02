package com.rock.android.gank.base;

/**
 * Created by rock on 2017/5/2.
 */

public class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {

    private V mView;

    @Override
    public void onAttach(V mvpView) {
        mView = mvpView;
    }

    @Override
    public void onDetach() {

    }

    public V getView(){
        return mView;
    }
}
