package ru.glushko.dnkstockapp.presentation.fragments.management

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
import ru.glushko.dnkstockapp.databinding.FragmentAddOrEditStockItemBinding
import ru.glushko.dnkstockapp.databinding.FragmentStockManagementBinding
import ru.glushko.dnkstockapp.domain.StockItem
import ru.glushko.dnkstockapp.presentation.viewmodels.ManagementViewModel
import ru.glushko.dnkstockapp.presentation.viewutils.Status
import ru.glushko.dnkstockapp.presentation.viewutils.recyclerAdapters.management.StockItemRecyclerAdapter

class StockManagementFragment : Fragment() {

    private lateinit var _stockManagementFragmentBinding: FragmentStockManagementBinding
    private val _managementViewModel by viewModel<ManagementViewModel>()
    private var _stockItemRecyclerAdapter = StockItemRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _stockManagementFragmentBinding =
            FragmentStockManagementBinding.inflate(inflater, container, false)

        setupRecyclerView()

        setupToolbarFunctional()

        return _stockManagementFragmentBinding.root
    }

    private fun setupToolbarFunctional() {
        _stockManagementFragmentBinding.addButton.setOnClickListener {
            showAddStockItemDialog()
        }
    }

    private fun setupRecyclerView() {
        _managementViewModel.loadAllStockItems().observe(viewLifecycleOwner, { stockItemList ->
            _stockItemRecyclerAdapter.submitList(stockItemList)
        })

        _stockManagementFragmentBinding.recyclerView.adapter = _stockItemRecyclerAdapter

        setupOnActionButtonClick(_stockItemRecyclerAdapter)
    }

    private fun setupOnActionButtonClick(stockItemRecyclerAdapter: StockItemRecyclerAdapter) {
        stockItemRecyclerAdapter.onPopupButtonClickListener = { stockItem, view ->
            showPopupMenu(
                stockItemElement = stockItem,
                view = view,
                menuRes = R.menu.action_popup_menu
            )
        }
    }

    private fun showPopupMenu(view: View, stockItemElement: StockItem, @MenuRes menuRes: Int) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.menuInflater.inflate(menuRes, popupMenu.menu)
        //TODO: Намутить свап записей.
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete_action -> {
                    _managementViewModel.deleteItemFromDatabase(stockItem = stockItemElement)
                }
                R.id.edit_action -> {
                    showEditStockItemDialog(stockItem = stockItemElement)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    private fun showAddStockItemDialog() {
        val binding: FragmentAddOrEditStockItemBinding = FragmentAddOrEditStockItemBinding.inflate(
            LayoutInflater.from(requireContext()), null, false)

        AlertDialog.Builder(requireContext(), R.style.MyAlertDialogRoundedTheme)
            .setTitle("Добавить новую запись") //Добавление заголовка.
            .setView(binding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Добавить") { _, _ ->
                _managementViewModel.addItemToDatabase(
                    name =  binding.itemNameEditText.text.toString(),
                    count = binding.itemCountEditText.text.toString(),
                )

                _managementViewModel.getStateAddItemLiveData().observe(viewLifecycleOwner, {
                    when (it!!) {
                        Status.ERROR -> Snackbar.make(
                            _stockManagementFragmentBinding.root, "Введите все данные.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        Status.SUCCESS -> Snackbar.make(
                            _stockManagementFragmentBinding.root, "Запись успешно добавлена!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                })
            }.setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }.show()
    }

    private fun showEditStockItemDialog(stockItem: StockItem) {
        val binding: FragmentAddOrEditStockItemBinding = FragmentAddOrEditStockItemBinding.inflate(
            LayoutInflater.from(requireContext()), null, false)

        with(binding) {
            itemNameEditText.setText(stockItem.name)
            itemCountEditText.setText(stockItem.count)
        }

        AlertDialog.Builder(requireContext(), R.style.MyAlertDialogRoundedTheme)
            .setTitle("Редактировать запись") //Добавление заголовка.
            .setView(binding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Готово") { _, _ ->

                _managementViewModel.updateItemInDatabase(
                    id = stockItem.id,
                    name = binding.itemNameEditText.text.toString(),
                    count = binding.itemCountEditText.text.toString(),
                )

                _managementViewModel.getStateEditItemLiveData().observe(viewLifecycleOwner, {
                    when (it!!) {
                        Status.ERROR -> Snackbar.make(
                            _stockManagementFragmentBinding.root, "Ошибка!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        Status.SUCCESS -> Snackbar.make(
                            _stockManagementFragmentBinding.root, "Запись успешно обновлена!",
                            Snackbar.LENGTH_SHORT
                        ).show() //TODO: Сделать красивее и умнее!!!
                    }
                })
            }.setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }.show()
    }

}