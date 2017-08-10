package com.example.admin.celebapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ListFragment.OnFragmentInteractionListener{
    private static final String DATA_FRAGMENT_TAG = "DataFragment";
    public static final String LIST_FRAGMENT_TAG = "ListFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListFragment foo = new ListFragment();
        DataFragment bar = new DataFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.ListFragment, foo, LIST_FRAGMENT_TAG )
                .commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.dataFragment, bar, DATA_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void onFragmentInteraction(String string) {
        String[] foo = string.split("\\s+");
        String firstName = foo[0];
        String lastName = foo[1];
        startDataFragment(firstName,lastName);
    }

    public void startDataFragment(String firstName, String lastName){
        DataFragment dataFragment = DataFragment.newInstance(firstName, lastName);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.dataFragment, dataFragment, DATA_FRAGMENT_TAG)
                .addToBackStack(DATA_FRAGMENT_TAG + firstName + lastName)
                .commit();
    }

    //2 Fragments

}
