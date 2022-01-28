package ru.glushko.dnkstockapp.presentation.fragments.review

import android.annotation.SuppressLint
import android.os.Bundle
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
import ru.glushko.dnkstockapp.domain.model.Item
import ru.glushko.dnkstockapp.presentation.viewmodels.ReviewViewModel
import ru.glushko.dnkstockapp.presentation.viewutils.recyclerAdapters.review.ItemRecyclerAdapter
import java.text.SimpleDateFormat
import java.util.*

class ConsumablesFragment : Fragment() {

    private lateinit var _consumablesFragmentBinding: FragmentConsumablesBinding
    private lateinit var _addOrEditItemFragmentBinding: FragmentAddOrEditItemBinding
    private lateinit var _itemInfoFragmentBinding: FragmentItemInfoBinding
    private lateinit var _calendar: Calendar
    private lateinit var _dateToday: String

    private val _reviewViewModel by viewModel<ReviewViewModel>()
    private var _itemRecyclerAdapter = ItemRecyclerAdapter()
    private var _localStockItemsList = listOf<String>() //TODO: Оптимизировать!!!
    private var _localStaffList = listOf<String>() //TODO: Оптимизировать!!!

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _calendar = Calendar.getInstance()
        _dateToday = SimpleDateFormat("dd/MM/yyyy").format(_calendar.time)

        _consumablesFragmentBinding =
            FragmentConsumablesBinding.inflate(inflater, container, false)

        setupToolbarButtons()
        setupRecyclerView()

