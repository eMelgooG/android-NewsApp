package com.example.newsapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class JobsStoriesFragment extends Fragment {
static ArrayList<String> titles;
static ArrayList<String> urls;
ListView listView;
static ArrayAdapter<String> adapter;

    public JobsStoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jobs_stories, container, false);

        if(listView!=null)
            listView.invalidateViews();

        if(titles==null) {
            titles = new ArrayList<>();
            urls = new ArrayList<>();
        }

            listView = (ListView) view.findViewById(R.id.listViewJobsStories);
            adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,titles);


            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), StoryViewer.class);
                    intent.putExtra("url",urls.get(position));
                    startActivity(intent);
                }
            });



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
