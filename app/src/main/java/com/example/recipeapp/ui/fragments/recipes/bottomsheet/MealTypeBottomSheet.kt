package com.example.recipeapp.ui.fragments.recipes.bottomsheet

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipesBinding
import com.example.recipeapp.databinding.MealTypeBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import java.util.Locale

class MealTypeBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: MealTypeBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = MealTypeBottomSheetBinding.inflate(inflater, container, false)

        setupChipGroups()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun createTagChip(context: Context, chipName: String): Chip {
        return Chip(context, null, R.attr.CustomChipChoice).apply {
            text = chipName
        }
    }

    private fun setupChipGroups(){
        mealTypeList.forEach { mealType ->
            binding.mealTypeChipGroup.addView(createTagChip(binding.root.context, mealType))
        }
        dietTypeList.forEach { dietType ->
            binding.dietTypeChipGroup.addView(createTagChip(binding.root.context, dietType))
        }

        binding.mealTypeChipGroup.check(binding.mealTypeChipGroup[0].id)
        binding.dietTypeChipGroup.check(binding.dietTypeChipGroup[0].id)
    }

    private fun createChipId(chipName: String): String {
        return chipName.lowercase(Locale.getDefault()).replace(" ", "_") + "_chip"
    }

    companion object{
        private val mealTypeList =
            listOf(
                "Main Course",
                "Side Dish",
                "Dessert",
                "Appetizer",
                "Salad",
                "Bread",
                "Breakfast",
                "Soup",
                "Beverage",
                "Sauce",
                "Marinade",
                "Fingerfood",
                "Snack",
                "Drink",
            )

        private val dietTypeList =
            listOf(
                "Gluten Free",
                "Vegan",
                "Vegetarian",
                "Ketogenic",
                "Lacto-Vegetarian",
                "Ovo-Vegetarian",
                "Pescetarian",
                "Paleo",
                "Primal",
                "Low FODMAP",
                "Whole30"
            )
    }
}
