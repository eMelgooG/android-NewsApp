package com.example.newsapp;

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
private static final String HN_TOP_STORIES_LINK = "https://hacker-news.firebaseio.com/v0/newstories.json?print=pretty";
private static final String L1 = "https://hacker-news.firebaseio.com/v0/item/";
private static final String L3 = ".json?print=pretty";
public static ArrayList<String> titles;
 public   static ArrayList<String> urls;

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

    titles = new ArrayList<>();
    urls = new ArrayList<>();
        fetchData();



        //Setting listener to clicks
        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new
                                                   TabLayout.OnTabSelectedListener() {
                                                       @Override
                                                       public void onTabSelected(TabLayout.Tab tab) {
                                                           viewPager.setCurrentItem(tab.getPosition());
                                                       }

                                                       @Override
                                                       public void onTabUnselected(TabLayout.Tab tab) {
                                                       }

                                                       @Override
                                                       public void onTabReselected(TabLayout.Tab tab) {
                                                       }
                                                   });
    }



    public void fetchData() {
        ArrayList<String> result = new ArrayList<>();
        String data;
        String element;
        try {
            DownloadJsonData dataTask = new DownloadJsonData();
            data = dataTask.execute(HN_TOP_STORIES_LINK).get();

            JSONArray arr = new JSONArray(data);
            int numberOfResults = 20;
            for (int i = 0; i < numberOfResults; i++) {
                Integer jsonPart = arr.getInt(i);
                dataTask = new DownloadJsonData();
                element = dataTask.execute(L1 + jsonPart + L3).get();
                JSONObject currentObject = new JSONObject(element);

                if( currentObject.has("title") && currentObject.has("url")) {
                    titles.add(currentObject.getString("title"));
                    urls.add(currentObject.getString("url"));
                }
                }


        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    class DownloadJsonData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder("");
            URL url;
            HttpURLConnection connection = null;

            try {
              url = new URL(urls[0]);
                connection =(HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data!=-1) {
                    char current = (char) data;
                    result.append(current);
                    data = reader.read();
                }

                return result.toString();


            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
