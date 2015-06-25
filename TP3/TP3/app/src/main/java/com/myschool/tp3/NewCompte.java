package com.myschool.tp3;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class NewCompte extends Activity {

    EditText nom,email,pw;
    Button newUser;
    Boolean verif_nom=false,verif_email=false,verif_pw=false,verif_server=false,verif_internet=false;
    String res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_compte);

        //initialisation des champs de texte et bouton
        nom=(EditText)findViewById(R.id.editText_nom);
        email=(EditText)findViewById(R.id.editText_email);
        pw=(EditText)findViewById(R.id.editText_pw);
        newUser=(Button)findViewById(R.id.button_save);





         //creer user
        newUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //verifier les champs sont differents de vide
                if (!nom.getText().toString().matches(""))
                    verif_nom = true;
                //si non afficher message d'erreur
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewCompte.this);
                    builder.setMessage("Le champ nom ne doit pas etre vide!");
                    builder.setCancelable(false);
                    builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }


                if (!email.getText().toString().matches(""))
                    verif_email = true;
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewCompte.this);
                    builder.setMessage("Le champ email ne doit pas etre vide!");
                    builder.setCancelable(false);
                    builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }

                if (!pw.getText().toString().matches(""))
                    verif_pw = true;
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewCompte.this);
                    builder.setMessage("Le champ mot de passe ne doit pas etre vide!");
                    builder.setCancelable(false);
                    builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }

                //si tous les champs sont different de vide
                if (verif_nom == true && verif_pw == true && verif_email == true) {

                    //verifier la connecion au serveur si ok
                    if (Boolean.toString(isConnectedToServer()).equals("true"))
                        verif_server = true;
                    //si non afficher message d'erreur
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(NewCompte.this);
                        builder.setMessage("Serveur non accessible!");
                        builder.setCancelable(false);
                        builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                    //verifier votre connexion ineternet
                    if (Boolean.toString(isOnline()).equals("true"))
                        verif_internet = true;
                    //si non message d'erreur
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(NewCompte.this);
                        builder.setMessage("Réseau non accessible!");
                        builder.setCancelable(false);
                        builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }

                   //Toast.makeText(NewCompte.this, Boolean.toString(isConnectedToServer()), Toast.LENGTH_LONG).show();
                    //Toast.makeText(NewCompte.this, Boolean.toString(isOnline()), Toast.LENGTH_LONG).show();

                    //si serveur accessible et connexion existe alors création du compte user
                    if(verif_internet.equals(true) && verif_server.equals(true)) {
                        //creation Request Payload
                        final String Objjson = "{\"name\": \"" + nom.getText().toString() + "\", \"email\": \"" + email.getText().toString() + "\", \"password\": \"" + pw.getText().toString() + "\"}";
                        JSONObject login = null;
                        try {
                            login = new JSONObject(Objjson);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, "http://questioncode.fr:10007/api/users", login,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // display response
                                        res=response.toString();


                                        //save token and nom user shares preferences

                                        SharedPreferences pref = getApplicationContext().getSharedPreferences(email.getText().toString(), MODE_PRIVATE);
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("nom", nom.getText().toString());  // Saving nom
                                        editor.putString("token", res);  // Saving token
                                        editor.putString("pw", pw.getText().toString());  // Saving pw
                                        // Save the changes in SharedPreferences
                                        editor.commit(); // commit changes


                                        /******* Create SharedPreferences ACTIVE_USER*******/
                                        SharedPreferences pref2 = getApplicationContext().getSharedPreferences("ACTIVE_USER", MODE_PRIVATE);
                                        SharedPreferences.Editor editor2 = pref2.edit();
                                        editor2.putString("email_user", email.getText().toString());  // Saving email
                                        // Save the changes in SharedPreferences ACTIVE_USER
                                        editor2.commit(); // commit changes

                                        Intent intent = new Intent(NewCompte.this, SavedCompte.class);
                                        intent.putExtra("email", email.getText().toString());
                                        startActivity(intent);


                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(NewCompte.this, "utilisateur déja existant", Toast.LENGTH_LONG).show();
                                    }
                                }
                        ) {
                        };

                        RequestQueue queue = Volley.newRequestQueue(NewCompte.this);
                        queue.add(getRequest);

                    }
                }
            }
        });
    }

    //verfier la connexion au serveur
    public boolean isConnectedToServer() {
        try {
            URL myUrl = new URL("http://questioncode.fr:10007/api/users");
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(30 * 1000);
            connection.connect();
            return true;
        } catch (Exception e) {
            // Handle your exceptions
            return false;
        }
    }

    //verifier la connexion internet
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_compte, menu);
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
