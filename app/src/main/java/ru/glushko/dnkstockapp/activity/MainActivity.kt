package ru.glushko.dnkstockapp.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.glushko.dnkstockapp.R
import ru.glushko.dnkstockapp.databinding.ActivityMainBinding
import ru.glushko.dnkstockapp.databinding.FragmentAddOrEditItemBinding
import ru.glushko.dnkstockapp.databinding.FragmentItemInfoBinding
import ru.glushko.dnkstockapp.model.Item
import ru.glushko.dnkstockapp.utils.Status
import ru.glushko.dnkstockapp.utils.recyclerAdapter.ItemRecyclerAdapter
import ru.glushko.dnkstockapp.viewmodels.AddOrEditItemViewModel
import ru.glushko.dnkstockapp.viewmodels.MainViewModel
import android.view.Gravity

import android.widget.TextView




class MainActivity : AppCompatActivity() {

    private lateinit var _mainActivityBinding: ActivityMainBinding
    private lateinit var _mainViewModel: MainViewModel
    private val DELETE_ITEM = 0
    private val EDIT_ITEM = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        _mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        _mainActivityBinding.mainVM = _mainViewModel

        setupRecyclerView()

        _mainActivityBinding.addItemRecordButton.setOnClickListener {
            showAddItemRecordDialog()
        }
    }

    private fun setupRecyclerView() {
        val itemRecyclerAdapter = ItemRecyclerAdapter()

        _mainViewModel.getItemsLiveData().observe(this, {
            itemRecyclerAdapter.submitList(it)
        })

        _mainActivityBinding.recyclerView.adapter = itemRecyclerAdapter

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

    private fun showAddItemRecordDialog() {
        val binding: FragmentAddOrEditItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this), R.layout.fragment_add_or_edit_item,
            null, false)
        val addItemFragmentViewModel =
            ViewModelProvider(this).get(AddOrEditItemViewModel::class.java)

        binding.addItemVM = addItemFragmentViewModel

        AlertDialog.Builder(this, R.style.MyAlertDialogRoundedTheme)
            .setTitle("Добавить новую запись") //Добавление заголовка.
            .setView(binding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Добавить") { _, _ ->
                _mainViewModel.addItemToDatabase(
                    binding.itemNameEditText.text.toString(),
                    binding.itemCountEditText.text.toString(),
                    binding.itemDateEditText.text.toString(),
                    binding.itemUserEditText.text.toString()
                )

                _mainViewModel.getStateAddItemLiveData().observe(this, {
                    when (it!!) {
                        Status.ERROR -> Snackbar.make(
                            _mainActivityBinding.root, "Введите все данные.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        Status.SUCCESS -> Snackbar.make(
                            _mainActivityBinding.root, "Запись успешно добавлена!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                })
            }.setNegativeButton("Отмена"){dialog, _ -> dialog.cancel()}.show()
    }

    private fun showEditItemRecordDialog(item:Item) {
        val binding: FragmentAddOrEditItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this), R.layout.fragment_add_or_edit_item,
            null, false)
        val addOrEditItemViewModel =
            ViewModelProvider(this).get(AddOrEditItemViewModel::class.java)

        binding.addItemVM = addOrEditItemViewModel

        with(binding){
            itemNameEditText.setText(item.name)
            itemCountEditText.setText(item.count)
            itemDateEditText.setText(item.date)
            itemUserEditText.setText(item.user)
        }

        AlertDialog.Builder(this, R.style.MyAlertDialogRoundedTheme)
            .setTitle("Редактировать запись") //Добавление заголовка.
            .setView(binding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Готово") { _, _ ->

                _mainViewModel.updateItemInDatabase(
                    item.id,
                    binding.itemNameEditText.text.toString(),
                    binding.itemCountEditText.text.toString(),
                    binding.itemDateEditText.text.toString(),
                    binding.itemUserEditText.text.toString()
                )

                _mainViewModel.getStateEditItemLiveData().observe(this, {
                    when (it!!) {
                        Status.ERROR -> Snackbar.make(
                            _mainActivityBinding.root, "Ошибка!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        Status.SUCCESS -> Snackbar.make(
                            _mainActivityBinding.root, "Запись успешно обновлена!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                })
            }.setNegativeButton("Отмена"){dialog, _ -> dialog.cancel()}.show()
    }

    @SuppressLint("SetTextI18n")
    private fun showInfoAboutItemRecordDialog(item:Item) {
        val binding: FragmentItemInfoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this), R.layout.fragment_item_info,
            null, false)

        with(binding){
            infoNameItem.text = item.name
            infoCountItem.text = item.count + " шт."
            infoDateItem.text = item.date
            infoUserItem.text = item.user
        }

        AlertDialog.Builder(this, R.style.MyAlertDialogRoundedTheme)
            .setTitle("Информация о выдаче") //Добавление заголовка.
            .setView(binding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Закрыть") { dialog, _ -> dialog.cancel() }.show()
    }
}