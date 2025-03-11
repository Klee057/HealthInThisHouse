package com.example.healthinthishouse.ui.counter;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.healthinthishouse.R;
import com.example.healthinthishouse.itemData;
import com.example.healthinthishouse.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.healthinthishouse.databinding.FragmentCaloriePredefinedBinding;

import java.util.ArrayList;
import java.util.List;

// for calorie prdefined fragment

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<itemData> dbitemsList;
    private OnNoteListener mOnNoteListener;

    public MyItemRecyclerViewAdapter(ArrayList<itemData> items, OnNoteListener onNoteListener) {
        dbitemsList = items;
        this.mOnNoteListener = onNoteListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView item_name;
        private TextView itemCalories;
        private TextView itemDescription;
        private ImageView itemImage;

        OnNoteListener onNoteListener;

        public MyViewHolder(final View view, OnNoteListener onNoteListener){
            super(view);
            this.item_name = view.findViewById(R.id.itemName);
            this.itemCalories = view.findViewById(R.id.itemCalories);
            this.itemImage = view.findViewById(R.id.itemImage);
            this.itemDescription = view.findViewById(R.id.itemDescription);

            this.onNoteListener = onNoteListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }

    @Override
    public MyItemRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_calorie_predefined, parent, false);
        return new MyViewHolder(itemView, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(final MyItemRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.item_name.setText(dbitemsList.get(position).getItemName());
        holder.itemCalories.setText(dbitemsList.get(position).getItemCalories());
    }

    @Override
    public int getItemCount() {
        return dbitemsList.size();
    }

}