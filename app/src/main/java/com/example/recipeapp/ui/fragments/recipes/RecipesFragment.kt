package com.example.recipeapp.ui.fragments.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.adapter.RecipesAdapter
import com.example.recipeapp.model.data.NetworkResult
import com.example.recipeapp.viewmodel.RecipeViewModel
import com.todkars.shimmer.ShimmerRecyclerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private val recipeViewModel: RecipeViewModel by viewModels()
    private lateinit var recyclerView: ShimmerRecyclerView
    private val adapter by lazy { RecipesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipes, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        setupRecyclerView()
        requestApiData()
        // Inflate the layout for this fragment
        return view
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
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_LONG).show()
                }

                is NetworkResult.Loading -> {
                    showSimmerEffect()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showSimmerEffect()
    }

    private fun showSimmerEffect() {
        recyclerView.showShimmer()
    }

    private fun hideSimmerEffect() {
        recyclerView.hideShimmer()
    }
}
