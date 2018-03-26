package com.jk.retrofitdemo.Sqlite_profile_example.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jk.retrofitdemo.R;
import com.jk.retrofitdemo.Sqlite_profile_example.database.DatabaseHandler;
import com.jk.retrofitdemo.Sqlite_profile_example.MainScreenActivity;
import com.jk.retrofitdemo.Sqlite_profile_example.model.UserProfile;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpdateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String name;
    String password;
    String phoneno;
    String emailid;

    ImageView mIvProfilepic;
    EditText mEtName;
    EditText mEtEmailid;
    EditText mEtPhoneno;
    EditText mEtPassword;
    Button mBtnUpdate;
    RadioButton mRbMale;
    RadioButton mRbFemale;
    RadioGroup mRggender;
    TextView mTvGender;
    String value2;
    private static final int GALLERY_CODE = 2;
    private static final int CAPTURE_CODE = 1;
    private static final int MY_PERMISSIONS = 101;
    private static final String TAG = "SignUpActivity";
    String picpath;
    private UserProfile userprofile;
    DatabaseHandler databaseHandler;

    private OnFragmentInteractionListener mListener;

    public UpdateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateFragment newInstance(String param1, String param2) {
        UpdateFragment fragment = new UpdateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            databaseHandler = new DatabaseHandler(getActivity());
            userprofile = new UserProfile();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update, container, false);

        final DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());

        mBtnUpdate = view.findViewById(R.id.btn_update);
        mEtEmailid = view.findViewById(R.id.et_email);
        mEtName = view.findViewById(R.id.et_name1);
        mEtPassword = view.findViewById(R.id.et_password1);
        mEtPhoneno = view.findViewById(R.id.et_phone);
        mIvProfilepic = view.findViewById(R.id.iv_profilepic);
        mRbMale = view.findViewById(R.id.rb_male);
        mRbFemale = view.findViewById(R.id.rb_female);
        mRggender = view.findViewById(R.id.rg_user);
        mTvGender = view.findViewById(R.id.tv_gender);

        mBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEtName.getText().toString();
                String password = mEtPassword.getText().toString();
                String phoneno = mEtPhoneno.getText().toString();
                String emailid = mEtEmailid.getText().toString();
                databaseHandler.updateUser(userprofile);
                Intent intent=new Intent(getActivity(),MainScreenActivity.class);
                startActivity(intent);
            }
        });

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
