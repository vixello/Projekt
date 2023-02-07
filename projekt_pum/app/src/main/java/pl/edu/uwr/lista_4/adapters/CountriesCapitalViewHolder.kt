package pl.edu.uwr.projekt_pum.adapters

import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import pl.edu.uwr.projekt_pum.FoodProductsFragmentDirections
import pl.edu.uwr.projekt_pum.data.FoodProductsResponseItem
import pl.edu.uwr.projekt_pum.databinding.ListItemRvBinding

class CountriesCapitalViewHolder(private val binding: ListItemRvBinding)
    : RecyclerView.ViewHolder(binding.root){
    fun bind(item: FoodProductsResponseItem){
        binding.name.text = item.title
        binding.servings.text = item.servings
/*        Glide.with(binding.root)
            .load(item.capital)
            .into(binding)*/
        binding.root.setOnClickListener {
            val action: NavDirections = FoodProductsFragmentDirections
                .actionFoodProductsFragmentToFoodDetailFragment(
                    item.title
                )
            Navigation.findNavController(binding.root).navigate(action)
        }
    }
}