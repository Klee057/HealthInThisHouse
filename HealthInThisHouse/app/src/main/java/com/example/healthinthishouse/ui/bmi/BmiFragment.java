package com.example.healthinthishouse.ui.bmi;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.healthinthishouse.R;
import com.example.healthinthishouse.databinding.FragmentBmiBinding;

public class BmiFragment extends Fragment {

    private FragmentBmiBinding binding;
    android.widget.Button bmiButton;
    TextView currentHeight, currentAge, currentWeight, UnderWeightBMI,HealthyBMI,OverWeightBMI,ObeseBMI;
    ImageView incrementWeight,decrementWeight, incrementAge, decrementAge;
    SeekBar heightSeekBar;
    RelativeLayout BMIUnderWeight,BMIHealthy,BMIOverWeight,BMIObese;
    int currentprogress;
    String mintprogress="170";
    String weight2 ="55";
    String age2 = "22";
    int intWeight = 55;
    int intAge = 22;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBmiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Instantiating
        bmiButton = binding.bmiButton;
        currentHeight = binding.heightChosen;
        currentAge = binding.currentAge;
        currentWeight =  binding.currentWeight;
        incrementWeight =  binding.incrementWeight;
        decrementWeight = binding.decrementWeight;;
        incrementAge = binding.incrementAge;
        decrementAge = binding.decrementAge;
        heightSeekBar =  binding.heightSeekBar;


        heightSeekBar.setMax(300);
        heightSeekBar.setProgress(170);
        heightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                currentprogress=i;
                mintprogress= String.valueOf(currentprogress);
                currentHeight.setText(mintprogress);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        incrementAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intAge = intAge+1;
                age2 = String.valueOf(intAge);
                currentAge.setText(age2);
            }
        });

        decrementAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intAge = intAge-1;
                age2 = String.valueOf(intAge);
                currentAge.setText(age2);
            }
        });

        incrementWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intWeight = intWeight+1;
                weight2 = String.valueOf(intWeight);
                currentWeight.setText(weight2);
            }
        });

        decrementWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intWeight = intWeight-1;
                weight2 = String.valueOf(intWeight);
                currentWeight.setText(weight2);
            }
        });



        bmiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mintprogress.equals("0")){
                    Log.d("Exception","Weight=0");
                    Toast.makeText(getActivity().getApplicationContext(), "Please select your height", Toast.LENGTH_SHORT).show();
                }
                else if(intAge==0 || intAge<0){
                    Toast.makeText(getActivity().getApplicationContext(), "Please select a valid age", Toast.LENGTH_SHORT).show();
                }
                else if(intWeight==0 || intWeight<0){
                    Toast.makeText(getActivity().getApplicationContext(), "Please select a valid weight", Toast.LENGTH_SHORT).show();
                }

                else{

                 Float height = Float.parseFloat(mintprogress);
                 Float weight = Float.parseFloat(weight2);

                 height= height/100;
                 Float bmi = weight/(height*height);
                 String mbmi = Float.toString(bmi);
                 String BMICat="x";


                    if(bmi<18.5){
                        BMICat = "Risk of Nutritional Deficiency";
                     }
                     else if(bmi>=18.5 && bmi<=22.9){
                        BMICat = "Low Risk (Healthy Range)";
                     }
                     else if(bmi>=23 && bmi<=27.4){
                        BMICat = "Moderate Risk";
                     } else if (bmi >= 27.5) {
                        BMICat = "High risk";
                    }

                    Bundle bundle = new Bundle();
                    bundle.putString("result",mbmi);
                    bundle.putString("category",BMICat);
                    BmiResults BmiResults = new BmiResults();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    BmiResults.setArguments(bundle);
                    ft.replace(R.id.nav_host_fragment_activity_main,BmiResults);
                    ft.commit();
                }
//                else{
//                     Float height = Float.parseFloat(mintprogress);
//                     Float weight = Float.parseFloat(weight2);
//
//                     height= height/100;
//                     Float bmi = weight/(height*height);
//                     String mbmi = Float.toString(bmi);
//
//                     if(bmi<18.5){
//                         BMIUnderWeight.setVisibility(View.VISIBLE);
//                         UnderWeightBMI.setText("Your BMI is: "+ mbmi);
//                     }
//                     else if(bmi>=18.5 && bmi<25){
//                         BMIHealthy.setVisibility(View.VISIBLE);
//                         HealthyBMI.setText("Your BMI is: "+ mbmi);
//                     }
//                     else if(bmi>=25 && bmi<30){
//                         BMIOverWeight.setVisibility((View.VISIBLE));
//                         OverWeightBMI.setText("Your BMI is: "+ mbmi);
//                     } else if (bmi >= 30) {
//                         BMIObese.setVisibility(View.VISIBLE);
//                         ObeseBMI.setText("Your BMI is: "+ mbmi);
//
//                     }
//                }
//
//                bmiButton.setVisibility(View.INVISIBLE);


                Log.d("Button clicked","Clicked");
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