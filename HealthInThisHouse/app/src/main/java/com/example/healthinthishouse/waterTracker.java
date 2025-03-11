package com.example.healthinthishouse;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthinthishouse.databinding.FragmentWaterTrackerBinding;
import com.example.healthinthishouse.databinding.FragmentWeekBinding;
import com.example.healthinthishouse.ui.dashboard.DashboardFragment;
import com.example.healthinthishouse.ui.recipes.RecipesFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link waterTracker#newInstance} factory method to
 * create an instance of this fragment.
 */
public class waterTracker extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context waterTracker;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Integer inputDrankNum = 0;
    EditText inputDrank;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Integer currentAmount;

    private FragmentWaterTrackerBinding binding;
    private static String litres = null;
    public waterTracker() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment waterTracker.
     */
    // TODO: Rename and change types and number of parameters
    public static waterTracker newInstance(String param1, String param2) {
        waterTracker fragment = new waterTracker();
        Bundle args = new Bundle();
//        if(fragment.inputDrankNum != null){
//            Log.d("TAG", "input: ");
//            args.putInt(litres, fragment.inputDrankNum);
//        }else {
//            Log.d("TAG", "input = 0: ");
//            args.putInt(litres, 0);
//        }
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
            Log.d("test", "onCreate: ");
//            String inputDrankStr = binding.inputDrank.getText().toString();
//            inputDrankNum = Integer.parseInt(inputDrankStr);
//            binding.progressBar.setProgress(savedInstanceState.getInt(litres));
        }

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWaterTrackerBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        Bundle bundle = this.getArguments();
//        final ProgressBar pb = binding.progressBar;
//        ProgressBar progressBar = binding.displayCalorieOfFoodAdded;
        binding.addWaterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//            add to database????

//                bundle.putInt("totalDrank",inputDrankNum);
                if (inputDrankNum ==null){
                    Log.d("asdasd", "null: ");

                } else {
                    Log.d("asdasd", inputDrankNum.toString());

                }
                try {
                    inputDrankNum = Integer.valueOf(binding.inputDrank.getText().toString());
                    Map<String,Object> rentry = new HashMap<>();
                    rentry.put("waterAmount", inputDrankNum);
                    Toast.makeText(getActivity(), "Water intake added!", Toast.LENGTH_SHORT).show();

                    // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_water_tracker, container, false);

                    db.collection("WaterTracker")
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

                } catch (Exception e){
                    Log.d("asds", "error: ");
                }


                Log.d("asdasda", "goback");
                DashboardFragment DashboardFragment = new DashboardFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment_activity_main,DashboardFragment);
                ft.commit();



//                db.collection("waterTracker")
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
//
//                                if (task.isSuccessful()){
//                                    currentAmount=0;
//                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                        currentAmount += Integer.valueOf(document.get("waterAmount").toString());
//                                    }
//                                    Log.d("hhh", currentAmount.toString());
//                                    binding.progressBar.setProgress(currentAmount);
//                                }else {
//                                    Log.d("Failed", "hehe");
//                                }
//
//                            }
//                        });
        //UNTIL HERE CORRECT
         }
        });
        return root;
    }
}