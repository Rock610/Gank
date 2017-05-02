package com.rock.android.gank.ui.main;

import com.rock.android.gank.base.BasePresenter;
import com.rock.android.gank.di.PerActivity;

/**
 * Created by rock on 2017/4/28.
 */
@PerActivity
public interface MainPresenter<V extends MainView> extends BasePresenter<V> {

    void requestBenefitsData();
}
