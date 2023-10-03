package com.dimasalamsyah.newapi.page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.SimpleAdapter
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dimasalamsyah.newapi.R
import com.dimasalamsyah.newapi.adapter.CategoryAdapter
import com.dimasalamsyah.newapi.adapter.CategoryClickListener
import com.dimasalamsyah.newapi.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        val view = binding.root

        val categories = arrayListOf<String>()
        categories.add("business")
        categories.add("entertainment")
        categories.add("general")
        categories.add("health")
        categories.add("science")
        categories.add("sports")
        categories.add("technology")

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        binding.recylerView.layoutManager = layoutManager
        val adapter = CategoryAdapter(categories, object: CategoryClickListener{
            override fun onItemClicked(view: View, category: String) {

                val bundle = bundleOf("category" to category)
                findNavController().navigate(R.id.action_categoryFragment_to_sourceFragment, bundle)

            }
        })
        binding.recylerView.adapter = adapter

        binding.search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                gotoSearch()
            }
            true
        }

        return view
    }

    private fun gotoSearch() {
        val bundle = bundleOf("search" to binding.search.text.toString())
        findNavController().navigate(R.id.action_categoryFragment_to_searchEverythingFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}