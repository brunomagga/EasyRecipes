package com.devspace.myapplication.detail.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.common.data.RetrofitClient
import com.devspace.myapplication.common.model.RecipesDto
import com.devspace.myapplication.common.model.RecipesResponse
import com.devspace.myapplication.detail.data.DetailService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RecipesDetailViewModel(
    private val detailService: DetailService
) : ViewModel() {

    private val _uiDetail = MutableStateFlow<RecipesDto?>(null)
    val uiDetail: StateFlow<RecipesDto?> = _uiDetail

    fun fetchRecipeDetail(recipeId: String) {
        if (_uiDetail.value == null) {
            viewModelScope.launch(Dispatchers.IO) {
                val response = detailService.getRecipesInformation(recipeId)
                if (response.isSuccessful) {
                    _uiDetail.value = response.body()
                } else {
                    Log.d("RecipesDetailViewModel", "Request Error ${response.errorBody()}")
                }
            }
        }
    }

    fun cleanRecipesId(){
        viewModelScope.launch {
            delay(1000)
        }
        _uiDetail.value = null
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val detailService =
                    RetrofitClient.retrofitInstance.create(DetailService::class.java)
                return RecipesDetailViewModel(
                    detailService
                ) as T
            }
        }
    }
}
