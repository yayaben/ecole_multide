package com.myschool.tp2;

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


public class ExistCompte extends Activity {

    EditText email,pw;
    Button compte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exist_compte);
        //initialisation
        email=(EditText)findViewById(R.id.editText_exist_mail);
        pw=(EditText)findViewById(R.id.editText_exist_pw);
        compte=(Button)findViewById(R.id.button_verif_exist);
        //verifier l'existance du compte user
        compte.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences(email.getText().toString(), MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                String motdepasse=pref.getString("pw", null);  // get pw

                //si email et mot de passe correcte passe vers l'activité SavedCompte
                if(pw.getText().toString().matches(motdepasse)){
                    Intent intent = new Intent(ExistCompte.this, SavedCompte.class);
                    intent.putExtra("email", email.getText().toString());
                    startActivity(intent);
                }
                //si non message d'erreur.
                else{


                    AlertDialog.Builder builder= new AlertDialog.Builder(ExistCompte.this);
                    builder.setMessage("l’email ou le mot de passe sont incorrects");
                    builder.setCancelable(false);
                    builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert=builder.create();
                    alert.show();
                }
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
