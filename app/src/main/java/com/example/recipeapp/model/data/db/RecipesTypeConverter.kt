package com.example.recipeapp.model.data.db

import androidx.room.TypeConverter
import com.example.recipeapp.model.RecipeModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun recipeToString(recipe: RecipeModel): String = gson.toJson(recipe)

    @TypeConverter
    fun stringToRecipe(data: String): RecipeModel =
        gson.fromJson(data, object : TypeToken<RecipeModel>() {}.type)
}
