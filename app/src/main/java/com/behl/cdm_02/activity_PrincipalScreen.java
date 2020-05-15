package com.behl.cdm_02;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

public class activity_PrincipalScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_screen);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.principal_screen_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Log.i("PRINCIPALSCREEN_TRUE", "_ONMENUOPTIONSSELECTED = IN");

        switch(item.getItemId()){
            case R.id.sair_option:
                Log.i("PRINCIPALSCREEN_TRUE", "_ONMENUOPTIONSSELECTED_ID = PRINCIPAL_SCREEN_MENU__SAIR_OPTION");
                sair();
        }
        return super.onOptionsItemSelected(item);
    }

    private void sair(){
        ParseUser.getCurrentUser();
        ParseUser.logOut();


        Intent intent = new Intent(getApplicationContext(), activity_LoginScreen.class);
        startActivity(intent);
    }
}
