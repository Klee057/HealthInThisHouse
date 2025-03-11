package com.example.healthinthishouse.ui.recipes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthinthishouse.R;
import com.example.healthinthishouse.databinding.FragmentAddRecipeBinding;
import com.example.healthinthishouse.itemData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddRecipesFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class AddRecipesFragment extends Fragment {
    FirebaseFirestore db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private FragmentAddRecipeBinding binding;
    private String mParam2;
    //Added for DropdownList
    private static ArrayList<String> itemsList = new ArrayList<>();
    ArrayList<String> list;
    private static HashMap<String,Integer> ItemCalorie = new HashMap<>();
    private static ArrayList<Object> masterlist = new ArrayList<>();
    private Integer totalCalorieCount = 0;
    private Integer count = 0;
    private static itemData tempobject;
    Button add,remove;
    ArrayList<String> addArray = new ArrayList<String>();
    ListView listview;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddRecipesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddRecipesFragment newInstance(String param1, String param2) {
        AddRecipesFragment fragment = new AddRecipesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AddRecipesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

//        Spinner s = (Spinner) findViewById(R.id.Spinner01);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, arraySpinner);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        s.setAdapter(adapter);
    }

    Spinner spinner;
    TextView recipeItemsList;
    TextView calorieCounter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = FirebaseFirestore.getInstance();
        ArrayList<String> spinnerItems = new ArrayList<String>();
        spinnerItems.add("Chicken");
        binding = FragmentAddRecipeBinding.inflate(inflater, container, false);
        Bundle bundle = this.getArguments();
        //For Dropdown Ingredient List
        spinner = binding.getRoot().findViewById(R.id.RecipeSpinner);
        recipeItemsList = (TextView)binding.getRoot().findViewById((R.id.ItemsList));
        calorieCounter = (TextView)binding.getRoot().findViewById(R.id.calorieCount);
        add = binding.getRoot().findViewById((R.id.add_button));
        remove = binding.getRoot().findViewById(R.id.remove_button);

        readsData(new FirestoreCallback() {
            @Override
            public void onCallback(ArrayList masterList) {
                itemsList = (ArrayList)masterList.get(0);
                ItemCalorie = (HashMap)masterList.get(1);

                ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,itemsList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                //System.out.println("HI");

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if(count!=0) {
                            String selected = spinner.getSelectedItem().toString();
                            System.out.println(selected);
                            Integer Calorie = Integer.valueOf(ItemCalorie.get(selected));
                            //Integer Calorie = Integer.valueOf(ItemCalorie.get(selected).toString());
                            add.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    totalCalorieCount = totalCalorieCount + Calorie;
                                    String getInput = "Item: " + selected + " Calories: " + Calorie ;
                                    recipeItemsList.setText("");
                                    addArray.add(getInput);
                                    //System.out.println(addArray);
                                    int arraySize = addArray.size();
                                    for (int i = 0 ; i<arraySize;i++){
                                        recipeItemsList.append(addArray.get(i) +System.lineSeparator());
                                    }
                                    //recipeItemsList.append("Item: " + selected + " Calories: " + Calorie + System.lineSeparator() );
                                    calorieCounter.setText("");
                                    calorieCounter.append(totalCalorieCount.toString());
                                }
                            });
                            remove.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //Integer Calorie = Integer.valueOf(ItemCalorie.get(selected).toString());
                                    totalCalorieCount = totalCalorieCount - Calorie;
                                    String getInput = "Item: " + selected + " Calories: " + Calorie ;
                                    recipeItemsList.setText("");
                                    addArray.remove(getInput);
                                    int arraySize = addArray.size();
                                    for (int i = 0 ; i<arraySize;i++){
                                        recipeItemsList.append(addArray.get(i) +System.lineSeparator());
                                    }
                                    //recipeItemsList.append("Item: " + selected + " Calories: " + Calorie + System.lineSeparator());
                                    //recipeItemsList.toString().replace("\n","");
                                   // recipeItemsList.toString().replaceAll("Item: " + selected + " Calories: " + Calorie, "");
                                    calorieCounter.setText("");
                                    calorieCounter.append(totalCalorieCount.toString());

                                }
                            });
                        }
                        count++;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
//                Log.d(itemsList.toString(), "size");
            }
        });

        // Default Values for the edit text boxes
        String editTextName = "Enter Recipe Name";
        String calorieCount = "Enter Calories";
        String ItemsList = "Enter ingredients";
        String editTextDesc = "Additional Recipe Notes";
        String editTextInstructions = "Instructions";

        if (bundle != null){
            editTextName = bundle.getString("bundleName");
            ItemsList = bundle.getString("bundleIngredients");
            calorieCount = bundle.getString("bundleCalories");
            editTextDesc = bundle.getString("bundleDesc");
            editTextInstructions = bundle.getString("bundleInstructions");
            binding.editTextName.setText(editTextName);
            binding.ItemsList.setText(ItemsList);
            binding.calorieCount.setText(calorieCount);
            binding.editTextDesc.setText(editTextDesc);
            binding.editTextInstructions.setText(editTextInstructions);
        }

//        binding.editTextName.setText(editTextName);
//        binding.editTextCalories.setText(editTextCalories);
//        binding.editTextDesc.setText(editTextDesc);
//        binding.editTextInstructions.setText(editTextInstructions);

        binding.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String saveName = binding.editTextName.getText().toString();
                Integer saveCalories = Integer.parseInt(binding.calorieCount.getText().toString());
                String saveIngredients = binding.ItemsList.getText().toString();
                String saveDesc = binding.editTextDesc.getText().toString();
                String saveInstructions = binding.editTextInstructions.getText().toString();

                Map<String,Object> rentry = new HashMap<>();
                rentry.put("Name", saveName);
                rentry.put("Ingredients",saveIngredients);
                rentry.put("Calories", saveCalories);
                rentry.put("Description", saveDesc);
                rentry.put("Instructions", saveInstructions);

                db.collection("Recipes")
                        .add(rentry)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("Add Recipes Frag", "Added Recipe");

                                RecipesFragment RecipesFragment = new RecipesFragment();
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.nav_host_fragment_activity_main, RecipesFragment);
                                ft.commit();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Log.d("Add Recipes Frag", "Failed to add Recipe");
                            }
                        });


            }
        });
        return binding.getRoot();
    }
    private interface FirestoreCallback {
        void onCallback(ArrayList list);
    }

    private void readsData(FirestoreCallback firestoreCallback) {
        db.collection("Calorie Database Search")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String itemName = document.get("Name").toString();
                                Integer itemCalories = Integer.valueOf(document.get("Calorie").toString());
                                //String itemDescription = document.get("Description").toString();
                                if(itemsList.contains(itemName)!=true) {
                                    itemsList.add(itemName);
                                    ItemCalorie.put(itemName,itemCalories);
                                }
                            }
                            masterlist.clear();
                            masterlist.add(itemsList);
                            masterlist.add(ItemCalorie);
//                            firestoreCallback.onCallback(itemsList);
                            firestoreCallback.onCallback(masterlist);
                        }else {

                            Log.d("AddRecipesFragment", "Unsuccessful");
                        }

                    }
                });
    }
}

