package com.rock.android.rocklibrary.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rock.android.rocklibrary.R;
import com.rock.android.rocklibrary.widget.TitleBar;

/**
 * Created by rock on 15/10/20.
 */
public class TitleBarBaseActivity extends BaseActivity{

    protected TitleBar mTitleBar;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    protected void setContentViewRes(int id){
        LayoutInflater inflater = LayoutInflater.from(this);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.baseactivity_content,null);
        setContentView(View.inflate(this, id, rootView));
        mTitleBar = (TitleBar) findViewById(R.id.titleBar);
    }

    public void setTitleText(String text){
        mTitleBar.setTitleText(text);
    }
}
