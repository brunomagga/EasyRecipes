package com.devspace.myapplication

import com.google.gson.annotations.SerializedName

data class RecipesDto (
    val id: Int,
    val title: String,
    val image: String,
    @SerializedName("poster_path")
    val posterPath: String,
    val summary: String
){
    val posterFullPath: String
        get() = "https://spoonacular.com/recipeImages/635350-240x150.jpg$posterPath"

}

