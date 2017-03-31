package com.example.brianturloff.moviedescription;

import java.io.Serializable;
import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONObject;




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
* @version January 2016
*/


public class MovieDescription implements Serializable{

    public String title;
    public String year;
    public String rated;
    public String metaScore;
    public String imbdRating;
    public String runtime;
    public String released;
    public String director;
    public String genre;
    public String actors;
    public String plot;
    public String author;
    public String writer;
    public String language;
    public String country;
    public String awards;
    public String poster;
    public String fileName;

    MovieDescription(String title, String year, String rated, String released, String runtime,
                     String genre, String actors, String plot){
        this.title = title;
        this.year = year;
        this.rated = rated;
        this.runtime = released;
        this.released = runtime;
        this.genre = genre;
        this.actors = actors;
        this.plot = plot;
        this.fileName = "";

    }

    MovieDescription(String jsonStr){
        try{
            JSONObject jo = new JSONObject(jsonStr);
            title = jo.getString("Title");
            year = jo.getString("Year");
            rated = jo.getString("Rated");
            runtime = jo.getString("Runtime");
            released = jo.getString("Released");
            genre = jo.getString("Genre");
            actors = jo.getString("Actors");
            plot = jo.getString("Plot");
            this.fileName = "";
//      }
        }catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),
                    "error converting to/from json");
        }
    }


    MovieDescription() {

    }


    public String toJsonString(){
        String ret = "";
        try{
            JSONObject jo = new JSONObject();
            jo.put("Title",title);
            jo.put("Year", year);
            jo.put("Rated", rated);
            jo.put("Released", released);
            jo.put("Runtime", runtime);

//            JSONArray ja = new JSONArray(takes);
//            jo.put("takes",ja);
//            ret = jo.toString();
        }catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),
                    "error converting to/from json");
        }
        return ret;
    }


}
