package com.example.recipeapp.util

class Constants {
    companion object{
        const val API_KEY = "c802465044bc4c0cad3d22bd87fe1f29"
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

        // Prefs
        const val DEFAULT_MEAL_TYPE = "main course"
        const val DEFAULT_DIET_TYPE = "gluten free"
        const val DEFAULT_SEARCH_NUMBER = "50"
        const val PREFERENCE_MEAL_TYPE = "mealType"
        const val PREFERENCE_MEAL_TYPE_ID = "mealTypeId"
        const val PREFERENCE_DIET_TYPE = "dietType"
        const val PREFERENCE_DIET_TYPE_ID = "dietTypeId"
        const val PREFERENCE_NAME = "recipes_preferences"
    }
}