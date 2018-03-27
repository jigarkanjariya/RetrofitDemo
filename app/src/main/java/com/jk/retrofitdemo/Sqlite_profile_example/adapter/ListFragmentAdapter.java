package com.jk.retrofitdemo.Sqlite_profile_example.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.jk.retrofitdemo.R;
import com.jk.retrofitdemo.Retrofit_Example1.PostAdapter;
import com.jk.retrofitdemo.Sqlite_profile_example.NavigationActivity;
import com.jk.retrofitdemo.Sqlite_profile_example.database.DatabaseHandler;
import com.jk.retrofitdemo.Sqlite_profile_example.model.Label;
import com.jk.retrofitdemo.Sqlite_profile_example.model.Task;
import com.jk.retrofitdemo.Sqlite_profile_example.model.TaskLabelId;
import com.jk.retrofitdemo.Sqlite_profile_example.utils.ItemAnimation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Abc on 3/16/2018.
 */

public class ListFragmentAdapter extends RecyclerView.Adapter<ListFragmentAdapter.Viewholder> {

    ArrayList<Task> listfragment;
    private ArrayList<Label> labelList = new ArrayList<>();
    private ArrayList<Label>labelselected;
    private List<Integer> value=new ArrayList<Integer>();
    private Context context;
    private String item;

    public ListFragmentAdapter(Context context, ArrayList<Task> listfragment) {
        this.context = context;
        this.listfragment = listfragment;
    }

    @Override
    public ListFragmentAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_listfragment, parent, false);
        return new ListFragmentAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(final ListFragmentAdapter.Viewholder holder, final int position) {
        final Task task = listfragment.get(position);
        final DatabaseHandler db=new DatabaseHandler(context);
        final Task newtask=new Task();
        Label label=new Label();
        final TaskLabelId taskLabelId=new TaskLabelId();

        final Task userListModel = listfragment.get(position);
        holder.task.setText(userListModel.getTask());
        holder.check.setOnCheckedChangeListener(null);
        holder.bind(position);
        holder.btnlabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Choose an labels");
                labelList= db.getAllLabelitem();

                final List<String> label_liststring = new ArrayList<>();
                for (int i = 0; i < labelList.size(); i++) {
                    item = (labelList.get(i).getLabelname());
                    label_liststring.add(item);
                }
                builder.setMultiChoiceItems(label_liststring.toArray(new String[label_liststring.size()]),null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            taskLabelId.labelid=labelList.get(which).getLabelId();
                            taskLabelId.taskid= Integer.parseInt(task.getId());
                            value.add(taskLabelId.getLabelid());
                            db.addTaskLabelid(taskLabelId);


                        }
                    }
                });
//                builder.setItems(label_liststring.toArray(new String[label_liststring.size()]), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which)
//                    {
//                        task.id = listfragment.get(position).getId();
//                        newtask.taskLabelid = labelList.get(which).getLabelId();
//                        holder.label.setText(labelList.get(which).getLabelname());
//                        db.updateLable(task);
//                    }
//                });
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        View v = View.inflate(context, R.layout.dialog_label, null);
                        final EditText  label = v.findViewById(R.id.et_label);
                        final Label labels = new Label();

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setView(v);
                        builder.setTitle("Add Label");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String edittext = label.getText().toString().trim();
                                labels.labelname = edittext;
                                db.addLabelitem(labels);
                            }
                        });
                        builder.setNegativeButton("cancel", null);
                        builder.show();

                    }
                }).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        db.joinTaskLabel(taskLabelId.getTaskid());
                        listfragment = db.getAllTodo();
                        notifyDataSetChanged();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

//        ArrayList<Label> arrayoflabelname=task.getLabels();
//        String labelname = "";
//
//        StringBuilder sb = new StringBuilder() ;
//
//        for (Label l:arrayoflabelname)
//        {
//            sb.append(labelname).append(l.getLabelname());
//            labelname = ",";
//
//        }

        holder.label.setText(task.getTaskLabelName());
        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (listfragment.get(position).getCheck()) {
                    Task task = new Task();
                    DatabaseHandler databaseHandler = new DatabaseHandler(context);
                    task.check = false;
                    task.task = listfragment.get(position).getTask();
                    task.id = listfragment.get(position).getId();
                    databaseHandler.updateTask(task);
                    userListModel.setCheck(false);


                } else {
                    Task task = new Task();
                    DatabaseHandler databaseHandler = new DatabaseHandler(context);
                    task.check = true;
                    task.task = listfragment.get(position).getTask();
                    task.id = listfragment.get(position).getId();
                    databaseHandler.updateTask(task);
                    userListModel.setCheck(true);
                }
                listupdated();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listfragment.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView task;
        CheckBox check;
        Button btnlabel;
        TextView label;

        public Viewholder(View itemview) {

            super(itemview);
            check = itemview.findViewById(R.id.cb_todo);
            task = itemview.findViewById(R.id.tv_task);
            btnlabel = itemview.findViewById(R.id.btn_label);
            label = itemview.findViewById(R.id.tv_label);
        }

        void bind(int position) {
            if (listfragment.get(position).getCheck()) {
                check.setChecked(true);
                task.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                check.setChecked(false);
                task.setPaintFlags(Paint.LINEAR_TEXT_FLAG);
            }
        }
    }

    public void listupdated() {
        Collections.sort(listfragment, new Comparator<Task>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public int compare(Task o1, Task o2) {

                return Boolean.compare(o1.getCheck(), o2.getCheck());

            }
        });
        notifyDataSetChanged();
    }
}
