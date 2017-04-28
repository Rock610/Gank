package com.rock.android.gank.di.modules;

import com.rock.android.gank.ui.main.MainPresenter;
import com.rock.android.gank.ui.main.MainPresenterImpl;

import dagger.Module;

/**
 * Created by rock on 2017/4/28.
 */
@Module
public class ActivityModule {

        MainPresenter provideMainPresenter(MainPresenterImpl mainPresenter){

        }
}
