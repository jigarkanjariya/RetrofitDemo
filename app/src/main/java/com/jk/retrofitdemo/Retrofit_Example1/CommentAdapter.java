package com.jk.retrofitdemo.Retrofit_Example1;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jk.retrofitdemo.R;

import java.util.ArrayList;

/**
 * Created by Abc on 3/1/2018.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.Viewholder> {

    ArrayList<CommentPojo> listcomment;

    public CommentAdapter(ArrayList<CommentPojo> listcomment) {
        this.listcomment = listcomment;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView id;
        TextView name;
        TextView body;
        TextView email;

        public Viewholder(View itemview)  {

            super(itemview);
            id = itemview.findViewById(R.id.tv_id);
            body = itemview.findViewById(R.id.tv_body);
            name = itemview.findViewById(R.id.tv_name);
            email = itemview.findViewById(R.id.tv_email);


        }
    }
    @Override
    public CommentAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_comment,parent,false);
        return new CommentAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(CommentAdapter.Viewholder holder, int position) {
        CommentPojo userListModel=listcomment.get(position);
        holder.id.setText(userListModel.getId());
        holder.name.setText(userListModel.getName());
        holder.body.setText(userListModel.getBody());
        holder.email.setText(userListModel.getEmail());
    }

    @Override
    public int getItemCount() {
        return listcomment.size();
    }
}
