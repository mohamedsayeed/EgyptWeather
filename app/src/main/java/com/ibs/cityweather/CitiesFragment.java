package com.ibs.cityweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import static com.ibs.cityweather.MainActivity.Cities;

public class CitiesFragment extends Fragment {

    SearchView searchView;
    RecyclerView CitiesList;
    CitiesAdapter Adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cities_list, container, false);

        CitiesList = (RecyclerView) rootView.findViewById(R.id.Rv_cities);

        setHasOptionsMenu(true);
        initViews();

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.main_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        Search();


    }


    private void Search() {
        final AutoCompleteTextView searchEditText = (AutoCompleteTextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setHint("Search For City");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String query) {
                // Here is where we are going to implement the filter logic
                Adapter.getFilter().filter(searchEditText.getText());
                CitiesList.setAdapter(Adapter);
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String filterString) {
                Adapter.getFilter().filter(searchEditText.getText());
                return true;
            }
        });
    }

    private void initViews() {
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getContext());
        CitiesList.setLayoutManager(LayoutManager);
        System.out.println("city size ======== " + Cities.size());
        Adapter = new CitiesAdapter(getActivity().getApplicationContext(), Cities);
        CitiesList.setAdapter(Adapter);
    }


}
