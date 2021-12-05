package ru.glushko.dnkstockapp.presentation.viewutils.recyclerAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.glushko.dnkstockapp.databinding.RecyclerItemBinding
import ru.glushko.dnkstockapp.domain.Item

class ItemRecyclerAdapter : ListAdapter<Item, ItemViewHolder>(ItemDiffCallback()) {

    var onPopupButtonClickListener: ((Item, View) -> Unit)? = null
    var onHolderViewClickListener: ((Item) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerItemBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val itemElement = getItem(position)
        with(holder.recyclerItem) {
            itemName.text = itemElement.name
            countItem.text = itemElement.count + " шт."
            moreButton.setOnClickListener {
                onPopupButtonClickListener?.invoke(itemElement, it)
            }
            root.setOnClickListener {
                onHolderViewClickListener?.invoke(itemElement)
            }
        }
    }
}