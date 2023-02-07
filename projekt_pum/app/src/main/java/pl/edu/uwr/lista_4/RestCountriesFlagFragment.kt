package pl.edu.uwr.projekt_pum

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import pl.edu.uwr.projekt_pum.adapters.CapitalAdapter
import pl.edu.uwr.projekt_pum.adapters.FlagAdapter
import pl.edu.uwr.projekt_pum.adapters.RestCountriesComparator
import pl.edu.uwr.projekt_pum.data.Flags
import pl.edu.uwr.projekt_pum.data.RestCountriesResponse
import pl.edu.uwr.projekt_pum.data.RestCountriesResponseItem
import pl.edu.uwr.projekt_pum.databinding.FragmentRestCountriesFlagBinding
import pl.edu.uwr.projekt_pum.databinding.FragmentRestCountriesListCapitalBinding
import pl.edu.uwr.projekt_pum.util.Resource

class RestCountriesFlagFragment: Fragment() {
    private lateinit var binding: FragmentRestCountriesFlagBinding
    private val restCountriesViewModel: RestCountriesViewModel by activityViewModels()
    private val TAG = "RestCountresFlagsFragment"

    private val flags: String? by lazy{ requireArguments().getString("flags") }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestCountriesFlagBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        restCountriesViewModel.getFlagList()        //swipeToDelete(adapter)

        val adapter = FlagAdapter(RestCountriesComparator())
        setupRecyclerView(adapter)
        observeFood(adapter)

    }

    private fun hideProgressBar(){
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }


    private fun observeFood(flagAdapter: FlagAdapter) {
        restCountriesViewModel.restCountriesFlagsList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        flagAdapter.submitList(it)
//                        binding.categoryFullNameTextView.text = it.nobelPrizes.first().categoryFullName.en
//                        binding.dateAwardTextView.text = it.nobelPrizes.first().dateAwarded
//                        laureateAdapter.submitList(it.nobelPrizes.first().laureates)
                    }

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
    private fun setupRecyclerView(flagAdapter: FlagAdapter) {
        binding.flagsRV.apply {
            adapter = flagAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}