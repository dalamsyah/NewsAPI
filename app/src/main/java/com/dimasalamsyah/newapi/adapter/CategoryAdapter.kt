package com.dimasalamsyah.newapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dimasalamsyah.newapi.R
import com.dimasalamsyah.newapi.databinding.ItemCategoryBinding

interface CategoryClickListener {
    fun onItemClicked(view: View, category: String)
}

class CategoryAdapter (private val mList: List<String>, val listener: CategoryClickListener) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.text.setOnClickListener {
            listener.onItemClicked(it, mList[position])
        }

        with(holder){
            with(mList[position]){
                binding.text.text = this
            }
        }
    }

    // return the size of languageList
    override fun getItemCount(): Int {
        return mList.size
    }

}