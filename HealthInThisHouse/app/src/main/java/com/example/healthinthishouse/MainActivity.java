package com.example.healthinthishouse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.healthinthishouse.databinding.FragmentAddRecipeBinding;
import com.example.healthinthishouse.databinding.FragmentRecipesBinding;
import com.example.healthinthishouse.databinding.FragmentSaveCaloriesBinding;
import com.example.healthinthishouse.databinding.FragmentSettingsBinding;
import com.example.healthinthishouse.ui.counter.CounterFragment;
import com.example.healthinthishouse.ui.counter.SaveCalories;
import com.example.healthinthishouse.ui.recipes.AddRecipesFragment;
import com.example.healthinthishouse.ui.settings.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.healthinthishouse.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    private ActivityMainBinding binding;
    private FragmentRecipesBinding FragRecipesBinding;
//    private FragmentSettingsBinding binding2;


    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("Signing out", "Here");
//                mAuth.signOut();
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//            }
//        });

        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        FragAddRecipebinding = FragmentAddRecipeBinding.inflate(getLayoutInflater());
        FragRecipesBinding = FragmentRecipesBinding.inflate(getLayoutInflater());

        FragmentSaveCaloriesBinding fragmentSaveCaloriesBinding = FragmentSaveCaloriesBinding.inflate(getLayoutInflater());

        LayoutInflater myLayoutInflater = getLayoutInflater();
        View FragRecipeView = myLayoutInflater.inflate(R.layout.fragment_recipes, null);

        //inflater


        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_calorieCounter, R.id.navigation_dashboard, R.id.navigation_recipes, R.id.navigation_bmi, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

//        FragRecipesBinding.addRecipeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("Add recipe button", "should change fragments");
//                AddRecipesFragment AddRecipesFragment = new AddRecipesFragment();
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.nav_host_fragment_activity_main, AddRecipesFragment);
//                ft.commit();
//            }
//        });

        FragRecipesBinding.addRecipeButton.setText("TEST");

    }


//    //TODO: for calorie
//    backBtn.setOnClickListener(new OnClickListener() {
//        public void onClick(View v)
//        {
//            //DO SOMETHING! {RUN SOME FUNCTION ... DO CHECKS... ETC}
//        }
//    });

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }

}