package ru.glushko.dnkstockapp.presentation.viewutils.recyclerAdapters.management.staff

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.glushko.dnkstockapp.databinding.StaffRecyclerItemBinding
import ru.glushko.dnkstockapp.domain.model.Staff

class StaffRecyclerAdapter : ListAdapter<Staff, StaffViewHolder>(StaffDiffCallback()) {

    var onPopupButtonClickListener: ((Staff, View) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StaffRecyclerItemBinding.inflate(inflater, parent, false)
        return StaffViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        val staffElement = getItem(position)
        with(holder.staffRecyclerItem) {
            countText.text = "${position+1}"
            fioStaff.text = "${staffElement.surname} ${staffElement.name} ${staffElement.lastname}"

            moreButton.setOnClickListener {
                onPopupButtonClickListener?.invoke(staffElement, it)
            }
        }
    }

    override fun onCurrentListChanged(
        previousList: MutableList<Staff>,
        currentList: MutableList<Staff>
    ) {
        if(currentList.size < previousList.size)
            notifyItemRangeChanged(0, currentList.size)
        super.onCurrentListChanged(previousList, currentList)
    }  //Исправление бага с нумерацией, если элемент из списка удаляется,
        // происходит переотрисовка всех элементов со сдвигом индекса.
}