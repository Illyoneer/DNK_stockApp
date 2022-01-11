package ru.glushko.dnkstockapp.presentation.viewutils.recyclerAdapters.management.stock

import androidx.recyclerview.widget.DiffUtil
import ru.glushko.dnkstockapp.domain.entity.StockItem

class StockItemDiffCallback : DiffUtil.ItemCallback<StockItem>() {
    override fun areItemsTheSame(oldItem: StockItem, newItem: StockItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: StockItem, newItem: StockItem): Boolean {
        return oldItem == newItem
    }
}