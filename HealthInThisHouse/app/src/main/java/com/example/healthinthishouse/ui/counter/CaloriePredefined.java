package com.example.healthinthishouse.ui.counter;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healthinthishouse.R;
import com.example.healthinthishouse.databinding.FragmentCalorieDbSearchListBinding;
import com.example.healthinthishouse.databinding.FragmentCaloriePredefinedListBinding;
import com.example.healthinthishouse.itemData;
import com.example.healthinthishouse.placeholder.PlaceholderContent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.LogDescriptor;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class CaloriePredefined extends Fragment implements MyItemRecyclerViewAdapter.OnNoteListener {

    private String TAG = "CaloriePredefined";

    private static ArrayList<itemData> dbitemsList = new ArrayList<>();
    private static itemData tempobject;

    MyItemRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    FragmentCaloriePredefinedListBinding binding;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public CaloriePredefined() {
        //empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCaloriePredefinedListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.list;

        readData(new FirestoreCallback(){
            @Override
            public void onCallback(List<itemData> itemsList) {
                Log.d(TAG, "onCallBack: read data from db");
            }
        });

//        dbitemsList.add((new itemData("Chicken Rice", "607 cal")));
//        dbitemsList.add((new itemData("Avocado Toast", "195 cal/slice")));
//        for (int i = 1; i < 10; i++) {
//            dbitemsList.add(new itemData(String.format("Example item %d", i), String.format("%d Calories", i* 100)));
//        }

        setAdapter();

        return root;

    }

    private void setAdapter() {
        adapter = new MyItemRecyclerViewAdapter(dbitemsList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        Log.d(TAG, "setAdapter: setting adapter");
    }

    @Override
    public void onNoteClick(int position) {
        dbitemsList.get(position);
        //put wht u wanna do when clicking the card
        String itemName = dbitemsList.get(position).getItemName();
        String itemCalories = dbitemsList.get(position).getItemCalories();
        transitionToAddServingSize(itemName, itemCalories);
        Log.d(TAG, "onNoteClick: A My Foods card is clicked - itemName: " + itemName + "; itemCalories: " + itemCalories);
    }

    private interface FirestoreCallback {
        void onCallback(List<itemData> list);
    }

    private void readData(FirestoreCallback firestoreCallback){
        db.collection("My Foods Calorie")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()){
                            Log.d(TAG, "Successful retrieval of My Foods Calorie Table");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String itemName = document.get("Name").toString();
                                Integer itemCalories = Integer.valueOf(document.get("Calorie").toString());
                                Log.d(TAG, "onComplete: Retrieved Tuple. itemName: " + itemName + "; itemCalories: " + itemCalories);

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    if (!dbitemsList.stream().anyMatch(o -> itemName.equals(o.getItemName()))) {
                                        dbitemsList.add(new itemData(itemName, itemCalories.toString()));
                                        Log.d(TAG, "onComplete: Added tuple values (itemName and itemCalories)into dbitemsList");
                                    }
                                }
                            }
                            Log.d("Done","Done");
                            firestoreCallback.onCallback(dbitemsList);
                        }else {

                            Log.d(TAG, "Unsuccessful retrieval of Calorie Database Search Table");
                        }
                    }
                });

    }
    public void transitionToAddServingSize(String itemName, String itemCalories){
        AddServingSize AddServingSize = new AddServingSize();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment_activity_main,AddServingSize);
        Bundle bundle = new Bundle();
        bundle.putString("itemName",itemName);
        bundle.putString("itemCalories",itemCalories);
        AddServingSize.setArguments(bundle);
        ft.commit();
    }

}