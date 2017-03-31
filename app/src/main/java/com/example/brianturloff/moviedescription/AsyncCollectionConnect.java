package com.example.brianturloff.moviedescription;

import android.os.AsyncTask;
import android.os.Looper;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

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

public class AsyncCollectionConnect extends AsyncTask<MethodInformation, Void, MethodInformation> {


    @Override
    protected void onPreExecute(){
        android.util.Log.d(this.getClass().getSimpleName(),"in onPreExecute on "+
                (Looper.myLooper() == Looper.getMainLooper()?"Main thread":"Async Thread"));
    }

    @Override
    protected MethodInformation doInBackground(MethodInformation... aRequest){
        // key is the waypoint category, value is an array of waypoint names in that category
        //Student aStud = new Student("unknown",0,(new String[]{"unknown"}));
        String resultStr = "";
        android.util.Log.d(this.getClass().getSimpleName(),"in doInBackground on "+
                (Looper.myLooper() == Looper.getMainLooper()?"Main thread":"Async Thread"));
        try {
            android.util.Log.d(" ","im here 1");
            JSONArray ja = new JSONArray(aRequest[0].params);
            android.util.Log.d(" ","params: "+ja.toString());
            String requestData = "{ \"jsonrpc\":\"2.0\", \"method\":\""+aRequest[0].method+"\", \"params\":"+ja.toString()+
                    ",\"id\":3}";
            android.util.Log.d(" ","im here2");
            android.util.Log.d(" ","requestData: "+requestData+" url: "+aRequest[0].urlString);
            JsonRPCRequestViaHttp conn = new JsonRPCRequestViaHttp((new URL(aRequest[0].urlString)));
            resultStr = conn.call(requestData);
            aRequest[0].resultAsJson = resultStr;
        }catch (Exception ex){
            android.util.Log.d(this.getClass().getSimpleName(),"exception in remote call "+
                    ex.getMessage());
        }
        return aRequest[0];
    }

    @Override
    protected void onPostExecute(MethodInformation res){
        android.util.Log.d(this.getClass().getSimpleName(), "in onPostExecute on " +
                (Looper.myLooper() == Looper.getMainLooper() ? "Main thread" : "Async Thread"));
        android.util.Log.d(this.getClass().getSimpleName(), " resulting is: " + res.resultAsJson);
        try {
            if (res.method.equals("getTitles")) {
                //res.parent = (MyActivity) res.parent;
                JSONObject jo = new JSONObject(res.resultAsJson);
                JSONArray ja = jo.getJSONArray("result");
                ArrayList<String> al = new ArrayList<String>();
                for (int i = 0; i < ja.length(); i++) {
                    al.add(ja.getString(i));
                }
                String[] names = al.toArray(new String[0]);
                android.util.Log.d(this.getClass().getSimpleName(), " names array: " + names[2]);
//  ((MyActivity)res.parent).mAdapter.clear();
                for (String n:names
                     ) {
                    try{
                        MethodInformation mi = new MethodInformation(((MyActivity)res.parent),
                                ((MyActivity)res.parent).getString(R.string.defaulturl),"get", new String[]{n});
                        android.util.Log.d("getting movie: ", n);
                        AsyncCollectionConnect ac = (AsyncCollectionConnect) new AsyncCollectionConnect().execute(mi);

                    } catch (Exception ex){
                        android.util.Log.w(this.getClass().getSimpleName(), "Exception creating adapter: " +
                                ex.getMessage());
                    }


                }
                for (int i = 0; i < names.length; i++) {
//                    ((MyActivity)res.parent).mAdapter.add("df");
                }

//                if (names.length > 0){
//                    try{
//                        MethodInformation mi = new MethodInformation(res.parent, res.urlString,"get",
//                                new String[]{names[0]});
//                        AsyncCollectionConnect ac = (AsyncCollectionConnect) new AsyncCollectionConnect().execute(mi);
//                    } catch (Exception ex){
//                        android.util.Log.w(this.getClass().getSimpleName(),"Exception processing spinner selection: "+
//                                ex.getMessage());
//                    }
//                }
            }
            else if(res.method.equals("get")){
                JSONObject jo = new JSONObject(res.resultAsJson);
                JSONObject ja = jo.getJSONObject("result");
                MovieDescription nMovie = new MovieDescription(ja.toString());
                ((MyActivity)res.parent).mAdapter.add(nMovie);
                String fName = ja.optString("Filename");
                if(fName != null){
                    nMovie.fileName = fName;
                }
                ((MyActivity)res.parent).mAdapter.notifyDataSetChanged();
//                android.util.Log.d(this.getClass().getSimpleName(), " ........Added: " + nMovie.title);

            }

            else if (res.method.equals("getMovie")){

                JSONObject jo = new JSONObject(res.resultAsJson);
                JSONObject mov = jo.getJSONObject("result");
                String title = mov.getString("Title");
                ((DetailActivity)res.parent).titleTV.setText(title);
                ((DetailActivity)res.parent).ratedTV.setText(mov.getString("Rated"));
                ((DetailActivity)res.parent).yearTV.setText(mov.getString("Year"));
                ((DetailActivity)res.parent).releasedTV.setText(mov.getString("Released"));
                ((DetailActivity)res.parent).runtimeTV.setText(mov.getString("Runtime"));
                ((DetailActivity)res.parent).genreTV.setText(mov.getString("Genre"));
                ((DetailActivity)res.parent).actorsTV.setText(mov.getString("Actors"));
                ((DetailActivity)res.parent).plotTV.setText(mov.getString("Plot"));



            }
//            else if (res.method.equals("get")) {
//                JSONObject jo = new JSONObject(res.resultAsJson);
//                MovieDescription aStud = new MovieDescription(jo.getJSONObject("result").toString());
//                res.parent.studentidET.setText((new Integer(aStud.studentid)).toString());
//                res.parent.nameET.setText(aStud.name);
//            }
        }catch (Exception ex){
            android.util.Log.d(this.getClass().getSimpleName(),"Exception: "+ex.getMessage());
        }
//                JSONObject jo = new JSONObject(res.resultAsJson);
//                String title = jo.getString("Title");
//                android.util.Log.d(this.getClass().getSimpleName(), " year: " + jo.getString("Rated"));
//                ((AddActivity)res.parent).view_title.setText(title);
//                int pos = ((AddActivity) res.parent).adapter.getPosition(jo.getString("Rated"));
//                if(pos >= 0)
//                        ((AddActivity) res.parent).spinner.setSelection(pos);
//                ((AddActivity) res.parent).view_year.setText(jo.getString("Year"));
//                ((AddActivity)res.parent).view_released.setText(jo.getString("Released"));
//                ((AddActivity)res.parent).view_runtime.setText(jo.getString("Runtime"));
//                ((AddActivity)res.parent).view_genre.setText(jo.getString("Genre"));
//                ((AddActivity)res.parent).view_actors.setText(jo.getString("Actors"));
//                ((AddActivity)res.parent).view_plot.setText(jo.getString("Plot"));




//            else if (refs.method.equals("get")) {
//                JSONObject jo = new JSONObject(res.resultAsJson);
//                MovieDescription aStud = new MovieDescription(jo.getJSONObject("result").toString());
//                res.parent.studentidET.setText((new Integer(aStud.studentid)).toString());
//                res.parent.nameET.setText(aStud.name);
//            }
//        }catch (Exception ex){
//            android.util.Log.d(this.getClass().getSimpleName(),"Exception: "+ex.getMessage());
//        }
    }

}
