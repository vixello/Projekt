package pl.edu.uwr.projekt_pum.fragments

import androidx.recyclerview.widget.DiffUtil
import pl.edu.uwr.projekt_pum.model.Item

class ItemComparator : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.name == newItem.name && oldItem.date == newItem.date
    }
}