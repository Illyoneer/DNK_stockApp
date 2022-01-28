package ru.glushko.dnkstockapp.presentation.viewutils.recyclerAdapters.management.archive

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.glushko.dnkstockapp.databinding.ArchiveRecyclerItemBinding
import ru.glushko.dnkstockapp.domain.model.ArchiveItem

class ArchiveItemRecyclerAdapter : ListAdapter<ArchiveItem, ArchiveItemViewHolder>(ArchiveItemDiffCallback()) {

    var onPopupButtonClickListener: ((ArchiveItem, View) -> Unit)? = null
    var onHolderViewClickListener: ((ArchiveItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchiveItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ArchiveRecyclerItemBinding.inflate(inflater, parent, false)
        return ArchiveItemViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ArchiveItemViewHolder, position: Int) {
        val archiveItemElement = getItem(position)
        with(holder.archiveRecyclerItem) {
            countText.text = "${position+1}"
            itemName.text = archiveItemElement.name
            countItem.text = archiveItemElement.count.toString() + " шт."
            moreButton.setOnClickListener {
                onPopupButtonClickListener?.invoke(archiveItemElement, it)
            }
            root.setOnClickListener {
                onHolderViewClickListener?.invoke(archiveItemElement)
            }
        }
    }

    override fun onCurrentListChanged(
        previousList: MutableList<ArchiveItem>,
        currentList: MutableList<ArchiveItem>
    ) {
        if(currentList.size < previousList.size)
            notifyItemRangeChanged(0, currentList.size)
        super.onCurrentListChanged(previousList, currentList)
    }  //Исправление бага с нумерацией, если элемент из списка удаляется,
        // происходит переотрисовка всех элементов со сдвигом индекса.
}