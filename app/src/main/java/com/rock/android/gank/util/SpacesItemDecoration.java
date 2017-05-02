package com.rock.android.gank.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by rock on 16/3/24.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    private int spanCount;
    private int spacing;
    private boolean includeEdge;

    public SpacesItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        if (includeEdge) {
            outRect.left = spacing-column * spacing / spanCount ; // spacing - column * ((1f / spanCount) * spacing)//- column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
//            if(column + 1 == spanCount){
//                outRect.right = spacing;
//            }else{
//                outRect.right = 0;
//            }
            if (position < spanCount) { // top edge
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // item bottom

        } else {
            outRect.left = column * spacing ; // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing ; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing; // item top
            }
        }
    }
}
