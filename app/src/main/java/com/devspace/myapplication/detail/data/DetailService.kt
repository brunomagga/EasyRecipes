package com.devspace.myapplication.detail.data

import com.devspace.myapplication.common.model.RecipesDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailService {

    @GET("recipes/{id}/information?includeNutrition=false")
    suspend fun getRecipesInformation(@Path("id") recipeId: String): Response<RecipesDto>
}