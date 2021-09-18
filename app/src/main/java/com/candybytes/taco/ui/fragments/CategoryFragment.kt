package com.candybytes.taco.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.candybytes.taco.databinding.FragmentCategoryBinding
import com.candybytes.taco.ui.events.FoodEvent
import com.candybytes.taco.ui.adapters.FoodListAdapter
import com.candybytes.taco.ui.vm.SearchFoodViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CategoryFragment : Fragment() {


    private lateinit var binding: FragmentCategoryBinding


    //get argument use navigation safe-args
    private val categoryFragmentArgs: CategoryFragmentArgs by navArgs()


    //Use the same viewModel as SearchFoodFragment they are have the same operations
    private val viewModel: SearchFoodViewModel by viewModels()

    //use the same adapter of the food list
    private lateinit var foodListAdapter: FoodListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(layoutInflater, container, false).apply {
            viewModel = this@CategoryFragment.viewModel
            lifecycleOwner = this@CategoryFragment.viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //send event to get all food in category and pass the id of the category
        viewModel.onNewEvent(FoodEvent.GetFoodByCategory(categoryFragmentArgs.category.id))

        //initializing adapter and recyclerview
        setupRecyclerAdapter()

        //subscribe as observer for viewModel LiveData
        subscribeObservers()
    }

    /* initialize the adapter and pass the action when item is clicked
     * The same as in FoodSearchFragment
     */
    private fun setupRecyclerAdapter() {
        foodListAdapter = FoodListAdapter() { food ->
            val action = CategoryFragmentDirections.actionCategoryFragmentToFoodFragment(
                food = food,
                foodTitle = food.description
            )
            findNavController().navigate(action)
        }
        binding.foodsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = foodListAdapter
        }
    }

    /*
    * Subscribe to LiveDat Observer to update the UI once the data changes
    */
    private fun subscribeObservers() {
        viewModel.foodList.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                //update the adapter with the new list
                foodListAdapter.submitData(lifecycle, it)
            }
        }
    }

}
