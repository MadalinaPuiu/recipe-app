package com.example.recipeapp.model.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipeapp.model.RecipeModel
import com.example.recipeapp.util.Constants.Companion.DATABASE_TABLE

@Entity(tableName = DATABASE_TABLE)
class RecipesEntity(
    var foodRecipe: RecipeModel
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}
