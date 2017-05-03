package com.rock.android.gank.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rock.android.gank.Model.Module;
import com.rock.android.gank.R;
import com.rock.android.rocklibrary.Utils.DateFormat;

import java.util.List;

/**
 * Created by rock on 16/3/24.
 */
public class MainRecyclerViewAdapter extends BaseRecyclerViewAdapter<Module,MainRecyclerViewAdapter.MainViewHolder>{


    public MainRecyclerViewAdapter(Activity activity) {
        super(activity);
    }

    public MainRecyclerViewAdapter(Activity activity, List<Module> mList) {
        super(activity, mList);
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_recyclerview,parent,false);

        return new MainViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        Module girls = mList.get(position);
        Context ctx = holder.itemView.getContext();
        Glide.with(ctx).load(girls.url).into(holder.imageView);
        holder.archivesTv.setText(DateFormat.dateToString(girls.publishedAt));

    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(View v,int position);
    }

    class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imageView;
        public TextView archivesTv;
        public MainViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.girlsImage);
            archivesTv = (TextView) itemView.findViewById(R.id.archivesTitleTv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if( listener != null){
                listener.onItemClick(v,getLayoutPosition());
            }
        }
    }
}
