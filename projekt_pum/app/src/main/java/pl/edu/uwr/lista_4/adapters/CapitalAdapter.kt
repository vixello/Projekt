package pl.edu.uwr.projekt_pum.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import pl.edu.uwr.projekt_pum.data.FoodProductsResponseItem
import pl.edu.uwr.projekt_pum.databinding.ListItemRvBinding

class CapitalAdapter (itemComparator: RestCountriesComparator)
    : ListAdapter<FoodProductsResponseItem, CountriesCapitalViewHolder>(itemComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesCapitalViewHolder {
        return CountriesCapitalViewHolder(
            ListItemRvBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CountriesCapitalViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    fun getItemAt(position: Int): FoodProductsResponseItem{
        return getItem(position)
    }
}