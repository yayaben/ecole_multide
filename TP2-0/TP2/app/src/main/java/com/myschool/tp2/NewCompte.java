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
import android.widget.Toast;


public class NewCompte extends Activity {

    EditText nom,email,pw;
    Button newUser;
    Boolean verif_nom=false,verif_email=false,verif_pw=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_compte);

        //initialisation des champs de texte et bouton
        nom=(EditText)findViewById(R.id.editText_nom);
        email=(EditText)findViewById(R.id.editText_email);
        pw=(EditText)findViewById(R.id.editText_pw);
        newUser=(Button)findViewById(R.id.button_save);




//bouton creer user
        newUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //si champ n'est pas vide
                if(!nom.getText().toString().matches(""))
                    verif_nom=true;
                //si non afficher message d'erreur
                else{
                    AlertDialog.Builder builder= new AlertDialog.Builder(NewCompte.this);
                    builder.setMessage("Le champ nom ne doit pas etre vide!");
                    builder.setCancelable(false);
                    builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert=builder.create();
                    alert.show();
                }


                if(!email.getText().toString().matches(""))
                    verif_email=true;
                else{
                    AlertDialog.Builder builder= new AlertDialog.Builder(NewCompte.this);
                    builder.setMessage("Le champ email ne doit pas etre vide!");
                    builder.setCancelable(false);
                    builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert=builder.create();
                    alert.show();
                }

                if(!pw.getText().toString().matches(""))
                    verif_pw=true;
                else{
                    AlertDialog.Builder builder= new AlertDialog.Builder(NewCompte.this);
                    builder.setMessage("Le champ mot de passe ne doit pas etre vide!");
                    builder.setCancelable(false);
                    builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert=builder.create();
                    alert.show();
                }

// si tous les champs differents de vide
                if(verif_nom==true && verif_pw==true && verif_email==true){
                    //Toast.makeText(getApplicationContext(), "ok......", Toast.LENGTH_LONG).show();
                    /******* Create SharedPreferences avec comme clé l'email*******/
                    SharedPreferences pref = getApplicationContext().getSharedPreferences(email.getText().toString(), MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("nom", nom.getText().toString());  // Saving nom
                    editor.putString("email", email.getText().toString());  // Saving email
                    editor.putString("pw", pw.getText().toString());  // Saving pw
                    // Save the changes in SharedPreferences
                    editor.commit(); // commit changes


                    /******* Create SharedPreferences CURRENT_LOGIN*******/
                    SharedPreferences pref2 = getApplicationContext().getSharedPreferences("CURRENT_LOGIN", MODE_PRIVATE);
                    SharedPreferences.Editor editor2 = pref2.edit();
                    editor2.putString("email_user", email.getText().toString());  // Saving email
                    // Save the changes in SharedPreferences CURRENT_LOGIN
                    editor2.commit(); // commit changes

                    //passage vers la nouvelle activité saved compte avec comme paramétre l'email de l'utilisateur.
                    Intent intent = new Intent(NewCompte.this, SavedCompte.class);
                    intent.putExtra("email", email.getText().toString());
                    startActivity(intent);
                }
            }
        });
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
