package com.rock.android.gank.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.rock.android.gank.base.BaseView;
import com.rock.android.gank.di.component.ActivityComponent;
import com.rock.android.gank.di.component.DaggerActivityComponent;
import com.rock.android.gank.di.modules.ActivityModule;

/**
 * Created by rock on 15/10/20.
 */
public class BaseActivity extends AppCompatActivity implements BaseView{
    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .build();
    }

    public ActivityComponent getComponent(){
        return mActivityComponent;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(int resId) {
        Toast.makeText(this,resId,Toast.LENGTH_SHORT).show();
    }
}
