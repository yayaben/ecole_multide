package com.myschool.tp2;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class SavedCompte extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_compte);

        //recuperer le parametre en passage
        String email = this.getIntent().getExtras().getString("email");
        //appel au preference
        SharedPreferences pref = getApplicationContext().getSharedPreferences(email, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        /**************** Get SharedPreferences data *******************/
        String nom=pref.getString("nom", null);         // getting nom

//afficher le Bienvenue nom user
        TextView text=(TextView)findViewById(R.id.msg_bienvenue);
        text.setText("Bienvenue "+nom);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_saved_compte, menu);
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
