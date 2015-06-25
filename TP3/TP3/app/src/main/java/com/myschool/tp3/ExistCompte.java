package com.myschool.tp3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class ExistCompte extends Activity {

    EditText email,pw;
    Button compte;
    String res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exist_compte);

        //initialisation
        email=(EditText)findViewById(R.id.editText_exist_mail);
        pw=(EditText)findViewById(R.id.editText_exist_pw);
        compte=(Button)findViewById(R.id.button_verif_exist);

        //verifier le  compte user existe ou non : bouton j'ai déja un compte
        compte.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                //creation Request Payload
                final String Objjson = "{\"email\": \"" + email.getText().toString() + "\", \"password\": \"" + pw.getText().toString() + "\"}";
               //initialisation de l'objet json
                JSONObject login = null;
                try {
                    login = new JSONObject(Objjson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //faire un request avec la method post.
                JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, "http://questioncode.fr:10007/auth/local", login,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // display la response
                                res=response.toString();// le token en retour
                                //save token dans shares preferences
                                SharedPreferences pref = getApplicationContext().getSharedPreferences(email.getText().toString(), MODE_PRIVATE);

                                SharedPreferences.Editor editor = pref.edit();
                                String nom = pref.getString("nom", null);
                                editor.putString("token", res);  // Saving token
                                editor.putString("nom", nom);  // Saving nom
                                // Save the changes in SharedPreferences
                                editor.commit(); // commit changes
                                //afficher l'activité SavedCompte
                                Intent intent = new Intent(ExistCompte.this, SavedCompte.class);
                                intent.putExtra("email", email.getText().toString());
                                startActivity(intent);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // erreur l’email ou le mot de passe sont incorrects
                                AlertDialog.Builder builder = new AlertDialog.Builder(ExistCompte.this);
                                builder.setMessage("l’email ou le mot de passe sont incorrects");
                                builder.setCancelable(false);
                                builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                AlertDialog alert = builder.create();
                                alert.show();

                            }
                        }
                ) {
                };

                RequestQueue queue = Volley.newRequestQueue(ExistCompte.this);
                queue.add(getRequest);



            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exist_compte, menu);
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
