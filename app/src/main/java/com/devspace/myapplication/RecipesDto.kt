package com.devspace.myapplication

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RecipesDto(
    val id: Int,
    val title: String,
    val image: String,
    val summary: String
)

