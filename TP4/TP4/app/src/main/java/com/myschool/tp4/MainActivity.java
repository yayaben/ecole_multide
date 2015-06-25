package com.myschool.tp4;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity {
    Button btn_user,btn_group;
    TextView response_text;
    // Progress dialog
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialisation
        btn_user=(Button)findViewById(R.id.btn_user);
        btn_group=(Button)findViewById(R.id.btn_group);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        response_text=(TextView)findViewById(R.id.txtResponse);

        //
        btn_user.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                makeJsonObjectRequest();

            }
        });


        btn_group.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            makeJsonArrayRequest();
            }
        });

    }


    /**
     * Method to make json object request where json response starts wtih {
     * */
    private void makeJsonObjectRequest() {

        showpDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                "http://questioncode.fr:10007/auth/local", null,
                new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("", response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    /*String id = response.getString("_id");
                    String provider = response.getString("provider");
                    String name = response.getString("name");
                    String email = response.getString("email");
                    String v = response.getString("_v");
                    String role = response.getString("role");

                    String jsonResponse = "";
                    jsonResponse += "Name: " + name + "\n\n";
                    jsonResponse += "Email: " + email + "\n\n";
                    jsonResponse += "id: " + id + "\n\n";
                    jsonResponse += "provider: " + provider + "\n\n";
                    jsonResponse += "_v: " + provider + "\n\n";
                    jsonResponse += "role: " + provider + "\n\n";



                    response_text.setText(jsonResponse);*/
                    response_text.setText(response.toString());
                } catch (Exception e) {//JSONException
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error1: " + e.getMessage()+e.getStackTrace(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Erreur2 "+error.getMessage()+error.getStackTrace(), Toast.LENGTH_LONG).show();
                // hide the progress dialog
                hidepDialog();
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

              // System.out.println("get header");
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "Bearer"+"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJfaWQiOiI1NTgyODJlZWNmOTNjYjE0MDBkZ"+
                        "DY0YTEiLCJpYXQiOjE0MzQ2MTY1NTgyMDIsImV4cCI6MTQzNDYzNDU1ODIwMn0.sxKtN"+
                        "RmlxTs_PMCwAIxdR-gwumTDjQgQ64QzEmfJ77Y");
               // headers.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJfaWQiOiI1NTgyODJlZWNmOTNjYjE0MDBkZ"+
                     //   "DY0YTEiLCJpYXQiOjE0MzQ2MTY1NTgyMDIsImV4cCI6MTQzNDYzNDU1ODIwMn0.sxKtN"+
                      //  "RmlxTs_PMCwAIxdR-gwumTDjQgQ64QzEmfJ77Y");

                return headers;
            }
        };


        // Adding request to request queue
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(jsonObjReq);
    }


    /**
     * Method to make json array request where response starts with [
     * */
    private void makeJsonArrayRequest() {

        showpDialog();

        JsonArrayRequest req = new JsonArrayRequest("http://api.androidhive.info/volley/person_array.json",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("", response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object
                            String jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject person = (JSONObject) response
                                        .get(i);

                                //jsonResponse += person.toString() + "\n\n";

                                String name = person.getString("name");
                                String email = person.getString("email");
                                JSONObject phone = person
                                        .getJSONObject("phone");
                                String home = phone.getString("home");
                                String mobile = phone.getString("mobile");

                                jsonResponse += "Name: " + name + "\n\n";
                                jsonResponse += "Email: " + email + "\n\n";
                                jsonResponse += "Home: " + home + "\n\n";
                                jsonResponse += "Mobile: " + mobile + "\n\n\n";

                            }

                            response_text.setText(jsonResponse);
                            //response_text.setText(jsonResponse);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

        // Adding request to request queue
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(req);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(MainActivity.this, AddGroup.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
