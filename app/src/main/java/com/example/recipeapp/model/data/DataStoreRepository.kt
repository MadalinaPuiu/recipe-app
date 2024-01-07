package com.example.recipeapp.model.data

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import com.example.recipeapp.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.recipeapp.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.recipeapp.util.Constants.Companion.PREFERENCE_DIET_TYPE
import com.example.recipeapp.util.Constants.Companion.PREFERENCE_DIET_TYPE_ID
import com.example.recipeapp.util.Constants.Companion.PREFERENCE_MEAL_TYPE
import com.example.recipeapp.util.Constants.Companion.PREFERENCE_MEAL_TYPE_ID
import com.example.recipeapp.util.Constants.Companion.PREFERENCE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val selectedMealType = preferencesKey<String>(PREFERENCE_MEAL_TYPE)
        val selectedMealTypeId = preferencesKey<Int>(PREFERENCE_MEAL_TYPE_ID)
        val selectedDietType = preferencesKey<String>(PREFERENCE_DIET_TYPE)
        val selectedDietTypeId = preferencesKey<Int>(PREFERENCE_DIET_TYPE_ID)
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(name = PREFERENCE_NAME)

    val readFilterOptions: Flow<FilterOptions> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val selectedMealType = preferences[PreferenceKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = preferences[PreferenceKeys.selectedMealTypeId] ?: 0
            val selectedDietType = preferences[PreferenceKeys.selectedDietType] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeId = preferences[PreferenceKeys.selectedDietTypeId] ?: 0
            FilterOptions(
                selectedMealType,
                selectedMealTypeId,
                selectedDietType,
                selectedDietTypeId
            )
        }

    suspend fun saveFilterOptions(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedMealType] = mealType
            preferences[PreferenceKeys.selectedMealTypeId] = mealTypeId
            preferences[PreferenceKeys.selectedDietType] = dietType
            preferences[PreferenceKeys.selectedDietTypeId] = dietTypeId
        }
    }

}

data class FilterOptions(
    val selectedMealType: String,
    val selectedMealTypeId: Int,
    val selectedDietType: String,
    val selectedDietTypeId: Int
)