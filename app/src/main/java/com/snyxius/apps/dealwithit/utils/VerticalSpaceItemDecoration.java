package com.snyxius.apps.dealwithit.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by snyxius on 26/11/15.
 */
public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int mVerticalSpaceHeight;

    public VerticalSpaceItemDecoration(int mVerticalSpaceHeight) {
        this.mVerticalSpaceHeight = mVerticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.bottom = mVerticalSpaceHeight;
    }
}


//If you don't want to insert space below the last item, add the following condition:

//        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
//        outRect.bottom = mVerticalSpaceHeight;
//        }