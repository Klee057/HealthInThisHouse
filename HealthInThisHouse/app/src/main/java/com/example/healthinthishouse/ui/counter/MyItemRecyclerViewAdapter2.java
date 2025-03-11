//package com.example.healthinthishouse.ui.counter;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.example.healthinthishouse.placeholder.PlaceholderContent.PlaceholderItem;
//import com.example.healthinthishouse.databinding.FragmentCalorieDbSearchBinding;
//
//import java.util.List;
//
///**
// * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
// * TODO: Replace the implementation with code for your data type.
// *
// * this is for calorie db search list fragment
// */
//public class MyItemRecyclerViewAdapter2 extends RecyclerView.Adapter<MyItemRecyclerViewAdapter2.ViewHolder> {
//
//    private final List<PlaceholderItem> mValues;
//
//    public MyItemRecyclerViewAdapter2(List<itemData> items) {
//        mValues = items;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        return new ViewHolder(FragmentCalorieDbSearchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
//
//    }
//
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.mItem = mValues.get(position);
//        holder.mIdView.setText(mValues.get(position).id);
//        holder.mContentView.setText(mValues.get(position).content);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mValues.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final TextView mIdView;
//        public final TextView mContentView;
//        public PlaceholderItem mItem;
//
//        public ViewHolder(FragmentCalorieDbSearchBinding binding) {
//            super(binding.getRoot());
//            mIdView = binding.itemNumber;
//            mContentView = binding.content;
//        }
//
//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
//    }
//}

package com.example.healthinthishouse.ui.counter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthinthishouse.R;
import com.example.healthinthishouse.itemData;

import java.util.ArrayList;

//for db calorie search fragment

public class MyItemRecyclerViewAdapter2 extends RecyclerView.Adapter<MyItemRecyclerViewAdapter2.MyViewHolder>{

    private ArrayList<itemData> dbitemsList;
    private OnNoteListener mOnNoteListener;

    public MyItemRecyclerViewAdapter2(ArrayList<itemData> dbitemsList, OnNoteListener onNoteListener){
        this.dbitemsList = dbitemsList;
        this.mOnNoteListener = onNoteListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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

    @NonNull
    @Override
    public MyItemRecyclerViewAdapter2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_calorie_db_search, parent, false);
        return new MyViewHolder(itemView, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyItemRecyclerViewAdapter2.MyViewHolder holder, int position) {
        holder.item_name.setText(dbitemsList.get(position).getItemName());
        holder.itemCalories.setText(dbitemsList.get(position).getItemCalories());

    }

    @Override
    public int getItemCount() {
        return dbitemsList.size();
    }

}
