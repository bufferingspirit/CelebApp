package com.example.admin.celebapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class DataFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText firstName, lastName, height, age, description;
    private Button saveData;
    private Button deleteData;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DataFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DataFragment newInstance(String param1, String param2) {
        DataFragment fragment = new DataFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firstName = (EditText) view.findViewById(R.id.firstNameField);
        lastName = (EditText) view.findViewById(R.id.lastNameField);
        height = (EditText) view.findViewById(R.id.heightField);
        age = (EditText) view.findViewById(R.id.ageField);
        description = (EditText) view.findViewById(R.id.descriptionField);
        saveData = (Button) view.findViewById(R.id.saveData);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DatabaseHelper db = new DatabaseHelper(this.getActivity());

        celebEntry entry = db.getEntry(mParam1, mParam2);
        firstName.setText(entry.getFirstName());
        lastName.setText(entry.getLastName());
        height.setText(entry.getHeight());
        age.setText(entry.getAge());
        description.setText(entry.getDescription());
        saveData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                saveData(view);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void saveData(View view) {
        String fName = firstName.getText().toString();
        String lName = lastName.getText().toString();
        String h = height.getText().toString();
        String a = age.getText().toString();
        String des = description.getText().toString();
        if(!fName.equals("") && !lName.equals("")) {
            celebEntry entry = new celebEntry(fName, lName, h, a, des, null);

            DatabaseHelper databaseHelper = new DatabaseHelper(this.getActivity());
            if (databaseHelper.checkIfEntryExits(fName, lName)) {
                databaseHelper.updateEntry(entry);
                mListener.onFragmentInteraction(fName + " " + lName, 1);
            } else {
                long saved = databaseHelper.saveNewCelebEntry(entry);
                if (saved == -1) {
                    Toast.makeText(this.getActivity(), "FAILED", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this.getActivity(), "Successfully saved!!!", Toast.LENGTH_SHORT).show();
                    mListener.onFragmentInteraction(fName + " " + lName, 1);
                }
            }
        }
        else{
            Toast.makeText(this.getActivity(), "Need a first and last name to save data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String name, int u_flag);
    }
}
