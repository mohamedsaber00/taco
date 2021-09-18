package com.candybytes.taco.ui.vm

import androidx.lifecycle.*
import com.candybytes.taco.interactor.CategoriesInteractor
import com.candybytes.taco.ui.events.CategoryEvent
import com.candybytes.taco.vo.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@InternalCoroutinesApi
@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoriesInteractor: CategoriesInteractor,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    /*
    * categories list LiveData
    * */
    val categoryList: LiveData<List<Category>>
        get() = _categoryList
    private val _categoryList = MutableLiveData<List<Category>>(emptyList())

    //LiveData for loading to observe the loading state so we can show progressbar on The UI
    val loading = MutableLiveData(false)


    /*
    * handle new event
    */

    fun onNewEvent(event: CategoryEvent) {
        viewModelScope.launch {
            try {
                //set every Event for it corresponding action
                when (event) {
                    is CategoryEvent.GetCategoriesEvent -> {
                        getCategories()
                    }
                }
            } catch (e: Exception) {
                Timber.e("launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
        }
    }

    //Get all Categories from the API
    private fun getCategories() {
        categoriesInteractor.execute().onEach { dataState ->
            //post emitted loading state to the liveData
            loading.postValue(dataState.loading)

            //post emitted data to LiveData
            dataState.data?.let { data ->
                _categoryList.postValue(data)
                getFoodCount(data)
            }
            //handle error
            dataState.error?.let { error ->
                Timber.e("getCategories: ${error}")
                //TODO ("handle error")
            }
        }.launchIn(viewModelScope)
    }

    /*
    * Count food in Categories buy looping in categoriesList and get food count in each item
    * This a tricky solution. not the best way but it's times saving and will do the job
    * */
    private fun getFoodCount(categoriesList: List<Category>) {
        //launch a parent CoroutineScope with IO dispatcher to do the job async..
        CoroutineScope(IO).launch {
            //launch a child job to get foods count of every category from db
            val job = launch {
                categoriesList.onEach { category ->
                    categoriesInteractor.getFoodCount(category).collectLatest { dataState ->
                        //newCategory is the same as category but with foodCount field
                        dataState.data?.let { newCategory ->
                            //add foodCount to categoriesList
                            category.foodCount = newCategory.foodCount
                        }
                    }
                }
            }
            //To prevent on OnCompletion being called without finishing the entire job
            job.join()
            job.invokeOnCompletion {
                //post categoriesList to the liveData
                _categoryList.postValue(categoriesList)
            }
        }

    }
}




