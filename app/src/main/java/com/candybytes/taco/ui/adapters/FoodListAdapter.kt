package com.candybytes.taco.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.candybytes.taco.databinding.ItemFoodBinding
import com.candybytes.taco.vo.Food

class FoodListAdapter(private var onItemClick: (Food) -> Unit) :
    PagingDataAdapter<Food, FoodListAdapter.FoodViewHolder>(FoodDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding =
            ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FoodViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class FoodViewHolder constructor(
        val binding: ItemFoodBinding,
        private var onItemClick: (Food) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Food) = with(itemView) {
            //binding data to ui
            binding.food = item
            binding.executePendingBindings()

            //use dataBinding to assign the onClickListener
            binding.setClickListener {
                onItemClick(item)
            }

        }

    }
}

class FoodDiffCallback() : DiffUtil.ItemCallback<Food>() {
    override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
        return oldItem == newItem
    }
}