package com.rock.android.gank.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rock.android.gank.Model.Module;
import com.rock.android.gank.R;
import com.rock.android.gank.ui.AboutActivity;
import com.rock.android.gank.ui.GankContentActivity;
import com.rock.android.gank.ui.adapter.MainRecyclerViewAdapter;
import com.rock.android.gank.ui.base.ToolbarActivity;
import com.rock.android.gank.util.SpacesItemDecoration;
import com.rock.android.rocklibrary.Utils.DensityUtils;

import java.util.ArrayList;

import javax.inject.Inject;

public class MainActivity extends ToolbarActivity implements MainView{

    private RecyclerView mainRecyclerView;
    private MainRecyclerViewAdapter mAdapter;
    private SwipeRefreshLayout mainSwipeRefreshLayout;
    private boolean mIsFirstTimeTouchBottom;
    private boolean isHasMore = true;

    @Inject
    MainPresenter<MainView> mPresenter;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getComponent().inject(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        init();

    }

    private void init(){
        mainSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mainSwipeRefreshLayout);
        mainRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        mainRecyclerView.setLayoutManager(layoutManager);
        mainRecyclerView.addItemDecoration(new SpacesItemDecoration(2,DensityUtils.dip2px(this,10),false));
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
                setHasMore(true);
                mainSwipeRefreshLayout.setEnabled(true);
                mAdapter.setPageOne();
                mPresenter.requestBenefitsData();
            }
        });

        mPresenter.onAttach(this);

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
                        setRefreshing(true);

                        mPresenter.requestBenefitsData();
                    }
                    else {
                        mIsFirstTimeTouchBottom = false;
                    }
                }
            }
        };
    }

    @Override
    public boolean isHasMore() {
        return isHasMore;
    }

    @Override
    public void setRefreshing(boolean b) {
        mainSwipeRefreshLayout.setRefreshing(b);
    }

    @Override
    public int getPage() {
        return mAdapter.getPage();
    }

    @Override
    public void setHasMore(boolean b) {
        isHasMore = b;
    }

    @Override
    public void clearList() {
        mAdapter.clear();
    }

    @Override
    public void refreshDataModule(ArrayList<Module> module,boolean fromDB) {
        mAdapter.addAll(module);

        if(!fromDB){
            mAdapter.notifyDataSetChanged();
            mAdapter.pagePlusOne();
        }

    }

    @Override
    public ArrayList<Module> getCurrentDataList() {
        return (ArrayList<Module>) mAdapter.getList();
    }

    @Override
    public boolean canBack() {
        return false;
    }
}
