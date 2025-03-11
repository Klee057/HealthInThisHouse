package com.example.healthinthishouse;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.healthinthishouse.databinding.FragmentCaloriePredefinedListBinding;
import com.example.healthinthishouse.databinding.FragmentWeekBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link week#newInstance} factory method to
 * create an instance of this fragment.
 */
public class week extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static String  TAG = "TESTTT";
    private FragmentWeekBinding binding;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public week() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment week.
     */
    // TODO: Rename and change types and number of parameters
    public static week newInstance(String param1, String param2) {
        week fragment = new week();
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
        binding = FragmentWeekBinding.inflate(inflater, container, false);
        Log.d(TAG, "onCreateView: binded ig");
        // Inflate the layout for this fragment
        View root = binding.getRoot();
        binding.refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onClick", "hihihihih");
                binding.thurBreakfast.setVisibility(view.INVISIBLE);
                binding.thurLunch.setVisibility(view.VISIBLE);
            }
        });
        return root;
//        return inflater.inflate(R.layout.fragment_week, container, false);
    }
}