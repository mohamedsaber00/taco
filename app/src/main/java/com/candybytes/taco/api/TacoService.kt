package com.candybytes.taco.api

import com.candybytes.taco.vo.Category
import com.candybytes.taco.vo.Food
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * REST API access points for taco service
 * see https://taco-food-api.herokuapp.com/ for more information
 */
interface TacoService {

    /**
     * Request all available categories
     */
    @GET("api/v1/category")
    suspend fun getAllCategoriesAsync(): List<Category>

    /*
     *  Request a specific category
     */
    @GET("api/v1/category/{id}")
    suspend fun getCategoryAsync(@Path("id") id: Int): List<Category>

    /**
     * Request all available food
     */
    @GET("api/v1/food")
    suspend fun getAllFoodAsync(): List<Food>


}
