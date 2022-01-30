package ru.glushko.dnkstockapp.presentation.viewutils.recyclerAdapters.management.archive

import androidx.recyclerview.widget.DiffUtil
import ru.glushko.dnkstockapp.domain.model.ArchiveItem

class ArchiveItemDiffCallback : DiffUtil.ItemCallback<ArchiveItem>() {
    override fun areItemsTheSame(oldItem: ArchiveItem, newItem: ArchiveItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ArchiveItem, newItem: ArchiveItem): Boolean {
        return oldItem == newItem
    }
}