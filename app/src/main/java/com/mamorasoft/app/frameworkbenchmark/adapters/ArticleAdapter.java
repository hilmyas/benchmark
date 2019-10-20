package com.mamorasoft.app.frameworkbenchmark.adapters;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mamorasoft.app.frameworkbenchmark.R;
import com.mamorasoft.app.frameworkbenchmark.helper.Constants;
import com.mamorasoft.app.frameworkbenchmark.models.Article;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Article> articles;
    Activity context;

    public ArticleAdapter(Activity context, ArrayList<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_data, viewGroup, false);

        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        ((Holder) viewHolder).textViewDatetime.setText(articles.get(i).getDatetime_created());
        ((Holder) viewHolder).textViewName.setText(articles.get(i).getName());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.fitCenter();
        requestOptions.placeholder(R.drawable.ic_loop_black_24dp);
        requestOptions.error(R.drawable.ic_report_problem_black_24dp);

        Glide
                .with(context)
                .load(Constants.BASE_URL + Constants.API_URL + Constants.IMAGE_COURSE_URL + articles.get(i).getImage())
                .apply(requestOptions)
                .into(((Holder)viewHolder).imageView);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    private class Holder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewDatetime;
        ImageView imageView;

        public Holder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDatetime = itemView.findViewById(R.id.textViewDatetime);
        }
    }
}
