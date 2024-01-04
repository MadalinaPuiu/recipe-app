package com.example.recipeapp.model.data.network

import com.example.recipeapp.model.RecipeModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RecipesApi {
    @GET("/recipes/complexSearch")
    suspend fun getRecipes(@QueryMap queryParams: Map<String, String>): Response<RecipeModel>
}
