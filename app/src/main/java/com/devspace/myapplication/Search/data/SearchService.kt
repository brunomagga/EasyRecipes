package com.devspace.myapplication.Search.data

import com.devspace.myapplication.Search.model.SearchRecipesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("/recipes/complexSearch?")
    fun searchRecipes(@Query("query") query: String): Call<SearchRecipesResponse>
}