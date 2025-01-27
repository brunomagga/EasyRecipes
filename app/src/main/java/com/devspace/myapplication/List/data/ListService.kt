package com.devspace.myapplication.List.data

import com.devspace.myapplication.common.model.RecipesResponse
import com.devspace.myapplication.Search.model.SearchRecipesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ListService {

    @GET("recipes/random?number=20")
    suspend fun getRandom(): Response<RecipesResponse>


}