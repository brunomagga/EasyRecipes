package com.devspace.myapplication

import com.google.gson.annotations.SerializedName

data class RecipesDto (
    val id: Int,
    val title: String,
    val image: String?,
    val summary: String
){
    val posterFullPath: String?
        get() = image?.let {"https://spoonacular.com/recipeImages/$it"
        }

}

