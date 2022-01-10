package ru.glushko.dnkstockapp.presentation.fragments.review

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.glushko.dnkstockapp.R
import ru.glushko.dnkstockapp.databinding.FragmentAddOrEditItemBinding
import ru.glushko.dnkstockapp.databinding.FragmentHardwareBinding
import ru.glushko.dnkstockapp.databinding.FragmentItemInfoBinding
import ru.glushko.dnkstockapp.domain.Item
import ru.glushko.dnkstockapp.presentation.viewmodels.ReviewViewModel
import ru.glushko.dnkstockapp.presentation.viewutils.Status
import ru.glushko.dnkstockapp.presentation.viewutils.recyclerAdapters.review.ItemRecyclerAdapter

class HardwareFragment : Fragment() {

    private lateinit var _hardwareFragmentBinding: FragmentHardwareBinding
    private val _reviewViewModel by viewModel<ReviewViewModel>()
    private val _itemRecyclerAdapter = ItemRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _hardwareFragmentBinding = FragmentHardwareBinding.inflate(
            inflater, container, false
        )

        setupRecyclerView()

        return _hardwareFragmentBinding.root
    }

    private fun setupRecyclerView() {

        _reviewViewModel.getGetHardwareItems().observe(viewLifecycleOwner, {
            _itemRecyclerAdapter.submitList(it)
        })

        _hardwareFragmentBinding.recyclerView.adapter = _itemRecyclerAdapter

        setupOnHolderViewClick(_itemRecyclerAdapter)
        setupOnActionButtonClick(_itemRecyclerAdapter)
    }

    private fun setupOnHolderViewClick(itemRecyclerAdapter: ItemRecyclerAdapter) {
        itemRecyclerAdapter.onHolderViewClickListener = {
            showInfoAboutItemRecordDialog(it)
        }
    }

    private fun setupOnActionButtonClick(itemRecyclerAdapter: ItemRecyclerAdapter) {
        itemRecyclerAdapter.onPopupButtonClickListener = { item, view ->
            showActionPopupMenu(view, item, R.menu.action_popup_menu)
        }
    }

    private fun showActionPopupMenu(view: View, itemElement: Item, @MenuRes menuRes: Int) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.menuInflater.inflate(menuRes, popupMenu.menu)
        //TODO: Намутить свап записей.
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete_action -> {
                    _reviewViewModel.deleteItemFromDatabase(item = itemElement)
                }
                R.id.edit_action -> {
                    showEditItemRecordDialog(itemElement)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    private fun showEditItemRecordDialog(item: Item) {
        val binding: FragmentAddOrEditItemBinding = FragmentAddOrEditItemBinding.inflate(
            LayoutInflater.from(requireContext()), null, false)

        with(binding) {
            itemNameEditText.setText(item.name)
            itemCountEditText.setText(item.count)
            itemDateEditText.setText(item.date)
            itemUserEditText.setText(item.user)
        }

        AlertDialog.Builder(requireContext(), R.style.MyAlertDialogRoundedTheme)
            .setTitle("Редактировать запись") //Добавление заголовка.
            .setView(binding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Готово") { _, _ ->

                _reviewViewModel.updateItemInDatabase(
                    id = item.id,
                    name = binding.itemNameEditText.text.toString(),
                    count = binding.itemCountEditText.text.toString(),
                    date = binding.itemDateEditText.text.toString(),
                    user = binding.itemUserEditText.text.toString(),
                    type = "hardware"
                )

                _reviewViewModel.getStateEditItemLiveData().observe(viewLifecycleOwner, {
                    when (it!!) {
                        Status.ERROR -> Snackbar.make(
                            _hardwareFragmentBinding.root, "Ошибка!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        Status.SUCCESS -> Snackbar.make(
                            _hardwareFragmentBinding.root, "Запись успешно обновлена!",
                            Snackbar.LENGTH_SHORT
                        ).show() //TODO: Сделать красивее и умнее!!!
                    }
                })
            }.setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }.show()
    }

    @SuppressLint("SetTextI18n")
    private fun showInfoAboutItemRecordDialog(item: Item) {
        val binding: FragmentItemInfoBinding = FragmentItemInfoBinding.inflate(
            LayoutInflater.from(requireContext()),
            null, false
        )

        with(binding) {
            infoNameItem.text = item.name
            infoCountItem.text = item.count + " шт."
            infoDateItem.text = item.date
            infoUserItem.text = item.user
            infoTypeItem.text = "Оборудование"
        }

        AlertDialog.Builder(requireContext(), R.style.MyAlertDialogRoundedTheme)
            .setTitle("Информация о выдаче") //Добавление заголовка.
            .setView(binding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Закрыть") { dialog, _ -> dialog.cancel() }.show()
    }

}