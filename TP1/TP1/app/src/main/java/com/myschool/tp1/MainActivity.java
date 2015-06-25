package com.myschool.tp1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {


    Button btn_fr,btn_en;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //un toast affichant le message :  Demarrage.
        Toast.makeText(getApplicationContext(), R.string.Demarrage, Toast.LENGTH_LONG).show();

        btn_fr = (Button)findViewById(R.id.button_fr);
        //message affiche   Bonjour  ! . en utlisant le fichier String.xml
        btn_fr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //R.string.message_fr : appel au fichier String.xml
                Toast.makeText(getApplicationContext(), R.string.message_fr, Toast.LENGTH_LONG).show();

            }
        });

        btn_en = (Button)findViewById(R.id.button_en);
        //message affiche   Hello ! . en utlisant le fichier String.xml
        btn_en.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //R.string.message_en :appel au fichier String.xml
                Toast.makeText(getApplicationContext(), R.string.message_en, Toast.LENGTH_LONG).show();

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

        //menu sortir
        if (id == R.id.action_settings) {
            //creation fenetre avec 2 bouton pour quitter l'application
            AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(R.string.msg);
            builder.setCancelable(false);

            // si oui on va quitter
            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    //code pour quitter
                    MainActivity.this.finish();
                }
            });

            // si non la fenetre se cache
            builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {


                    dialog.cancel();
                }
            });
            AlertDialog alert=builder.create();
            alert.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
