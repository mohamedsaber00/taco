package com.candybytes.taco.ui.events


/*
* A class to have all possible event in Category section
* */
sealed class FoodEvent{

    object  GetFoodsEvent: FoodEvent()

    data class SearchFoodEvent(val query :String) : FoodEvent()

    data class GetFoodByCategory(val categoryId: Int) : FoodEvent()
}