package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
private static final String HN_TOP_STORIES_LINK = "https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty";
private static final String L1 = "https://hacker-news.firebaseio.com/v0/item/";
private static final String L3 = ".json?print=pretty";
ArrayList<String> titles;
    ArrayList<String> urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.listView);
    titles = new ArrayList<>();
    urls = new ArrayList<>();
        fetchData();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,titles);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, StoryViewer.class);
                intent.putExtra("url",urls.get(position));
                startActivity(intent);
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