package com.example.healthinthishouse.ui.settings;

import android.content.Intent;
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

import com.example.healthinthishouse.LoginActivity;
import com.example.healthinthishouse.MainActivity;
import com.example.healthinthishouse.R;
import com.example.healthinthishouse.databinding.FragmentRecipesBinding;
import com.example.healthinthishouse.databinding.FragmentSettingsBinding;
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

public class SettingsFragment extends Fragment{

    String s1[], s2[];
    int images[];

    private FragmentSettingsBinding binding;
    private static ArrayList<itemData> itemsList = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static itemData tempobject;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Bundle bundle = this.getArguments();




        //Change fragments start
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Add recipe button", "should change fragments");
                startActivity(new Intent(getActivity(), LoginActivity.class));
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