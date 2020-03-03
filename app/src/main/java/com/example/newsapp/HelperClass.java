package com.example.newsapp;

import android.os.AsyncTask;
import android.util.Log;

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
     static final String HN_NEW_STORIES_LINK = "https://hacker-news.firebaseio.com/v0/newstories.json?print=pretty";
     static final String HN_TOP_STORIES_LINK = "https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty";
     static final String HN_JOB_STORIES_LINK = "https://hacker-news.firebaseio.com/v0/jobstories.json?print=pretty";

    public static void fetchDataStories(String link) {
        ArrayList<String> result = new ArrayList<>();
        String data;
        String element;
        try {
            DownloadJsonData dataTask = new DownloadJsonData();
            data = dataTask.execute(link).get();

            JSONArray arr = new JSONArray(data);
            int numberOfResults = 20;
            for (int i = 0; i < numberOfResults; i++) {
                Integer jsonPart = arr.getInt(i);

                if(link.equals(HN_NEW_STORIES_LINK)) {
                    DownloadJsonDataNew  dataT = new DownloadJsonDataNew();
                    dataT.execute(L1 + jsonPart + L3);
                } else if(link.equals(HN_TOP_STORIES_LINK)) {
                    DownloadJsonDataTop  dataT = new DownloadJsonDataTop();
                    dataT.execute(L1 + jsonPart + L3);
                } else {
                    DownloadJsonDataJob   dataT = new DownloadJsonDataJob();
                    dataT.execute(L1 + jsonPart + L3);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    static class DownloadJsonDataNew extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject currentObject = new JSONObject(s);

                if (currentObject.has("title") && currentObject.has("url")) {
                    MainActivity.titles.add(currentObject.getString("title"));
                    MainActivity.urls.add(currentObject.getString("url"));
                }
                NewStoriesFragment.adapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.i("Fatal error: ", e.getMessage());
            }

        }

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

    static class DownloadJsonDataTop extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject currentObject = new JSONObject(s);

                if (currentObject.has("title") && currentObject.has("url")) {
                    BestStoriesFragment.titles.add(currentObject.getString("title"));
                    BestStoriesFragment.urls.add(currentObject.getString("url"));
                }
                BestStoriesFragment.adapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.i("Fatal error: ", e.getMessage());
            }

        }
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

    static class DownloadJsonDataJob extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject currentObject = new JSONObject(s);

                if (currentObject.has("title") && currentObject.has("url")) {
                    JobsStoriesFragment.titles.add(currentObject.getString("title"));
                    JobsStoriesFragment.urls.add(currentObject.getString("url"));
                }
                JobsStoriesFragment.adapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.i("Fatal error: ", e.getMessage());
            }

        }
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
