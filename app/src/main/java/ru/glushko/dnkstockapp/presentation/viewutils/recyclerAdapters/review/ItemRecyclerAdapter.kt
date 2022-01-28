package ru.glushko.dnkstockapp.presentation.viewutils.recyclerAdapters.review

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.glushko.dnkstockapp.databinding.ReviewRecyclerItemBinding
import ru.glushko.dnkstockapp.domain.model.Item

class ItemRecyclerAdapter : ListAdapter<Item, ItemViewHolder>(ItemDiffCallback()) {

    var onPopupButtonClickListener: ((Item, View) -> Unit)? = null
    var onHolderViewClickListener: ((Item) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ReviewRecyclerItemBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val itemElement = getItem(position)
        with(holder.reviewRecyclerItem) {
            countText.text = "${position+1}"
            itemName.text = itemElement.name
            countItem.text = itemElement.count.toString() + " шт."
            moreButton.setOnClickListener {
                onPopupButtonClickListener?.invoke(itemElement, it)
            }
            root.setOnClickListener {
                onHolderViewClickListener?.invoke(itemElement)
            }
        }
    }

    override fun onCurrentListChanged(
        previousList: MutableList<Item>,
        currentList: MutableList<Item>
    ) {
        if(currentList.size < previousList.size)
            notifyItemRangeChanged(0, currentList.size)
        super.onCurrentListChanged(previousList, currentList)
    }  //Исправление бага с нумерацией, если элемент из списка удаляется,
        // происходит переотрисовка всех элементов со сдвигом индекса.
}