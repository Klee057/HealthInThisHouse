package com.example.healthinthishouse.ui.bmi;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthinthishouse.R;
import com.example.healthinthishouse.calorieTracker;
import com.example.healthinthishouse.databinding.FragmentAddServingSizeBinding;
import com.example.healthinthishouse.databinding.FragmentBmiResultsBinding;
import com.example.healthinthishouse.ui.counter.CounterFragment;

public class BmiResults extends Fragment {

    private FragmentBmiResultsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBmiResultsBinding.inflate(inflater, container, false);  // binds to dashboard fragment
        View root = binding.getRoot();
        TextView bmiResult = binding.BMIResult;
        TextView BMICat = binding.BMICat;
        ImageView BMIImage1 = binding.BMIImage1;
        ImageView BMIImage2 = binding.BMIImage2;
        ImageView BMIImage3 = binding.BMIImage3;
        ImageView BMIImage4 = binding.BMIImage4;


        Bundle bundle = getArguments();
        String result = bundle.getString("result");
        String bmiCat = bundle.getString("category");
        bmiResult.setText(result);
        BMICat.setText(bmiCat);

        if(bmiCat=="Risk of Nutritional Deficiency"){
            BMIImage1.setVisibility(View.VISIBLE);
        }
        else if(bmiCat=="Low Risk (Healthy Range)"){
            BMIImage2.setVisibility(View.VISIBLE);
        }
        else if(bmiCat=="Moderate Risk"){
            BMIImage3.setVisibility(View.VISIBLE);
        }
        else if(bmiCat=="High risk"){
            BMIImage4.setVisibility(View.VISIBLE);
        }


        binding.RecalBmiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmiFragment BmiFragment = new BmiFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment_activity_main,BmiFragment);
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