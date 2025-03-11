package com.example.healthinthishouse.ui.counter;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthinthishouse.R;
import com.example.healthinthishouse.calorieTracker;
import com.example.healthinthishouse.databinding.FragmentAddServingSizeBinding;
import com.example.healthinthishouse.databinding.FragmentSaveCaloriesBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SaveCalories extends Fragment {

    private String TAG = "SaveCalories";
    private Context mContext;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FragmentSaveCaloriesBinding binding;

    private static String foodItemName = null;
    private static String foodItemCalories = null;

    private String myFoodItemName;
    private String myFoodItemCaloriesString;
    private int myFoodItemCalories;

    public SaveCalories() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SaveCalories.
     */
    public static SaveCalories newInstance(String name, String calories) {
        SaveCalories fragment = new SaveCalories();
        Bundle args = new Bundle();
        args.putString(foodItemName, name);
        args.putString(foodItemCalories, calories);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        db = FirebaseFirestore.getInstance();
        if (getArguments() != null) {
            myFoodItemName = getArguments().getString(foodItemName);
            myFoodItemCaloriesString = getArguments().getString(foodItemCalories);


        }
        Log.d(TAG, "onCreate: entered Save Calories Fragment");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSaveCaloriesBinding.inflate(inflater, container, false);  // binds to dashboard fragment
        View root = binding.getRoot();

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CounterFragment CounterFragment = new CounterFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment_activity_main,CounterFragment);
                ft.commit();
            }
        });

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCalariesToDB(view);
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //TODO: NOT TESTED
    public void saveCalariesToDB(View view) {

        //retrieving input data from edittext
        Log.d(TAG, "saveCalariesToDB: Retrieving user input");
        EditText foodItemNameInput = binding.foodItemNameInput;
        EditText calorieInput = binding.calorieInput;
        Log.d(TAG, "saveCalariesToDB: Food Item Name: " + foodItemNameInput.getText() + "; Calorie Inputted: " + calorieInput.getText());
        if (foodItemNameInput.getText().toString().matches("") || calorieInput.getText().toString().matches("")) {
            Toast.makeText(mContext, "Please ensure that both fields are filled", Toast.LENGTH_SHORT).show();
            return;
        }
        myFoodItemName = foodItemNameInput.getText().toString();
        myFoodItemCaloriesString = calorieInput.getText().toString();
        try {
            myFoodItemCalories = Integer.parseInt(myFoodItemCaloriesString);
            Log.i(TAG, "saveCaloriesToDB: myFoodItemCaloriesString successfully parsed as an int");
        } catch (NumberFormatException e) {
            Log.i(TAG, "saveCaloriesToDB: myFoodItemCaloriesString failed to parsed as an int");
        }

        //saving newly defined calories into db
        Log.d(TAG, "saveCalariesToDB: Saving My Food calories");

//        db = FirebaseFirestore.getInstance();

        Map<String, Object> rentry = new HashMap<>();
        rentry.put("Name", myFoodItemName);
        rentry.put("Calorie", myFoodItemCalories);

        db.collection("My Foods Calorie")
                .add(rentry)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(mContext, "Successful", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onSuccess: Successfully added MyFoods input into db");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "Failed. Please try again", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onSuccess: Failed to add MyFoods input into db");
                    }
                });

        //transition back to prev page
        Log.d(TAG, "saveCalariesToDB: Transitioning back to Calorie Counter Page..");
        CounterFragment CounterFragment = new CounterFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment_activity_main,CounterFragment);
        ft.commit();
    }



}