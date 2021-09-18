package com.candybytes.taco.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.candybytes.taco.databinding.ItemCategoryBinding
import com.candybytes.taco.vo.Category

class CategoryListAdapter(private var onItemClick: (Category) -> Unit) :
    ListAdapter<Category, CategoryListAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CategoryViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CategoryViewHolder constructor(
        val binding: ItemCategoryBinding,
        private var onItemClick: (Category) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category) = with(itemView) {
            //binding data to the ui
            binding.category = item
            binding.executePendingBindings()
            //Listen to on item click
            binding.setOnCategoryClickListener {
                onItemClick(item)
            }
        }


    }
}

class CategoryDiffCallback() : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id &&oldItem.foodCount == newItem.foodCount

    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id &&oldItem.foodCount == newItem.foodCount
    }
}