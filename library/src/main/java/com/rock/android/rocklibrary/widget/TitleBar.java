package com.rock.android.rocklibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rock.android.rocklibrary.R;

/**
 * Created by rock on 15/10/20.
 */
public class TitleBar extends RelativeLayout{

    private TextView leftBtn,rightBtn,titleText;

    public TitleBar(Context context) {
        super(context);
        initView();
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        LayoutInflater.from(getContext()).inflate(R.layout.titlebar,this);
        leftBtn = (TextView) findViewById(R.id.leftBtn);
        rightBtn = (TextView) findViewById(R.id.rightBtn);
        titleText = (TextView) findViewById(R.id.titleText);
    }

    public void setTitleText(String text){
        titleText.setText(text);
    }

    public TextView getLeftBtn(){
        return leftBtn;
    }

    public TextView getRightBtn(){
        return rightBtn;
    }

    public TextView getTitleText(){
        return titleText;
    }

}
