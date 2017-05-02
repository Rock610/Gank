package com.rock.android.gank.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.rock.android.gank.GankApp;
import com.rock.android.gank.Model.Module;
import com.rock.android.gank.Model.ModuleResult;
import com.rock.android.gank.R;
import com.rock.android.gank.network.NetWorkManager;
import com.rock.android.gank.ui.main.MainActivity;

import java.util.ArrayList;

import rx.Subscriber;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final ImageView imageView = (ImageView) findViewById(R.id.backgroundImg);

        final Module module = queryDbToLoadData();
        if(module != null){
            Glide.with(this).load(module.url).centerCrop().into(imageView);
            toMain();
        }else{
            Subscriber<ModuleResult> subscriber = new Subscriber<ModuleResult>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(ModuleResult moduleResult) {
                    if(moduleResult.results != null && moduleResult.results.size() > 0){
                        Glide.with(SplashActivity.this).load(moduleResult.results.get(0).url).centerCrop().into(imageView);
                    }

                    toMain();

                }
            };

            NetWorkManager.getInstance().getDataByType(subscriber,"福利",1,1);
        }


    }

    Handler handler = new Handler();

    private void toMain(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }

    private Module queryDbToLoadData(){
        QueryBuilder qb = QueryBuilder.create(Module.class);
        qb.appendOrderDescBy("publishedAt");
        qb.limit(0,10);
        ArrayList<Module> modules = GankApp.GankDB.query(qb);

        if(modules!=null && modules.size() > 0){
            return modules.get(1);
        }

        return null;
    }

}
