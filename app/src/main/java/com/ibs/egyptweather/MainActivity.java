package com.ibs.egyptweather;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;

/**
 * Created by Mohamed Sayed on 9/3/2017.
 */

public class MainActivity extends AppCompatActivity {

    public static ArrayList<String> names = new ArrayList<>();
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addCities();

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.main);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        TabSelector adapter = new TabSelector(getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        Search();

        return true;
    }

    private void Search() {
        final AutoCompleteTextView searchEditText = (AutoCompleteTextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setHint("Search");

//        searchEditText.setHintTextColor(getResources().getColor(R.color.custom));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String query) {
//                Adapter.getFilter().filter(searchEditText.getText());
//                RestaurantsList.setAdapter(Adapter);
//                Toast.makeText(getApplicationContext(), searchEditText.getText(), Toast.LENGTH_SHORT).show();

                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String filterString) {
//                Adapter.getFilter().filter(searchEditText.getText());
                return true;
            }
        });
    }

    private void addCities() {
        names.add("Cairo,EG");
        names.add("Giza,EG");
        names.add("Alexandria,EG");
        names.add("Ismailia,EG");
        names.add("Aswan,EG");
        names.add("Luxor,EG");
        names.add("PortSaid,EG");
        names.add("Suez,EG");
        names.add("Tanta,EG");
        names.add("Arish,EG");
        names.add("Banha,EG");
        names.add("Beni Suef,EG");
        names.add("El-Mahalla El-Kubra,EG");
        names.add("Faiyum,EG");
        names.add("Mansoura,EG");
        names.add("Zagazig,EG");
        names.add("Asyut,EG");
    }

}

