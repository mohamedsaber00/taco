package com.candybytes.taco.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.candybytes.taco.databinding.FragmentFoodBinding
import com.candybytes.taco.ui.adapters.NutrientAdapter
import com.candybytes.taco.ui.util.ImageUtils
import com.candybytes.taco.ui.vm.FoodViewModel
import com.candybytes.taco.vo.Category
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class FoodFragment : Fragment() {


    private lateinit var binding: FragmentFoodBinding

    private val viewModel: FoodViewModel by viewModels()

    private lateinit var nutrientAdapter: NutrientAdapter

    //receive food details from navigation using safe-args
    private val foodFragmentArgs: FoodFragmentArgs by navArgs()

    private var imageUri: Uri? = null

    /*
    * init contract using ActivityResultContracts to be used when taking a picture
    * */
    private val takePicContract =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                //Succeeded to take a picture, selectedImageUri contain the uri of selected image
                Timber.d("Image uri: ${imageUri}")
                viewModel.setImageUri(imageUri)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFoodBinding.inflate(layoutInflater, container, false).apply {
            viewModel = this@FoodFragment.viewModel
            lifecycleOwner = this@FoodFragment.viewLifecycleOwner
        }

        //pass the navigation args to the viewModel
        viewModel.setupFoodDetails(foodFragmentArgs.food)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setup recyclerview
        setupRecyclerAdapter()

        subscribeObservers()

        //set listener for image click
        binding.setOnSelectImageListener {
            selectImage()
        }

        //set listener for category button click
        binding.setOnCategoryClickListener {
            //check first if category is null then it's still loading or there is an error
            //if not null navigate to category fragmentl

            viewModel.category.value?.let {
                navigateToCategory(it)
            } ?: Timber.e("Category Info not loaded from the API")
        }

    }


    /*
     * Subscribe to LiveDat Observer to update the UI once the data changes
     */
    private fun subscribeObservers() {
        viewModel.nutrients.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                //update the adapter with the new list
                nutrientAdapter.submitList(it)

            }
        }
    }


    /*
     * init RecyclerAdapter and attach it to recyclerView
     */

    private fun setupRecyclerAdapter() {
        //init Adapter
        nutrientAdapter = NutrientAdapter()

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = nutrientAdapter
        }
    }

    private fun selectImage() {
        //Create temp file to get the uri
        ImageUtils.getTmpFileUri(requireContext()).let { uri ->
            //save the uri in local variable
            imageUri = uri
            //launch the contract with the uri of file we created
            takePicContract.launch(uri)
        }
    }

    private fun navigateToCategory(category: Category) {
        val action = FoodFragmentDirections.actionFoodFragmentToCategoryFragment(
            category = category,
            categoryTitle = category.name
        )
        findNavController().navigate(action)

    }
}