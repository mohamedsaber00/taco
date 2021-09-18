package com.candybytes.taco.ui.vm


import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
     private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val showBottomBar = MutableLiveData(true)


}
