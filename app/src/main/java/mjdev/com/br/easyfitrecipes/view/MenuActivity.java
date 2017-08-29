package mjdev.com.br.easyfitrecipes.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import mjdev.com.br.easyfitrecipes.R;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView tvOptBreak = (TextView)findViewById(R.id.tv_optionbreakfast);
        TextView tvOptLunch = (TextView)findViewById(R.id.tv_optionlunch);
        TextView tvOptDinner = (TextView)findViewById(R.id.tv_optiondinner);
        TextView tvOptDessert = (TextView)findViewById(R.id.tv_optiondessert);

        tvOptBreak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(),SubmenuActivity.class);
                myIntent.putExtra("option","1");
                startActivity(myIntent);
            }
        });
        tvOptLunch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(),SubmenuActivity.class);
                myIntent.putExtra("option","2");
                startActivity(myIntent);
            }
        });
        tvOptDinner.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(),SubmenuActivity.class);
                myIntent.putExtra("option","3");
                startActivity(myIntent);
            }
        });
        tvOptDessert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(),SubmenuActivity.class);
                myIntent.putExtra("option","4");
                startActivity(myIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent myIntent = null;
        boolean flagCloseApp = false;
        AlertDialog alerta;

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            myIntent = new Intent(getApplicationContext(),MenuActivity.class);
        } else if (id == R.id.nav_createrecipe) {
            myIntent = new Intent(getApplicationContext(),CreateRecipeActivity.class);
        } else if (id == R.id.nav_maps) {
            myIntent = new Intent(getApplicationContext(),MapsActivity.class);
        } else if (id == R.id.nav_share) {
            myIntent = new Intent(getApplicationContext(),ShareActivity.class);
        } else if (id == R.id.nav_about) {
            myIntent = new Intent(getApplicationContext(),AboutActivity.class);
        } else if (id == R.id.nav_exit) {
            flagCloseApp = true;
        }

        if (flagCloseApp){
            //Cria o gerador do AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //define o titulo
            builder.setTitle(R.string.exit_title);
            //define a mensagem
            builder.setMessage(R.string.exit_message);
            //define um botão como positivo

            builder.setPositiveButton(R.string.exit_yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    Log.d("*****TEST***", "setPositiveButton yes");
                    SharedPreferences sharedPref = getSharedPreferences("appSharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();

                    editor.putString("user", "");
                    editor.putString("pass", "");
                    editor.putBoolean("flag", false);
                    editor.apply();

                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                }
            });
            //define um botão como negativo.
            builder.setNegativeButton(R.string.exit_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {

                }
            });
            //cria o AlertDialog
            alerta = builder.create();
            //Exibe
            alerta.show();
        }else
        {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(myIntent);
        }

//        finish();

        return true;
    }

}
