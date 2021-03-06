package com.app.travelapp.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.travelapp.R;
import com.app.travelapp.data.datasources.DataSourceCache;
import com.app.travelapp.data.datasources.DataSourceFirebase;
import com.app.travelapp.data.datasources.Session;
import com.app.travelapp.data.repositories.UserRepository;
import com.app.travelapp.ui.auth.login.LoginActivity;
import com.app.travelapp.ui.creditos.CreditosActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_add, R.id.navigation_profile)
                .build();
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.main_menu_action_logout:
                setResult(Activity.RESULT_OK);
                finish();
                Session.logout();
                intent = new Intent(this, LoginActivity.class);
                Toast.makeText(this, "See you soon!!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case R.id.main_menu_action_credits:
                intent = new Intent(this, CreditosActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}
