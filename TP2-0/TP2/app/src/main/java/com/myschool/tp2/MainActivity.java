package com.myschool.tp2;

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
import android.widget.Toast;

import java.io.File;


public class MainActivity extends Activity {

    Button new_c,old_c;
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialisation des boutons et le champ texte
        new_c = (Button)findViewById(R.id.new_compte);
        old_c= (Button)findViewById(R.id.existe_compte);
        email=(TextView)findViewById(R.id.textView_email);
        //verifier si le sharedperefrence exist
       File f = new File("/data/data/com.myschool.tp2/shared_prefs/CURRENT_LOGIN.xml");
        if (f.exists()){
            //existe on va afficher l'email de l'utilsateur
            email.setVisibility(View.VISIBLE);
            SharedPreferences pref = getApplicationContext().getSharedPreferences("CURRENT_LOGIN", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            //Get SharedPreferences data
            String mail=pref.getString("email_user", null);         // getting email

            email.setText("votre email est : "+mail);
        }
        //n'existe pas afficher les 2 boutons.
        else{


            new_c.setVisibility(View.VISIBLE);
            old_c.setVisibility(View.VISIBLE);

       }
        //cliquer sur le bouton creer compte : afficher nouvelle activity newCompte.
        new_c.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewCompte.class);
                startActivity(intent);
            }
        });


        //cliquer sur le bouton j'ai déja un compte : afficher nouvelle activity existCompte.
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

            //supprimer SharedPreferences CURRENT_LOGIN et réafficher les deux boutons.

            File f = new File("/data/data/com.myschool.tp2/shared_prefs/CURRENT_LOGIN.xml");
            f.delete();
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

   //gestion du fleche retour pour quitter l'application.
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.isTracking()
                && !event.isCanceled()) {
            //supprimer le fichier SharedPreferences CURRENT_LOGIN.
            File f = new File("/data/data/com.myschool.tp2/shared_prefs/CURRENT_LOGIN.xml");
            f.delete();
            //quitter l'application.
            System.exit(0);
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
