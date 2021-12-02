package ru.glushko.dnkstockapp.utils.recyclerAdapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.glushko.dnkstockapp.databinding.RecyclerItemBinding
import ru.glushko.dnkstockapp.model.Item

class ItemRecyclerAdapter : ListAdapter<Item, ItemViewHolder>(ItemDiffCallback()) {

    var onPopupButtonClickListener: ((Item) -> Unit)? = null
    var onHolderViewClickListener: ((Item) -> Unit)? = null
    private var count = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerItemBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        Log.i("onBindViewHolder", "count: ${++count}")
        val itemElement = getItem(position)
        with(holder.recyclerItem) {
            itemName.text = itemElement.name
            countItem.text = itemElement.count + " шт."
            /*dateItem.text = itemElement.date
            userItem.text = itemElement.user*/
            moreButton.setOnClickListener {
                showPopupMenu(it, itemElement = itemElement)
            }
            root.setOnClickListener {
                onHolderViewClickListener?.invoke(itemElement)
            }
        }
    }

    private fun showPopupMenu(view: View, itemElement: Item) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.menu.add(0, 1, Menu.NONE, "Удалить")
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                1 -> {
                    onPopupButtonClickListener?.invoke(itemElement)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }
}