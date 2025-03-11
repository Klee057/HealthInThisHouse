package com.example.healthinthishouse.ui.counter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthinthishouse.R;
import com.example.healthinthishouse.databinding.FragmentCounterBinding;
import com.example.healthinthishouse.week;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;


public class CounterFragment extends Fragment {

    private String TAG = "CounterFragment";

    private FragmentCounterBinding binding;

    public CounterFragment(){
        //empty constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CounterViewModel counterViewModel =
                new ViewModelProvider(this).get(CounterViewModel.class);

        binding = FragmentCounterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Bundle bundle = this.getArguments();
        final TextView textView = binding.textCounter;
        TextView displayCalorieOfFoodAdded = binding.displayCalorieOfFoodAdded;
        TextView displayFoodAdded = binding.displayFoodAdded;
        EditText addCaloriesInput = binding.addCaloriesInput;


        counterViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        binding.addCaloriesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Add button clicked");
                addCalorie(view);

                String text = addCaloriesInput.getText().toString();
                binding.displayFoodAdded.setText("Food Item");
                binding.intermediate.setText("---------");
                binding.displayCalorieOfFoodAdded.setText(text);

            }
        });

        binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalorieDBSearch CalorieDBSearch = new CalorieDBSearch();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.resultsFragContainer,CalorieDBSearch);
                ft.commit();
            }
        });

        binding.myFoodsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CaloriePredefined CaloriePredefined = new CaloriePredefined();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.resultsFragContainer,CaloriePredefined);
                ft.commit();
            }
        });

        binding.recipesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalorieRecipe CalorieRecipe = new CalorieRecipe();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.resultsFragContainer,CalorieRecipe);
                ft.commit();
            }
        });


        binding.submitMyCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //writing total calories into db
                TextView displayCalorieOfFoodAdded = binding.displayCalorieOfFoodAdded;
                int itemTotalCalorieInt = Integer.valueOf(displayCalorieOfFoodAdded.getText().toString().replaceAll("[^0-9]", ""));
                writeDataTocaloriesConsumed(itemTotalCalorieInt);
                Toast.makeText(getContext(), "Successfully added new calories! ", Toast.LENGTH_SHORT).show();
            }
        });


        if(bundle!=null) {
            String name = bundle.getString("name");
            String result = bundle.getString("result");
            displayFoodAdded.setText(name);
            displayCalorieOfFoodAdded.setText(result);
        }

        return root;
    }

    private void writeDataTocaloriesConsumed(int itemTotalCalorieInt) {

        Log.d(TAG, "writeDataTocaloriesConsumed: Saving itemTotalCalorieInt to caloriesConsumed table");

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> rentry = new HashMap<>();
        rentry.put("calorieIntake", itemTotalCalorieInt);

        db.collection("caloriesConsumed")
                .add(rentry)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "onSuccess: Successfully added itemTotalCalorieInt to caloriesConsumed table");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onSuccess: Failed to add itemTotalCalorieInt to caloriesConsumed table");
                    }
                });
    }

    private void addCalorie(View view) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        Log.d(TAG,"addCalorie: Yes button clicked");
                        saveMyCalories();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        Log.d(TAG,"addCalorie: No button clicked");
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getView().getContext());
        builder.setMessage("Would you like to save your input for future references?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();



    }

    private void saveMyCalories() {
        SaveCalories SaveCaloriesFragment = new SaveCalories();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment_activity_main, SaveCaloriesFragment);
        ft.commit();
        Log.d(TAG, "saveMyCalaries: Transition to Save Calories Page");
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}



////template to display the calories to be added
//binding.displayFoodAdded.setText("Eg: Chicker");
//binding.intermediate.setText("---------");
//binding.displayCalorieOfFoodAdded.setText("400 cal");