package com.rock.android.gank.di.component;

import com.rock.android.gank.di.PerActivity;
import com.rock.android.gank.di.modules.ActivityModule;
import com.rock.android.gank.ui.main.MainActivity;

import dagger.Component;

/**
 * Created by rock on 2017/4/28.
 */
@PerActivity
@Component(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);
}
