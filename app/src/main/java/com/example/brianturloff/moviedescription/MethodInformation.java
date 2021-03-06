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

import android.support.v7.app.AppCompatActivity;

public class MethodInformation {
    public String method;
    public String[] params;
    public AppCompatActivity parent;
    public String urlString;
    public String resultAsJson;

    MethodInformation(AppCompatActivity parent, String urlString, String method, String[] params){
        this.method = method;
        this.parent = parent;
        this.urlString = urlString;
        this.params = params;
        this.resultAsJson = "{}";
    }

    MethodInformation(AppCompatActivity parent, String urlString){
        //this.method = method;
        this.parent = parent;
        this.urlString = urlString;
//        this.params = params;
//        this.resultAsJson = "{}";
    }
}
