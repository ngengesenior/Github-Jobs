package com.ngengeapps.backbasejobs.models

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class Screen(val route:String){
    object Home:Screen("home")
    object Details:Screen("details")
}

@HiltViewModel
class NavigationViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle): ViewModel() {



}