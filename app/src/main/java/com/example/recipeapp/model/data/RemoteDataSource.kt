package com.example.recipeapp.model.data

import com.example.recipeapp.model.RecipeModel
import com.example.recipeapp.model.data.network.RecipesApi
import retrofit2.Response
import javax.inject.Inject


class RemoteDataSource @Inject constructor(
    private val recipesApi: RecipesApi
) {
    suspend fun getRecipes(queryParams: Map<String, String>): Response<RecipeModel> {
        return recipesApi.getRecipes(queryParams)
    }
}