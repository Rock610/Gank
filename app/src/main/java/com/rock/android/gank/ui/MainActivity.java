package com.rock.android.gank.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;

import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mainRecyclerView;
    private MainRecyclerViewAdapter mAdapter;
    private SwipeRefreshLayout mainSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        mainRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mAdapter = new MainRecyclerViewAdapter(this);

        mainRecyclerView.setAdapter(mAdapter);

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
                System.out.println(module);
                mAdapter.addAll((ArrayList<Module>) module.results);
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
}
