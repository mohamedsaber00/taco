package com.candybytes.taco.ui.fragments

import android.app.SearchManager
import android.content.Context.SEARCH_SERVICE
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.candybytes.taco.R
import com.candybytes.taco.databinding.FragmentSearchFoodBinding
import com.candybytes.taco.ui.events.FoodEvent
import com.candybytes.taco.ui.adapters.FoodListAdapter
import com.candybytes.taco.ui.vm.SearchFoodViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SearchFoodFragment : Fragment() {

    private lateinit var binding: FragmentSearchFoodBinding

    private val viewModel: SearchFoodViewModel by viewModels()

    private lateinit var foodListAdapter: FoodListAdapter

    private lateinit var searchView: SearchView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchFoodBinding.inflate(layoutInflater, container, false).apply {
            viewModel = this@SearchFoodFragment.viewModel
            //set it to viewLifecycleOwner to avoid memoryLeak
            lifecycleOwner = this@SearchFoodFragment.viewLifecycleOwner
        }

        //to receive menu related callbacks
        setHasOptionsMenu(true)

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
    * Attach toolbar menu to the toolbar
    * */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        //initialize searchView
        initSearchView(menu)
    }


    override fun onResume() {
        super.onResume()
        // refresh food list
        viewModel.onNewEvent(FoodEvent.GetFoodsEvent)

    }


    /*
    *
    * Subscribe to LiveDat Observer to update the UI once the data changes
    *
    * */
    private fun subscribeObservers() {
        viewModel.foodList.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                //update the adapter with the new list
                foodListAdapter.submitData(lifecycle,it)

            }
        }
    }

    private fun setupRecyclerAdapter() {

        /* initialize the adapter and pass the action when item is clicked
         *  set action for on item click to navigate to the details fragment
         *   pass  @food using safe-args
         *   pass @foodTitle to change the toolbar title using navigation arguments
        */

        foodListAdapter = FoodListAdapter(){food ->
            val action = SearchFoodFragmentDirections.actionSearchFoodFragmentToFoodFragment(
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

    private fun initSearchView(menu: Menu){
        activity?.apply {
            //set searchView properties
            val searchManager: SearchManager = getSystemService(SEARCH_SERVICE) as SearchManager
            searchView = menu.findItem(R.id.action_search).actionView as SearchView
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchView.maxWidth = Integer.MAX_VALUE
            searchView.setIconifiedByDefault(true)
            searchView.isSubmitButtonEnabled = true
        }

        // Make the searchView responsive to the enter click on the keyboard
        val searchPlate = searchView.findViewById(R.id.search_src_text) as EditText
        searchPlate.setOnEditorActionListener { v, actionId, event ->

            //Enter Clicked
            if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED
                || actionId == EditorInfo.IME_ACTION_SEARCH ) {
                val searchQuery = v.text.toString()
                //pass the query to viewModel to start searching
                Timber.d( "SearchView: start searching: ${searchQuery}")
                viewModel.onNewEvent(FoodEvent.SearchFoodEvent(searchQuery))
            }
            true
        }

        // Make the searchView responsive to the search button in the toolbar
        val searchButton = searchView.findViewById(R.id.search_go_btn) as View
        searchButton.setOnClickListener {
            val searchQuery = searchPlate.text.toString()
            Timber.d( "SearchView: start searching: ${searchQuery}")
            //pass the query to viewModel to start searching
            viewModel.onNewEvent(FoodEvent.SearchFoodEvent(searchQuery))

        }
    }

}
