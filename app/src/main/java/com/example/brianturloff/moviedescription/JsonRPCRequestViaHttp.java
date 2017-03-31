package com.example.brianturloff.moviedescription;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

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

public class JsonRPCRequestViaHttp {

    private URL url;
    private String requestData;

    public JsonRPCRequestViaHttp(URL url) {
        this.url = url;
    }

    public String call(String requestData) throws Exception {
        android.util.Log.d(this.getClass().getSimpleName(),"in call, url: "+url.toString());
        String respData = post(url, requestData);
        return respData;
    }

    private String post(URL url, String data) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(10000 /* milliseconds */);
        connection.setConnectTimeout(15000 /* milliseconds */);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.connect();
        OutputStream out = null;
        try {
            out = connection.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();
            int statusCode = connection.getResponseCode();
        android.util.Log.d("HTTP: ", "The response is: " + statusCode);
            if (statusCode != HttpURLConnection.HTTP_OK) {
                throw new Exception(
                        "Unexpected status from post: " + statusCode);
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }
        //String responseEncoding = connection.getHeaderField("Content-Encoding");
        //responseEncoding = (responseEncoding == null ? "" : responseEncoding.trim());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        InputStream in = connection.getInputStream();
        try {
            in = connection.getInputStream();
            //String contentAsString = readIt(is, len);
//            if ("gzip".equalsIgnoreCase(responseEncoding)) {
//                in = new GZIPInputStream(in);
//            }
            in = new BufferedInputStream(in);
            byte[] buff = new byte[1024];
            int n;
            while ((n = in.read(buff)) > 0) {
                bos.write(buff, 0, n);
            }
            bos.flush();
            bos.close();
        } finally {
            if (in != null) {
                in.close();
            }
        }
        android.util.Log.d(this.getClass().getSimpleName(),"json rpc request via http returned string "+bos.toString());
        return bos.toString();
    }
}
