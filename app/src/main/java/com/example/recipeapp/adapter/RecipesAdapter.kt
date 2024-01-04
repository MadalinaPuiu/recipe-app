package com.example.recipeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.RecipeItemBinding
import com.example.recipeapp.model.RecipeModel
import com.example.recipeapp.model.Result
import com.example.recipeapp.util.RecipesDiffUtil

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>() {

    private var recipes = emptyList<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder.from(parent)
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    fun setData(model: RecipeModel) {
        val recipesDiffUtil = RecipesDiffUtil(recipes, model.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = model.results
        diffUtilResult.dispatchUpdatesTo(this)
    }

    class RecipeViewHolder(private val binding: RecipeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.result = result
            //update layout
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): RecipeViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipeItemBinding.inflate(layoutInflater, parent, false)
                return RecipeViewHolder(binding)
            }
        }
    }
}
