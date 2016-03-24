package com.rock.android.gank.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rock.android.gank.Model.Module;
import com.rock.android.gank.R;

import java.util.List;

/**
 * Created by rock on 16/3/24.
 */
public class GankContentAdapter extends BaseRecyclerViewAdapter<List<Module>,GankContentAdapter.ViewHolder> {

    public GankContentAdapter(Activity activity) {
        super(activity);
    }

    public GankContentAdapter(Activity activity, List<List<Module>> mList) {
        super(activity, mList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gankcontent,null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        holder.gankContentTv.setText(mList.get(position).get(0).desc);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView gankContentTv;
        public ViewHolder(View itemView) {
            super(itemView);

            gankContentTv = (TextView) itemView.findViewById(R.id.gankContentTv);
        }
    }
}
