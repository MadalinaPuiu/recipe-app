package com.example.recipeapp.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.adapter.RecipesAdapter
import com.example.recipeapp.databinding.FragmentRecipesBinding
import com.example.recipeapp.model.data.NetworkResult
import com.example.recipeapp.util.observeOnce
import com.example.recipeapp.viewmodel.RecipeViewModel
import com.todkars.shimmer.ShimmerRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!
    private val recipeViewModel: RecipeViewModel by viewModels()
    private lateinit var recyclerView: ShimmerRecyclerView
    private val adapter by lazy { RecipesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.recipeViewModel = recipeViewModel

        recyclerView = binding.recyclerView
        setupRecyclerView()
        fetchData()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setupRecyclerView() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showSimmerEffect()
    }

    private fun loadCachedData() {
        lifecycleScope.launch {
            recipeViewModel.readRecipes.observe(viewLifecycleOwner) { database ->
                Log.d("RecipesFragment", "Read data from db called")
                if (database.isNotEmpty()) {
                    adapter.setData(database[0].foodRecipe)
                    hideSimmerEffect()
                }
            }
        }
    }

    private fun fetchData() {
        lifecycleScope.launch {
            recipeViewModel.readRecipes.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    Log.d("RecipesFragment", "Read data from db called")
                    adapter.setData(database[0].foodRecipe)
                    hideSimmerEffect()
                } else {
                    Log.e("RecipesFragment", "Read data from api called")
                    requestApiData()
                }
            }
        }
    }

    private fun requestApiData() {
        recipeViewModel.getRecipes()
        recipeViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideSimmerEffect()
                    response.data?.let { adapter.setData(it) }
                }

                is NetworkResult.Error -> {
                    hideSimmerEffect()
                    loadCachedData()
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_LONG).show()
                }

                is NetworkResult.Loading -> {
                    showSimmerEffect()
                }
            }
        }
    }

    private fun showSimmerEffect() {
        recyclerView.showShimmer()
    }

    private fun hideSimmerEffect() {
        recyclerView.hideShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
