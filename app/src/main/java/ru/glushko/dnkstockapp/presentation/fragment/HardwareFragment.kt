package ru.glushko.dnkstockapp.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.glushko.dnkstockapp.R
import ru.glushko.dnkstockapp.databinding.FragmentAddOrEditItemBinding
import ru.glushko.dnkstockapp.databinding.FragmentHardwareBinding
import ru.glushko.dnkstockapp.databinding.FragmentItemInfoBinding
import ru.glushko.dnkstockapp.domain.Item
import ru.glushko.dnkstockapp.presentation.viewmodels.AddOrEditItemViewModel
import ru.glushko.dnkstockapp.presentation.viewmodels.MainViewModel
import ru.glushko.dnkstockapp.presentation.viewutils.Status
import ru.glushko.dnkstockapp.presentation.viewutils.recyclerAdapter.ItemRecyclerAdapter

class HardwareFragment : Fragment() {

    private lateinit var _hardwareFragmentBinding: FragmentHardwareBinding
    private val _mainViewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _hardwareFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_hardware, container, false
        )
        _hardwareFragmentBinding.mainVM = _mainViewModel

        setupRecyclerView()

        return _hardwareFragmentBinding.root
    }

    private fun setupRecyclerView() {
        val itemRecyclerAdapter = ItemRecyclerAdapter()

        _mainViewModel.getGetHardwareItems().observe(viewLifecycleOwner, {
            itemRecyclerAdapter.submitList(it)
        })

        _hardwareFragmentBinding.recyclerView.adapter = itemRecyclerAdapter

        setupOnHolderViewClick(itemRecyclerAdapter)
        setupOnActionButtonClick(itemRecyclerAdapter)
    }

    private fun setupOnHolderViewClick(itemRecyclerAdapter: ItemRecyclerAdapter) {
        itemRecyclerAdapter.onHolderViewClickListener = {
            showInfoAboutItemRecordDialog(it)
        }
    }

    private fun setupOnActionButtonClick(itemRecyclerAdapter: ItemRecyclerAdapter) {
        itemRecyclerAdapter.onPopupButtonClickListener = { item, view ->
            showPopupMenu(view, item)
        }
    }

    private fun showPopupMenu(view: View, itemElement: Item) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.menu.add(0, DELETE_ITEM, Menu.NONE, "Удалить")
        popupMenu.menu.add(0, EDIT_ITEM, Menu.NONE, "Редактировать")
        //TODO: Намутить свап записей.
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                DELETE_ITEM -> {
                    _mainViewModel.deleteItemFromDatabase(itemElement)
                }
                EDIT_ITEM -> {
                    showEditItemRecordDialog(itemElement)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    private fun showEditItemRecordDialog(item: Item) {
        val binding: FragmentAddOrEditItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()), R.layout.fragment_add_or_edit_item,
            null, false
        )
        val addOrEditItemViewModel =
            ViewModelProvider(this)[AddOrEditItemViewModel::class.java]

        binding.addItemVM = addOrEditItemViewModel

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

                _mainViewModel.updateItemInDatabase(
                    item.id,
                    binding.itemNameEditText.text.toString(),
                    binding.itemCountEditText.text.toString(),
                    binding.itemDateEditText.text.toString(),
                    binding.itemUserEditText.text.toString(),
                    "hardware"
                )

                _mainViewModel.getStateEditItemLiveData().observe(viewLifecycleOwner, {
                    when (it!!) {
                        Status.ERROR -> Snackbar.make(
                            _hardwareFragmentBinding.root, "Ошибка!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        Status.SUCCESS -> Snackbar.make(
                            _hardwareFragmentBinding.root, "Запись успешно обновлена!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                })
            }.setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }.show()
    }

    @SuppressLint("SetTextI18n")
    private fun showInfoAboutItemRecordDialog(item: Item) {
        val binding: FragmentItemInfoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()), R.layout.fragment_item_info,
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

    companion object {
        private const val EDIT_ITEM = 1
        private const val DELETE_ITEM = 0
    }

}