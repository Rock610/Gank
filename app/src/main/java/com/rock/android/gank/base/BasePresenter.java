package com.rock.android.gank.base;

/**
 * Created by rock on 2017/4/28.
 */

public interface BasePresenter<V extends BaseView> {
    void onAttach(V mvpView);

    void onDetach();

}
