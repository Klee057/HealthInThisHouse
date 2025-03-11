package com.example.healthinthishouse.ui.recipes;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthinthishouse.MainActivity;
import com.example.healthinthishouse.R;
import com.example.healthinthishouse.databinding.FragmentRecipesBinding;
import com.example.healthinthishouse.itemData;
import com.example.healthinthishouse.recyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RecipesFragment extends Fragment implements recyclerAdapter.OnNoteListener {

    String s1[], s2[];
    int images[];

    private FragmentRecipesBinding binding;
    private static ArrayList<itemData> itemsList = new ArrayList<>();
    private RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static itemData tempobject;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRecipesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.recyclerView;
        Button addRecipeButton = binding.addRecipeButton;
        recyclerAdapter adapter = new recyclerAdapter(itemsList, this);
        Bundle bundle = this.getArguments();
        if (bundle != null){
            Log.d("Removing Items", "onCreateView: ");
            Integer itemPosition = bundle.getInt("itemPosition");
            itemsList.remove(itemPosition.intValue());
            //Log.d("List", Integer.valueOf(itemsList.size()).toString());
            Log.d("List", itemPosition.toString());
        }
        readData(new FirestoreCallback() {
            @Override
            public void onCallback(List<itemData> itemsList) {
                Log.d(itemsList.get(0).getItemName(), itemsList.get(0).getItemDescription());
//                Log.d(itemsList.toString(), "size");
            }
        });


//          Log.d(itemsList.get(0).getItemName(), itemsList.get(0).getItemDescription());
//        if (itemsList.size() == 0){
//            itemsList.add((new itemData("Chicken Rice", "607 cal", "delicious", "1. Add chicken, 2. Cook chicken")));
//        }
//        itemsList.add((new itemData("Avocado Toast", "195 cal/slice")));
//        for (int i = 1; i < 10; i++) {
//            itemsList.add(new itemData(String.format("Example item %d", i), String.format("%d Calories", i* 100)));
//        }



        //Change fragments start
        binding.addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Add recipe button", "should change fragments");

                AddRecipesFragment AddRecipesFragment = new AddRecipesFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment_activity_main, AddRecipesFragment);
                ft.commit();
            }
        });

        // Change fragments end


        //Onclick doenst seem to work yet

//        addRecipeButton.setOnClickListener(new View.OnClickListener() {  // Adds an instance of itemData(name, calories)
//            @Override
//            public void onClick(View view) {
//                Log.d("add recipe button pressed", "woop");
//                itemsList.add(new itemData("testName", "testCalories"));
//                Log.d("ITEMLIST", "" + itemsList.get(0).getItemName());
//
////                adapter.notifyDataSetChanged();
//
//            }
//        });

        setAdapter();
        return root;
    }

    private void setAdapter(){
        recyclerAdapter adapter = new recyclerAdapter(itemsList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onNoteClick(int position) {
        itemsList.get(position);
        Bundle bundle = new Bundle();

        // Store data into bundle to pass to next fragment
        bundle.putString("bundleName", itemsList.get(position).getItemName());
        bundle.putString("bundleCalories", itemsList.get(position).getItemCalories());
        bundle.putString("bundleIngredients", itemsList.get(position).getItemIngredients());
        bundle.putString("bundleDesc", itemsList.get(position).getItemDescription());
        bundle.putString("bundleInstructions", itemsList.get(position).getItemInstructions());
        bundle.putInt("itemPosition", position);

        Log.d(itemsList.get(position).toString(), "test");
        EditRecipesFragment EditRecipesFragment = new EditRecipesFragment();
        EditRecipesFragment.setArguments(bundle);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment_activity_main, EditRecipesFragment);
        ft.commit();
    }

    private interface FirestoreCallback {
        void onCallback(List<itemData> list);
    }

    private void readData(FirestoreCallback firestoreCallback) {
        db.collection("Recipes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()){
                            Log.d("Recipes Fragment", "Success");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Adding recipe", "Success");
                                String itemName = document.get("Name").toString();
                                Integer itemCalories = Integer.valueOf(document.get("Calories").toString());
                                String itemIngredients = document.get("Ingredients").toString();
                                String itemDescription = document.get("Description").toString();
                                String itemInstruction = document.get("Instructions").toString();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    if (!itemsList.stream().anyMatch(o -> itemName.equals(o.getItemName()))) {
                                        itemsList.add(new itemData(itemName, itemCalories.toString(), itemDescription,itemInstruction,itemIngredients));
                                    }
                                }
                            }
                            Log.d("Done","Done");
                            firestoreCallback.onCallback(itemsList);
                        }else {

                            Log.d("RecipesFragment", "Unsuccessful");
                        }
                    }
                });
    }
}