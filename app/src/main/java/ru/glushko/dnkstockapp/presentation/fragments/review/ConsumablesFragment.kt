package ru.glushko.dnkstockapp.presentation.fragments.review

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.glushko.dnkstockapp.R
import ru.glushko.dnkstockapp.databinding.FragmentAddOrEditItemBinding
import ru.glushko.dnkstockapp.databinding.FragmentConsumablesBinding
import ru.glushko.dnkstockapp.databinding.FragmentItemInfoBinding
import ru.glushko.dnkstockapp.domain.entity.Item
import ru.glushko.dnkstockapp.presentation.viewmodels.ReviewViewModel
import ru.glushko.dnkstockapp.presentation.viewutils.recyclerAdapters.review.ItemRecyclerAdapter
import java.text.SimpleDateFormat

class ConsumablesFragment : Fragment() {

    private lateinit var _consumablesFragmentBinding: FragmentConsumablesBinding
    private lateinit var _addOrEditItemFragmentBinding: FragmentAddOrEditItemBinding
    private lateinit var _itemInfoFragmentBinding: FragmentItemInfoBinding

    private val _reviewViewModel by viewModel<ReviewViewModel>()
    private var _itemRecyclerAdapter = ItemRecyclerAdapter()

    private var _localStockItemsList = listOf<String>() //TODO: Оптимизировать!!!
    private var _localStaffList = listOf<String>() //TODO: Оптимизировать!!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _reviewViewModel.allStockItems.observe(viewLifecycleOwner, {
            _localStockItemsList = it.map { stockItem -> stockItem.name }
        }) //TODO: Оптимизировать!!!

        _reviewViewModel.allStaff.observe(viewLifecycleOwner, {
            _localStaffList = it.map { staff -> staff.surname + " " + staff.name + " " + staff.lastname[0] + "."}
        }) //TODO: Оптимизировать!!!

        Log.d("1", _localStaffList.toString())
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

        val userEditTextAdapter = ArrayAdapter(requireContext(), R.layout.list_item, _localStaffList)
        val nameEditTextAdapter = ArrayAdapter(requireContext(), R.layout.list_item, _localStockItemsList)
        _addOrEditItemFragmentBinding.itemNameEditText.setAdapter(nameEditTextAdapter)
        _addOrEditItemFragmentBinding.itemUserEditText.setAdapter(userEditTextAdapter)

        _addOrEditItemFragmentBinding.itemDateButton.setOnClickListener{
            showDatePickerDialog(_addOrEditItemFragmentBinding.itemDateButton)
        }

        with(_addOrEditItemFragmentBinding) {
            itemNameEditText.setText(item.name)
            itemCountEditText.setText(item.count)
            itemDateButton.text = item.date
            itemUserEditText.setText(item.user)
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Редактировать запись") //Добавление заголовка.
            .setView(_addOrEditItemFragmentBinding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Готово") { _, _ ->
                _reviewViewModel.updateItemInDatabase(
                    id = item.id,
                    name = _addOrEditItemFragmentBinding.itemNameEditText.text.toString(),
                    count = _addOrEditItemFragmentBinding.itemCountEditText.text.toString(),
                    date = _addOrEditItemFragmentBinding.itemDateButton.text.toString(),
                    user = _addOrEditItemFragmentBinding.itemUserEditText.text.toString(),
                    type = "consumables"
                )
                _reviewViewModel.transactionStatus.observe(viewLifecycleOwner, { status ->
                    Toast.makeText(requireContext(), status, Toast.LENGTH_SHORT).show()
                })
            }
            .setNeutralButton("Отмена") { dialog, _ -> dialog.cancel() }
            .show()
    }

    @SuppressLint("SimpleDateFormat")
    private fun showDatePickerDialog(button: Button) {
        val datePickerBuilder: MaterialDatePicker.Builder<*> = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Выберите дату")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())

        val datePicker: MaterialDatePicker<*> = datePickerBuilder
            .build()
        datePicker.show(childFragmentManager, "tag")

        datePicker.addOnPositiveButtonClickListener {
            button.text = SimpleDateFormat("dd/MM/yyyy").format(datePicker.selection)
        }
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

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Информация о выдаче") //Добавление заголовка.
            .setView(_itemInfoFragmentBinding.root) //Присвоение View полученного ранее.
            .setNegativeButton( "Закрыть") { dialog, _ -> dialog.cancel() }
            .show()
    }

}