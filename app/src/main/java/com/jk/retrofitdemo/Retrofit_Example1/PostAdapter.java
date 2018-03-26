package com.jk.retrofitdemo.Retrofit_Example1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jk.retrofitdemo.R;

import java.util.ArrayList;

/**
 * Created by Abc on 2/28/2018.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.Viewholder> {

    ArrayList<PostPojo> list;
    OnItemClicklistner listener;
    Context context;

    public PostAdapter(ArrayList<PostPojo> list) {
        this.list = list;
    }
    public PostAdapter(Context applicationContext, ArrayList<PostPojo> list, OnItemClicklistner onclickListener) {
        this.list = list;
        this.listener = onclickListener;
        this.context = applicationContext;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView userid;
        TextView title;
        TextView body;

        public Viewholder(View itemview)  {

            super(itemview);
            userid = itemview.findViewById(R.id.tv_userid);
            body = itemview.findViewById(R.id.tv_body);
            title = itemview.findViewById(R.id.tv_title);


        }
    }
    @Override
    public PostAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_post,parent,false);;
        final PostAdapter.Viewholder viewHolder = new PostAdapter.Viewholder(inflater);
        inflater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder.getPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PostAdapter.Viewholder holder, int position) {
        PostPojo userListModel=list.get(position);
        holder.userid.setText(String.valueOf(userListModel.getUserId()));
        holder.title.setText(userListModel.getTitle());
        holder.body.setText(userListModel.getBody());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
