package com.example.admin.celebapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment implements AdapterView.OnItemClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "ListFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button load;
    private ListView lv;
    private ArrayList<String> names;
    private ArrayAdapter<String> adapter;

    private OnFragmentInteractionListener mListener;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
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
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = view.findViewById(R.id.name_list);
        names = new ArrayList<String>();
        populateNameList(names);

        setSelection();
        lv.setOnItemClickListener(this);
    }

    public List<String> populateNameList(ArrayList<String> names) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this.getActivity());
        ArrayList<celebEntry> entries = databaseHelper.getEntries();
        for(int i = 0; i < entries.size(); i++){
            names.add(i, entries.get(i).getFirstName() + " " + entries.get(i).getLastName());
            Log.d(TAG, "getContact: " + "Name: " + entries.get(i).getFirstName());
        }
        return names;
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //pass the name back to the main activity
        mListener.onFragmentInteraction(names.get(i));

    }

    public void update(String name) {
        names.add(name);
        adapter.notifyDataSetChanged();
        Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
    }


    public void remove(String name) {
        names.remove(name);
        adapter.notifyDataSetChanged();
        Toast.makeText(getActivity(), "Removed", Toast.LENGTH_SHORT).show();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String string);
    }

    public void setSelection(){
        try {
            adapter = new ArrayAdapter<String>(this.getActivity(),
                    android.R.layout.simple_list_item_1, names);
            lv.setAdapter(adapter);
        }
        catch (Exception ex){}
    }
}
