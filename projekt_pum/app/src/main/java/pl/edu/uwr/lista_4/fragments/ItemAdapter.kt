package pl.edu.uwr.projekt_pum.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import pl.edu.uwr.projekt_pum.databinding.ItemRecyclerviewBinding
import pl.edu.uwr.projekt_pum.model.Item

class ItemAdapter(itemComparator: ItemComparator)
    : ListAdapter<Item, ItemViewHolder>(itemComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    public fun getItemAt(position: Int): Item{
        return getItem(position)
    }


}