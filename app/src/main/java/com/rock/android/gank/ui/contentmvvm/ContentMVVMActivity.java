package com.rock.android.gank.ui.contentmvvm;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.rock.android.gank.BR;
import com.rock.android.gank.R;
import com.rock.android.gank.databinding.ActivityContentMvvmBinding;

public class ContentMVVMActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ContentViewModel contentViewModel = new ContentViewModel();
        final ActivityContentMvvmBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_content_mvvm);

        viewDataBinding.setModule(contentViewModel);

        viewDataBinding.tvId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentViewModel.module.get().set_id("hello im id");
                contentViewModel.module.notifyPropertyChanged(BR.module);
            }
        });

        contentViewModel.requestModule();
//        NetWorkManager.getInstance().getGankDateData(new Subscriber<GankDateData>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(GankDateData gankDateData) {
//                contentViewModel.module.set(gankDateData.results.Android.get(0));
//            }
//        }, "2018/06/12");

    }
}
