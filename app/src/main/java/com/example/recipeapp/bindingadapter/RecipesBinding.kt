package com.example.recipeapp.bindingadapter

import android.media.Image
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.recipeapp.model.RecipeModel
import com.example.recipeapp.model.data.NetworkResult
import com.example.recipeapp.model.data.db.RecipesEntity

class RecipesBinding {
    companion object {
        @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
        @JvmStatic
        fun errorViewVisibility(
            view: View,
            apiResponse: NetworkResult<RecipeModel>?,
            database: List<RecipesEntity>?
        ) {
            if (apiResponse != null && database != null && apiResponse is NetworkResult.Error && database.isEmpty()) {
                if (view is TextView) view.text = apiResponse.message
                view.visibility = View.VISIBLE
            } else {
                view.visibility = View.INVISIBLE
            }
        }
    }
}