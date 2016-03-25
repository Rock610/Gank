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
public class GankContentAdapter extends BaseRecyclerViewAdapter<Module,GankContentAdapter.ViewHolder> {

    private String type;
    public GankContentAdapter(Activity activity) {
        super(activity);
    }

    public GankContentAdapter(Activity activity, List<Module> mList) {
        super(activity, mList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gankcontent,null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        final Module module = mList.get(position);
        String curType = module.type;
        if(position == 0){
            holder.titleTv.setVisibility(View.VISIBLE);
            holder.titleTv.setText(module.type);
        }else{
            boolean isShowTitle = !curType.equals(mList.get(position - 1).type);
            if(isShowTitle){
                holder.titleTv.setVisibility(View.VISIBLE);
                holder.titleTv.setText(module.type);
            }else{
                holder.titleTv.setVisibility(View.GONE);
            }
        }

        holder.gankContentTv.setText(mList.get(position).desc.trim());

    }

    private OnContentTvClickListener mListener;

    public void setListener(OnContentTvClickListener mListener) {
        this.mListener = mListener;
    }

    public interface OnContentTvClickListener{
        void onContentTvClick(View v,int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView gankContentTv;
        public TextView titleTv;
        public String type;
        public ViewHolder(View itemView) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(R.id.titleTv);
            gankContentTv = (TextView) itemView.findViewById(R.id.gankContentTv);
            gankContentTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        mListener.onContentTvClick(v,getLayoutPosition());
                    }
                }
            });

        }
    }
}
