package com.candybytes.taco.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.candybytes.taco.databinding.FragmentCategoryListBinding
import com.candybytes.taco.ui.adapters.CategoryListAdapter
import com.candybytes.taco.ui.events.CategoryEvent
import com.candybytes.taco.ui.vm.CategoriesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@AndroidEntryPoint
class CategoryListFragment : Fragment() {

    private val viewModel: CategoriesViewModel by viewModels()

    private lateinit var categoryListAdapter: CategoryListAdapter

    private lateinit var binding: FragmentCategoryListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryListBinding.inflate(layoutInflater, container, false).apply {
            viewModel = this@CategoryListFragment.viewModel
            lifecycleOwner = this@CategoryListFragment.viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initializing adapter and recyclerview
        setupRecyclerAdapter()
        //subscribe as observer for viewModel LiveData
        subscribeObservers()
    }

    /*
    * Subscribe to LiveDat Observer to update the UI once the data changes
    */
    private fun subscribeObservers() {
        viewModel.categoryList.observe(viewLifecycleOwner) {
            //update the adapter with the new list
            categoryListAdapter.submitList(it)
            //Helps to update the adapter after adding foodCounts
            categoryListAdapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        //refresh category list
        viewModel.onNewEvent(CategoryEvent.GetCategoriesEvent)
    }

    private fun setupRecyclerAdapter() {
        categoryListAdapter = CategoryListAdapter { category ->
            //category on item clicked : navigate to the details page
            val action = CategoryListFragmentDirections.actionCategoriesFragmentToCategoryFragment(
                category = category,
                categoryTitle = category.name
            )
            findNavController().navigate(action)
        }
        //init RecyclerView
        binding.categoriesRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(
                2, // 2 columns
                StaggeredGridLayoutManager.VERTICAL
            )
            adapter = categoryListAdapter
        }
    }


}
