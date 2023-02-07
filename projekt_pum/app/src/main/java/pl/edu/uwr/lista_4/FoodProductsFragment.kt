package pl.edu.uwr.projekt_pum

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import pl.edu.uwr.projekt_pum.adapters.CapitalAdapter
import pl.edu.uwr.projekt_pum.adapters.RestCountriesComparator
import pl.edu.uwr.projekt_pum.databinding.FragmentRestCountriesListCapitalBinding
import pl.edu.uwr.projekt_pum.util.Resource

class FoodProductsFragment: Fragment() {
    private lateinit var binding: FragmentRestCountriesListCapitalBinding
    private val foodProductsViewModel: FoodProductsViewModel by activityViewModels()
    private val TAG = "RestCountresCapitalFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestCountriesListCapitalBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        getSearch("")
        val adapter = CapitalAdapter(RestCountriesComparator())
        setupRecyclerView(adapter)

        observeCapital(adapter)
        setupSearchView(adapter)
    }

    private fun hideProgressBar(){
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView(capitalAdapter: CapitalAdapter) {
        binding.countriesRV.apply {
            adapter = capitalAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    fun getSearch(search: String){
        foodProductsViewModel.getCapitalList(search)
        println("RATATATA")

    }

    private fun setupSearchView(adapter: CapitalAdapter) {
        binding.searchSearchView
            .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) search(query, adapter)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) search(newText, adapter)
                    return true
                }
            })
    }

    private fun search(query: String, adapter: CapitalAdapter) {
        getSearch(query)
    }


    private fun observeCapital(capitalAdapter: CapitalAdapter) {
        foodProductsViewModel.restCountriesCapitalList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        capitalAdapter.submitList(it)
//                        binding.textView.text = binding.search.text
//                        binding.categoryFullNameTextView.text = it.nobelPrizes.first().categoryFullName.en
//                        binding.dateAwardTextView.text = it.nobelPrizes.first().dateAwarded
//                        laureateAdapter.submitList(it.nobelPrizes.first().laureates)
                    }
//                    println("PRRRRRRRRRINTINGGG NOWQWWWW"+binding.root )

                    /*              response.data?.let { res ->
                                      capitalAdapter.submitList(res.)
                                  }*/
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { Log.e(TAG, "Error occurred: $it") }
                }
                is Resource.Loading -> showProgressBar()
            }
        }
    }

}