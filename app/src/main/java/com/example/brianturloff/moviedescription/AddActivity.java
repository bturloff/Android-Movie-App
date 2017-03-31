package com.example.brianturloff.moviedescription;

/*
* Copyright 2016 Brian Turloff,
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.

* @author Brian Turloff mailto:bturlof@asu.edu
*         Computer Science, CIDSE, ASU - Tempe
* @version February 2016
*/

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import java.net.URI;
import java.util.Arrays;

// hello
public class AddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SearchView.OnQueryTextListener{

    MovieDescription new_movie;
    public TextView view_title;
    public TextView view_year;
    public TextView view_rated;
    public TextView view_released;
    public TextView view_runtime;
    public TextView view_genre;
    public TextView view_actors;
    public TextView view_plot;
    private SearchView searchView;
    private Menu menu;
    private String searchString;
    public Spinner spinner;
    public ArrayAdapter<CharSequence> adapter;
    //String url = "http://www.omdbapi.com/?t=Bourne&y=&plot=short&r=json";


    // This is a method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new_movie = new MovieDescription();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);

        view_title = (EditText) findViewById(R.id.editText);
        view_year = (EditText) findViewById(R.id.editText2);
        //view_rated = (EditText) findViewById(R.id.editText3);

        spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this,
                R.array.ratings_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        view_released = (EditText) findViewById(R.id.editText4);
        view_runtime = (EditText) findViewById(R.id.editText5);
        view_genre = (EditText) findViewById(R.id.editText6);
        view_actors = (EditText) findViewById(R.id.editText7);
        view_plot = (EditText) findViewById(R.id.editText8);

    }


    public void update_click(View view){

        new_movie.title = view_title.getText().toString();
        new_movie.year = (String) view_year.getText().toString();
        new_movie.runtime = (String) view_runtime.getText().toString();
        new_movie.released = (String) view_released.getText().toString();
        new_movie.genre = (String) view_genre.getText().toString();
        new_movie.actors = (String) view_actors.getText().toString();
        new_movie.plot = (String) view_plot.getText().toString();

        MyActivity.mAdapter.add(new_movie);

        try{
            MovieDB db = new MovieDB((Context)this);
            SQLiteDatabase crsDB = db.openDB();
            String insert = "insert into movie values('"+new_movie.title+"','"+
                    new_movie.year+"','"+new_movie.runtime+"','"+new_movie.rated+"','"+
                    new_movie.released+"','"+new_movie.genre+"','"+new_movie.actors+
                    "','"+new_movie.plot+"');";
            crsDB.execSQL(insert);
            crsDB.close();
            db.close();
//            String addedName = this.nameET.getText().toString();
//            setupStudentSpinner();
//            this.selectedStudent = addedName;
//            this.studentSpinner.setSelection(Arrays.asList(studs).indexOf(this.selectedStudent));
            //this.loadFields();
        } catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),"Exception adding student information: "+
                    ex.getMessage());
        }

        for(MovieDescription m : MyActivity.mAdapter.mDataset){

            android.util.Log.d("Title: ", m.title);
        }

        finish();

        return;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

         String v  = (String) parent.getItemAtPosition(position);
        android.util.Log.d("spinner: ", v);
        new_movie.rated = v;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d(this.getClass().getSimpleName(), "in onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;

        // setup the search in action bar
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (android.widget.SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent in = new Intent(this, AddActivity.class);
            startActivity(in);

        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onQueryTextChange(String query){
        //android.util.Log.d(this.getClass().getSimpleName(), "in onQueryTextChange: " + query);
        return false;
    }

    public boolean onQueryTextSubmit(String query){
        android.util.Log.d(this.getClass().getSimpleName(), "in onQueryTextSubmit: " + query);
        this.searchString = query;
        //MenuItemCompat.collapseActionView((MenuItem)menu.findItem(R.id.action_search));
        searchView.clearFocus();
        android.util.Log.d(this.getClass().getSimpleName(), "Search string is " + this.searchString);
        try{

            String q = query.replace(' ', '+');
            String u = "http://www.omdbapi.com/?t=" + q + "&y=&plot=short&r=json";
            MethodInformation mi = new MethodInformation(this, u);
            AsyncCollectionConnect ac = (AsyncCollectionConnect) new AsyncCollectionConnect().execute(mi);
        } catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(), "Exception creating adapter: " +
                    ex.getMessage());
        }
        return false;
    }
}