        return _consumablesFragmentBinding.root
    }

    override fun onStart() {
        _reviewViewModel.consumablesItems.observe(viewLifecycleOwner) { consumablesItemsList ->
            _itemRecyclerAdapter.submitList(consumablesItemsList)
        }

        _reviewViewModel.allStockItems.observe(viewLifecycleOwner) {
            _localStockItemsList = it.map { stockItem -> stockItem.name }
        }

        _reviewViewModel.allStaff.observe(viewLifecycleOwner) {
            _localStaffList =
                it.map { staff -> staff.surname + " " + staff.name + " " + staff.lastname[0] + "." }
        }
        super.onStart()
    }

    private fun setupToolbarButtons() {
        _consumablesFragmentBinding.addButton.setOnClickListener {
            showAddConsumablesItemRecordDialog(
                dateToday = _dateToday,
                staffList = _localStaffList,
                stockItemsList = _localStockItemsList
            )
        }

        _consumablesFragmentBinding.sortButton.setOnClickListener {
            showSortPopupMenu(it, R.menu.sort_popup_menu)
        }
    }

    private fun setupRecyclerView() {
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
            showActionPopupMenu(view, item, R.menu.review_action_popup_menu)
        }
    }

    private fun showSortPopupMenu(view: View, @MenuRes menuRes: Int) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.menuInflater.inflate(menuRes, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.sort_by_date -> {
                    Toast.makeText(requireContext(), "Сортировка по дате", Toast.LENGTH_SHORT)
                        .show()
                }
                R.id.sort_by_item_name -> {
                    Toast.makeText(requireContext(), "Сортировка по названию", Toast.LENGTH_SHORT)
                        .show()
                }
                R.id.sort_by_surname -> {
                    Toast.makeText(requireContext(), "Сортировка по фамилии", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
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
                R.id.archive_action -> {
                    _reviewViewModel.moveItemToArchive(item = itemElement, dateToday = _dateToday)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    @SuppressLint("SimpleDateFormat")
    private fun showAddConsumablesItemRecordDialog(
        dateToday: String,
        staffList: List<String>,
        stockItemsList: List<String>
    ) {
        _addOrEditItemFragmentBinding =
            FragmentAddOrEditItemBinding.inflate(LayoutInflater.from(requireContext()), null, false)

        val userEditTextAdapter = ArrayAdapter(requireContext(), R.layout.list_item, staffList)
        val nameEditTextAdapter = ArrayAdapter(requireContext(), R.layout.list_item, stockItemsList)
        _addOrEditItemFragmentBinding.itemNameEditText.setAdapter(nameEditTextAdapter)
        _addOrEditItemFragmentBinding.itemUserEditText.setAdapter(userEditTextAdapter)
        _addOrEditItemFragmentBinding.itemDateButton.text = dateToday

        _addOrEditItemFragmentBinding.itemDateButton.setOnClickListener {
            showDatePickerDialog(_addOrEditItemFragmentBinding.itemDateButton)
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Добавить новую запись") //Добавление заголовка.
            .setView(_addOrEditItemFragmentBinding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Добавить") { _, _ ->
                if (_localStockItemsList.contains(_addOrEditItemFragmentBinding.itemNameEditText.text.toString())) {
                    _reviewViewModel.addItemToDatabase(
                        name = _addOrEditItemFragmentBinding.itemNameEditText.text.toString(),
                        count = Integer.parseInt(_addOrEditItemFragmentBinding.itemCountEditText.text.toString()),
                        date = _addOrEditItemFragmentBinding.itemDateButton.text.toString(),
                        user = _addOrEditItemFragmentBinding.itemUserEditText.text.toString(),
                        type = "consumables"
                    )
                } else
                    Toast.makeText(requireContext(), "На складе нет такого предмета!", Toast.LENGTH_SHORT).show()

                _reviewViewModel.transactionStatus.observe(viewLifecycleOwner) { status ->
                    Toast.makeText(requireContext(), status, Toast.LENGTH_SHORT).show()
                }
            }
            .setNeutralButton("Отмена") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun showEditItemRecordDialog(item: Item) {
        _addOrEditItemFragmentBinding =
            FragmentAddOrEditItemBinding.inflate(LayoutInflater.from(requireContext()), null, false)

        val userEditTextAdapter =
            ArrayAdapter(requireContext(), R.layout.list_item, _localStaffList)
        val nameEditTextAdapter =
            ArrayAdapter(requireContext(), R.layout.list_item, _localStockItemsList)
        _addOrEditItemFragmentBinding.itemNameEditText.setAdapter(nameEditTextAdapter)
        _addOrEditItemFragmentBinding.itemUserEditText.setAdapter(userEditTextAdapter)

        _addOrEditItemFragmentBinding.itemDateButton.setOnClickListener {
            showDatePickerDialog(_addOrEditItemFragmentBinding.itemDateButton)
        }

        with(_addOrEditItemFragmentBinding) {
            itemNameEditText.setText(item.name)
            itemCountEditText.setText(item.count.toString())
            itemDateButton.text = item.date
            itemUserEditText.setText(item.user)
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Редактировать запись") //Добавление заголовка.
            .setView(_addOrEditItemFragmentBinding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Готово") { _, _ ->
                if (_localStockItemsList.contains(_addOrEditItemFragmentBinding.itemNameEditText.text.toString())) {
                    _reviewViewModel.updateItemInDatabase(
                        id = item.id,
                        name = _addOrEditItemFragmentBinding.itemNameEditText.text.toString(),
                        count = Integer.parseInt(_addOrEditItemFragmentBinding.itemCountEditText.text.toString()),
                        date = _addOrEditItemFragmentBinding.itemDateButton.text.toString(),
                        user = _addOrEditItemFragmentBinding.itemUserEditText.text.toString(),
                        type = "consumables",
                        start_count = item.count
                    )
                } else
                    Toast.makeText(requireContext(), "На складе нет такого предмета!", Toast.LENGTH_SHORT).show()

                _reviewViewModel.transactionStatus.observe(viewLifecycleOwner) { status ->
                    Toast.makeText(requireContext(), status, Toast.LENGTH_SHORT).show()
                }
            }
            .setNeutralButton("Отмена") { dialog, _ -> dialog.cancel() }
            .show()
    }

    @SuppressLint("SimpleDateFormat")
    private fun showDatePickerDialog(button: Button) {
        val datePickerBuilder: MaterialDatePicker.Builder<*> =
            MaterialDatePicker.Builder.datePicker()
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
            infoCountItem.text = item.count.toString() + " шт."
            infoDateItem.text = item.date
            infoUserItem.text = item.user
            infoTypeItem.text = "Расходник"
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Информация о выдаче") //Добавление заголовка.
            .setView(_itemInfoFragmentBinding.root) //Присвоение View полученного ранее.
            .setNegativeButton("Закрыть") { dialog, _ -> dialog.cancel() }
            .show()
    }
}