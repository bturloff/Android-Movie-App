
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

package com.example.brianturloff.moviedescription;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


public class MyActivity extends AppCompatActivity {

    TextView jsonTV;
    public RecyclerView mRecyclerView;
    public static MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinkedList<MovieDescription> movie_titles;
    private String selectedMovie;
    private LinkedList<String> list_of_titles;
    private MovieLibrary my_movies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        android.util.Log.d("My: ", "in onCreate");
        list_of_titles = new LinkedList<>();
        my_movies = new MovieLibrary();
        super.onCreate(savedInstanceState);

        JSONObject jsonObject;



        setContentView(R.layout.activity_my);
        //jsonTV = (TextView) findViewById(R.id.textView2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(toolbar);

        loadFields();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        try{
            InputStream is = getAssets().open("movies.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            JSONObject jo = new JSONObject(new JSONTokener(br.readLine()));
            android.util.Log.d(this.getClass().getSimpleName(), jo.toString());
            Iterator<String> it =  jo.keys();
            while(it.hasNext()) {
                String element = it.next();
                JSONObject ob = jo.getJSONObject(element);
                String title = ob.getString("Year");
                MovieDescription newM = new MovieDescription(ob.toString());
                my_movies.listOfMovies.add(newM);
//                android.util.Log.d(this.getClass().getSimpleName(), newM.title + " " + newM.year);

            }
            try{
                MethodInformation mi = new MethodInformation(this, getString(R.string.defaulturl),"getTitles", new String[]{});
                AsyncCollectionConnect ac = (AsyncCollectionConnect) new AsyncCollectionConnect().execute(mi);

            } catch (Exception ex){
                android.util.Log.w(this.getClass().getSimpleName(), "Exception creating adapter: " +
                        ex.getMessage());
            }

            // specify an adapter (see also next example)
            //String[] array = {"goodbye", "sweetness", "cauliflaur"};
//            android.util.Log.d("MyACtivity", my_movies.listOfMovies.get(0).title);
            mAdapter = new MyAdapter(my_movies.listOfMovies, getBaseContext());
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected void onStart(){
        super.onStart();
        android.util.Log.d("In onStart()", "......................................");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
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



    private void loadFields(){
        try{
            MovieDB db = new MovieDB((Context)this);
            SQLiteDatabase crsDB = db.openDB();
            String title, year, rating, released, runtime, genre, actors, plot;
            Cursor cur = crsDB.rawQuery("select title,yearr,rating, released, runtime, genre, actors,plot from movie;",
                    new String[]{});
            while (cur.moveToNext()){
                title = cur.getString(0);
                year = cur.getString(1);
                rating = cur.getString(2);
                released = cur.getString(3);
                runtime = cur.getString(4);
                genre = cur.getString(5);
                actors = cur.getString(6);
                plot = cur.getString(7);

                //list_of_titles.add(title);
                my_movies.addMovie(new MovieDescription(title, year, rating, released, runtime, genre, actors, plot));
                android.util.Log.d("loadFields: ", title + ": " + "year" + year + my_movies.listOfMovies.size());
            }
//            nameET.setText(selectedMovie);
//            majorET.setText(major);
//            emailET.setText(email);
//            numET.setText(num);
            cur.close();
            //cur = crsDB.rawQuery("select course.coursename FROM student,course,studenttakes WHERE student.studentid=studenttakes.studentid and course.courseid=studenttakes.courseid and student.name=?",
            //        new String[]{selectedStudent});
//            ArrayList<String> al = new ArrayList<>();
//            while(cur.moveToNext()){
//                al.add(cur.getString(0));
//            }
//            String[] courses = (String[]) al.toArray(new String[al.size()]);
//            if (courses.length == 0){
//                courses = new String[]{"unknown"};
//            }
//            courseAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, courses);
//            coursesSpinner.setAdapter(courseAdapter);
//            coursesSpinner.setOnItemSelectedListener(this);
//            cur.close();
            crsDB.close();
            db.close();
        } catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),"Exception getting student info: "+
                    ex.getMessage());
        }
    }

    public void buttonClicked(View v) {

        MovieDescription m = new MovieDescription(getString(R.string.movie_bourne));
        android.util.Log.w(this.getClass().getSimpleName(), getString(R.string.movie_bourne));
        android.util.Log.w(this.getClass().getSimpleName(), m.title);
    }
}
