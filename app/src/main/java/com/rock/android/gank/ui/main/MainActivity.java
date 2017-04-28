package com.rock.android.gank.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.litesuits.orm.db.assit.QueryBuilder;
import com.rock.android.gank.GankApp;
import com.rock.android.gank.Model.Module;
import com.rock.android.gank.Model.ModuleResult;
import com.rock.android.gank.R;
import com.rock.android.gank.network.NetWorkManager;
import com.rock.android.gank.ui.AboutActivity;
import com.rock.android.gank.ui.GankContentActivity;
import com.rock.android.gank.ui.adapter.MainRecyclerViewAdapter;
import com.rock.android.gank.ui.base.ToolbarActivity;
import com.rock.android.gank.util.SpacesItemDecoration;
import com.rock.android.rocklibrary.Utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;

public class MainActivity extends ToolbarActivity {

    private RecyclerView mainRecyclerView;
    private MainRecyclerViewAdapter mAdapter;
    private SwipeRefreshLayout mainSwipeRefreshLayout;
    private boolean mIsFirstTimeTouchBottom;
    private boolean isHasMore = true;
    private Subscription mSubscription;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        init();

        mainSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                requestBenefitsData();
            }
        });

    }

    private void init(){
        mainSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mainSwipeRefreshLayout);
        mainRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        mainRecyclerView.setLayoutManager(layoutManager);
        mainRecyclerView.addItemDecoration(new SpacesItemDecoration(2,DensityUtils.dip2px(this,10),true));
        mAdapter = new MainRecyclerViewAdapter(this);

        mainRecyclerView.setAdapter(mAdapter);
        mainRecyclerView.addOnScrollListener(getOnBottomListener(layoutManager));


        mAdapter.setOnItemClickListener(new MainRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Module module = mAdapter.getList().get(position);
                Intent intent = new Intent(MainActivity.this,GankContentActivity.class);
                intent.putExtra(GankContentActivity.KEY_DATE, module.fetchPublishedAtAsLocal());
                startActivity(intent);
            }
        });

        mainSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isHasMore = true;
                mainSwipeRefreshLayout.setEnabled(true);
                mAdapter.setPageOne();
                requestBenefitsData();
            }
        });

        queryDbToLoadData();

    }

    private void queryDbToLoadData(){
        QueryBuilder qb = QueryBuilder.create(Module.class);
        qb.appendOrderDescBy("publishedAt");
        qb.limit(0,10);
        mAdapter.addAll(GankApp.GankDB.query(qb));

    }

    private void requestBenefitsData(){
        if(!isHasMore) return;

        Subscriber<ModuleResult> subscriber = new Subscriber<ModuleResult>() {
            @Override
            public void onCompleted() {
                mainSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                mainSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, R.string.request_failed,Toast.LENGTH_SHORT).show();
                Log.e("requestBenefits",e.toString());
            }

            @Override
            public void onNext(ModuleResult module) {
                Log.d("module",module.toString());

                List<Module> list = module.results;
                if(list == null){
                    isHasMore = false;
                    return;
                }
                if(list.size() < 10){
                    isHasMore = false;
                }

                if(mAdapter.getPage() == 1){
                    mAdapter.clear();
                }

                Observable.just(module).flatMap(new Func1<ModuleResult, Observable<Module>>() {
                    @Override
                    public Observable<Module> call(ModuleResult moduleResult) {

                        return Observable.from(moduleResult.results);
                    }
                }).subscribe(new Subscriber<Module>() {
                    @Override
                    public void onCompleted() {
                        mAdapter.notifyDataSetChanged();
                        mAdapter.pagePlusOne();
                        GankApp.GankDB.save(mAdapter.getList());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Module module) {
                        ArrayList<Module> list = (ArrayList<Module>) mAdapter.getList();
                        String dateTemp = module.fetchPublishedAtAsLocal();
                        boolean hasTheDate = false;
                        //逐个比较日期，排除相同的日期的照片
                        for (Module module1 : list) {
                            if(dateTemp.equals(module1.fetchPublishedAtAsLocal())){
                                hasTheDate = true;
                                break;
                            }
                        }
                        if(!hasTheDate){
                            list.add(module);
                        }
                    }
                });

            }
        };

        mSubscription = NetWorkManager.getInstance().getDataByType(subscriber,"福利",10,mAdapter.getPage());
    }

    @Override
    protected void onDestroy() {
        mSubscription.unsubscribe();
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent intent = new Intent(this,AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    RecyclerView.OnScrollListener getOnBottomListener(final GridLayoutManager layoutManager) {
        return new RecyclerView.OnScrollListener() {
            @Override public void onScrolled(RecyclerView rv, int dx, int dy) {
                boolean isBottom =
                        layoutManager.findLastCompletelyVisibleItemPosition() >=
                                mAdapter.getItemCount() -
                                        10;
                if (!mainSwipeRefreshLayout.isRefreshing() && isBottom) {
                    if (!mIsFirstTimeTouchBottom && isHasMore) {
                        mainSwipeRefreshLayout.setRefreshing(true);
                        requestBenefitsData();
                    }
                    else {
                        mIsFirstTimeTouchBottom = false;
                    }
                }
            }
        };
    }
}
