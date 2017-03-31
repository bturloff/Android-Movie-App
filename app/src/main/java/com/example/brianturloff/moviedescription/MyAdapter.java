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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by brianturloff on 2/9/16.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public LinkedList<MovieDescription> mDataset;
    private ViewGroup parent;
    private MyAdapter this_adapter;
    public Context mContext;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {


        // each data item is just a string in this case
        public TextView text_title;

        public MyViewHolder(View v) {
            super(v);
            text_title = (TextView) v.findViewById(R.id.text_title);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(LinkedList<MovieDescription> myDataset, Context context)
    {
        this.mContext = context;

        this_adapter = this;
        mDataset = myDataset;

    }

    @Override
    public void onViewRecycled(MyViewHolder holder) {
        super.onViewRecycled(holder);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        this.parent = parent;
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.text_title.setText(mDataset.get(position).title);
        holder.text_title.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int pos1 = holder.getLayoutPosition();
                android.util.Log.d("onBind: ", String.valueOf(pos1));
                MovieDescription movie = mDataset.get(pos1);
                Intent i= new Intent(parent.getContext(), DetailActivity.class);
                i.putExtra("movie", movie);
                i.putExtra("position", pos1);
                parent.getContext().startActivity(i);

            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void delete(int position) {

        //int position = mDataset.indexOf(movie);
        mDataset.remove(position);
        android.util.Log.d("Index: ", String.valueOf(position));
        notifyItemRemoved(position);
    }

    public void add(MovieDescription movie){

        mDataset.add(movie);
        notifyDataSetChanged();

    }


}