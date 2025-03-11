package com.example.healthinthishouse;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthinthishouse.ui.recipes.AddRecipesFragment;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder>{

    private ArrayList<itemData> itemList;
    private OnNoteListener mOnNoteListener;
    public recyclerAdapter(ArrayList<itemData> itemList, OnNoteListener onNoteListener){
        this.itemList = itemList;
        this.mOnNoteListener = onNoteListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView itemName;
        private TextView itemCalories;
        private TextView itemDescription;
        private ImageView itemImage;
        private TextView itemInstructions;
        private TextView itemIngredients;
        private ImageButton editItem;

        OnNoteListener onNoteListener;
        public MyViewHolder(final View view, OnNoteListener onNoteListener){
            super(view);
            this.itemName = view.findViewById(R.id.itemName);
            this.itemCalories = view.findViewById(R.id.itemCalories);
            this.itemImage = view.findViewById(R.id.itemImage);
            this.itemDescription = view.findViewById(R.id.itemDescription);
            this.itemInstructions =view.findViewById((R.id.editTextInstructions));
            this.itemIngredients = view.findViewById(R.id.ItemsList);


            this.editItem = view.findViewById(R.id.editItem);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
            //this.editItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getBindingAdapterPosition());
        }
    }

    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipeitems, parent, false);
        return new MyViewHolder(itemView, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {
        holder.itemName.setText(itemList.get(position).getItemName());
        holder.itemCalories.setText(itemList.get(position).getItemCalories() + " Calories");
        holder.editItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
//        holder.itemImage.setImageResource(itemList.get(position).itemImage);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface OnNoteListener{
        void onNoteClick(int position);

    }

}

