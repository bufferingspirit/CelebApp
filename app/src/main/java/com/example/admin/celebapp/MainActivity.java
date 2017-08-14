package com.example.admin.celebapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ListFragment.OnFragmentInteractionListener, DataFragment.OnFragmentInteractionListener{
    private static final String DATA_FRAGMENT_TAG = "DataFragment";
    public static final String LIST_FRAGMENT_TAG = "ListFragment";
    ListFragment foo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        foo = new ListFragment();
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
        if(foo.length > 1) {
            startDataFragment(foo[0], foo[1]);
        }
        else{
            Toast.makeText(this, "Need A Last Name", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFragmentInteraction(String name, int flag){
        if(flag > 0){
            foo.update(name);
        }
        else{
            foo.remove(name);
        }
    }

    public void startDataFragment(String firstName, String lastName){
        DataFragment dataFragment = DataFragment.newInstance(firstName, lastName);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.dataFragment, dataFragment, DATA_FRAGMENT_TAG)
                .addToBackStack(DATA_FRAGMENT_TAG + firstName + lastName)
                .commit();
    }


}
