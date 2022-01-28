package ru.glushko.dnkstockapp.presentation.viewutils.recyclerAdapters.management.stock

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.glushko.dnkstockapp.databinding.StockRecyclerItemBinding
import ru.glushko.dnkstockapp.domain.model.StockItem

class StockItemRecyclerAdapter : ListAdapter<StockItem, StockItemViewHolder>(StockItemDiffCallback()) {

    var onPopupButtonClickListener: ((StockItem, View) -> Unit)? = null
    var onHolderViewClickListener: ((StockItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StockRecyclerItemBinding.inflate(inflater, parent, false)
        return StockItemViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StockItemViewHolder, position: Int) {
        val stockItemElement = getItem(position)
        with(holder.stockRecyclerItem) {
            countText.text = "${position+1}"
            itemName.text = stockItemElement.name
            countItem.text = stockItemElement.balance.toString() + " шт."
            moreButton.setOnClickListener {
                onPopupButtonClickListener?.invoke(stockItemElement, it)
            }

            root.setOnClickListener{
                onHolderViewClickListener?.invoke(stockItemElement)
            }
        }
    }

    override fun onCurrentListChanged(
        previousList: MutableList<StockItem>,
        currentList: MutableList<StockItem>
    ) {
        if(currentList.size < previousList.size)
            notifyItemRangeChanged(0, currentList.size)
        super.onCurrentListChanged(previousList, currentList)
    }  //Исправление бага с нумерацией, если элемент из списка удаляется,
        // происходит переотрисовка всех элементов со сдвигом индекса.
}