package com.example.recipeapp.ui.fragments.recipes.bottomsheet

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.databinding.MealTypeBottomSheetBinding
import com.example.recipeapp.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.recipeapp.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.recipeapp.viewmodel.RecipeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.Locale

class MealTypeBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: MealTypeBottomSheetBinding
    private lateinit var recipeViewModel: RecipeViewModel
    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeViewModel = ViewModelProvider(requireActivity())[RecipeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = MealTypeBottomSheetBinding.inflate(inflater, container, false)

        setupChipGroups()
        setupApplyButton()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun createTagChip(context: Context, chipName: String, id: Int): Chip {
        return Chip(context, null, R.attr.CustomChipChoice).apply {
            text = chipName
            isCheckable = true
            setId(id)
        }
    }

    private fun setupChipGroups() {

        recipeViewModel.readFilterOptions.observe(viewLifecycleOwner) { value ->
            mealTypeChip = value.selectedMealType
            dietTypeChip = value.selectedDietType
            updateChips(value.selectedMealTypeId, binding.mealTypeChipGroup)
            updateChips(value.selectedDietTypeId, binding.dietTypeChipGroup)
        }

        mealTypeList.forEachIndexed { index, mealType ->
            binding.mealTypeChipGroup.addView(createTagChip(binding.root.context, mealType, index))
        }
        dietTypeList.forEachIndexed { index, dietType ->
            binding.dietTypeChipGroup.addView(createTagChip(binding.root.context, dietType, index))
        }

        if (binding.mealTypeChipGroup.checkedChipId == -1) updateChips(
            mealTypeChipId,
            binding.mealTypeChipGroup
        )

        if (binding.dietTypeChipGroup.checkedChipId == -1) updateChips(
            dietTypeChipId,
            binding.dietTypeChipGroup
        )

        binding.mealTypeChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val selectedChipId = checkedIds.last()
            selectedChipId?.let {
                val chip = group.findViewById<Chip>(it)
                val selectedMealType = chip.text.toString().lowercase(Locale.getDefault())
                mealTypeChip = selectedMealType
                mealTypeChipId = it
            }
        }

        binding.dietTypeChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val selectedChipId = checkedIds[0]
            selectedChipId?.let {
                val chip = group.findViewById<Chip>(it)
                val selectedMealType = chip.text.toString().lowercase(Locale.getDefault())
                dietTypeChip = selectedMealType
                dietTypeChipId = it
            }
        }
    }

    private fun updateChips(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                chipGroup.check(chipId)
            } catch (e: Exception) {
                Log.e("MealTypeBottomSheet", e.message.toString())
            }
        } else {
            chipGroup.check(chipGroup[0].id)
        }
    }

    private fun setupApplyButton() {
        binding.applyButton.setOnClickListener {
            recipeViewModel.saveFilterOptions(
                mealTypeChip,
                mealTypeChipId,
                dietTypeChip,
                dietTypeChipId
            )
            val action =
                MealTypeBottomSheetDirections.actionMealTypeBottomSheetToRecipesFragment(true)
            findNavController().navigate(action)
        }
    }

    companion object {
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
