package com.example.recipeapp.model


import com.google.gson.annotations.SerializedName

data class RecipeModel(
    @SerializedName("results")
    val results: List<Result>
)
