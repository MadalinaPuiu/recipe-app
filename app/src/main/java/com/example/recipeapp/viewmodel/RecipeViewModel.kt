package com.example.recipeapp.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.model.RecipeModel
import com.example.recipeapp.model.data.NetworkResult
import com.example.recipeapp.model.data.RecipeRepository
import com.example.recipeapp.model.data.db.RecipesEntity
import com.example.recipeapp.model.data.network.QueryParametersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository,
    private val queryParametersProvider: QueryParametersProvider,
    application: Application
) : AndroidViewModel(application) {

    /** ROOM DATABASE **/
    var readRecipes: LiveData<List<RecipesEntity>> = repository.localDS.readRecipes().asLiveData()

    private fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.localDS.insertRecipes(recipesEntity)
        }

    private fun offlineCacheRecipes(recipe: RecipeModel) {
        val recipesEntity = RecipesEntity(recipe)
        insertRecipes(recipesEntity)
    }

    fun saveFilterOptions(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        viewModelScope.launch {
            queryParametersProvider.saveFilterOptions(mealType, mealTypeId, dietType, dietTypeId)
        }
    }

    val readFilterOptions = queryParametersProvider.readFilterOptions.asLiveData()



    /** RETROFIT **/
    var recipesResponse: MutableLiveData<NetworkResult<RecipeModel>> = MutableLiveData()

    fun getRecipes() = viewModelScope.launch {
        val params = queryParametersProvider.generateSearchQueryParams(viewModelScope)
        getRecipeSafeCall(params)
    }

    private suspend fun getRecipeSafeCall(queryParams: Map<String, String>) {
        recipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remoteDS.getRecipes(queryParams)
                recipesResponse.value = handleRecipesResponse(response)

                recipesResponse.value?.let { networkResponse ->
                    networkResponse.data?.let {
                        offlineCacheRecipes(it)
                    }
                }

            } catch (e: Exception) {
                recipesResponse.value = NetworkResult.Error("Recipes not found")
            }
        } else {
            recipesResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private fun handleRecipesResponse(response: Response<RecipeModel>): NetworkResult<RecipeModel> {
        val responseBody = response.body()
        return when {
            response.message().toString().contains("timeout") -> NetworkResult.Error("Timeout")
            response.code() == 402 -> NetworkResult.Error("API Key Limited")
            responseBody == null || responseBody.results.isEmpty() -> NetworkResult.Error("Recipes not found")
            response.isSuccessful -> NetworkResult.Success(responseBody)
            else -> NetworkResult.Error(response.message())
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false

        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}