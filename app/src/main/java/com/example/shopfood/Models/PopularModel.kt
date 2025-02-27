package com.example.shopfood.Models

class PopularModel{

    private var foodImage : Int? = null
    private var foodName : String = ""
    private var foodPrice : String = ""
    private var foodCount : Int = 1

    constructor()
    constructor(FoodImage: Int?, FoodName: String, FoodPrice: String, foodCount : Int) {
        this.foodImage = FoodImage
        this.foodName = FoodName
        this.foodPrice = FoodPrice
        this.foodCount = foodCount
    }

    fun getFoodCount() : Int {
        return foodCount
    }

    fun setFoodCount(foodCount: Int){
        this.foodCount = foodCount
    }

     fun getFoodImage() : Int? {
        return foodImage
    }

     fun getFoodName() : String{
        return foodName
    }

     fun getFoodPrice() : String{
        return foodPrice
    }
     fun setFoodImage(foodImage: Int?){
        this.foodImage = foodImage
    }
     fun setFootName(foodName : String){
        this.foodName = foodName
    }
     fun setFootPrice(foodPrice : String){
        this.foodPrice = foodPrice
    }

}