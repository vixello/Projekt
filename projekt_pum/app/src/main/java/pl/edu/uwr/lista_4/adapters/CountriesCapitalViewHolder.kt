package pl.edu.uwr.projekt_pum.adapters

import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import pl.edu.uwr.projekt_pum.RestCountriesCapitalFragmentDirections
import pl.edu.uwr.projekt_pum.data.RestCountriesResponseItem
import pl.edu.uwr.projekt_pum.databinding.ListItemRvBinding

class CountriesCapitalViewHolder(private val binding: ListItemRvBinding)
    : RecyclerView.ViewHolder(binding.root){
    fun bind(item: RestCountriesResponseItem){
        binding.name.text = item.title
        binding.servings.text = item.servings
/*        Glide.with(binding.root)
            .load(item.capital)
            .into(binding)*/
        binding.root.setOnClickListener {
            val action: NavDirections = RestCountriesCapitalFragmentDirections
                .actionRestCountriesCapitalFragmentToFoodDetailFragment(
                    item.title
                )
            Navigation.findNavController(binding.root).navigate(action)
        }
    }
}