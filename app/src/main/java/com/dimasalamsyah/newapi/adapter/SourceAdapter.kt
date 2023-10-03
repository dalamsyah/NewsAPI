package com.dimasalamsyah.newapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dimasalamsyah.newapi.databinding.ItemCategoryBinding
import com.dimasalamsyah.newapi.model.SourcesItem

interface SourceClickListener {
    fun onItemClicked(view: View, sourceItem: SourcesItem)
}

class SourceAdapter (private val mList: MutableList<SourcesItem>, val listener: SourceClickListener) : RecyclerView.Adapter<SourceAdapter.ViewHolder>() {

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
                binding.text.text = mList[position].name
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun updateList(list: List<SourcesItem>) {
        mList.clear()
        mList.addAll(list);
    }

}