package com.example.android.pirateships;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity{
    private RequestQueue requestQueue;
    private int objectId;
    private TextView titleView, priceView, descriptionView;
    private String title, price, description, imageURL, greetingType;
    private ImageView imageView;
    private GreetingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().hide();

        Toolbar toolbar = findViewById(R.id.details_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        objectId = getIntent().getIntExtra("ID", 0);
        title = getIntent().getStringExtra("TITLE");
        price = getIntent().getStringExtra("PRICE");
        imageURL = getIntent().getStringExtra("imageURL");

        titleView = findViewById(R.id.detail_view_title);
        priceView = findViewById(R.id.detail_view_price);
        imageView = findViewById(R.id.detail_view_image);
        descriptionView = findViewById(R.id.detail_view_description);

        titleView.setText(title);
        priceView.setText("$ " + price);
        Picasso.with(DetailsActivity.this).load(imageURL).fit().centerInside().into(imageView);

        dialog = new GreetingDialog();

        requestQueue = Volley.newRequestQueue(this);
        parseData();

        Button dialogButton = findViewById(R.id.dialog_button);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show(getSupportFragmentManager(), "greeting");
            }
        });


    }

    private void parseData() {
        String url = "https://assets.shpock.com/android/interview-test/pirateships";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("ships");

                            for (int idx = 0; idx < jsonArray.length(); idx++) {

                                if(jsonArray.get(idx).equals(null)) {
                                    //skip object
                                } else {
                                    JSONObject object = jsonArray.getJSONObject(idx);
                                    if(object.getInt("id") == objectId) {
                                        description = object.getString("description");
                                        descriptionView.setText(description);

                                        if(object.has("greeting_type") && !object.get("greeting_type").equals(null)) {
                                            greetingType = object.getString("greeting_type");
                                            dialog.setGreetingTyp(greetingType);
                                        } else {
                                            dialog.setGreetingTyp("never greets");
                                        }

                                        idx = jsonArray.length();
                                    } else {
                                        //verify next object
                                    }
                                }
                            }
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
