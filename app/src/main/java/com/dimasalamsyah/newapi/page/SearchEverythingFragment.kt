package com.dimasalamsyah.newapi.page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dimasalamsyah.newapi.R
import com.dimasalamsyah.newapi.adapter.SearchAdapter
import com.dimasalamsyah.newapi.adapter.SearchClickListener
import com.dimasalamsyah.newapi.adapter.SourceAdapter
import com.dimasalamsyah.newapi.adapter.SourceClickListener
import com.dimasalamsyah.newapi.config.NetworkConfig
import com.dimasalamsyah.newapi.databinding.FragmentSearchEverythingBinding
import com.dimasalamsyah.newapi.model.ArticlesItem
import com.dimasalamsyah.newapi.model.SearchResponse
import com.dimasalamsyah.newapi.model.SourceResponse
import com.dimasalamsyah.newapi.model.SourcesItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchEverythingFragment : Fragment() {

    private var _binding: FragmentSearchEverythingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentSearchEverythingBinding.inflate(inflater, container, false)
        val view = binding.root

        val list = arrayListOf<ArticlesItem>()

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        binding.recylerView.layoutManager = layoutManager
        val adapter = SearchAdapter(list, object: SearchClickListener {
            override fun onItemClicked(view: View, articlesItem: ArticlesItem) {
                val bundle = bundleOf("urlArticle" to articlesItem.url)
                findNavController().navigate(R.id.action_searchEverythingFragment_to_articleFragment, bundle)
            }
        })
        binding.recylerView.adapter = adapter

        val search = arguments?.getString("search")

        NetworkConfig().getService()
            .getSearch(search)
            .enqueue(object : Callback<SearchResponse> {
                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {

                    binding.progressBar.visibility = View.GONE

                    var list = mutableListOf<ArticlesItem>()
                    response.body()?.articles?.forEach {
                        it?.let { it1 -> list.add(it1) }
                    }

                    adapter.updateList(list)
                    adapter.notifyDataSetChanged()
                }
            })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}