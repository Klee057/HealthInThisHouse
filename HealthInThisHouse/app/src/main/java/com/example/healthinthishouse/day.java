package com.example.healthinthishouse;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.healthinthishouse.databinding.FragmentDashboardBinding;
import com.example.healthinthishouse.databinding.FragmentDayBinding;
import com.example.healthinthishouse.ui.dashboard.DashboardFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link day#newInstance} factory method to
 * create an instance of this fragment.
 */
public class day extends Fragment {
    FragmentDayBinding binding;
    private static int dayCalorieInt;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public day() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment day.
     */
    // TODO: Rename and change types and number of parameters
    public static day newInstance(String param1, String param2) {
        day fragment = new day();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDayBinding.inflate(inflater, container, false);  // binds to dashboard fragment
        View root = binding.getRoot();
        TextView dayCalorieText = binding.textView4;
        TextView dayCalorieBelowText = binding.textView9;
        TextView dayCaloriesUnderBudgetText = binding.textView11;
        ProgressBar progBar = binding.imageView;
        dayCalorieInt = 0;
        readData(new FirestoreCallback() {
            @Override
            public void onCallback(int totalcalorie) {
                Log.d("DayCalorie", "onCallBack: read data from db");
                int underBudget = 2000 - totalcalorie;
                float progressPercentage = (totalcalorie/20);

                Log.d("test", String.valueOf(progressPercentage));
                dayCalorieText.setText(Integer.valueOf(totalcalorie).toString() + " cal");
                dayCalorieBelowText.setText(Integer.valueOf(totalcalorie).toString() + " cal");
                dayCaloriesUnderBudgetText.setText(Integer.valueOf(underBudget).toString() + " cal");
                progBar.setMax(100);
                progBar.setProgress(Math.round(progressPercentage));

            }
        });

        return root;
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
                            dayCalorieInt = 0;
                            Log.d("test", "Successful retrieval of caloriesConsumed Table");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Integer itemCalories = Integer.valueOf(document.get("calorieIntake").toString());
                                dayCalorieInt += itemCalories;
                                Log.d("test", "onComplete: Retrieved Tuple. calorieIntake: " + itemCalories);
                            }
                            Log.d("test","onComplete: final totalCalarie retrieved from db - " + dayCalorieInt);
                            firestoreCallback.onCallback(dayCalorieInt);
                        }else {

                            Log.d("test", "Unsuccessful retrieval of Calorie Database Search Table");
                        }
                    }
                });
    }
}