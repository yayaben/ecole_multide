package com.myschool.tp4;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class AddGroup extends Activity {

    Button add;
    EditText email1, email2,nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        //initalisation
        add=(Button)findViewById(R.id.button_add);
        nom=(EditText)findViewById(R.id.editText_name);
        email1=(EditText)findViewById(R.id.editText_email1);
        email2=(EditText)findViewById(R.id.editText_email2);

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                /*final String Objjson = "{\"name\": \"" + nom.getText().toString() + "\", \"emails\": \"[\" \"" + email1.getText().toString() + "\", \"" + email2.getText().toString() + "\" \"]\"}";
                JSONObject login = null;
                try {
                    login = new JSONObject(Objjson);
                } catch (JSONException e) {
                    System.out.println("erreur \n" + e.getCause() + "\n" + e.getMessage() + e.getLocalizedMessage());

                }*/



                StringRequest getRequest = new StringRequest (Request.Method.POST, "http://questioncode.fr:10007/api/groups",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // display response
                                Toast.makeText(AddGroup.this, response.toString(), Toast.LENGTH_LONG).show();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(AddGroup.this, "Erreur"+error.getMessage(), Toast.LENGTH_LONG).show();
                                System.out.println("-------- "+error.getMessage()+error.getLocalizedMessage()+error.getCause()+error.getStackTrace());
                            }
                        }
                ) {


                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("name", nom.getText().toString());
                        params.put("emails",email1.getText().toString());
                        return params;
                    }



                };

                RequestQueue queue = Volley.newRequestQueue(AddGroup.this);
                queue.add(getRequest);


            }


        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_group, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
