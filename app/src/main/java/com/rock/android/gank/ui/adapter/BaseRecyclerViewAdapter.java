package com.rock.android.gank.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rock on 16/3/24.
 */
public class BaseRecyclerViewAdapter<T,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>{

    protected Activity activity;
    protected List<T> mList;
    protected int page = 1;

    public BaseRecyclerViewAdapter(Activity activity) {
        this(activity,null);

    }

    public List<T> getList(){
        return mList;
    }

    public BaseRecyclerViewAdapter(Activity activity, List<T> mList) {
        this.activity = activity;
        if(mList == null){
            this.mList = new ArrayList<>();
        }else{
            this.mList = mList;
        }
    }

    public void addAll(ArrayList<T> list){
        if(list == null && list.size() == 0) return;
        mList.addAll(list);

        notifyDataSetChanged();
    }

    public void clear(){
        if(mList.size() > 0){
            mList.clear();
            notifyDataSetChanged();
        }

    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public int getItemCount() {
        return mList == null? 0:mList.size();
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

    }

    public void pagePlusOne(){
        page ++;
    }

    public int getPage(){
        return page;
    }

    public void setPageOne(){
        page = 1;
    }

}
