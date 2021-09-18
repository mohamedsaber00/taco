package com.candybytes.taco.ui.vm

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.candybytes.taco.api.TacoService
import com.candybytes.taco.ui.util.toList
import com.candybytes.taco.vo.Category
import com.candybytes.taco.vo.Food
import com.candybytes.taco.vo.Nutrient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(private val tacoService: TacoService) : ViewModel() {

    /*
    * Food LiveData
    */
    val foodDetails: LiveData<Food>
        get() = _foodDetails

    private val _foodDetails = MutableLiveData<Food>()


    /*
    *  Category LiveData
    */

    val category: LiveData<Category>
        get() = _category

    private val _category = MutableLiveData<Category>()

    /*
     *  Nutrient LiveData
     */

    val nutrients: LiveData<List<Nutrient>>
        get() = _nutrients

    private val _nutrients = MutableLiveData<List<Nutrient>>()


    //imageUri used for ActivityResultContract
    val selectedImageUri: LiveData<Uri>
        get() = _selectedImageUri

    private val _selectedImageUri = MutableLiveData<Uri>()


    /*
     *  called once the fragment created to pass the navigation argument to LiveData
     */
    fun setupFoodDetails(food: Food) {
        _foodDetails.value = food

        //update category asynchronous
        updateCategory(food.categoryId)

        //an extension function to get list of _nutrients from hashMap then pass to the LiveData
        _nutrients.value = food.nutrients.toList()
    }


    /*
    *  get category async from the API and post value to LiveData
    *
    *  "This is not the best solution but because of the tight time frame"
    * */
    private fun updateCategory(id: Int) {
        viewModelScope.launch {
            flow {
                emit(tacoService.getCategoryAsync(id))
            }.collectLatest {
                //  API returns a list with a single item use first() to get it
                _category.value = it.first()
            }
        }
    }

    fun setImageUri(uri: Uri?) {
        //set image uri to the liveData only if not null check for null safety
        uri?.let {
            _selectedImageUri.value = it
        }
        //TODO : Save the URI in the database so we can retrieve it and show it with the other details
    }


}