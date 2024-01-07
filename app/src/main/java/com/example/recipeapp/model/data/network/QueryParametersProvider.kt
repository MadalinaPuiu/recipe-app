package com.example.recipeapp.model.data.network

import com.example.recipeapp.model.data.DataStoreRepository
import com.example.recipeapp.util.Constants.Companion.API_KEY
import com.example.recipeapp.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.recipeapp.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.recipeapp.util.Constants.Companion.DEFAULT_SEARCH_NUMBER
import com.example.recipeapp.util.Constants.Companion.QUERY_API_KEY
import com.example.recipeapp.util.Constants.Companion.QUERY_DIET
import com.example.recipeapp.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.example.recipeapp.util.Constants.Companion.QUERY_NUMBER
import com.example.recipeapp.util.Constants.Companion.QUERY_RECIPE_INFORMATION
import com.example.recipeapp.util.Constants.Companion.QUERY_TYPE
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@ActivityRetainedScoped
class QueryParametersProvider @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    val readFilterOptions = dataStoreRepository.readFilterOptions

    suspend fun saveFilterOptions(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) = dataStoreRepository.saveFilterOptions(mealType, mealTypeId, dietType, dietTypeId)

    fun generateSearchQueryParams(scope: CoroutineScope): HashMap<String, String> {

        scope.launch {
            readFilterOptions.collect { value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        return hashMapOf(
            QUERY_NUMBER to DEFAULT_SEARCH_NUMBER,
            QUERY_API_KEY to API_KEY,
            QUERY_TYPE to mealType,
            QUERY_DIET to dietType,
            QUERY_RECIPE_INFORMATION to true.toString(),
            QUERY_FILL_INGREDIENTS to true.toString(),
        )
    }
}
