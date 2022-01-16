package ru.glushko.dnkstockapp.presentation.viewutils.recyclerAdapters.management.staff

import androidx.recyclerview.widget.DiffUtil
import ru.glushko.dnkstockapp.domain.entity.Staff

class StaffDiffCallback : DiffUtil.ItemCallback<Staff>() {
    override fun areItemsTheSame(oldItem: Staff, newItem: Staff): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Staff, newItem: Staff): Boolean {
        return oldItem == newItem
    }
}