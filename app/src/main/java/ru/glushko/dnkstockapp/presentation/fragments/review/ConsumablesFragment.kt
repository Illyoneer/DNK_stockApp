package ru.glushko.dnkstockapp.presentation.fragments.review

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.glushko.dnkstockapp.R
import ru.glushko.dnkstockapp.databinding.FragmentAddOrEditItemBinding
import ru.glushko.dnkstockapp.databinding.FragmentConsumablesBinding
import ru.glushko.dnkstockapp.databinding.FragmentItemInfoBinding
import ru.glushko.dnkstockapp.domain.Item
import ru.glushko.dnkstockapp.presentation.viewmodels.ReviewViewModel
import ru.glushko.dnkstockapp.presentation.viewutils.recyclerAdapters.review.ItemRecyclerAdapter

class ConsumablesFragment : Fragment() {

    private lateinit var _consumablesFragmentBinding: FragmentConsumablesBinding
    private lateinit var _addOrEditItemFragmentBinding: FragmentAddOrEditItemBinding
    private lateinit var _itemInfoFragmentBinding: FragmentItemInfoBinding

    private val _reviewViewModel by viewModel<ReviewViewModel>()
    private var _itemRecyclerAdapter = ItemRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _consumablesFragmentBinding =
            FragmentConsumablesBinding.inflate(inflater, container, false)

        setupRecyclerView()

        return _consumablesFragmentBinding.root
    }

    private fun setupRecyclerView() {
        _reviewViewModel.consumablesItems.observe(viewLifecycleOwner, { consumablesItemsList ->
            _itemRecyclerAdapter.submitList(consumablesItemsList)
        })

        _consumablesFragmentBinding.recyclerView.adapter = _itemRecyclerAdapter

        setupOnHolderViewClick(_itemRecyclerAdapter)
        setupOnActionButtonClick(_itemRecyclerAdapter)
    }

    private fun setupOnHolderViewClick(itemRecyclerAdapter: ItemRecyclerAdapter) {
        itemRecyclerAdapter.onHolderViewClickListener = { item ->
            showInfoAboutItemRecordDialog(item)
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
                    showEditItemRecordDialog(item = itemElement)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    private fun showEditItemRecordDialog(item: Item) {
        _addOrEditItemFragmentBinding =
            FragmentAddOrEditItemBinding.inflate(LayoutInflater.from(requireContext()), null, false)

        with(_addOrEditItemFragmentBinding) {
            itemNameEditText.setText(item.name)
            itemCountEditText.setText(item.count)
            itemDateEditText.setText(item.date)
            itemUserEditText.setText(item.user)
        }

        AlertDialog.Builder(requireContext(), R.style.MyAlertDialogRoundedTheme)
            .setTitle("Редактировать запись") //Добавление заголовка.
            .setView(_addOrEditItemFragmentBinding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Готово") { _, _ ->

                _reviewViewModel.updateItemInDatabase(
                    id = item.id,
                    name = _addOrEditItemFragmentBinding.itemNameEditText.text.toString(),
                    count = _addOrEditItemFragmentBinding.itemCountEditText.text.toString(),
                    date = _addOrEditItemFragmentBinding.itemDateEditText.text.toString(),
                    user = _addOrEditItemFragmentBinding.itemUserEditText.text.toString(),
                    type = "consumables"
                )

                _reviewViewModel.transactionStatus.observe(viewLifecycleOwner, { status ->
                    Toast.makeText(requireContext(), status, Toast.LENGTH_SHORT).show()
                })
            }
            .setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }
            .show()
    }

    @SuppressLint("SetTextI18n")
    private fun showInfoAboutItemRecordDialog(item: Item) {
        _itemInfoFragmentBinding =
            FragmentItemInfoBinding.inflate(LayoutInflater.from(requireContext()), null, false)

        with(_itemInfoFragmentBinding) {
            infoNameItem.text = item.name
            infoCountItem.text = item.count + " шт."
            infoDateItem.text = item.date
            infoUserItem.text = item.user
            infoTypeItem.text = "Расходник"
        }

        AlertDialog.Builder(requireContext(), R.style.MyAlertDialogRoundedTheme)
            .setTitle("Информация о выдаче") //Добавление заголовка.
            .setView(_itemInfoFragmentBinding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Закрыть") { dialog, _ -> dialog.cancel() }
            .show()
    }

}