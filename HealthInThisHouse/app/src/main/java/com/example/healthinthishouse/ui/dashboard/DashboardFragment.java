package com.example.healthinthishouse.ui.dashboard;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthinthishouse.R;
import com.example.healthinthishouse.calorieTracker;
import com.example.healthinthishouse.databinding.FragmentDashboardBinding;
import com.example.healthinthishouse.exerciseTracker;
import com.example.healthinthishouse.itemData;
import com.example.healthinthishouse.ui.counter.CalorieDBSearch;
import com.example.healthinthishouse.waterTracker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String TAG = "DashboardFragment";
    private static int totalcalorie;
    private static int totalwater;
    private static int totalexercise;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);  // binds to dashboard fragment
        View root = binding.getRoot();
        TextView waterAmt = binding.waterAmt;
        TextView exerciseAmt = binding.exerciseAmt;
        totalexercise=0;
        totalwater = 0;
        totalcalorie = 0;
        binding.TotalCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calorieTracker calorieTracker = new calorieTracker();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment_activity_main,calorieTracker);
                ft.commit();
            }
        });
        binding.TotalWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waterTracker waterTracker = new waterTracker();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment_activity_main, waterTracker);
                ft.commit();
            }
        });

        binding.TotalExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exerciseTracker exerciseTracker = new exerciseTracker();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment_activity_main, exerciseTracker);
                ft.commit();
            }
        });

        TextView dashboardCalories = binding.dashboardCalories;

        readData(new FirestoreCallback() {
            @Override
            public void onCallback(int totalcalorie) {
                Log.d(TAG, "onCallBack: read data from db");
                dashboardCalories.setText(Integer.valueOf(totalcalorie).toString() + " cal");

                readWater(new FirestoreCallback() {
                    @Override
                    public void onCallback(int totalwater){
                        waterAmt.setText(Integer.valueOf(totalwater).toString() + " ml");
                    }
                });
            }
        });

        TextView dashboardExercise = binding.exerciseAmt;

        readData(new FirestoreCallback() {
            @Override
            public void onCallback(int totalexercise) {
                Log.d(TAG, "onCallBack: read data from db");
                dashboardExercise.setText("You have exercised" + " " + Integer.valueOf(totalexercise).toString()  + "x/ Week");

                readExercise(new FirestoreCallback() {
                    @Override
                    public void onCallback(int totalexercise){
                        exerciseAmt.setText("You have exercised" + " " + Integer.valueOf(totalexercise).toString() + "x/ Week");
                    }
                });
            }
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private interface FirestoreCallback {
        void onCallback(int dailycalorie);
    }

    private void readData(FirestoreCallback firestoreCallback){
        db.collection("caloriesConsumed")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()){
                            totalcalorie = 0;
                            Log.d(TAG, "Successful retrieval of caloriesConsumed Table");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Integer itemCalories = Integer.valueOf(document.get("calorieIntake").toString());
                                totalcalorie += itemCalories;
                                Log.d(TAG, "onComplete: Retrieved Tuple. calorieIntake: " + itemCalories);
                            }
                            Log.d(TAG,"onComplete: final totalCalarie retrieved from db - " + totalcalorie);
                            firestoreCallback.onCallback(totalcalorie);
                        }else {

                            Log.d(TAG, "Unsuccessful retrieval of Calorie Database Search Table");
                        }
                    }
                });
    }

    private void readWater(FirestoreCallback firestoreCallback){
        db.collection("WaterTracker")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()){
                            Log.d(TAG, "Successful retrieval of caloriesConsumed Table");
                            totalwater = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Integer itemWater = Integer.valueOf(document.get("waterAmount").toString());
                                totalwater += itemWater;
                                Log.d(TAG, "onComplete: Retrieved Tuple. waterIntake: " + itemWater);
                            }
                            Log.d(TAG,"onComplete: final totalwater retrieved from db - " + totalwater);
                            firestoreCallback.onCallback(totalwater);
                        }else {

                            Log.d(TAG, "Unsuccessful retrieval of water Database Search Table");
                        }
                    }
                });
    }

    private void readExercise(FirestoreCallback firestoreCallback){
        db.collection("ExerciseTotal")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()){
                            Log.d(TAG, "Successful retrieval of caloriesConsumed Table");
                            totalexercise = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Integer itemExercise = Integer.valueOf(document.get("frequency").toString());
                                totalexercise += itemExercise;
                                Log.d(TAG, "onComplete: Retrieved Tuple. waterIntake: " + itemExercise);
                            }
                            Log.d(TAG,"onComplete: final totalwater retrieved from db - " + totalexercise);
                            firestoreCallback.onCallback(totalexercise);
                        }else {

                            Log.d(TAG, "Unsuccessful retrieval of water Database Search Table");
                        }
                    }
                });
    }

}