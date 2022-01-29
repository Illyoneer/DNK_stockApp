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
import ru.glushko.dnkstockapp.databinding.FragmentHardwareBinding
import ru.glushko.dnkstockapp.databinding.FragmentItemInfoBinding
import ru.glushko.dnkstockapp.domain.model.Item
import ru.glushko.dnkstockapp.presentation.viewmodels.ReviewViewModel
import ru.glushko.dnkstockapp.presentation.viewutils.recyclerAdapters.review.ItemRecyclerAdapter
import java.text.SimpleDateFormat
import java.util.*

class HardwareFragment : Fragment() {

    private lateinit var _hardwareFragmentBinding: FragmentHardwareBinding
    private lateinit var itemFBinding: FragmentAddOrEditItemBinding
    private lateinit var itemInfoFBinding: FragmentItemInfoBinding
    private lateinit var _calendar: Calendar
    private lateinit var _dateToday: String

    private val _reviewViewModel by viewModel<ReviewViewModel>()
    private val _itemRecyclerAdapter = ItemRecyclerAdapter()

    private var _localStockItemsList = listOf<String>() //TODO: Оптимизировать!!!
    private var _localStaffList = listOf<String>() //TODO: Оптимизировать!!!

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _calendar = Calendar.getInstance()
        _dateToday = SimpleDateFormat("dd/MM/yyyy").format(_calendar.time)

        _hardwareFragmentBinding =
            FragmentHardwareBinding.inflate(inflater, container, false)

        setupToolbarButtons()
        setupRecyclerView()


