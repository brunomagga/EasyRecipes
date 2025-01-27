package com.devspace.myapplication.List.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.List.data.ListService
import com.devspace.myapplication.common.data.RetrofitClient
import com.devspace.myapplication.common.model.RecipesDto
import com.devspace.myapplication.common.model.RecipesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class RecipesListViewModel(
    listService: ListService
) : ViewModel() {

    private val _uiGetRecipes = MutableStateFlow<List<RecipesDto>>(emptyList())
    val uiGetRecipes: StateFlow<List<RecipesDto>> = _uiGetRecipes

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getRandom()
            if (response.isSuccessful) {
                Log.d("Response", "error.message ${response}")
                _uiGetRecipes.value = response.body()?.recipes ?: emptyList()

            } else {
                Log.d("RecipesListViewModel", "Request Error ${response.errorBody()}")
            }
        }
    }
    companion object{
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val listService = RetrofitClient.retrofitInstance.create(ListService::class.java)
                return RecipesListViewModel(
                    listService
                ) as T
            }
        }
    }
}