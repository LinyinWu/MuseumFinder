package com.example.linyin.museumfinder;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class SearchActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String url = getIntent().getStringExtra("url");
        new Search().execute(url);
    }

    public class Search extends AsyncTask<String, Void, String> {

        /* to set up connection with Internet and using Places API to search museums in specific city*/
        @Override
        protected String doInBackground (String... params) {
            HttpClient client = new DefaultHttpClient();     //set up connection
            HttpGet httpGet = new HttpGet(params[0]);
            StringBuffer replyBuffer = new StringBuffer();
            try {
                HttpResponse response = client.execute(httpGet);     //make a query with given url
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line = "";
                while((line = bufferedReader.readLine()) != null ) {
                    replyBuffer.append(line);
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
            String replyStr = replyBuffer.toString();
            return replyStr;
        }

        /* handle the replyStr which is JSON format to get useful information which we need*/
        @Override
        protected void onPostExecute(String result) {
            final ArrayList<Museum> museums = new ArrayList<>();
            ArrayList<String> museumList = new ArrayList<>();

            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.has("results")) {
                    JSONArray resultsArray = jsonObject.getJSONArray("results");
                    for (int i=0; i<resultsArray.length(); i++) {
                        JSONObject oneResult = resultsArray.getJSONObject(i);

                        Museum museum = new Museum();
                        museum.name = oneResult.getString("name");
                        museum.address = oneResult.getString("formatted_address");
                        JSONObject location = oneResult.getJSONObject("geometry").getJSONObject("location");

                        museum.lat = location.getString("lat");
                        museum.lng = location.getString("lng");

                        museums.add(museum);
                        museumList.add(museum.name+":\t\t"+museum.address);
                    }
                }
            }catch(Exception e) {
                e.printStackTrace();
            }

            String[] museumStr = new String[museumList.size()];
            museumStr = museumList.toArray(museumStr);

            /* configure each row in the ListView */
            ArrayAdapter<String> adapter= new ArrayAdapter<> (getApplicationContext(),R.layout.single_row,R.id.textView,museumStr);
            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);

            /* when click one row, going to call MapActivity */
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Museum m = museums.get(position);
                    String name = m.name;
                    String lat = m.lat;
                    String lng = m.lng;

                    Intent i = new Intent(getApplicationContext(),MapActivity.class);
                    i.putExtra("name",name);
                    i.putExtra("lat",lat);
                    i.putExtra("lng",lng);
                    startActivity(i);
                }
            });

        }
    }


}
