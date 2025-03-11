package com.example.healthinthishouse;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthinthishouse.databinding.FragmentDayBinding;
import com.example.healthinthishouse.databinding.FragmentExerciseTrackerBinding;
import com.example.healthinthishouse.ui.bmi.BmiFragment;
import com.example.healthinthishouse.ui.dashboard.DashboardFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link exerciseTracker#newInstance} factory method to
 * create an instance of this fragment.
 */
public class exerciseTracker extends Fragment {
    FragmentExerciseTrackerBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Integer counter= 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public exerciseTracker() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment exerciseTracker.
     */
    // TODO: Rename and change types and number of parameters
    public static exerciseTracker newInstance(String param1, String param2) {
        exerciseTracker fragment = new exerciseTracker();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
//    bind buttons to increment counter then go back page   if no then go back page toast?
//

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
        binding = FragmentExerciseTrackerBinding.inflate(inflater, container, false);  // binds to dashboard fragment
        View root = binding.getRoot();
        Button yes = binding.yesBtn;
        Button no = binding.noBtn;

        binding.yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//            add to database????

//                bundle.putInt("totalDrank",inputDrankNum);
//                counter = Integer.valueOf(binding.yesBtn.getText().toString());
                Map<String,Object> rentry = new HashMap<>();
                rentry.put("frequency", counter);
                Toast.makeText(getActivity(), "Exercise recorded!", Toast.LENGTH_SHORT).show();

                // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_water_tracker, container, false);

                db.collection("ExerciseTotal")
                        .add(rentry)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("Success", "hehe");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Log.d("Failed", "hehe");
                            }
                        });

            }
        });

        binding.noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DashboardFragment DashboardFragment = new DashboardFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment_activity_main,DashboardFragment);
                ft.commit();
            }
        });


        return root;
//        return inflater.inflate(R.layout.fragment_exercise_tracker, container, false);
    }
}