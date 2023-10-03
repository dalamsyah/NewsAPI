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
import com.dimasalamsyah.newapi.adapter.SourceAdapter
import com.dimasalamsyah.newapi.adapter.SourceClickListener
import com.dimasalamsyah.newapi.config.NetworkConfig
import com.dimasalamsyah.newapi.databinding.FragmentSourceBinding
import com.dimasalamsyah.newapi.model.SourceResponse
import com.dimasalamsyah.newapi.model.Source
import com.dimasalamsyah.newapi.model.SourcesItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SourceFragment : Fragment() {

    private var _binding: FragmentSourceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentSourceBinding.inflate(inflater, container, false)
        val view = binding.root

        val list = arrayListOf<SourcesItem>()

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        binding.recylerView.layoutManager = layoutManager
        val adapter = SourceAdapter(list, object: SourceClickListener{
            override fun onItemClicked(view: View, sourceItem: SourcesItem) {
                val bundle = bundleOf("urlArticle" to sourceItem.url)
                findNavController().navigate(R.id.action_sourceFragment_to_articleFragment, bundle)
            }
        })
        binding.recylerView.adapter = adapter

        val category = arguments?.getString("category")

        NetworkConfig().getService()
            .getSources(category)
            .enqueue(object : Callback<SourceResponse> {
                override fun onFailure(call: Call<SourceResponse>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(
                    call: Call<SourceResponse>,
                    response: Response<SourceResponse>
                ) {

                    binding.progressBar.visibility = View.GONE

                    var list = mutableListOf<SourcesItem>()
                    response.body()?.sources?.forEach {
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