package pl.edu.uwr.projekt_pum.adapters

import androidx.recyclerview.widget.DiffUtil
import pl.edu.uwr.projekt_pum.data.Flags
import pl.edu.uwr.projekt_pum.data.RestCountriesResponseItem

class RestCountriesComparator : DiffUtil.ItemCallback<RestCountriesResponseItem>() {
    override fun areItemsTheSame(
        oldItem: RestCountriesResponseItem,
        newItem: RestCountriesResponseItem
    ): Boolean {
        return newItem.title == oldItem.title
    }

    override fun areContentsTheSame(oldItem: RestCountriesResponseItem, newItem: RestCountriesResponseItem): Boolean {
        return newItem.servings == oldItem.servings
//        return newItem.name == oldItem.name

    }
}