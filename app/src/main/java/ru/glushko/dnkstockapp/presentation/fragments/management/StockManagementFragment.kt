package ru.glushko.dnkstockapp.presentation.fragments.management

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.glushko.dnkstockapp.R
import ru.glushko.dnkstockapp.databinding.FragmentAddIncomingStockItemBinding
import ru.glushko.dnkstockapp.databinding.FragmentAddOrEditStockItemBinding
import ru.glushko.dnkstockapp.databinding.FragmentStockItemInfoBinding
import ru.glushko.dnkstockapp.databinding.FragmentStockManagementBinding
import ru.glushko.dnkstockapp.domain.model.StockItem
import ru.glushko.dnkstockapp.presentation.viewmodels.ManagementViewModel
import ru.glushko.dnkstockapp.presentation.viewutils.recyclerAdapters.management.stock.StockItemRecyclerAdapter

class StockManagementFragment : Fragment() {

    private lateinit var _stockManagementFragmentBinding: FragmentStockManagementBinding
    private lateinit var _stockItemFBinding: FragmentAddOrEditStockItemBinding
    private lateinit var _stockItemInfoFBinding: FragmentStockItemInfoBinding
    private lateinit var _stockItemIncomingFBinding: FragmentAddIncomingStockItemBinding

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

    override fun onResume() {
        _managementViewModel.allStockItems.observe(viewLifecycleOwner) { stockItemList ->
            _stockItemRecyclerAdapter.submitList(stockItemList)
            if (stockItemList.isNullOrEmpty())
                showEmptyAttentionDialog()
        }

        super.onResume()
    }

    private fun showEmptyAttentionDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Внимание!")
            .setMessage("Для начала работы вам необходимо добавить хотя бы одну запись.")
            .setPositiveButton("Добавить") { _, _ -> showAddStockItemDialog() }
            .setNeutralButton("Отмена") { dialog, _ -> dialog.cancel() }
            .show()

    }

    private fun setupToolbarFunctional() {
        _stockManagementFragmentBinding.addButton.setOnClickListener {
            showAddStockItemDialog()
        }
    }

    private fun setupRecyclerView() {
        _stockManagementFragmentBinding.recyclerView.adapter = _stockItemRecyclerAdapter

        setupOnActionButtonClick(_stockItemRecyclerAdapter)
        setupOnHolderViewClick(_stockItemRecyclerAdapter)
    }

    private fun setupOnHolderViewClick(stockItemRecyclerAdapter: StockItemRecyclerAdapter) {
        stockItemRecyclerAdapter.onHolderViewClickListener = {
            showInfoAboutStockItemRecordDialog(it)
        }
    }

    private fun setupOnActionButtonClick(stockItemRecyclerAdapter: StockItemRecyclerAdapter) {
        stockItemRecyclerAdapter.onPopupButtonClickListener = { stockItem, view ->
            showPopupMenu(
                stockItemElement = stockItem,
                view = view,
                menuRes = R.menu.stock_action_popup_menu
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
                    _managementViewModel.deleteStockItemFromDatabase(stockItem = stockItemElement)
                }
                R.id.edit_action -> {
                    showEditStockItemDialog(stockItem = stockItemElement)
                }
                R.id.add_action -> {
                    showAddIncomingStockItemDialog(stockItem = stockItemElement)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    private fun showAddIncomingStockItemDialog(stockItem: StockItem) {
        _stockItemIncomingFBinding = FragmentAddIncomingStockItemBinding.inflate(
            LayoutInflater.from(requireContext()),
            null,
            false
        )

        _stockItemIncomingFBinding.stockItemIncomingName.text = stockItem.name

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Добавить поступление") //Добавление заголовка.
            .setView(_stockItemIncomingFBinding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Добавить") { _, _ ->
                val incomingCount = try {
                    _stockItemIncomingFBinding.stockItemIncomingEditText.text.toString().toInt()
                } catch (e: NumberFormatException) { 0 }

                _managementViewModel.updateStockItemBalanceInDatabase(incomingCount, stockItem.name)

                _managementViewModel.transactionStatus.observe(viewLifecycleOwner) { status ->
                    Toast.makeText(requireContext(), status, Toast.LENGTH_SHORT).show()
                }
            }
            .setNeutralButton("Отмена") { dialog, _ -> dialog.cancel() }
            .show()

    }

    private fun showAddStockItemDialog() {
        _stockItemFBinding =
            FragmentAddOrEditStockItemBinding.inflate(
                LayoutInflater.from(requireContext()),
                null,
                false
            )

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Добавить новую запись") //Добавление заголовка.
            .setView(_stockItemFBinding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Добавить") { _, _ ->
                val count = try {
                    _stockItemFBinding.itemCountEditText.text.toString().trim().toInt()
                } catch (e: NumberFormatException) {
                    0
                }
                val balance = try {
                    _stockItemFBinding.itemBalanceEditText.text.toString().trim().toInt()
                } catch (e: NumberFormatException) {
                    0
                }
                val name = _stockItemFBinding.itemNameEditText.text.toString().trim()

                _managementViewModel.addStockItemToDatabase(
                    name = name,
                    count = count,
                    balance = balance
                )

                _managementViewModel.transactionStatus.observe(viewLifecycleOwner) { status ->
                    Toast.makeText(requireContext(), status, Toast.LENGTH_SHORT).show()
                }
            }
            .setNeutralButton("Отмена") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun showEditStockItemDialog(stockItem: StockItem) {
        _stockItemFBinding =
            FragmentAddOrEditStockItemBinding.inflate(
                LayoutInflater.from(requireContext()),
                null,
                false
            )

        with(_stockItemFBinding) {
            itemNameEditText.setText(stockItem.name)
            itemCountEditText.setText(stockItem.count.toString())
            itemBalanceEditText.setText(stockItem.balance.toString())
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Редактировать запись") //Добавление заголовка.
            .setView(_stockItemFBinding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Готово") { _, _ ->
                val count = try {
                    _stockItemFBinding.itemCountEditText.text.toString().trim().toInt()
                } catch (e: NumberFormatException) {
                    0
                }
                val balance = try {
                    _stockItemFBinding.itemBalanceEditText.text.toString().trim().toInt()
                } catch (e: NumberFormatException) {
                    0
                }
                val name = _stockItemFBinding.itemNameEditText.text.toString().trim()

                _managementViewModel.updateStockItemInDatabase(
                    id = stockItem.id,
                    name = name,
                    count = count,
                    balance = balance
                )

                _managementViewModel.transactionStatus.observe(viewLifecycleOwner) { status ->
                    Toast.makeText(requireContext(), status, Toast.LENGTH_SHORT).show()
                }
            }
            .setNeutralButton("Отмена") { dialog, _ -> dialog.cancel() }
            .show()
    }

    @SuppressLint("SetTextI18n")
    private fun showInfoAboutStockItemRecordDialog(stockItem: StockItem) {
        _stockItemInfoFBinding =
            FragmentStockItemInfoBinding.inflate(LayoutInflater.from(requireContext()), null, false)

        with(_stockItemInfoFBinding) {
            infoNameStockItem.text = stockItem.name
            infoStartCountStockItem.text = stockItem.count.toString() + " шт."
            infoBalanceStockItem.text = stockItem.balance.toString() + " шт."
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Информация о выдаче") //Добавление заголовка.
            .setView(_stockItemInfoFBinding.root) //Присвоение View полученного ранее.
            .setNegativeButton("Закрыть") { dialog, _ -> dialog.cancel() }
            .show()
    }
}