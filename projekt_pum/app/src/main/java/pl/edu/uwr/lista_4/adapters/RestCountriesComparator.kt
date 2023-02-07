package pl.edu.uwr.projekt_pum.adapters

import androidx.recyclerview.widget.DiffUtil
import pl.edu.uwr.projekt_pum.data.FoodProductsResponseItem

class RestCountriesComparator : DiffUtil.ItemCallback<FoodProductsResponseItem>() {
    override fun areItemsTheSame(
        oldItem: FoodProductsResponseItem,
        newItem: FoodProductsResponseItem
    ): Boolean {
        return newItem.title == oldItem.title
    }

    override fun areContentsTheSame(oldItem: FoodProductsResponseItem, newItem: FoodProductsResponseItem): Boolean {
        return newItem.servings == oldItem.servings
//        return newItem.name == oldItem.name

    }
}