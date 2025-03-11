package com.example.healthinthishouse;

public class itemData {
    String itemName;
    String itemCalories;
    String itemDescription;
    String itemInstructions;
    String itemIngredients;
    int itemImage;

    public itemData(String itemName, String itemCalories){
        this.itemName = itemName;
        this.itemCalories = itemCalories;
    }

    public itemData(String itemName, String itemCalories, String itemDescription){
        this.itemName = itemName;
        this.itemCalories = itemCalories;
        this.itemDescription = itemDescription;
    }

    public itemData(String itemName, String itemCalories, String itemDescription, String itemInstructions){
        this.itemName = itemName;
        this.itemCalories = itemCalories;
        this.itemDescription = itemDescription;
        this.itemInstructions = itemInstructions;
    }

    public itemData(String itemName, String itemCalories, String itemDescription, String itemInstructions, String itemIngredients){
        this.itemName = itemName;
        this.itemCalories = itemCalories;
        this.itemDescription = itemDescription;
        this.itemInstructions = itemInstructions;
        this.itemIngredients = itemIngredients;
    }

    public String getItemName(){
        return this.itemName;
    }

    public String getItemCalories(){
        return this.itemCalories;
    }

    public String getItemDescription() { return this.itemDescription; }

    public String getItemInstructions() { return this.itemInstructions; }

    public String getItemIngredients() {return this.itemIngredients; }

}
