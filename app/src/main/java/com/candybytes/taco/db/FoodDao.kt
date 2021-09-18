package com.candybytes.taco.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.candybytes.taco.vo.Food

/**
 * Interface for database access for Food related operations.
 */
@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsync(food: Food)

    /*
   * Get all using pagination
   * */
    @Query("SELECT * FROM food")
    fun getAllPaginated(): PagingSource<Int, Food>

    /*
    * Search by description using pagination
    * */
    @Query("SELECT * FROM food WHERE description LIKE '%' || :searchQuery || '%'")
    fun getSearchPaginated(searchQuery: String): PagingSource<Int, Food>

    /*
  * Get all without pagination
  * */
    @Query("SELECT * FROM food")
    suspend fun getAllAsync(): List<Food>


    /*
    * Get all foods in the same category paginated
    * */
    @Query("SELECT * FROM food WHERE categoryId = :categoryId")
    fun getFoodByCategory(categoryId: Int): PagingSource<Int, Food>

    /*
   * Get number of food in specific category
   * */
    @Query("SELECT COUNT(*) FROM food WHERE categoryId = :categoryId")
    suspend fun getFoodCount(categoryId: Int): Int


}
