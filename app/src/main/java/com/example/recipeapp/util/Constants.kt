package com.example.recipeapp.util

class Constants {
    companion object{
        const val API_KEY = "your_key"
        const val BASE_URL = "https://api.spoonacular.com"
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET= "diet"
        const val QUERY_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"

        //Room DB
        const val DATABASE_NAME = "recipes_database"
        const val DATABASE_TABLE = "recipes_table"
    }
}