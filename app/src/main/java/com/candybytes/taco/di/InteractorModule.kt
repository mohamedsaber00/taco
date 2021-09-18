package com.candybytes.taco.di

import com.candybytes.taco.api.TacoService
import com.candybytes.taco.db.FoodDao
import com.candybytes.taco.interactor.CategoriesInteractor
import com.candybytes.taco.interactor.FoodsInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object InteractorModule {


    /*
   * provide [CategoriesInteractor] to handle category data
   * */
    @ViewModelScoped
    @Provides
    fun provideCategoriesInteractor(tacoService: TacoService, foodDao: FoodDao): CategoriesInteractor {
        return CategoriesInteractor(tacoService, foodDao)
    }


    /*
    * provide [FoodsInteractor] to handle food data
    * */
    @ViewModelScoped
    @Provides
    fun providFoodsInteractor(foodDao: FoodDao): FoodsInteractor {
        return FoodsInteractor(foodDao)
    }
}