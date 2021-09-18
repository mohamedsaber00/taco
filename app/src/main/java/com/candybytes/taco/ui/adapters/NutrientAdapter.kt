package com.candybytes.taco.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.candybytes.taco.databinding.ItemNutrientBinding
import com.candybytes.taco.vo.Nutrient

class NutrientAdapter :
    ListAdapter<Nutrient, NutrientAdapter.NutrientViewHolder>(NutrientDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NutrientViewHolder {
        val binding =
            ItemNutrientBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return NutrientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NutrientViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class NutrientViewHolder constructor(
        val binding: ItemNutrientBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Nutrient) = with(itemView) {
            //binding data to ui
            binding.nutrient = item
            binding.executePendingBindings()
        }

    }

}

class NutrientDiffCallback() : DiffUtil.ItemCallback<Nutrient>() {
    override fun areItemsTheSame(oldItem: Nutrient, newItem: Nutrient): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Nutrient, newItem: Nutrient): Boolean {
        return oldItem == newItem
    }
}