package com.example.android.pirateships;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecylerViewAdapter adapter;
    private ArrayList<Ship> shipList;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        shipList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
        parseData();
    }

    private void parseData() {
        String url = "https://assets.shpock.com/android/interview-test/pirateships";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("ships");

                            for (int idx = 0; idx < jsonArray.length(); idx++)
                            {
                                if(jsonArray.get(idx).equals(null)){
                                    //skip object
                                }
                                else{
                                    JSONObject object = jsonArray.getJSONObject(idx);

                                    int id = object.getInt("id");
                                    String title = object.getString("title");
                                    String imageURL = object.getString("image");
                                    String price = object.getString("price");

                                    if(title.equals("null"))
                                        title = "[no title]";

                                    shipList.add(new Ship(id, imageURL, title, price));
                                }
                            }

                            adapter = new RecylerViewAdapter(MainActivity.this, shipList);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }

}