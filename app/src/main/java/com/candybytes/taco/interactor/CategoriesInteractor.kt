package com.candybytes.taco.interactor

import com.candybytes.taco.api.TacoService
import com.candybytes.taco.db.FoodDao
import com.candybytes.taco.domain.data.DataState
import com.candybytes.taco.vo.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber



/*
*
* A class to handle all category data operations from the db or api
*
* */
class CategoriesInteractor(
    private val tacoService: TacoService,
    private val foodDao: FoodDao
) {

    //An Execute function to get all Categories from the API
    fun execute(
    ): Flow<DataState<List<Category>>> = flow {
        try {
            //emit a Loading state -helpful if we want to show a progressbar-
            emit(DataState.loading())

            //get all categories from the API
            val categories = tacoService.getAllCategoriesAsync()

            //emit the data with success
            emit(DataState.success(categories))

        } catch (e: Exception) {
            //emit an Error state so we can show an alert on the UI
            Timber.e(e)
        }
    }

    //Count available food in a specific category
    fun getFoodCount(category: Category) = flow {
        try {
            //get food count and assign it to category
            category.foodCount = foodDao.getFoodCount(category.id)
            //emit the new category after adding the foodCount
            emit(DataState.success(category))
        } catch (e: Exception) {
            //emit an Error state so we can show an alert on the UI
            Timber.e(e)
        }

    }

}


