package com.rock.android.gank.di.modules;

import android.app.Activity;

import com.rock.android.gank.di.PerActivity;
import com.rock.android.gank.ui.main.MainPresenter;
import com.rock.android.gank.ui.main.MainPresenterImpl;
import com.rock.android.gank.ui.main.MainView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rock on 2017/4/28.
 */
@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @PerActivity
    @Provides
    MainPresenter<MainView> provideMainPresenter(MainPresenterImpl<MainView> mainPresenter) {
        return mainPresenter;
    }
}
