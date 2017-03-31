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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by brianturloff on 2/8/16.
 */
public class MovieLibrary{

    public LinkedList<MovieDescription> listOfMovies;


    public MovieLibrary(){
        listOfMovies = new LinkedList<>();
    }

    public MovieLibrary(JSONObject movie_list){

        listOfMovies = new LinkedList<>();
        Iterator<String> movie_titles = movie_list.keys();

        MovieDescription newMovie;
        while( movie_titles.hasNext()){
            String title = movie_titles.next();
            try {
                newMovie = new MovieDescription(movie_list.getString(title));
                listOfMovies.add(newMovie);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    public void addMovie(MovieDescription movie){

        listOfMovies.add(movie);
    }

//    public void removeMovie(String title){
//
//        listOfMovies.
//    }
}
