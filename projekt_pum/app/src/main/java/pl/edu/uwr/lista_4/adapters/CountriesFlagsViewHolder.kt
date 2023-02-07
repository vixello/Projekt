package pl.edu.uwr.projekt_pum.adapters

import androidx.navigation.NavDirections
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pl.edu.uwr.projekt_pum.data.Flags
import pl.edu.uwr.projekt_pum.data.RestCountriesResponse
import pl.edu.uwr.projekt_pum.data.RestCountriesResponseItem
import pl.edu.uwr.projekt_pum.databinding.ListItemRvBinding
import pl.edu.uwr.projekt_pum.databinding.ListItemRvFlagBinding

class CountriesFlagsViewHolder (private val binding: ListItemRvFlagBinding)
    : RecyclerView.ViewHolder(binding.root){
    fun bind(item: RestCountriesResponseItem){
        binding.name.text = item.title

//        Glide.with(binding.root)
//            .load(item.flags.png)
//            .into(binding.image)
//
    }
}