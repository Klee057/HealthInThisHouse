package com.example.healthinthishouse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthinthishouse.R;
import com.example.healthinthishouse.databinding.FragmentCalorieTrackerBinding;
import com.example.healthinthishouse.day;

public class calorieTracker extends Fragment {

    private FragmentCalorieTrackerBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCalorieTrackerBinding.inflate(inflater, container, false);  // binds to dashboard fragment
        View root = binding.getRoot();

        binding.dayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day day = new day();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.calorieFragContainer,day);
                ft.commit();
            }
        });

        binding.weekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                week week = new week();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.calorieFragContainer,week);
                ft.commit();
            }
        });

        binding.monthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month month = new month();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.calorieFragContainer,month);
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