package pl.edu.uwr.projekt_pum

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import pl.edu.uwr.projekt_pum.data.FoodProductsResponseItem
import pl.edu.uwr.projekt_pum.databinding.FragmentFoodDetailBinding
import pl.edu.uwr.projekt_pum.util.Resource

class FoodDetailFragment : Fragment() {

    private lateinit var binding: FragmentFoodDetailBinding

    private val foodViewModel: FoodProductsViewModel by activityViewModels()
    private val TAG = "FoodDetailFragment"

    private val title: String? by lazy { requireArguments().getString("title") }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFoodDetailBinding.inflate(layoutInflater, container, false)
        foodViewModel.getMealById(title!!)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeMeal()
    }

    private fun observeMeal() {
        foodViewModel.meal.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { res ->
                        val item = res.first()
                        println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+res)
                        inflate(item)
//                        binding.favoriteButton.setOnClickListener {
//                            foodViewModel.insert(item)
//                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { Log.e(TAG, "Error occurred: $it") }
                }
                is Resource.Loading -> showProgressBar()
            }
        }
    }

    private fun inflate(item: FoodProductsResponseItem) {
//        Glide.with(this)
//            .load(item.strMealThumb)
//            .into(binding.foodImage)
        binding.servings.text = item.servings
        binding.ingredients.text = item.ingredients
        binding.title.text = item.title
        binding.instructions.text = item.instructions
    }

    private fun hideProgressBar(){
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }
}