package com.candybytes.taco.ui.vm

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.candybytes.taco.interactor.FoodsInteractor
import com.candybytes.taco.ui.events.FoodEvent
import com.candybytes.taco.vo.Food
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class SearchFoodViewModel @Inject constructor(
    private val foodsInteractor: FoodsInteractor,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val foodList: LiveData<PagingData<Food>>
        get() = _foodList
    private val _foodList = MutableLiveData<PagingData<Food>>()

    //LiveData for loading to observe the loading state so we can show progressbar on The UI
    val loading: LiveData<Boolean>
        get() = _loading
    private val _loading = MutableLiveData<Boolean>(false)


    /*
     * handle new event
     */
    fun onNewEvent(event: FoodEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is FoodEvent.GetFoodsEvent -> {
                        //pass null to get all foods
                        getFoods(null)
                    }
                    is FoodEvent.SearchFoodEvent -> {
                        //pass the searchQuery to search for it
                        getFoods(event.query)
                    }
                    is FoodEvent.GetFoodByCategory -> {
                        //get all foods in a specific category
                        getFoodByCategory(event.categoryId)
                    }
                }
            } catch (e: Exception) {
                Timber.e("launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
        }
    }

    private fun getFoods(searchQuery: String?) {
        /*
         if searchQuery is null It's an get All food Event otherwise it's a search event
         */
        foodsInteractor.getFoods(searchQuery).onEach { dataState ->

            //update the loading
            _loading.postValue(dataState.loading)

            //update  data
            dataState.data?.let { data ->
                data.flow.cachedIn(viewModelScope).collectLatest {
                    //pass the data from the flow to the liveData
                    _foodList.postValue(it)
                }
            }

            //update the error
            dataState.error?.let { error ->
                Timber.e("getFoods: ${error}")
                //TODO ("handle error")
            }
        }.launchIn(viewModelScope)
    }

    private fun getFoodByCategory(categoryId: Int) {
        foodsInteractor.getFoodByCategoryId(categoryId).onEach { dataState ->
            //update the loading
            _loading.postValue(dataState.loading)

            //update  data
            dataState.data?.let { data ->
                data.flow.cachedIn(viewModelScope).collectLatest {
                    //pass the data from the flow to the liveData
                    _foodList.postValue(it)
                }
            }

            //update the error
            dataState.error?.let { error ->
                //TODO ("handle error")
            }
        }.launchIn(viewModelScope)
    }
}
