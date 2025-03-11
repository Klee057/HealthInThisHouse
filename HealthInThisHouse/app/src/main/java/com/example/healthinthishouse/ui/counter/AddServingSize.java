package com.example.healthinthishouse.ui.counter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthinthishouse.R;
import com.example.healthinthishouse.calorieTracker;
import com.example.healthinthishouse.databinding.FragmentAddServingSizeBinding;

public class AddServingSize extends Fragment {

    private FragmentAddServingSizeBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAddServingSizeBinding.inflate(inflater, container, false);  // binds to dashboard fragment
        View root = binding.getRoot();

        TextView AddServingFood = binding.AddServingFood;
        TextView calorieNumber = binding.calorieNumber;
        EditText ServingSize = binding.ServingSize;

        Bundle bundle = getArguments();
        String itemName = bundle.getString("itemName");
        String itemCalories = bundle.getString("itemCalories");

        AddServingFood.setText(itemName);
        calorieNumber.setText(itemCalories);


        binding.servingCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CounterFragment CounterFragment = new CounterFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment_activity_main,CounterFragment);
                ft.commit();
            }
        });

        binding.servingAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("name",itemName);

                int servingSizeInt = Integer.valueOf(ServingSize.getText().toString());
                int itemCalorieInt = Integer.valueOf(itemCalories.replaceAll("[^0-9]", ""));
                Integer finalResult = Integer.valueOf(servingSizeInt*itemCalorieInt);
                bundle.putString("result",finalResult.toString() + " cal");

                CounterFragment CounterFragment = new CounterFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                CounterFragment.setArguments(bundle);
                ft.replace(R.id.nav_host_fragment_activity_main,CounterFragment);
                ft.commit();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}