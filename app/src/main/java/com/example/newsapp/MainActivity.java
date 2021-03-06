package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity {
public static ArrayList<String> titles;
 public  static ArrayList<String> urls;
 boolean bestStoriesFetched = false;
 boolean jobStoriesFetched = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label3));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.pager);
        final PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        if(savedInstanceState==null) {
            titles = new ArrayList<>();
            urls = new ArrayList<>();
            HelperClass.fetchDataStories(HelperClass.HN_NEW_STORIES_LINK);
        } else {
            bestStoriesFetched = savedInstanceState.getBoolean("bestStories");
            jobStoriesFetched = savedInstanceState.getBoolean("jobStories");
            titles = savedInstanceState.getStringArrayList("newTitles");
            urls = savedInstanceState.getStringArrayList("newUrls");
            NewStoriesFragment.adapter.notifyDataSetChanged();
        }

        //Setting listener to clicks
        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new
                                                   TabLayout.OnTabSelectedListener() {
                                                       @Override
                                                       public void onTabSelected(TabLayout.Tab tab) {
                                                           viewPager.setCurrentItem(tab.getPosition());
                                                           if(tab.getPosition()==1 && !bestStoriesFetched ) {
                                                               HelperClass.fetchDataStories(HelperClass.HN_TOP_STORIES_LINK);
                                                              bestStoriesFetched = !bestStoriesFetched;
                                                           } else if (tab.getPosition() == 2 && !jobStoriesFetched) {
                                                               HelperClass.fetchDataStories(HelperClass.HN_JOB_STORIES_LINK);
                                                               jobStoriesFetched = !jobStoriesFetched;
                                                           }
                                                       }

                                                       @Override
                                                       public void onTabUnselected(TabLayout.Tab tab) {
                                                       }

                                                       @Override
                                                       public void onTabReselected(TabLayout.Tab tab) {
                                                       }
                                                   });



    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("newTitles",titles);
        outState.putStringArrayList("newUrls",urls);
        outState.putBoolean("bestStories",bestStoriesFetched);
        outState.putBoolean("jobStories",jobStoriesFetched);
    }
}
