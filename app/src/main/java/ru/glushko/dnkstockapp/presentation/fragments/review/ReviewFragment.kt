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
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.glushko.dnkstockapp.R
import ru.glushko.dnkstockapp.databinding.FragmentAddOrEditItemBinding
import ru.glushko.dnkstockapp.databinding.FragmentReviewBinding
import ru.glushko.dnkstockapp.presentation.viewmodels.ReviewViewModel
import ru.glushko.dnkstockapp.presentation.viewutils.tabAdapters.ReviewTabAdapter
import java.text.SimpleDateFormat
import java.util.*


class ReviewFragment: Fragment() {

    private lateinit var _reviewFragmentBinding: FragmentReviewBinding
    private lateinit var _addOrEditItemBinding: FragmentAddOrEditItemBinding
    private lateinit var _calendar: Calendar
    private lateinit var _dateToday: String
    private val _reviewViewModel by viewModel<ReviewViewModel>()

    private var _stockItemsList = listOf<String>()
    private var _staffList = listOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _reviewFragmentBinding = FragmentReviewBinding.inflate(inflater, container, false)
        _calendar = Calendar.getInstance()
        _dateToday = SimpleDateFormat("dd/MM/yyyy").format(_calendar.time)

        _reviewViewModel.allStockItems.observe(viewLifecycleOwner, {
            _stockItemsList = it.map { stockItem -> stockItem.name }
        })

        _reviewViewModel.allStaff.observe(viewLifecycleOwner, {
            _staffList = it.map { staff -> staff.surname + " " + staff.name + " " + staff.lastname[0] + "."}
        })

        val pagerAdapter = ReviewTabAdapter(this)
        _reviewFragmentBinding.viewPager2.adapter = pagerAdapter

        TabLayoutMediator(
            _reviewFragmentBinding.tabLayout,
            _reviewFragmentBinding.viewPager2
        ) { tab, position ->
            val tabNames = listOf("Расходники", "Оборудование")
            tab.text = tabNames[position]
        }.attach()

        setupToolbarButtons()

        return _reviewFragmentBinding.root
    }

    private fun setupToolbarButtons() {
        _reviewFragmentBinding.addButton.setOnClickListener {
            if (_reviewFragmentBinding.viewPager2.currentItem == 0)
                showAddConsumablesItemRecordDialog()
            else
                showAddHardwareItemRecordDialog()
        }

        _reviewFragmentBinding.sortButton.setOnClickListener {
            showSortPopupMenu(it, R.menu.sort_popup_menu)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun showAddConsumablesItemRecordDialog() {
        _addOrEditItemBinding =
            FragmentAddOrEditItemBinding.inflate(LayoutInflater.from(requireContext()), null, false)

        val userEditTextAdapter = ArrayAdapter(requireContext(), R.layout.list_item, _staffList)
        val nameEditTextAdapter = ArrayAdapter(requireContext(), R.layout.list_item, _stockItemsList)
        _addOrEditItemBinding.itemNameEditText.setAdapter(nameEditTextAdapter)
        _addOrEditItemBinding.itemUserEditText.setAdapter(userEditTextAdapter)
        _addOrEditItemBinding.itemDateButton.text = _dateToday

        _addOrEditItemBinding.itemDateButton.setOnClickListener{
            showDatePickerDialog(_addOrEditItemBinding.itemDateButton)
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Добавить новую запись") //Добавление заголовка.
            .setView(_addOrEditItemBinding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Добавить") { _, _ ->
                _reviewViewModel.addItemToDatabase(
                    name = _addOrEditItemBinding.itemNameEditText.text.toString(),
                    count = _addOrEditItemBinding.itemCountEditText.text.toString(),
                    date = _addOrEditItemBinding.itemDateButton.text.toString(),
                    user = _addOrEditItemBinding.itemUserEditText.text.toString(),
                    type = "consumables"
                )

                _reviewViewModel.transactionStatus.observe(viewLifecycleOwner, { status ->
                    Toast.makeText(requireContext(), status, Toast.LENGTH_SHORT).show()
                })
            }
            .setNeutralButton("Отмена") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun showAddHardwareItemRecordDialog() {
        _addOrEditItemBinding =
            FragmentAddOrEditItemBinding.inflate(LayoutInflater.from(requireContext()), null, false)

        val userEditTextAdapter = ArrayAdapter(requireContext(), R.layout.list_item, _staffList)
        val nameEditTextAdapter = ArrayAdapter(requireContext(), R.layout.list_item, _stockItemsList)
        _addOrEditItemBinding.itemNameEditText.setAdapter(nameEditTextAdapter)
        _addOrEditItemBinding.itemUserEditText.setAdapter(userEditTextAdapter)
        _addOrEditItemBinding.itemDateButton.text = _dateToday

        _addOrEditItemBinding.itemDateButton.setOnClickListener{
            showDatePickerDialog(_addOrEditItemBinding.itemDateButton)
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Добавить новую запись") //Добавление заголовка.
            .setView(_addOrEditItemBinding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Добавить") { _, _ ->
                _reviewViewModel.addItemToDatabase(
                    name = _addOrEditItemBinding.itemNameEditText.text.toString(),
                    count = _addOrEditItemBinding.itemCountEditText.text.toString(),
                    date = _addOrEditItemBinding.itemDateButton.text.toString(),
                    user = _addOrEditItemBinding.itemUserEditText.text.toString(),
                    type = "hardware"
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
        val datePickerBuilder:MaterialDatePicker.Builder<*> = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Выберите дату")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())

        val datePicker: MaterialDatePicker<*> = datePickerBuilder
            .build()
        datePicker.show(childFragmentManager, "tag")

        datePicker.addOnPositiveButtonClickListener {
            button.text = SimpleDateFormat("dd/MM/yyyy").format(datePicker.selection)
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
}