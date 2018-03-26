package com.jk.retrofitdemo.Retrofit_Example2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.jk.retrofitdemo.R;

import java.util.ArrayList;

/**
 * Created by Abc on 3/5/2018.
 */

public class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.Viewholder>  {

    private ArrayList<ImgPojo.DataBean.BasketsBean> list;
    Context context;
    private Retrofit_HashmapActivity retro;

    public ImgAdapter(ArrayList<ImgPojo.DataBean.BasketsBean> list, Context callback) {
        this.context=  callback;
        this.list=list;
    }


    public class Viewholder extends RecyclerView.ViewHolder {

        TextView name;
        TextView intro;
        ImageView img;
        CheckBox show;
        Button showdetail;

        public Viewholder(View itemview) {

            super(itemview);
            name = itemview.findViewById(R.id.tvname);
            intro = itemview.findViewById(R.id.tvintro);
            img = itemview.findViewById(R.id.imgview);
            show = itemview.findViewById(R.id.cb_show);
            showdetail = itemview.findViewById(R.id.btn_showdetail);
        }

        public void bind(int position)
        {
            if (list.get(position).isChecked()) {
                show.setChecked(true);
                intro.setVisibility(View.VISIBLE);
            }
            else {
                show.setChecked(false);
                intro.setVisibility(View.GONE);
            }
        }
        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }

    }
    @Override
    public ImgAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_example2,parent,false);
        return new ImgAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(final ImgAdapter.Viewholder holder, final int position) {
        final ImgPojo.DataBean.BasketsBean userListModel= (ImgPojo.DataBean.BasketsBean) list.get(position);
        holder.name.setText(userListModel.getB_name());
        holder.intro.setText(userListModel.getB_intro());
       // holder.img.se(userListModel.getImg());
        Glide.with(context).load(list.get(position).getB_logo()).asBitmap().into(new BitmapImageViewTarget(holder.img) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.img.setImageDrawable(circularBitmapDrawable);
            }
        });

        holder.showdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,NextInformationActivity.class);
                intent.putExtra("name",list.get(position).getB_name());
                intent.putExtra("intro",list.get(position).getB_intro());
                intent.putExtra("image",list.get(position).getB_logo());
                context.startActivities(new Intent[]{intent});
            }
        });
        holder.setIsRecyclable(false);
        holder.bind(position);
        holder.show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (list.get(position).isChecked()) {
                    list.get(position).setChecked(false);
                    holder.intro.setVisibility(View.GONE);
                }
                    else {
                    //holder.chk.setChecked(true);
                    list.get(position).setChecked(true);
                    holder.intro.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
