package com.candybytes.taco.interactor

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.candybytes.taco.db.FoodDao
import com.candybytes.taco.domain.data.DataState
import kotlinx.coroutines.flow.flow


/*
*
* A class to handle all food data operations from the db
*
* */
class FoodsInteractor(private val foodDao: FoodDao) {

    /*
    * Get all food OR search for foods
    * */
    fun getFoods(
        searchQuery: String?
    ) = flow {
        try {
            //emit loading state to the UI
            emit(DataState.loading())

            //Start get data paginated
            val pagingConfig = PagingConfig(
                pageSize = 30,
                enablePlaceholders = true
            )

            val foods = Pager(pagingConfig) {
                /*
                * Check if there is a search query
                * TRUE : Search action
                * FALSE : Get All data from the database
                * */
                if (searchQuery.isNullOrEmpty()) {
                    foodDao.getAllPaginated()
                } else {
                    foodDao.getSearchPaginated(searchQuery)
                }
            }

            emit(DataState.success(foods))

        } catch (e: Exception) {
            //emit error here
        }
    }

    /*
    *
    * Get all food using category
    *
    * */

    fun getFoodByCategoryId(
        categoryId: Int
    ) = flow {
        try {
            //emit loading state for the UI upadte
            emit(DataState.loading())

            //Start get data paginated
            val pagingConfig = PagingConfig(
                pageSize = 30,
                enablePlaceholders = true
            )

            val foods = Pager(pagingConfig) {
                foodDao.getFoodByCategory(categoryId)
            }

            //emit data
            emit(DataState.success(foods))

        } catch (e: Exception) {
            //emit error here
        }
    }

}