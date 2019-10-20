package com.mamorasoft.app.frameworkbenchmark.helper;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mamorasoft.app.frameworkbenchmark.R;

public class RecyclerViewManager {

    public static void setLayoutLinear(RecyclerView recyclerView, Context context, int orientation,
                                       boolean reverse){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context.getApplicationContext(),
                orientation, reverse);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public static void setLayoutGrid(RecyclerView recyclerView, Context context){
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context.getApplicationContext(), ScreenUtil.calculateNoOfColumns(context, context.getResources().getDimension(R.dimen.tablet_item_layout)));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
