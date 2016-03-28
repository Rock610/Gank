package com.rock.android.gank.ui;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.widget.TextView;

import com.rock.android.gank.R;
import com.rock.android.gank.comm.Constants;
import com.rock.android.gank.ui.base.ToolbarActivity;

public class AboutActivity extends ToolbarActivity {

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_about;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle(R.string.about);
        TextView blogTv = (TextView) findViewById(R.id.blogTv);
        TextView jianshuTv = (TextView) findViewById(R.id.jianshuTv);


        blogTv.setText(getUrlSpannable(getString(R.string.my_blog),Constants.MY_BLOG));
        blogTv.setMovementMethod(LinkMovementMethod.getInstance());

        jianshuTv.setText(getUrlSpannable(getString(R.string.my_jianshu),Constants.MY_JIANSHU));
        jianshuTv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private SpannableStringBuilder getUrlSpannable(String title,String url){
        SpannableStringBuilder ssb = new SpannableStringBuilder(title);
        SpannableString ss = new SpannableString(url);
        ss.setSpan(new URLSpan(url),0,url.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ssb.append(ss);
        return ssb;
    }
}
