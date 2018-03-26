package com.jk.retrofitdemo.Sqlite_profile_example.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.jk.retrofitdemo.R;
import com.jk.retrofitdemo.Sqlite_profile_example.OnItemClicklistner;
import com.jk.retrofitdemo.Sqlite_profile_example.database.DatabaseHandler;
import com.jk.retrofitdemo.Sqlite_profile_example.fragment.TODOFragment;
import com.jk.retrofitdemo.Sqlite_profile_example.model.Task;
import com.jk.retrofitdemo.Sqlite_profile_example.utils.ItemAnimation;

import java.util.ArrayList;

/**
 * Created by Abc on 3/8/2018.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.Viewholder>  {

    ArrayList<Task> list;
    ArrayList<Task> listdelete;
    Context context;
    OnItemClicklistner listener;
    private static final String TAG = "TodoAdapter";
    TodoDeleteAdapter mAdapterDelete;
    TODOFragment fragment;
    MyListener mylistener;



    public TodoAdapter(Context context, ArrayList<Task> list)
    {
        this.context=context;
        this.list=list;
    }



    public TodoAdapter(Context context, ArrayList<Task> list, OnItemClicklistner onItemClicklistner) {
        this.context = context;
        this.list = list;
        this.listener = onItemClicklistner;
    }

    public TodoAdapter() {

    }

    public void setCustomObjectListener(MyListener myListener){
        this.mylistener = myListener;
    }

    @Override
    public TodoAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_todo,parent,false);
        final TodoAdapter.Viewholder viewHolder = new TodoAdapter.Viewholder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder.getPosition());
            }
        });
        return new TodoAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(final TodoAdapter.Viewholder holder, final int position) {
        final Task userListModel=list.get(position);
        holder.task.setText(userListModel.getTask());
        holder.bind(position);
        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (list.get(position).getCheck())
                {
                    DatabaseHandler db=new DatabaseHandler(context);
                    Task task=new Task();
                    task.check=false;
                    task.id=list.get(position).getId();
                    task.task=list.get(position).getTask();
                    db.updateTask(task);
                    userListModel.setCheck(false);


                }
                else {
                    DatabaseHandler db=new DatabaseHandler(context);
                    Task task=new Task();
                    task.check=true;
                    task.id=list.get(position).getId();
                    task.task=list.get(position).getTask();
                    db.updateTask(task);
                    userListModel.setCheck(true);
                    ItemAnimation itemAnimator = new ItemAnimation(context);
                    itemAnimator.animateAdd(holder);


                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHandler db=new DatabaseHandler(context);
                Task task=new Task();
                task.id=list.get(position).getId();
                //String name = list.get(position).getTask();
                task.task = list.get(position).getTask();
                db.delete(task);
                list.remove(position);
                notifyDataSetChanged();
                db.deletetask(task.task);
                listdelete = db.getAllDeleteTask();
                mylistener.refresh(listdelete);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder  {

        TextView task;
        CheckBox check;
        TextView delete;

        public Viewholder(View itemview)  {

            super(itemview);
            check = itemview.findViewById(R.id.cb_todo);
            task = itemview.findViewById(R.id.tv_task);
            delete = itemview.findViewById(R.id.tv_delete);
        }

        void bind(int position) {
            if (list.get(position).getCheck()) {
                check.setChecked(true);

            }
            else {
                check.setChecked(false);
            }
        }
    }
public interface MyListener{

         void refresh(ArrayList<Task> arrayList);

}


}