        return _hardwareFragmentBinding.root
    }

    private fun setupToolbarButtons() {
        _hardwareFragmentBinding.addButton.setOnClickListener {
            showAddHardwareItemRecordDialog(
                dateToday = _dateToday,
                staffList = _localStaffList,
                stockItemsList = _localStockItemsList
            )
        }

        _hardwareFragmentBinding.sortButton.setOnClickListener {
            showSortPopupMenu(it, R.menu.sort_popup_menu)
        }
    }

    override fun onResume() {
        _reviewViewModel.hardwareItems.observe(viewLifecycleOwner) { hardwareItemsList ->
            _itemRecyclerAdapter.submitList(hardwareItemsList)
        }

        _reviewViewModel.allStockItems.observe(viewLifecycleOwner) {
            _localStockItemsList = it.map { stockItem -> stockItem.name }
        }

        _reviewViewModel.allStaff.observe(viewLifecycleOwner) {
            _localStaffList =
                it.map { staff -> staff.surname + " " + staff.name + " " + staff.lastname[0] + "." }
        }
        super.onResume()
    }

    private fun setupRecyclerView() {
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

    private fun showAddHardwareItemRecordDialog(
        dateToday: String,
        staffList: List<String>,
        stockItemsList: List<String>
    ) {
        itemFBinding =
            FragmentAddOrEditItemBinding.inflate(LayoutInflater.from(requireContext()), null, false)

        val userEditTextAdapter = ArrayAdapter(requireContext(), R.layout.list_item, staffList)
        val nameEditTextAdapter = ArrayAdapter(requireContext(), R.layout.list_item, stockItemsList)
        itemFBinding.itemNameEditText.setAdapter(nameEditTextAdapter)
        itemFBinding.itemUserEditText.setAdapter(userEditTextAdapter)
        itemFBinding.itemDateButton.text = dateToday

        itemFBinding.itemDateButton.setOnClickListener {
            showDatePickerDialog(itemFBinding.itemDateButton)
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Добавить новую запись") //Добавление заголовка.
            .setView(itemFBinding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Добавить") { _, _ ->
                val count = try {
                    itemFBinding.itemCountEditText.text.toString().trim().toInt()
                } catch (e: NumberFormatException) { 0 }
                val name = itemFBinding.itemNameEditText.text.toString()
                val date = itemFBinding.itemDateButton.text.toString().trim()
                val user = itemFBinding.itemUserEditText.text.toString().trim()

                if (_localStockItemsList.contains(name)) {
                    if (_localStaffList.contains(user)) {
                        addItemToDatabase(name = name, count = count, date = date, user = user)
                    } else {
                        showUserQuestionDialog(
                            id = 0,
                            name = name,
                            count = count,
                            date = date,
                            user = user,
                            start_count = 0,
                            action = "add"
                        )
                    }
                } else
                    Toast.makeText(
                        requireContext(),
                        "На складе нет такого предмета!",
                        Toast.LENGTH_SHORT
                    ).show()
            }
            .setNeutralButton("Отмена") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun showEditItemRecordDialog(item: Item) {
        itemFBinding =
            FragmentAddOrEditItemBinding.inflate(LayoutInflater.from(requireContext()), null, false)

        val userEditTextAdapter =
            ArrayAdapter(requireContext(), R.layout.list_item, _localStaffList)
        val nameEditTextAdapter =
            ArrayAdapter(requireContext(), R.layout.list_item, _localStockItemsList)
        itemFBinding.itemNameEditText.setAdapter(nameEditTextAdapter)
        itemFBinding.itemUserEditText.setAdapter(userEditTextAdapter)

        itemFBinding.itemDateButton.setOnClickListener {
            showDatePickerDialog(itemFBinding.itemDateButton)
        }

        with(itemFBinding) {
            itemNameEditText.setText(item.name)
            itemCountEditText.setText(item.count.toString())
            itemDateButton.text = item.date
            itemUserEditText.setText(item.user)
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Редактировать запись") //Добавление заголовка.
            .setView(itemFBinding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Готово") { _, _ ->
                val count = try {
                    itemFBinding.itemCountEditText.text.toString().trim().toInt()
                } catch (e: NumberFormatException) { 0 }
                val name = itemFBinding.itemNameEditText.text.toString()
                val date = itemFBinding.itemDateButton.text.toString().trim()
                val user = itemFBinding.itemUserEditText.text.toString().trim()

                if (_localStockItemsList.contains(name)) {
                    if (_localStaffList.contains(user)) {
                        updateItemInDatabase(
                            id = item.id,
                            name = name,
                            count = count,
                            date = date,
                            user = user,
                            start_count = item.count
                        )
                    } else
                        showUserQuestionDialog(
                            id = item.id,
                            name = name,
                            count = count,
                            date = date,
                            user = user,
                            start_count = item.count,
                            action = "update"
                        )
                } else
                    Toast.makeText(
                        requireContext(),
                        "На складе нет такого предмета!",
                        Toast.LENGTH_SHORT
                    ).show()
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

    private fun showUserQuestionDialog(id: Int, name: String, count: Int, date: String, user: String, start_count: Int, action: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Внимание!")
            .setMessage("Введенного вами пользователя нет в вашей базе. Желаете добавить новую запись?")
            .setPositiveButton("Да") { dialog, _ -> dialog.cancel() }
            .setNeutralButton("Нет") { _, _ ->
                if (action == "add")
                    addItemToDatabase(name = name, count = count, date = date, user = user)
                else
                    updateItemInDatabase(
                        id = id,
                        name = name,
                        count = count,
                        date = date,
                        user = user,
                        start_count = start_count
                    )
            }
            .show()
    }

    private fun updateItemInDatabase(id: Int, name: String, count: Int, date: String, user: String, start_count: Int) {
        _reviewViewModel.updateItemInDatabase(
            id = id,
            name = name,
            count = count,
            date = date,
            user = user,
            type = "hardware",
            start_count = start_count
        )
        _reviewViewModel.transactionStatus.observe(viewLifecycleOwner) { status ->
            Toast.makeText(requireContext(), status, Toast.LENGTH_SHORT).show()
        }
    }

    private fun addItemToDatabase(name: String, count: Int, date: String, user: String) {
        _reviewViewModel.addItemToDatabase(
            name = name,
            count = count,
            date = date,
            user = user,
            type = "hardware"
        )
        _reviewViewModel.transactionStatus.observe(viewLifecycleOwner) { status ->
            Toast.makeText(requireContext(), status, Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showInfoAboutItemRecordDialog(item: Item) {
        itemInfoFBinding =
            FragmentItemInfoBinding.inflate(LayoutInflater.from(requireContext()), null, false)

        with(itemInfoFBinding) {
            infoNameItem.text = item.name
            infoCountItem.text = item.count.toString() + " шт."
            infoDateItem.text = item.date
            infoUserItem.text = item.user
            infoTypeItem.text = "Оборудование"
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Информация о выдаче") //Добавление заголовка.
            .setView(itemInfoFBinding.root) //Присвоение View полученного ранее.
            .setNegativeButton("Закрыть") { dialog, _ -> dialog.cancel() }
            .show()
    }

}