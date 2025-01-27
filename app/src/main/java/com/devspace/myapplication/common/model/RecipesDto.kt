package com.devspace.myapplication.common.model

import kotlinx.serialization.Serializable

@Serializable
data class RecipesDto(
    val id: Int,
    val title: String,
    val image: String,
    val summary: String
)

