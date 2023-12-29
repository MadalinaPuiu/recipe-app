package com.example.recipeapp.model.data.network

import com.example.recipeapp.util.Constants.Companion.API_KEY
import com.example.recipeapp.util.Constants.Companion.QUERY_API_KEY
import com.example.recipeapp.util.Constants.Companion.QUERY_DIET
import com.example.recipeapp.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.example.recipeapp.util.Constants.Companion.QUERY_NUMBER
import com.example.recipeapp.util.Constants.Companion.QUERY_RECIPE_INFORMATION
import com.example.recipeapp.util.Constants.Companion.QUERY_TYPE

class QueryParametersProvider {
    fun generateSearchQueryParams(): HashMap<String, String> {
        return hashMapOf(
            QUERY_NUMBER to "50",
            QUERY_API_KEY to API_KEY,
            QUERY_TYPE to "snack",
            QUERY_DIET to "vegan",
            QUERY_RECIPE_INFORMATION to "true",
            QUERY_FILL_INGREDIENTS to "true",
        )
    }
}