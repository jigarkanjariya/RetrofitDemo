package com.jk.retrofitdemo.Sqlite_profile_example.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableArrayList;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;

import com.jk.retrofitdemo.R;
import com.jk.retrofitdemo.Sqlite_profile_example.OnItemClicklistner;
import com.jk.retrofitdemo.Sqlite_profile_example.adapter.TodoAdapter;
import com.jk.retrofitdemo.Sqlite_profile_example.adapter.TodoDeleteAdapter;
import com.jk.retrofitdemo.Sqlite_profile_example.database.DatabaseHandler;
import com.jk.retrofitdemo.Sqlite_profile_example.model.Task;

import java.util.ArrayList;

import static com.jk.retrofitdemo.Sqlite_profile_example.shaaredprefs.SharedPref.MyPREFERENCES;


public class TODOFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerView;
    RecyclerView deleterecyclerView;
    TodoAdapter mAdapter;
    TodoDeleteAdapter mAdapterDelete;
    ArrayList<Task> list;
    ObservableArrayList<Task> listdelete;
    DatabaseHandler dbhelper;
    CheckBox checkBox;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String task_name;

    private OnFragmentInteractionListener mListener;

    public TODOFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        View line = view.findViewById(R.id.line);
        dbhelper = new DatabaseHandler(getActivity());
        final SharedPreferences sharedpreferences;
        sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        task_name = sharedpreferences.getString("name",null);


        list = dbhelper.taskjoin(task_name);
        listdelete = dbhelper.getAllDeleteTask();


        recyclerView = view.findViewById(R.id.todo_recyclerview);

        deleterecyclerView = view.findViewById(R.id.delete_recyclerview);

        RecyclerView.LayoutManager  layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.LayoutManager  layoutManager1=new LinearLayoutManager(getActivity());
        deleterecyclerView.setLayoutManager(layoutManager1);


       mAdapterDelete = new TodoDeleteAdapter(getContext(),listdelete);
        mAdapter = new TodoAdapter(getContext(),list);
        mAdapter = new TodoAdapter(getContext(), list, new OnItemClicklistner() {
            @Override
            public void onItemClick(View v, int position) {
                checkBox = v.findViewById(R.id.cb_todo);

                if (checkBox.isChecked())
                {
                    checkBox.setChecked(false);
                } else
                {
                    checkBox.setChecked(true);
                }
            }
        });

        recyclerView.setAdapter(mAdapter);
        deleterecyclerView.setAdapter(mAdapterDelete);
        line.setVisibility(View.VISIBLE);


        mAdapter.setCustomObjectListener(new TodoAdapter.MyListener() {
            @Override
            public void refresh(ArrayList<Task> arrayList) {
                mAdapterDelete = new TodoDeleteAdapter(getContext(),arrayList);
                deleterecyclerView.setAdapter(mAdapterDelete);
                deleterecyclerView.smoothScrollToPosition(deleterecyclerView.getAdapter().getItemCount());
               // deleterecyclerView.scrollToPosition(arrayList.size());
            }
        });

    }
//    public void fordelete(TodoDeleteAdapter todo,ArrayList<Task> tasks){
//        todo = new TodoDeleteAdapter(getContext(),tasks);
//        deleterecyclerView.setAdapter(todo);
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //task_name = getArguments().getString("taskname");

        View view = inflater.inflate(R.layout.fragment_todo, container, false);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



//    @Override
//    public void passlist(ArrayList<Task> arrayList) {
//        deleterecyclerView.setAdapter(mAdapterDelete);
//        mAdapterDelete.notifyDataSetChanged();
//    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
