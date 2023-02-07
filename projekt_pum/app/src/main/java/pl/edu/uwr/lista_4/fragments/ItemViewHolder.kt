package pl.edu.uwr.projekt_pum.fragments

import android.net.Uri
import androidx.navigation.NavDirections
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import pl.edu.uwr.projekt_pum.databinding.ItemRecyclerviewBinding
import pl.edu.uwr.projekt_pum.model.Item

class ItemViewHolder(private val binding: ItemRecyclerviewBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Item) {
        binding.nameTextViewRV.text = item.name
        binding.dateTextViewRV.text = item.date.toString()
        binding.imageViewFood.setImageURI(Uri.parse(item.image.toString()))


        binding.root.setOnClickListener {
            val action: NavDirections = ListFragmentDirections
                .actionListFragmentToUpdateFragment(item.id)
            findNavController(binding.root).navigate(action)
        }
    }

}