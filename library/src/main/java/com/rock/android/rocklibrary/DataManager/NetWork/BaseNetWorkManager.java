package com.rock.android.rocklibrary.DataManager.NetWork;

import com.alibaba.fastjson.JSON;
import com.rock.android.rocklibrary.Utils.fastjsonConverter.FastjsonConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by rock on 16/3/22.
 */
public class BaseNetWorkManager {
    public static final String BASE_URL = "http://gank.io/api/";

    private static final int DEFAULT_TIMEOUT = 30;

    protected Retrofit retrofit;

    //构造方法私有
    protected BaseNetWorkManager() {
        //配置默认date格式
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(FastjsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

    }


}
