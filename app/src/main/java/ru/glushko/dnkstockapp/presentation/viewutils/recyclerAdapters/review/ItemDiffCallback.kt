package ru.glushko.dnkstockapp.presentation.viewutils.recyclerAdapters.review

import androidx.recyclerview.widget.DiffUtil
import ru.glushko.dnkstockapp.domain.entity.Item

class ItemDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}