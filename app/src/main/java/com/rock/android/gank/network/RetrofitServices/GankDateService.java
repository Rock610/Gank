package com.rock.android.gank.network.RetrofitServices;

import com.rock.android.gank.Model.GankDateData;
import com.rock.android.gank.Model.ModuleResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by rock on 16/3/22.
 */
public interface GankDateService {

    @GET("day/{date}")
    Observable<GankDateData> getGankDateData(@Path("date") String date );

    @GET("day/history")
    Observable<List<String>> getHistory();

    @GET("data/{type}/{size}/{page}")
    Observable<ModuleResult> getGankDataByType(@Path("type") String type, @Path("size") int limit, @Path("page") int page);
}
