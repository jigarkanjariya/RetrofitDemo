package com.jk.retrofitdemo.Sqlite_profile_example.adapter;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jk.retrofitdemo.R;
import com.jk.retrofitdemo.Retrofit_Example1.PostAdapter;
import com.jk.retrofitdemo.Sqlite_profile_example.OnItemClicklistner;
import com.jk.retrofitdemo.Sqlite_profile_example.model.Task;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Abc on 3/13/2018.
 */

public class TodoDeleteAdapter<T> extends RecyclerView.Adapter<TodoDeleteAdapter.Viewholder> {

    // ArrayList<Task> listdelete;
    Context mcontext;
    OnItemClicklistner listener;
    private ArrayList<Task> listdelete;



    // WeakReferenceOnListChangedCallback<Task> onListChangedCallback = new WeakReferenceOnListChangedCallback<Task>();


    public TodoDeleteAdapter(Context context, ArrayList<Task> list) {
        this.mcontext = context;
        this.listdelete = list;
    }

//    public ObservableList<Task> getItems()
//    {
//        return listdelete;
//    }
//
//    public void setItems(@Nullable Collection<Task> items)
//    {
////        if (this.listdelete == items)
////        {
////            return;
////        }
////
////        if (this.listdelete != null)
////        {
////            this.listdelete.removeOnListChangedCallback(onListChangedCallback);
////            notifyItemRangeRemoved(0, this.listdelete.size());
////        }
//
//        if (items instanceof ObservableList)
//        {
//            this.listdelete = (ObservableList<Task>) items;
//            notifyItemRangeInserted(0, this.listdelete.size());
//            this.listdelete.addOnListChangedCallback(onListChangedCallback);
//        }
//        else if (items != null)
//        {
//            this.listdelete = new ObservableArrayList<>();
//            this.listdelete.addOnListChangedCallback(onListChangedCallback);
//            this.listdelete.addAll(items);
//        }
//        else
//        {
//            this.listdelete = null;
//        }
//    }


//    public void refreshEvents(ArrayList<Task> events) {
//        this.listdelete.addAll(events);
//        notifyDataSetChanged();
//    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView deletetask;


        public Viewholder(View itemview) {

            super(itemview);
            deletetask = itemview.findViewById(R.id.tv_deletetask);
        }
    }


    @Override
    public TodoDeleteAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_todo_delete, parent, false);
        final TodoDeleteAdapter.Viewholder viewHolder = new TodoDeleteAdapter.Viewholder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder.getPosition());
            }
        });
        return new TodoDeleteAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(TodoDeleteAdapter.Viewholder holder, int position) {

        final Task userListModel = listdelete.get(position);
        holder.deletetask.setText(userListModel.getTask());
        holder.deletetask.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public int getItemCount() {
        return listdelete.size();
    }

//    private static class WeakReferenceOnListChangedCallback<Task> extends ObservableList.OnListChangedCallback {
//
//        private final WeakReference<TodoDeleteAdapter<Task>> adapterReference;
//
//        public WeakReferenceOnListChangedCallback(TodoDeleteAdapter<Task> bindingRecyclerViewAdapter) {
//            this.adapterReference = new WeakReference<>(bindingRecyclerViewAdapter);
//        }
//
//        @Override
//        public void onChanged(ObservableList sender) {
//            TodoDeleteAdapter<Task> adapter = adapterReference.get();
//            if (adapter != null) {
//                adapter.notifyDataSetChanged();
//            }
//        }
//
//        @Override
//        public void onItemRangeChanged(ObservableList observableList, int i, int i1) {
//            RecyclerView.Adapter adapter = adapterReference.get();
//            if (adapter != null)
//            {
//                adapter.notifyItemRangeChanged(i, i1);
//            }
//
//        }
//
//        @Override
//        public void onItemRangeInserted(ObservableList observableList, int i, int i1) {
//            RecyclerView.Adapter adapter = adapterReference.get();
//            if (adapter != null)
//            {
//                adapter.notifyItemRangeInserted(i, i1);
//            }
//        }
//
//        @Override
//        public void onItemRangeMoved(ObservableList observableList, int i, int i1, int i2) {
//            RecyclerView.Adapter adapter = adapterReference.get();
//            if (adapter != null)
//            {
//                adapter.notifyItemMoved(i, i1);
//            }
//        }
//
//        @Override
//        public void onItemRangeRemoved(ObservableList observableList, int i, int i1) {
//            RecyclerView.Adapter adapter = adapterReference.get();
//            if (adapter != null)
//            {
//                adapter.notifyItemRangeRemoved(i, i1);
//            }
//        }
//    }
}
