package ru.glushko.dnkstockapp.presentation.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.glushko.dnkstockapp.R
import ru.glushko.dnkstockapp.databinding.ActivityMainBinding
import ru.glushko.dnkstockapp.databinding.FragmentAddOrEditItemBinding
import ru.glushko.dnkstockapp.databinding.FragmentItemInfoBinding
import ru.glushko.dnkstockapp.domain.Item
import ru.glushko.dnkstockapp.presentation.viewmodels.AddOrEditItemViewModel
import ru.glushko.dnkstockapp.presentation.viewmodels.MainViewModel
import ru.glushko.dnkstockapp.presentation.viewutils.Status
import ru.glushko.dnkstockapp.presentation.viewutils.recyclerAdapter.ItemRecyclerAdapter


class MainActivity : AppCompatActivity() {

    private lateinit var _consumablesFragmentBinding: ActivityMainBinding
    private val _consumablesViewModel by viewModel<MainViewModel>()
    private val _types = arrayListOf("Расходник", "Оборудование")
    private lateinit var _spinnerAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _consumablesFragmentBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        _consumablesFragmentBinding.mainVM = _consumablesViewModel

        setupRecyclerView()

        _consumablesFragmentBinding.addItemRecordButton.setOnClickListener {
            showAddItemRecordDialog()
        }

        _spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            _types
        )
        _spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    private fun setupRecyclerView() {
        val itemRecyclerAdapter = ItemRecyclerAdapter()

        _consumablesViewModel.getItemsList().observe(this, {
            itemRecyclerAdapter.submitList(it)
        })

        _consumablesFragmentBinding.recyclerView.adapter = itemRecyclerAdapter

        setupOnHolderViewClick(itemRecyclerAdapter)
        setupOnActionButtonClick(itemRecyclerAdapter)
    }

    private fun setupOnHolderViewClick(itemRecyclerAdapter: ItemRecyclerAdapter) {
        itemRecyclerAdapter.onHolderViewClickListener = {
            showInfoAboutItemRecordDialog(it)
        }
    }

    private fun setupOnActionButtonClick(itemRecyclerAdapter: ItemRecyclerAdapter) {
        itemRecyclerAdapter.onPopupButtonClickListener = {
            item, view ->
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
                    _consumablesViewModel.deleteItemFromDatabase(itemElement)
                }
                EDIT_ITEM -> {
                    showEditItemRecordDialog(itemElement)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    private fun showAddItemRecordDialog() {
        val binding: FragmentAddOrEditItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this), R.layout.fragment_add_or_edit_item,
            null, false)
        val addItemFragmentViewModel =
            ViewModelProvider(this)[AddOrEditItemViewModel::class.java]

        binding.addItemVM = addItemFragmentViewModel
        binding.itemTypeSpinner.adapter = _spinnerAdapter

        AlertDialog.Builder(this, R.style.MyAlertDialogRoundedTheme)
            .setTitle("Добавить новую запись") //Добавление заголовка.
            .setView(binding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Добавить") { _, _ ->
                _consumablesViewModel.addItemToDatabase(
                    binding.itemNameEditText.text.toString(),
                    binding.itemCountEditText.text.toString(),
                    binding.itemDateEditText.text.toString(),
                    binding.itemUserEditText.text.toString(),
                    binding.itemTypeSpinner.selectedItem.toString()
                )

                _consumablesViewModel.getStateAddItemLiveData().observe(this, {
                    when (it!!) {
                        Status.ERROR -> Snackbar.make(
                            _consumablesFragmentBinding.root, "Введите все данные.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        Status.SUCCESS -> Snackbar.make(
                            _consumablesFragmentBinding.root, "Запись успешно добавлена!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                })
            }.setNegativeButton("Отмена"){dialog, _ -> dialog.cancel()}.show()
    }

    private fun showEditItemRecordDialog(item: Item) {
        val binding: FragmentAddOrEditItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this), R.layout.fragment_add_or_edit_item,
            null, false)
        val addOrEditItemViewModel =
            ViewModelProvider(this)[AddOrEditItemViewModel::class.java]

        binding.addItemVM = addOrEditItemViewModel
        binding.itemTypeSpinner.adapter = _spinnerAdapter

        with(binding){
            itemNameEditText.setText(item.name)
            itemCountEditText.setText(item.count)
            itemDateEditText.setText(item.date)
            itemUserEditText.setText(item.user)
            if(item.type == "Расходник")
                itemTypeSpinner.setSelection(0)
            else
                itemTypeSpinner.setSelection(1)
        }

        AlertDialog.Builder(this, R.style.MyAlertDialogRoundedTheme)
            .setTitle("Редактировать запись") //Добавление заголовка.
            .setView(binding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Готово") { _, _ ->

                _consumablesViewModel.updateItemInDatabase(
                    item.id,
                    binding.itemNameEditText.text.toString(),
                    binding.itemCountEditText.text.toString(),
                    binding.itemDateEditText.text.toString(),
                    binding.itemUserEditText.text.toString(),
                    binding.itemTypeSpinner.selectedItem.toString()
                )

                _consumablesViewModel.getStateEditItemLiveData().observe(this, {
                    when (it!!) {
                        Status.ERROR -> Snackbar.make(
                            _consumablesFragmentBinding.root, "Ошибка!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        Status.SUCCESS -> Snackbar.make(
                            _consumablesFragmentBinding.root, "Запись успешно обновлена!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                })
            }.setNegativeButton("Отмена"){dialog, _ -> dialog.cancel()}.show()
    }

    @SuppressLint("SetTextI18n")
    private fun showInfoAboutItemRecordDialog(item: Item) {
        val binding: FragmentItemInfoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this), R.layout.fragment_item_info,
            null, false)

        with(binding){
            infoNameItem.text = item.name
            infoCountItem.text = item.count + " шт."
            infoDateItem.text = item.date
            infoUserItem.text = item.user
            infoTypeItem.text = item.type
        }

        AlertDialog.Builder(this, R.style.MyAlertDialogRoundedTheme)
            .setTitle("Информация о выдаче") //Добавление заголовка.
            .setView(binding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Закрыть") { dialog, _ -> dialog.cancel() }.show()
    }

    companion object {
        private const val EDIT_ITEM = 1
        private const val DELETE_ITEM = 0
    }
}