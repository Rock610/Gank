package com.rock.android.gank.ui;

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

import com.rock.android.gank.Model.Module;
import com.rock.android.gank.Model.ModuleResult;
import com.rock.android.gank.R;
import com.rock.android.gank.network.NetWorkManager;
import com.rock.android.gank.ui.adapter.MainRecyclerViewAdapter;
import com.rock.android.gank.ui.base.ToolbarActivity;
import com.rock.android.gank.util.SpacesItemDecoration;
import com.rock.android.rocklibrary.Utils.DateFormat;
import com.rock.android.rocklibrary.Utils.DensityUtils;

import java.util.ArrayList;

import rx.Subscriber;

public class MainActivity extends ToolbarActivity {

    private RecyclerView mainRecyclerView;
    private MainRecyclerViewAdapter mAdapter;
    private SwipeRefreshLayout mainSwipeRefreshLayout;
    private boolean mIsFirstTimeTouchBottom;

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


        requestBenefitsData();
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
                intent.putExtra(GankContentActivity.KEY_DATE, DateFormat.dateToString(module.publishedAt));
                startActivity(intent);
            }
        });

        mainSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.setPageOne();
                requestBenefitsData();
            }
        });
    }

    private void requestBenefitsData(){
        Subscriber<ModuleResult> subscriber = new Subscriber<ModuleResult>() {
            @Override
            public void onCompleted() {
                mainSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("requestBenefits",e.toString());
            }

            @Override
            public void onNext(ModuleResult module) {
                Log.d("module",module.toString());
                mAdapter.addAll((ArrayList<Module>) module.results);
                mAdapter.pagePlusOne();
            }
        };

        NetWorkManager.getInstance().getDataByType(subscriber,"福利",10,mAdapter.getPage());
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
        if (id == R.id.action_settings) {
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
                    if (!mIsFirstTimeTouchBottom) {
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
