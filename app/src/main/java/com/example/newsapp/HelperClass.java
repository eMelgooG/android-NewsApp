package com.example.newsapp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HelperClass {
    private static final String L1 = "https://hacker-news.firebaseio.com/v0/item/";
    private static final String L3 = ".json?print=pretty";
    private static final String HN_NEW_STORIES_LINK = "https://hacker-news.firebaseio.com/v0/newstories.json?print=pretty";
    private static final String HN_TOP_STORIES_LINK = "https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty";

    public static void fetchDataNewStories() {
        ArrayList<String> result = new ArrayList<>();
        String data;
        String element;
        try {
            DownloadJsonData dataTask = new DownloadJsonData();
            data = dataTask.execute(HN_NEW_STORIES_LINK).get();

            JSONArray arr = new JSONArray(data);
            int numberOfResults = 20;
            for (int i = 0; i < numberOfResults; i++) {
                Integer jsonPart = arr.getInt(i);
                dataTask = new DownloadJsonData();
                element = dataTask.execute(L1 + jsonPart + L3).get();
                JSONObject currentObject = new JSONObject(element);

                if( currentObject.has("title") && currentObject.has("url")) {
                    MainActivity.titles.add(currentObject.getString("title"));
                    MainActivity.urls.add(currentObject.getString("url"));
                }
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void fetchDataBestStories() {
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
                    BestStoriesFragment.titles.add(currentObject.getString("title"));
                    BestStoriesFragment.urls.add(currentObject.getString("url"));
                }
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
    }
   static class DownloadJsonData extends AsyncTask<String, Void, String> {

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
