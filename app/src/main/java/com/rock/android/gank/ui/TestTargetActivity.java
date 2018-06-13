package com.rock.android.gank.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rock.android.gank.R;

public class TestTargetActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_target);
    }
}
