package com.myschool.tp3;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;


public class MainActivity extends Activity {

    Button new_c,old_c;
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialisation
        new_c = (Button)findViewById(R.id.new_compte);
        old_c= (Button)findViewById(R.id.existe_compte);
        email=(TextView)findViewById(R.id.textView_email);


        //verifier si le sharedperefrence exist
        File f = new File("/data/data/com.myschool.tp3/shared_prefs/ACTIVE_USER.xml");
        if (f.exists()){

            //si existe affichage de l'email de l'utilisateur
            email.setVisibility(View.VISIBLE);
            SharedPreferences pref = getApplicationContext().getSharedPreferences("ACTIVE_USER", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            //Get SharedPreferences data
            String mail=pref.getString("email_user", null);         // getting email

            email.setText("votre email est : "+mail);
        }
        else{


            //si non afficher les deux boutons
            new_c.setVisibility(View.VISIBLE);
            old_c.setVisibility(View.VISIBLE);

        }

        //afficher nouvelle activity newCompte.
        new_c.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewCompte.class);
                startActivity(intent);
            }
        });


        //afficher nouvelle activity existCompte.
        old_c.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ExistCompte.class);
                startActivity(intent);
            }
        });
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

        //menu deconnexion
        if (id == R.id.action_settings) {

            //supprimer SharedPreferences ACTIVE_USER et retour à l'activité login

            File f = new File("/data/data/com.myschool.tp3/shared_prefs/ACTIVE_USER.xml");
            f.delete();
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
